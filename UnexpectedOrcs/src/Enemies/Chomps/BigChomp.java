package Enemies.Chomps;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Utility.Collision.CircleObject;
import Utility.Util;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class BigChomp extends Chomp {

    public BigChomp(float x, float y, int tier) {
        super(x, y, tier);
        radius = 0.5f;
        sprite = charSprites.get("CHOMP_BLACK");
        stats.health = 25 * tier;
        stats.attack = 11 * tier;
        stats.speed = 1.2f * tier;
        stats.defence = 3 * tier;
    }

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        return (Util.distance(x, y, pointX, pointY) < radius);
    }

    public void onDeath() {
        engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itemBag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itemBag);
    }

}