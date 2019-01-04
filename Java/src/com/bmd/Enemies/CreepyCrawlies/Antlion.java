package com.bmd.Enemies.CreepyCrawlies;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class Antlion extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.44f, h = 0.5f;

    public Antlion(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("ANTLION"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 8 + 4 * tier;
        stats.health = 25 + 25 * tier;
        stats.vitality = 2;
        attackWaitTime = 0.5f;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "DEFENCE"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.3) {
            itembag.addItem(ItemFactory.createRandomArmour(tier));
        }
        Main.engine.addDrop(itembag);
    }

}