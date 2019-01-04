package com.bmd.Items;

import com.bmd.Util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Weapon extends Item {

    //Bulletspeed in  tiles per second?
    public int damage = 1, numBullets = 1, range = 1, bulletSpeed = 1;

    //FireRate, is the wait time in seconds between shots
    public float fireRate = 1;
    //Accuracy, angle in radians the weapon will shoot within
    public float accuracy = 0;

    public BufferedImage bulletSprite;
    public Color tipColour = new Color(50, 50, 50);

    public ArrayList<Pair> statusEffects = new ArrayList<Pair>();

    public Weapon(String spriteName, String name, ArrayList<Pair> statusEffects) {
        super(spriteName, name);
        this.type = "Weapon";
        this.statusEffects = statusEffects;
    }

    public Weapon(BufferedImage sprite, String name, ArrayList<Pair> statusEffects) {
        super(sprite, name);
        this.type = "Weapon";
        this.statusEffects = statusEffects;
    }

    public void playSound() {}
}