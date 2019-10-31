package Enemies.Goblins;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class GoblinSpearman extends MeleeEnemy implements  RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinSpearman(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("GOBLIN_SPEARMAN"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 3 * tier;
        stats.health = 20 + 15 * tier;
        stats.healthMax = (int)stats.health;
        stats.vitality = 2;
        attackWaitTime = 0.6f;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("GOBLIN_SPEARMAN"), charSprites.get("GOBLIN_SPEARMAN_WALKING")}, 0.24f);
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "VITALITY"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itembag.addItem(itemFactory.createSpear(tier));
        }
        engine.addDrop(itembag);
    }

}
