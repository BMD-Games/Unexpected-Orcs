package Items.Weapons;

import Items.Weapon;
import Sprites.Sprites;
import Utility.Util;

public class Wand extends Weapon {

    public Wand(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("WAND"), Sprites.itemSprites.get("WAND_TIP"), colour), "Wand", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("WAND"), colour);
        tipColour = colour;
        weaponType = WeaponType.MAGIC;
    }
}
