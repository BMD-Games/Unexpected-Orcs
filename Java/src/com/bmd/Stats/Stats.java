package com.bmd.Stats;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class Stats {
    public static HashMap<String, Boolean> STATUSES = new HashMap<String, Boolean>();
    public static HashMap<String, Color> statColours = new HashMap<String, Color>();
    public static String[] stats = {"HEALTH", "MANA", "SPEED", "WISDOM", "DEFENCE", "ATTACK"};

    public int healthMax = 0, manaMax = 0;
    public float mana = 0, health = 0;

    public int vitality = 0, attack = 0, wisdom = 0, defence = 0;

    public float speed = 0, fireTimer = 0;

    public HashMap<String, Float> statusEffects = new HashMap<String, Float>();

    public void update(double delta) {
        String s;
        for(int i = 0; i < statusEffects.keySet().size(); i++) {
            s = (String)statusEffects.keySet().toArray()[i];
            if(statusEffects.get(s) - delta < 0) {
                statusEffects.remove(s);
            } else {
                statusEffects.replace(s, (float)(statusEffects.get(s) - delta));
            }
        }

        if(health < healthMax) {
            health = health + (float)(getVitality() * delta);
        }
        if(mana < manaMax) {
            mana = mana + (float)(getWisdom() * delta);
        }
        fireTimer += (float)(delta);
    }

    public void addStatusEffect(String name, float duration) {
        if(STATUSES.getOrDefault(name, false)) {
            statusEffects.put(name, duration);
        }
    }

    public int getHealth() { return (int)health; }
    public int getHealthMax() { return healthMax; }
    public int getMana() { return (int)mana; }
    public int getManaMax() { return manaMax; }

    public int getVitality() {
        if(statusEffects.containsKey("SICK") && !statusEffects.containsKey("HEALING")) {
            return 0;
        }
        if(!statusEffects.containsKey("SICK") && statusEffects.containsKey("HEALING")) {
            return vitality * 2;
        }
        return vitality;
    }

    public int getAttack() {
        if(statusEffects.containsKey("WEAK") && !statusEffects.containsKey("DAMAGING")) {
            return 0;
        }
        if(!statusEffects.containsKey("WEAK") && statusEffects.containsKey("DAMAGING")) {
            return attack * 2;
        }
        return attack;
    }

    public int getWisdom() {
        if(statusEffects.containsKey("CURSED") && !statusEffects.containsKey("SMART")) {
            return 0;
        }
        if(!statusEffects.containsKey("CURSED") && statusEffects.containsKey("SMART")) {
            return wisdom * 2;
        }
        return wisdom;
    }

    public int getDefence() {
        if(statusEffects.containsKey("ARMOURBREAK") && !statusEffects.containsKey("ARMOURED")) {
            return 0;
        }
        if(!statusEffects.containsKey("ARMOURBREAK") && statusEffects.containsKey("ARMOURED")) {
            return defence * 2;
        }
        return defence;
    }

    public float getFireRate() {
        if(statusEffects.containsKey("DAZED") && !statusEffects.containsKey("BEZERK")) {
            return 2;
        }
        if(!statusEffects.containsKey("SICK") && statusEffects.containsKey("HEALING")) {
            return 0.5f;
        }
        return 1;
    }

    public float getSpeed() {
        if(statusEffects.containsKey("SLOWED") && !statusEffects.containsKey("SWIFT")) {
            return speed/2;
        }
        if(!statusEffects.containsKey("SLOWED") && statusEffects.containsKey("SWIFT")) {
            return speed * 1.5f;
        }
        return speed;
    }

    public void loadStats() {
        STATUSES.put("SICK", true);
        STATUSES.put("HEALING", true);
        STATUSES.put("WEAK", true);
        STATUSES.put("DAMAGING", true);
        STATUSES.put("CURSED", true);
        STATUSES.put("SMART", true);
        STATUSES.put("ARMOURBREAK", true);
        STATUSES.put("ARMOURED", true);
        STATUSES.put("DAZED", true);
        STATUSES.put("BEZERK", true);
        STATUSES.put("SLOWED", true);
        STATUSES.put("SWIFT", true);

        statColours.put("HEALTH", Color.color(230, 100, 100));
        statColours.put("MANA", Color.color(153, 217, 234));
        statColours.put("SPEED", Color.color(100, 230, 100));
        statColours.put("WISDOM", Color.color(140, 50, 230));
        statColours.put("DEFENCE", Color.color(0, 15, 230));
        statColours.put("ATTACK", Color.color(230, 150, 0));
        statColours.put("VITALITY", Color.color(255, 105, 180));
    }
}
