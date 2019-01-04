package com.bmd.Enemies.Goblins;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class GoblinSpearman extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinSpearman(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("GOBLIN_SPEARMAN"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 3 * tier;
        stats.health = 20 + 15 * tier;
        stats.vitality = 2;
        attackWaitTime = 0.6f;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "VITALITY"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itembag.addItem(ItemFactory.createSpear(tier));
        }
        Main.engine.addDrop(itembag);
    }

}