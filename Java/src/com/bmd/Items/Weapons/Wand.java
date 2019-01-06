package com.bmd.Items.Weapons;

import com.bmd.Items.Weapon;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.scene.paint.Color;

public class Wand extends Weapon {

    public Wand(Color colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("WAND"), Sprites.itemSprites.get("WAND_TIP"), colour), "Wand", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("WAND"), colour);
        tipColour = colour;
    }

}