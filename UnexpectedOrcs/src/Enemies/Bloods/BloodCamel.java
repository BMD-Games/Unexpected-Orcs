package Enemies.Bloods;

import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Stats.StatType;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class BloodCamel extends MeleeEnemy implements RectangleObject {

    private float w = 0.5f, h = 1f;

    public BloodCamel(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("BLOOD_CAMEL"));
        stats.speed = 0.7f + 0.1f * tier;
        stats.attack = 10 + 14 * tier;
        stats.defence = 6 * tier;
        stats.health = 40 + 35 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 3;
        attackWaitTime = 0.5f;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("BLOOD_CAMEL"), charSprites.get("BLOOD_CAMEL_WALKING")}, 0.3f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, StatType.ATTACK));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.3) {
            itembag.addItem(itemFactory.createRandomArmour(tier));
        }
        engine.addDrop(itembag);
    }

}