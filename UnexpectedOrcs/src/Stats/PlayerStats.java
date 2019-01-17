package Stats;

import java.util.HashMap;

import static Utility.Constants.*;

public class PlayerStats extends Stats {

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

    private float baseSpeed = 2;

    private int totalKills = 0;

    public PlayerStats() {
        health = baseHealth;
        mana = baseMana;
        calcAllStats();
    }

    public void addKill() {
        totalKills ++;
    }

    public void addOrbStat(String stat, int tier) {
        switch(stat) {
            case("HEALTH"):
                healthKills.put(tier, healthKills.getOrDefault(tier, 0) + 1);
                healthMax = (int)calcStatValue(healthKills, baseHealth, 5, 0.5f);
                break;
            case("MANA"):
                manaKills.put(tier, manaKills.getOrDefault(tier, 0) + 1);
                manaMax = (int)calcStatValue(manaKills, baseMana, 5, 0.2f);
                break;
            case("VITALITY"):
                vitalityKills.put(tier, vitalityKills.getOrDefault(tier, 0) + 1);
                vitality = (int)calcStatValue(vitalityKills, baseVitality, 1, 0.1f);
                break;
            case("ATTACK"):
                attackKills.put(tier, attackKills.getOrDefault(tier, 0) + 1);
                attack = (int)calcStatValue(attackKills, baseAttack, 1, 0.1f);
                break;
            case("WISDOM"):
                wisdomKills.put(tier, wisdomKills.getOrDefault(tier, 0) + 1);
                wisdom = (int)calcStatValue(wisdomKills, baseWisdom, 1, 0.1f);
                break;
            case("DEFENCE"):
                defenceKills.put(tier, defenceKills.getOrDefault(tier, 0) + 1);
                defence = (int)calcStatValue(defenceKills, baseDefence, 1, 0.1f);
                break;
            case("SPEED"):
                speedKills.put(tier, speedKills.getOrDefault(tier, 0) + 1);
                speed = calcStatValue(speedKills, baseSpeed, 1, 0.1f);
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
}