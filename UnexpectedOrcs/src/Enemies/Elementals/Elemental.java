package Enemies.Elementals;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Sprites.AnimatedSprite;
import Utility.Collision.CircleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public abstract class Elemental extends MeleeEnemy implements Enemy, CircleObject {

    protected float animationTime = 0;
    protected PImage[] sprites = {charSprites.get("FIRE_ELEMENTAL"), charSprites.get("ICE_ELEMENTAL"), charSprites.get("MAGIC_ELEMENTAL"), charSprites.get("POISON_ELEMENTAL")};
    protected String statusEffect;

    public Elemental(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("FIRE_ELEMENTAL"));
        radius = 0.25f;
        stats.health = 6 * tier;
        stats.healthMax = (int)stats.health;
        stats.attack = 10 * tier;
        stats.speed = 2 * tier;
        stats.defence = 2 * tier;
    }

    public boolean update(double delta) {
        //animationTime += delta + game.random((float)delta / 2);
        //animationTime %= 0.8;
        //sprite = sprites[(int)(animationTime / 0.2)];
        return super.update(delta);
    }

    protected void attack() {
        super.attack();
        engine.player.stats.addStatusEffect(statusEffect, 3);
    }

    public float getRadius() {
        return radius;
    }

}