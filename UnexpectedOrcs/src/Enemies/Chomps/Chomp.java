package Enemies.Chomps;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.CircleObject;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Chomp extends MeleeEnemy implements Enemy, CircleObject {

    public Chomp(float x, float y, int tier) {
        super(x, y, tier,charSprites.get("CHOMP_BLACK_SMALL"));
        radius = 0.25f;
        range = 6;
        stats.health = 14 * tier;
        stats.attack = 5 * tier;
        stats.speed = 1.3f * tier;
        stats.defence = 2 * tier;
        animatedSprite = new AnimatedSprite(charSprites.get("CHOMP_BLACK_SMALL"));
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.12) {
            itemBag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itemBag);
    }

    public float getRadius() {
        return radius;
    }
}