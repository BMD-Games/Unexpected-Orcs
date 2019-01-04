package com.bmd.Enemies.Chomps;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.CircleObject;
import com.bmd.Util.Util;

public class Chomp extends MeleeEnemy implements Enemy, CircleObject {

    public Chomp(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("CHOMP_BLACK_SMALL"));
        radius = 0.25f;
        range = 6;
        stats.health = 14 * tier;
        stats.attack = 5 * tier;
        stats.speed = 1.3f * tier;
        stats.defence = 2 * tier;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.12) {
            itemBag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itemBag);
    }

    public float getRadius() {
        return radius;
    }
}