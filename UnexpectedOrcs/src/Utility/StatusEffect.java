package Utility;

import Stats.StatusEffectType;

import java.io.Serializable;

public class StatusEffect implements Serializable {

    public StatusEffectType affects, status;
    public float duration;

    /**
     * Holds information about a status effect to be applied to an entity;
     *
     * @param affects the type of monster affected by the status
     * @param status the actual status effect to apply
     * @param duration the length of the status effect
     */
    public StatusEffect(StatusEffectType affects, StatusEffectType status, float duration) {
        this.affects = affects;
        this.status = status;

        this.duration = duration;
    }

}