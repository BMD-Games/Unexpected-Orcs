package com.bmd.Enemies.Goblins;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.Portals.CellarPortal;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class GoblinBoxer extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 1, h = 1;

    public GoblinBoxer(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("GOBLIN_BOXER"));
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
        Main.engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.5) {
            itembag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        if(Util.random(1) < 0.5) {
            itembag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itembag);
        Main.engine.addDrop(new CellarPortal(x, y));
    }

}