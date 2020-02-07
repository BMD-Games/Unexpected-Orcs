package Enemies.Goblins;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import Utility.Util;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;
import static Utility.Colour.*;

public class GoblinMage extends RangedEnemy implements  RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinMage(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("GOBLIN_MAGE"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 10 + 20 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 1;
        shotWaitTime = 1.2f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 4;
        retreatDistance = 2.6f;
        predictAim = true;
        accuracy = 0.02f;
        bulletSpeed = 6;
        projectileSprite = Util.applyColourToImage(projectileSprites.get("STAFF"), colour(124, 10, 10));
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("GOBLIN_MAGE"), charSprites.get("GOBLIN_MAGE_WALKING")}, 0.29f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createStaff(tier));
        }
        engine.addDrop(itembag);
    }

}