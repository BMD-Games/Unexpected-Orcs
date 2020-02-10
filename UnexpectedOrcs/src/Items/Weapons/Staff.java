package Items.Weapons;

import Items.Weapon;
import Utility.Util;
import Sprites.Sprites;

public class Staff extends Weapon {

    public Staff(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("STAFF"), Sprites.itemSprites.get("STAFF_TIP"), colour), "Staff", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("STAFF"), colour);
        tipColour = colour;
        weaponType = WeaponType.MAGIC;
    }

}