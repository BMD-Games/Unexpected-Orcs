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

public class GoblinWarrior extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinWarrior(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("GOBLIN_WARRIOR"));
        stats.speed = 0.9f + 0.2f * tier;
        stats.attack = 12 + 20 * tier;
        stats.defence = 5 * tier;
        stats.health = 30 + 20 * tier;
        stats.vitality = 3;
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
        if(Util.random(1) < 0.05) {
            itembag.addItem(ItemFactory.createWand(tier));
        }
        Main.engine.addDrop(itembag);
    }

}