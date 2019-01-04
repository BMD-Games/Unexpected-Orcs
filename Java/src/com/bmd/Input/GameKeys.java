package com.bmd.Input;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pkeys;

    private static final int NUM_KEYS = 8;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ABILITY = 4;

    public static void update() {
        for(int i = 0; i < NUM_KEYS; i ++) {
            pkeys[i] = keys[i];
        }
    }

    public static void setKeys(int k, boolean b) {
        keys[k] = b;
    }

    public static boolean isDown(int k) {
        return keys[k];
    }

    public static boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

    public static boolean isReleased(int k) {
        return !keys[k] && pkeys[k];
    }
}
