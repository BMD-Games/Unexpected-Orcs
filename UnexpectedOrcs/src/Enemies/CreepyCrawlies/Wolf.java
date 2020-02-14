package Enemies.CreepyCrawlies;

import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Stats.StatType;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Wolf extends MeleeEnemy implements RectangleObject {

    private float w = 0.5f, h = 1f;

    public Wolf(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("WOLF"));
        stats.speed = 1.3f + 0.2f * tier;
        stats.attack = 12 + 16 * tier;
        stats.defence = 3 * tier;
        stats.health = 25 + 25 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 2;
        attackWaitTime = 0.5f;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("WOLF"), charSprites.get("WOLF_BITING")}, 0.3f);
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