package Enemies.Goblins;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class GoblinWarrior extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.4f, h = 0.5f;

    public GoblinWarrior(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("GOBLIN_WARRIOR"));
        stats.speed = 0.9f + 0.2f * tier;
        stats.attack = 12 + 20 * tier;
        stats.defence = 5 * tier;
        stats.health = 30 + 20 * tier;
        stats.vitality = 3;
        animatedSprite = new AnimatedSprite(charSprites.get("GOBLIN_WARRIOR"));
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "DEFENCE"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.05) {
            itembag.addItem(itemFactory.createWand(tier));
        }
        engine.addDrop(itembag);
    }

}