package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Utility.Collision.RectangleObject;
import Utility.Util;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Scorpion extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;
    protected float attackWait = 0;
    protected float attackWaitTime = 0.8f;

    public Scorpion(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("SCORPION"));
        stats.speed = 0;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.vitality = 1;
        shotWaitTime = 0.8f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 0.1f;
        retreatDistance = 0;
        accuracy = 0.04f;
        projectileSprite = projectileSprites.get("STINGER");
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public boolean update(double delta) {
        if (Util.distance(x, y, engine.player.x, engine.player.y) < range) {
            attackWait += delta;
            if (pointCollides(engine.player.x, engine.player.y) && attackWait > attackWaitTime) {
                engine.player.damage(18);
                attackWait = 0;
            }
        }
        return super.update(delta);
    }

    public void onDeath() {
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createRandomArmour(tier));
        }
        engine.addDrop(itembag);
    }

}