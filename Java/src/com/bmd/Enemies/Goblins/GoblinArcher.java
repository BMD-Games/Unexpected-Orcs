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

public class GoblinArcher extends RangedEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinArcher(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("GOBLIN_ARCHER"));
        stats.speed = 0.7f + 0.2f * tier;
        stats.attack = 5 + 15 * tier;
        stats.defence = 2 + 2 * tier;
        stats.health = 10 + 8 * tier;
        stats.vitality = 1;
        shotWaitTime = 0.9f - Util.fastAbs(0.03f * tier * Util.randomGaussian());
        shootDistance = 2.6f;
        retreatDistance = 2;
        accuracy = 0.04f;
        projectileSprite = Util.getCombinedSprite(Sprites.projectileSprites.get("ARROW"), Sprites.projectileSprites.get("ARROW_TIP"), new Color(50,50,50));
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
            itembag.addItem(ItemFactory.createBow(tier));
        }
        Main.engine.addDrop(itembag);
    }

}