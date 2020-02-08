package Utility;

import Stats.StatusEffectType;

import java.io.Serializable;

public class Pair implements Serializable {

    public StatusEffectType a;
    public StatusEffectType b;

    public Pair(StatusEffectType a, StatusEffectType b) {
        this.a = a;
        this.b = b;
    }

}