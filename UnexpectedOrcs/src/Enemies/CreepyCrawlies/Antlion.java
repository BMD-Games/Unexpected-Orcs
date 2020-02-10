package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Stats.StatType;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Antlion extends MeleeEnemy implements  RectangleObject {

    private float w = 0.44f, h = 0.5f;

    public Antlion(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("ANTLION_LEFT"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 8 + 4 * tier;
        stats.health = 25 + 25 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 2;
        attackWaitTime = 0.5f;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("ANTLION_LEFT"), charSprites.get("ANTLION_RIGHT")}, 0.26f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, StatType.DEFENCE));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.3) {
            itembag.addItem(itemFactory.createRandomArmour(tier));
        }
        engine.addDrop(itembag);
    }

}