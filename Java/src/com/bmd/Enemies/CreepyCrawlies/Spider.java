package com.bmd.Enemies.CreepyCrawlies;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.RangedEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.Projectile;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.PVector;
import com.bmd.Util.Pair;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

import java.util.ArrayList;

public class Spider extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public Spider(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("SPIDER"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 12 + 12 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.vitality = 1;
        shotWaitTime = 1.2f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 2;
        retreatDistance = 0.3f;
        accuracy = 0.01f;
        projectileSprite = Sprites.projectileSprites.get("STAFF");
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itembag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itembag);
    }

    protected void attack() {
        if((stats.fireTimer > shotWaitTime * stats.getFireRate()) && (Main.engine.currentLevel.canSee((int)x, (int)y, (int)Main.engine.player.x, (int)Main.engine.player.y))) {
            stats.fireTimer = 0;
            ArrayList<Pair> statusEffects = new ArrayList(1);
            statusEffects.add(new Pair("ALL", "SLOWED"));
            Main.engine.enemyProjectiles.add(new Projectile(x, y, new PVector((float)Math.cos(angle), (float)Math.sin(angle)), stats.speed * 3, range, stats.attack, projectileSprite, statusEffects));
        }
    }

}