package com.bmd.Enemies.Chomps;

import com.bmd.App.Main;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.Portals.CavePortal;
import com.bmd.Entities.StatOrb;
import com.bmd.Items.ItemFactory;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;

public class BossChomp extends Chomp {

    public BossChomp(float x, float y, int tier) {
        super(x, y, tier);
        radius = 1;
        sprite = Sprites.charSprites.get("CHOMP_BOSS");
        stats.health = 45 * tier;
        stats.attack = 20 * tier;
        stats.speed = 1.1f * tier;
        stats.defence = 8 * tier;
    }

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        return (Util.dist(x, y, pointX, pointY) < radius);
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
        ItemBag itemBag = new ItemBag(x, y, tier);
        if(Util.random(1) < 0.2) {
            itemBag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        if(Util.random(1) < 0.3) {
            itemBag.addItem(ItemFactory.createRandomWeapon(tier));
        }
        Main.engine.addDrop(itemBag);
        Main.engine.addDrop(new CavePortal(x, y));
    }
}