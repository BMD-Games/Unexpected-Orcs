package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Entities.Projectile;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import Utility.Util;
import processing.core.PImage;
import processing.core.PVector;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Scorpion extends RangedEnemy implements RectangleObject {

    private float w = 0.5f, h = 1f;
    protected float attackWait = 0;
    protected float attackWaitTime = 0.8f;

    public Scorpion(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("SCORPION"));
        stats.speed = 0;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 1;
        shotWaitTime = 0.8f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 0.1f;
        retreatDistance = 0;
        accuracy = 0.04f;
        projectileSprite = projectileSprites.get("STINGER");
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("SCORPION"), charSprites.get("SCORPION_BITING")}, 0.2f);
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
            //Check melee attack
            if (pointCollides(engine.player.x, engine.player.y) && attackWait > attackWaitTime) {
                engine.player.damage(new Projectile(x, y, new PVector(game.cos(angle), game.sin(angle)), 0, 0, stats.attack, null));
                attackWait = 0;
            }
        }
        return super.update(delta);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createRandomArmour(tier));
        }
        engine.addDrop(itembag);
    }

}