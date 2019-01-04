package com.bmd.Enemies.Goblins;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.RangedEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

import java.awt.*;

import static com.bmd.Util.Util.randomGaussian;

public class GoblinMage extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinMage(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("GOBLIN_MAGE"));
        stats.speed = 0.6f + 0.1f * tier;
        stats.attack = 10 + 20 * tier;
        stats.defence = 2 * tier;
        stats.health = 8 + 6 * tier;
        stats.vitality = 1;
        shotWaitTime = 1.2f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 4;
        retreatDistance = 2.6f;
        predictAim = true;
        accuracy = 0.02f;
        bulletSpeed = 6;
        projectileSprite = Util.applyColourToImage(Sprites.projectileSprites.get("STAFF"), new Color(124, 10, 10));
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itembag.addItem(ItemFactory.createStaff(tier));
        }
        Main.engine.addDrop(itembag);
    }

}