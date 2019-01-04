package com.bmd.Enemies.CreepyCrawlies;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.Portals.GrassPortal;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class Basilisk extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 1, h = 1;

    public Basilisk(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("BASILISK"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 20 + 30 * tier;
        stats.defence = 3 * tier;
        stats.health = 30 + 25 * tier;
        stats.vitality = 3;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.5) {
            itembag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        if(Util.random(1) < 0.5) {
            itembag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itembag);
        Main.engine.addDrop(new GrassPortal(x, y));
    }

}