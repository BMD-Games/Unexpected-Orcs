package com.bmd.Items;

import com.bmd.Util.Pair;
import com.bmd.Util.Util;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Weapon extends Item implements Serializable {

    //Bulletspeed in  tiles per second?
    public int damage = 1, numBullets = 1, range = 1, bulletSpeed = 1;

    //FireRate, is the wait time in seconds between shots
    public float fireRate = 1;
    //Accuracy, angle in radians the weapon will shoot within
    public float accuracy = 0;

    public transient BufferedImage bulletSprite;
    public Color tipColour = Util.color(50, 50, 50);

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

    private void writeObject(ObjectOutputStream out) {

        try {
            out.defaultWriteObject();
            ImageIO.write(bulletSprite, "png", out);
        } catch (IOException e) {
            Util.println("tits", e);
        }

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        bulletSprite = ImageIO.read(in);
    }
}