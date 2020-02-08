package Stats;

import GUI.WrappedText;
import Sound.SoundManager;
import Utility.Util;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.Serializable;
import java.util.*;

import static Stats.StatType.*;
import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class PlayerStats extends Stats implements Serializable {

    //-----KEEP TRACK OF KILLS AND TIERS----
    public HashMap<Integer, Integer> healthKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> manaKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> vitalityKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> attackKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> wisdomKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> defenceKills = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> speedKills = new HashMap<Integer, Integer>();

    private int baseHealth = 100, baseMana = 100;
    public int baseVitality = 5, baseAttack = 1, baseWisdom = 5, baseDefence = 1;

    private long randomSeed;

    private float baseSpeed = 2;

    private int totalKills = 0;

    public PlayerStats() {
        calcAllStats();
        health = baseHealth;
        mana = baseMana;
    }

    public void addPack(StatType stat, int tier) {
        SoundManager.playSound("HEAL");
        switch(stat) {
            case HEALTH:
                health = game.constrain(health + tier * 10, 0, healthMax);
                break;
            case MANA:
                mana = game.constrain(mana + tier * 10, 0, manaMax);
                break;
        }
    }

    public void addOrbStat(StatType stat, int tier) {
        SoundManager.playSound("ORB");
        totalKills ++;
        int newVal;
        int colour = statColours.get(stat);
        switch(stat) {
            case HEALTH:
                healthKills.put(tier, healthKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(healthKills, baseHealth, 5, 0.5f);
                if(healthMax != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Health leveled up", engine.player.x, engine.player.y, 1f, colour);
                    healthMax = newVal;
                }
                break;
            case MANA:
                manaKills.put(tier, manaKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(manaKills, baseMana, 5, 0.2f);
                if(manaMax != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Mana leveled up", engine.player.x, engine.player.y, 1f, colour);
                    manaMax = newVal;
                }
                break;
            case VITALITY:
                vitalityKills.put(tier, vitalityKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(vitalityKills, baseVitality, 1, 0.1f);
                if(vitality != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Vitality leveled up", engine.player.x, engine.player.y, 1f, colour);
                    vitality = newVal;
                }
                break;
            case ATTACK:
                attackKills.put(tier, attackKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(attackKills, baseAttack, 1, 0.1f);
                if(attack != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Attack leveled up", engine.player.x, engine.player.y, 1f, colour);
                    attack = newVal;
                }
                break;
            case WISDOM:
                wisdomKills.put(tier, wisdomKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(wisdomKills, baseWisdom, 1, 0.1f);
                if(wisdom != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Wisdom leveled up", engine.player.x, engine.player.y, 1f, colour);
                    wisdom = newVal;
                }
                break;
            case DEFENCE:
                defenceKills.put(tier, defenceKills.getOrDefault(tier, 0) + 1);
                newVal = (int)calcStatValue(defenceKills, baseDefence, 1, 0.1f);
                if(defence != newVal) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Defence leveled up", engine.player.x, engine.player.y, 1f, colour);
                    defence = newVal;
                }
                break;
            case SPEED:
                speedKills.put(tier, speedKills.getOrDefault(tier, 0) + 1);
                float newSpeed = calcStatValue(speedKills, baseSpeed, 1, 0.1f);
                if(speed != newSpeed) {
                    SoundManager.playSound("LEVEL_UP");
                    engine.addText("Speed leveled up", engine.player.x, engine.player.y, 1f, colour);
                    speed = newSpeed;
                }
                break;
        }
    }

    public void calcAllStats() {
        healthMax = (int)calcStatValue(healthKills, baseHealth, 5, 0.5f);
        manaMax = (int)calcStatValue(manaKills, baseMana, 5, 0.2f);
        vitality = (int)calcStatValue(vitalityKills, baseVitality, 1, 0.1f);
        attack = (int)calcStatValue(attackKills, baseAttack, 1, 0.1f);
        wisdom = (int)calcStatValue(wisdomKills, baseWisdom, 1, 0.1f);
        defence = (int)calcStatValue(defenceKills, baseDefence, 1, 0.1f);
        speed = calcStatValue(speedKills, baseSpeed, 1, 0.1f);
    }

    public float calcStatValue(HashMap<Integer, Integer> stat, float base, int max, float rate) {
        float value = base;
        for(int tier : stat.keySet()) {
            value += calcStatTierValue(max, rate, stat.get(tier)) * (tier + 1);
        }
        return value;
    }

    private float calcStatTierValue(int max, float rate, int num) {
        return (-max * game.exp(-rate * num) + max);
    }

    public int getTotalKills() {
        return totalKills;
    }

    @Override
    public String toString() {
        String text = "";

        text += " Health: " + this.getHealthMax();
        text += " Mana: " + this.getManaMax();
        text += " Attack: " + this.getAttack();
        text += " Defence: " + this.getDefence();
        text += " Vitality: " + this.getVitality();
        text += " Wisdom: " + this.getWisdom();
        text += " Speed: " + (int)(100 * this.getSpeed());


        return text;

    }

    public void show(PGraphics screen, float x, float y) {
        PImage attackSprite = itemSprites.get("ATTACK_ICON");
        PImage defenceSprite = itemSprites.get("DEFENCE_ICON");
        PImage vitalitySprite = itemSprites.get("VITALITY_ICON");
        PImage wisdomSprite = itemSprites.get("WISDOM_ICON");
        PImage speedSprite = itemSprites.get("SPEED_ICON");

        screen.pushMatrix();
        screen.translate(x, y);

        screen.noStroke();


        //Stat progress
        float attackFloat = calcStatValue(attackKills, baseAttack, 1, 0.1f);
        attackFloat = attackFloat % 1;
        screen.fill(150, 150, 150);
        screen.rect(0, 0, SPRITE_SIZE/2, SPRITE_SIZE * 2);
        screen.fill(statColours.get(ATTACK));
        screen.rect(0, SPRITE_SIZE * 2, SPRITE_SIZE/2, - SPRITE_SIZE * 2 * attackFloat);

        float defenceFloat = calcStatValue(defenceKills, baseDefence, 1, 0.1f);
        defenceFloat = defenceFloat % 1;
        screen.fill(150, 150, 150);
        screen.rect(TILE_SIZE * 3/2, 0, SPRITE_SIZE/2, SPRITE_SIZE * 2);
        screen.fill(statColours.get(DEFENCE));
        screen.rect(TILE_SIZE * 3/2, SPRITE_SIZE * 2, SPRITE_SIZE/2, -SPRITE_SIZE * 2 * defenceFloat);

        float vitalityFloat = calcStatValue(vitalityKills, baseVitality, 1, 0.1f);
        vitalityFloat = vitalityFloat % 1;
        screen.fill(150, 150, 150);
        screen.rect(0, gui.buff + TILE_SIZE/2, SPRITE_SIZE/2, SPRITE_SIZE * 2);
        screen.fill(statColours.get(VITALITY));
        screen.rect(0, gui.buff + TILE_SIZE/2 + SPRITE_SIZE * 2, SPRITE_SIZE/2, - SPRITE_SIZE * 2 * vitalityFloat);

        float wisdomFloat = calcStatValue(wisdomKills, baseWisdom, 1, 0.1f);
        wisdomFloat = wisdomFloat % 1;
        screen.fill(150, 150, 150);
        screen.rect(TILE_SIZE * 3/2, gui.buff + TILE_SIZE/2, SPRITE_SIZE/2, SPRITE_SIZE * 2);
        screen.fill(statColours.get(WISDOM));
        screen.rect(TILE_SIZE * 3/2, gui.buff + TILE_SIZE/2 + SPRITE_SIZE * 2, SPRITE_SIZE/2, - SPRITE_SIZE * 2 * wisdomFloat);

        screen.textAlign(game.LEFT);
        screen.textSize(TILE_SIZE/2);
        screen.fill(30);

        //Draw stat values
        screen.text(attack, TILE_SIZE, SPRITE_SIZE * 3/2);
        screen.text(defence, TILE_SIZE * 5/2, SPRITE_SIZE * 3/2);
        screen.text(vitality, TILE_SIZE, gui.buff + SPRITE_SIZE * 3/2 + TILE_SIZE/2);
        screen.text(wisdom, TILE_SIZE * 5/2, gui.buff + SPRITE_SIZE * 3/2 + TILE_SIZE/2);
        screen.text((int)(speed * 100), TILE_SIZE, 2 * gui.buff + SPRITE_SIZE * 3/2 + TILE_SIZE);

        //Draw stat sprites
        screen.image(attackSprite, TILE_SIZE/2 - gui.buff * 2, 0, attackSprite.width * 2, attackSprite.height * 2);
        screen.image(defenceSprite, TILE_SIZE * 2 - gui.buff * 2, 0, defenceSprite.width * 2, defenceSprite.height * 2);
        screen.image(vitalitySprite, TILE_SIZE/2 - gui.buff * 2, gui.buff + TILE_SIZE / 2, vitalitySprite.width * 2, vitalitySprite.height * 2);
        screen.image(wisdomSprite, TILE_SIZE * 2 - gui.buff * 2, gui.buff + TILE_SIZE / 2, wisdomSprite.width * 2, wisdomSprite.height * 2);
        screen.image(speedSprite, TILE_SIZE/2 - gui.buff * 2, 2 * gui.buff + TILE_SIZE, speedSprite.width * 2, speedSprite.height * 2);
        screen.popMatrix();

        mouseOverStat(x, y);


    }

    private void mouseOverStat(float tx, float ty) {

        int x = game.mouseX;
        int y = game.mouseY;

        String statName = "";
        String type = "";
        String desc = "";

        if (Util.pointInBox(x, y, TILE_SIZE/2 - gui.buff * 2 + tx, ty, TILE_SIZE / 2, TILE_SIZE / 2)) { // attack sprite hover
            statName = ATTACK.name();
            type = String.valueOf(getAttack());
            desc = "Increases Damage dealt by player projectiles";
        } else if (Util.pointInBox(x, y, TILE_SIZE * 2 - gui.buff * 2 + tx, ty, TILE_SIZE / 2, TILE_SIZE / 2)) { // defence sprite hover
            statName = DEFENCE.name();
            type = String.valueOf(getDefence());
            desc = "Decreases damage taken from enemies";
        } else if (Util.pointInBox(x, y, TILE_SIZE/2 - gui.buff * 2 + tx, gui.buff + TILE_SIZE / 2 + ty, TILE_SIZE / 2, TILE_SIZE / 2)) { // vitality hover
            statName = VITALITY.name();
            type = String.valueOf(getVitality());
            desc = "Increases health regeneration rate";
        } else if (Util.pointInBox(x, y, TILE_SIZE * 2 - gui.buff * 2 + tx, gui.buff + TILE_SIZE / 2 + ty, TILE_SIZE / 2, TILE_SIZE / 2)) { // wisdom hover
            statName = WISDOM.name();
            type = String.valueOf(getVitality());
            desc = "Increases mana regeneration rate";
        } else if (Util.pointInBox(x, y, TILE_SIZE/2 - gui.buff * 2 + tx, 2 * gui.buff + TILE_SIZE + ty, TILE_SIZE / 2, TILE_SIZE / 2)) { // speed hover
            statName = SPEED.name();
            type = String.valueOf((int)(speed * 100));
            desc = "Increases player speed";
        }

        if (!statName.equals("")) {

            int mouseOverWidth = 3 * GUI_WIDTH/4;
            WrappedText title = WrappedText.wrapText(statName, mouseOverWidth - gui.buff * 4, TILE_SIZE/2);
            WrappedText subtitle = WrappedText.wrapText(type, mouseOverWidth - gui.buff * 4, TILE_SIZE/3);
            WrappedText description = WrappedText.wrapText(desc, mouseOverWidth - gui.buff * 4, TILE_SIZE/3);

            gui.drawMouseOverText(x, y, title, subtitle, description);
        }
    }

    public void onDeath() {
        //set all stats kills to be 80% of original
        float percent = 0.8f;
        reduceStat(healthKills, percent);
        reduceStat(manaKills, percent);
        reduceStat(defenceKills, percent);
        reduceStat(attackKills, percent);
        reduceStat(vitalityKills, percent);
        reduceStat(wisdomKills, percent);
        reduceStat(speedKills, percent);
    }

    private void reduceStat(HashMap<Integer, Integer> stat, float percent) {
        for(int key : stat.keySet()) {
            stat.put(key, (int)(stat.get(key) * percent));
        }
    }

    public Long getSeed() {
        return this.randomSeed;
    }

    public void setSeed(long seed) {
        this.randomSeed = seed;
    }

    public int getMedianTeir(StatType stat) {
     Integer[] statTiers = null;
        if(stat.equals(HEALTH)) {
            statTiers = healthKills.keySet().toArray(new Integer[healthKills.size()]);
        } else if(stat.equals(MANA)) {
            statTiers = manaKills.keySet().toArray(new Integer[manaKills.size()]);
        } else if(stat.equals(VITALITY)) {
            statTiers = vitalityKills.keySet().toArray(new Integer[vitalityKills.size()]);
        } else if(stat.equals(ATTACK)) {
            statTiers = attackKills.keySet().toArray(new Integer[attackKills.size()]);
        } else if(stat.equals(WISDOM)) {
            statTiers = wisdomKills.keySet().toArray(new Integer[wisdomKills.size()]);
        } else if(stat.equals(DEFENCE)) {
            statTiers = defenceKills.keySet().toArray(new Integer[defenceKills.size()]);
        } else if(stat.equals(SPEED)) {
            statTiers = speedKills.keySet().toArray(new Integer[speedKills.size()]);
        }
        if(statTiers == null) return 1;

        ArrayList<Integer> tiers = new ArrayList<Integer>(Arrays.asList(statTiers));
        tiers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        return tiers.size() == 0 ? 0 : tiers.get(tiers.size()/2);
    }

}