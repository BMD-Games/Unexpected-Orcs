package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.Portals.GrassPortal;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Basilisk extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 1, h = 1;

    public Basilisk(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("BASILISK"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 20 + 30 * tier;
        stats.defence = 3 * tier;
        stats.health = 30 + 25 * tier;
        stats.vitality = 3;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("BASILISK"), charSprites.get("BASILISK_2")}, 0.29f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
        engine.addDrop(new GrassPortal(x, y));
    }

}