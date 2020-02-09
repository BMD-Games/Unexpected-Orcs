package Stats;

import java.io.Serializable;
import java.util.HashMap;

import static Utility.Constants.*;

public class Stats implements Serializable {
    public int healthMax = 0, manaMax = 0;
    public float mana = 0, health = 0;

    public int vitality = 0, attack = 0, wisdom = 0, defence = 0;

    public float speed = 0, fireTimer = 0;

    public HashMap<StatusEffectType, Float> statusEffects = new HashMap<StatusEffectType, Float>();

    public void update(double delta) {
        StatusEffectType s;
        for(int i = 0; i < statusEffects.keySet().size(); i++) {
            s = (StatusEffectType) statusEffects.keySet().toArray()[i];
            if(statusEffects.get(s) - delta < 0) {
                statusEffects.remove(s);
            } else {
                statusEffects.replace(s, (float)(statusEffects.get(s) - delta));
            }
        }

        if(health < healthMax) {
            health = game.constrain(health + (float)(getVitality() * delta), 0, healthMax);
        }
        if(mana < manaMax) {
            mana = game.constrain(mana + (float)(getWisdom() * delta), 0, manaMax);
        }
        fireTimer += (float)(delta);
    }

    public void addStatusEffect(StatusEffectType statusEffect, float duration) {
        statusEffects.put(statusEffect, duration);
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
            return 1/2;
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
}
