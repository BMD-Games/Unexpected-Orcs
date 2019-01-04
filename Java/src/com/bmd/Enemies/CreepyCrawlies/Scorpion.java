package com.bmd.Enemies.CreepyCrawlies;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.RangedEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class Scorpion extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;
    protected float attackWait = 0;
    protected float attackWaitTime = 0.8f;

    public Scorpion(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("SCORPION"));
        stats.speed = 0;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.vitality = 1;
        shotWaitTime = 0.8f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 0.1f;
        retreatDistance = 0;
        accuracy = 0.04f;
        projectileSprite = Sprites.projectileSprites.get("STINGER");
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public boolean update(double delta) {
        if (Util.dist(x, y, Main.engine.player.x, Main.engine.player.y) < range) {
            attackWait += delta;
            if (pointCollides(Main.engine.player.x, Main.engine.player.y) && attackWait > attackWaitTime) {
                Main.engine.player.damage(18);
                attackWait = 0;
            }
        }
        return super.update(delta);
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itembag.addItem(ItemFactory.createRandomArmour(tier));
        }
        Main.engine.addDrop(itembag);
    }

}