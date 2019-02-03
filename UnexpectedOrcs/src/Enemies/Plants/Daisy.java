package Enemies.Plants;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Utility.Collision.RectangleObject;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Daisy extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public Daisy(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("DAISY_LONG"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 24 + 20 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.vitality = 1;
        shotWaitTime = 1.2f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 4;
        retreatDistance = 0.3f;
        accuracy = 0.03f;
        projectileSprite = projectileSprites.get("LEAF");
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
    }

}