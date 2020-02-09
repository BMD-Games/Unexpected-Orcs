package Items.Weapons;

import Items.Weapon;
import Sprites.Sprites;
import Utility.Util;

public class Axe extends Weapon {

    public Axe(int colour) {
        super(Util.getCombinedSprite(Sprites.itemSprites.get("AXE"), Sprites.itemSprites.get("AXE_TIP"), colour), "Axe", null);
        bulletSprite = Util.applyColourToImage(Sprites.projectileSprites.get("AXE"), colour);
        tipColour = colour;
        numBullets = 2;
        weaponType = WeaponType.MELEE;
    }

}
