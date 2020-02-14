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

public class BloodBiter extends MeleeEnemy implements RectangleObject {

    private float w = 0.5f, h = 0.5f;

    public BloodBiter(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("BLOOD_BITER"));
        stats.speed = 1.6f + 0.25f * tier;
        stats.attack = 14 + 14 * tier;
        stats.defence = 2 * tier;
        stats.health = 25 + 10 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 2;
        attackWaitTime = 0.2f;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("BLOOD_BITER"), charSprites.get("BLOOD_BITER_BITING")}, 0.17f);
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