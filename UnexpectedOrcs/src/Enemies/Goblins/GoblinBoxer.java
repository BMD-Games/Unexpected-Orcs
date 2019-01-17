package Enemies.Goblins;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.Portals.CellarPortal;
import Entities.Drops.StatOrb;
import Utility.Collision.RectangleObject;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class GoblinBoxer extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 1, h = 1;

    public GoblinBoxer(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("GOBLIN_BOXER"));
        stats.speed = 0.6f + 0.15f * tier;
        stats.attack = 20 + 30 * tier;
        stats.defence = 8 * tier;
        stats.health = 40 + 35 * tier;
        stats.vitality = 6;
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
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
        engine.addDrop(new CellarPortal(x, y));
    }

}