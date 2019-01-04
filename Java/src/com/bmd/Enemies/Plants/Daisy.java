package com.bmd.Enemies.Plants;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.RangedEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

public class Daisy extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public Daisy(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("DAISY"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 24 + 20 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.vitality = 1;
        shotWaitTime = 1.2f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 4;
        retreatDistance = 0.3f;
        accuracy = 0.03f;
        projectileSprite = Sprites.projectileSprites.get("LEAF");
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

}