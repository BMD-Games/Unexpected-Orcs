package Enemies.Plants;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Stats.StatType;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Rose extends RangedEnemy implements  RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public Rose(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("ROSE_LONG"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 10 + 12 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 1;
        shotWaitTime = 0.6f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 4;
        retreatDistance = 0.3f;
        accuracy = 0.05f;
        projectileSprite = projectileSprites.get("THORN");
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("ROSE_LONG"), charSprites.get("ROSE_SHORT")}, 0.34f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, StatType.WISDOM));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
    }

}