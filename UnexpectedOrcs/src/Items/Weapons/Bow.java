package Items.Weapons;

import Items.Weapon;
import Utility.Util;
import Sprites.Sprites;

public class Bow extends Weapon {

    public Bow(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("BOW"), Sprites.itemSprites.get("BOW_TIP"), colour), "Bow", null);
        bulletSprite = Util.getCombinedSprite(Sprites.projectileSprites.get("ARROW"), Sprites.projectileSprites.get("ARROW_TIP"), colour);
        tipColour = colour;
        weaponType = "BOW";
    }

}