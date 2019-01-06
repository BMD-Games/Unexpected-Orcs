package com.bmd.Items.Weapons;

import com.bmd.Items.Weapon;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.scene.paint.Color;


public class Staff extends Weapon {

    public Staff(Color colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("STAFF"), Sprites.itemSprites.get("STAFF_TIP"), colour), "Staff", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("STAFF"), colour);
        tipColour = colour;
    }

}
