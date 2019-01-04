package com.bmd.Items.Weapons;

import com.bmd.Items.Weapon;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;

import java.awt.*;

public class Bow extends Weapon {

    public Bow(Color colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("BOW"), Sprites.itemSprites.get("BOW_TIP"), colour), "Bow", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("BOW"), colour);
        tipColour = colour;
    }

}