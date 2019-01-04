package com.bmd.Enemies.Chomps;

import com.bmd.App.Main;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;

public class BigChomp extends Chomp {

    public BigChomp(float x, float y, int tier) {
        super(x, y, tier);
        radius = 0.5f;
        sprite = Sprites.charSprites.get("CHOMP_BLACK");
        stats.health = 25 * tier;
        stats.attack = 11 * tier;
        stats.speed = 1.2f * tier;
        stats.defence = 3 * tier;
    }

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        return (Util.dist(x, y, pointX, pointY) < radius);
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itemBag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itemBag);
    }

}
