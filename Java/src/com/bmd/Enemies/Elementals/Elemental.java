package com.bmd.Enemies.Elementals;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.MeleeEnemy;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.CircleObject;

import java.awt.image.BufferedImage;

import static com.bmd.Util.Util.random;

public abstract class Elemental extends MeleeEnemy implements Enemy, CircleObject {

    protected float animationTime = 0;
    protected BufferedImage[] sprites = {Sprites.charSprites.get("FIRE_ELEMENTAL"), Sprites.charSprites.get("ICE_ELEMENTAL"), Sprites.charSprites.get("MAGIC_ELEMENTAL"), Sprites.charSprites.get("POISON_ELEMENTAL")};
    protected String statusEffect;

    public Elemental(float x, float y, int tier) {
        super(x, y, tier, Sprites.charSprites.get("FIRE_ELEMENTAL"));
        radius = 0.25f;
        stats.health = 6 * tier;
        stats.attack = 10 * tier;
        stats.speed = 2 * tier;
        stats.defence = 2 * tier;
    }

    public boolean update(double delta) {
        animationTime += delta + random((float)delta / 2);
        animationTime %= 0.8;
        sprite = sprites[(int)(animationTime / 0.2)];
        return super.update(delta);
    }

    protected void attack() {
        super.attack();
        Main.engine.player.stats.addStatusEffect(statusEffect, 3);
    }

    public float getRadius() {
        return radius;
    }

}