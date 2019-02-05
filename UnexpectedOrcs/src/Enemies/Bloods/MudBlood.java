package Enemies.Bloods;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.Portals.CavePortal;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Sprites.Sprites.charSprites;
import static Utility.Constants.engine;
import static Utility.Constants.game;
import static Utility.Constants.itemFactory;

public class MudBlood extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 1, h = 1;

    public MudBlood(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("BLOOD_MONSTER"));
        stats.speed = 1.2f + 0.2f * tier;
        stats.attack = 20 + 30 * tier;
        stats.defence = 3 * tier;
        stats.health = 25 + 20 * tier;
        stats.vitality = 7;
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("BLOOD_MONSTER"), charSprites.get("BLOOD_MONSTER_MOVING")}, 0.43f);
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
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        if(game.random(1) < 0.5) {
            itembag.addItem(itemFactory.createRandomWeapon(tier));
        }
        engine.addDrop(itembag);
        engine.addDrop(new CavePortal(x, y));
    }
}
