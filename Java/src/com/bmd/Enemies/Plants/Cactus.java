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

import java.awt.*;

public class Cactus extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public Cactus(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("CACTUS"));
        stats.speed = 0;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.vitality = 1;
        shotWaitTime = 0.8f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 10;
        retreatDistance = 0;
        accuracy = 0.04f;
        predictAim = true;
        projectileSprite = Util.applyColourToImage(Sprites.projectileSprites.get("WAND"), new Color(0,0,0));
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
        if(Util.random(1) < 0.2) {
            itembag.addItem(ItemFactory.createWand(tier));
        }
        Main.engine.addDrop(itembag);
    }

}