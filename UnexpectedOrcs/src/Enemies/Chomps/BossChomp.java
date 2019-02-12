package Enemies.Chomps;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.Portals.CavePortal;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.CircleObject;
import Utility.Util;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class BossChomp extends Chomp {

    public BossChomp(float x, float y, int tier) {
        super(x, y, tier);
        radius = 1;
        setSprite(charSprites.get("CHOMP_BOSS"));
        stats.health = 45 * tier;
        stats.healthMax = (int)stats.health;
        stats.attack = 20 * tier;
        stats.speed = 1.1f * tier;
        stats.defence = 8 * tier;
        animatedSprite = new AnimatedSprite(charSprites.get("CHOMP_BOSS"));
        game.println(sprite.width, sprite.height, damageSprite.width, damageSprite.height);
    }

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        return (Util.distance(x, y, pointX, pointY) < radius);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.2) {
            itemBag.addItem(itemFactory.createRandomWeapon(tier));
        }
        if(game.random(1) < 0.3) {
            itemBag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itemBag);
        engine.addDrop(new CavePortal(x, y));
    }
}

