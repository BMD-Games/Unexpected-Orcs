package com.bmd.Enemies.CreepyCrawlies;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.Scrolls.DebuffScroll;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.RectangleObject;
import com.bmd.Util.Util;

import java.awt.image.BufferedImage;


public class Bat extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.5f, h = 0.5f;
    private float animationTime = 0;
    private int lastAnim = 1;
    protected BufferedImage[] sprites = {Sprites.charSprites.get("BAT_SPREAD"), Sprites.charSprites.get("BAT_FLAPPING")};

    public Bat(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("BAT_SPREAD"));
        stats.speed = 2.2f + 0.3f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 4 * tier;
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

    public boolean update(double delta) {
        animationTime += delta + Util.random((float)delta / 2);
        animationTime %= 0.3;
        int currentAnim = (int)(animationTime / 0.1);
        if((currentAnim == 0) && (lastAnim != 0)) {
            y += 0.05;
            sprite = sprites[1];
        } else if((currentAnim != 0) && (currentAnim != lastAnim)) {
            y -= 0.025;
            sprite = sprites[0];
        }
        lastAnim = currentAnim;
        return super.update(delta);
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.1) {
            itembag.addItem(new DebuffScroll(new String[] {"DAZED"}));
        }
        Main.engine.addDrop(itembag);
    }

    protected void attack() {
        super.attack();
        Main.engine.player.stats.addStatusEffect("DAZED", 5);
    }

}