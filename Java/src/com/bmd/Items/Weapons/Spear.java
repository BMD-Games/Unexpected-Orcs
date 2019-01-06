package com.bmd.Items.Weapons;

import com.bmd.Items.Weapon;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.scene.paint.Color;

public class Spear extends Weapon {

    public Spear(Color colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("SPEAR"), Sprites.itemSprites.get("SPEAR_TIP"), colour), "Spear", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("SPEAR"), colour);
        tipColour = colour;
    }

}
