package Items.Weapons;

import Items.Weapon;
import Utility.Util;
import Sprites.Sprites;

public class Spear extends Weapon {

    public Spear(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("SPEAR"), Sprites.itemSprites.get("SPEAR_TIP"), colour), "Spear", null);
        bulletSprite = Util.getCombinedSprite(Sprites.projectileSprites.get("SPEAR"), Sprites.projectileSprites.get("SPEAR_TIP"), colour);
        tipColour = colour;
        weaponType = WeaponType.RANGED;
    }

}