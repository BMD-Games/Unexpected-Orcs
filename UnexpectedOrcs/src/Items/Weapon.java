package Items;

import Entities.Projectile;
import Sound.SoundManager;
import Utility.Pair;
import processing.core.PImage;
import processing.core.PVector;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static Utility.Colour.colour;
import static Utility.Constants.SPRITE_SIZE;
import static Utility.Constants.engine;
import static Utility.Constants.game;

public class Weapon extends Item implements Serializable {

    //Bulletspeed in  tiles per second?
    public int damage = 1, numBullets = 1, range = 1, bulletSpeed = 1;

    //FireRate, is the wait time in seconds between shots
    public float fireRate = 1;
    //Accuracy, angle in radians the weapon will shoot within
    public float spread = 0;

    transient public PImage bulletSprite;
    public int tipColour = colour(50, 50, 50);

    protected String weaponType;

    public ArrayList<Pair> statusEffects = new ArrayList<Pair>();

    public Weapon(String spriteName, String name, ArrayList<Pair> statusEffects) {
        super(spriteName, name);
        this.type = "Weapon";
        this.statusEffects = statusEffects;
    }

    public Weapon(PImage sprite, String name, ArrayList<Pair> statusEffects) {
        super(sprite, name);
        this.type = "Weapon";
        this.statusEffects = statusEffects;
    }



    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        bulletSprite.loadPixels();
        out.writeObject(bulletSprite.pixels);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        bulletSprite = game.createImage(SPRITE_SIZE/2, SPRITE_SIZE/2, game.ARGB);
        bulletSprite.loadPixels();
        bulletSprite.pixels = (int[])in.readObject();
        bulletSprite.updatePixels();
    }

    public void playSound() {
        String name = this.getClass().getName();
        name = name.substring(name.lastIndexOf('.') + 1).toUpperCase();

        SoundManager.playSound("WEAPON_" + name);
    }

    public ArrayList<Projectile> fire() {
        ArrayList<Projectile> projectiles = new ArrayList<>(numBullets);
        ArrayList<Pair> projectileStats = statusEffects == null ? new ArrayList<Pair>() : statusEffects;
        if(engine.player.currentScroll() != null) {
            projectileStats.addAll(engine.player.currentScroll().statusEffects);
        }
        for (int i = 0; i < numBullets; ++i) {
            projectiles.add(new Projectile(engine.player.x, engine.player.y, numBullets == 1 ? PVector.fromAngle(engine.player.ang) : PVector.fromAngle(engine.player.ang - (spread * ((i/(numBullets-1))-0.5f))),
                    bulletSpeed, range, damage + engine.player.stats.getAttack(), bulletSprite, projectileStats));
        }
        return projectiles;
    }
}