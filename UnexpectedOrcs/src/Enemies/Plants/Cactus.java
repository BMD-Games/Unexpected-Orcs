package Enemies.Plants;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Utility.Collision.RectangleObject;
import Utility.Util;

import static Utility.Colour.*;
import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Cactus extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public Cactus(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("CACTUS"));
        stats.speed = 0;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.vitality = 1;
        shotWaitTime = 0.8f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 10;
        retreatDistance = 0;
        accuracy = 0.04f;
        predictAim = true;
        projectileSprite = Util.applyColourToImage(projectileSprites.get("WAND"), colour(0,0,0));
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createWand(tier));
        }
        engine.addDrop(itembag);
    }

}