package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.RangedEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Entities.Projectile;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import Utility.Pair;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Spider extends RangedEnemy implements  RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public Spider(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("SPIDER"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 12 + 12 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 1;
        shotWaitTime = 1.2f - game.abs(0.03f * tier * game.randomGaussian());
        shootDistance = 0.5f;
        retreatDistance = 0.0f;
        accuracy = 0.01f;
        projectileSprite = projectileSprites.get("STAFF");
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("SPIDER"), charSprites.get("SPIDER_JUMPING")}, 0.41f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
    }

    protected void attack() {
        if((stats.fireTimer > shotWaitTime * stats.getFireRate()) && (engine.currentLevel.canSee((int)x, (int)y, (int)engine.player.x, (int)engine.player.y))) {
            stats.fireTimer = 0;
            ArrayList<Pair> statusEffects = new ArrayList(1);
            statusEffects.add(new Pair("ALL", "SLOWED"));
            engine.enemyProjectiles.add(new Projectile(x, y, new PVector(game.cos(angle), game.sin(angle)), stats.speed * 3, range, stats.attack, projectileSprite, statusEffects));
        }
    }

}