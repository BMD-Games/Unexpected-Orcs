package Items.Weapons;

import Items.Weapon;
import Sprites.Sprites;
import Utility.Util;

public class Sword extends Weapon {

    public Sword(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("SWORD"), Sprites.itemSprites.get("SWORD_TIP"), colour), "Sword", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("SWORD"), colour);
        tipColour = colour;
        weaponType = WeaponType.MELEE;
    }
}
