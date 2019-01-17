package Items;

import Items.Weapons.Bow;
import Items.Weapons.Spear;
import Items.Weapons.Staff;
import Items.Weapons.Wand;
import Utility.Pair;
import Utility.Util;

import static Utility.Constants.*;

public class ItemFactory {

    //Weapons: Wand, Staff, Spear, Bow
    //Scrolls: Debuff Scroll
    //Armours: Leather,
    //Abilities: Telescope, FireBomb, SpellBomb, SwiftBoots

    public Weapon createRandomWeapon(int tier) {
        Weapon weapon = null;
        switch((int)game.random(4)) {
            case 0:
                weapon = createWand(tier);
                break;
            case 1:
                weapon = createStaff(tier);
                break;
            case 2:
                weapon = createSpear(tier);
                break;
            case 3:
                weapon = createBow(tier);
                break;
        }
        return weapon;
    }

    public Armour createRandomArmour(int tier) {
        switch((int)game.random(5)) {
            case 0:
                return new Armour("LEATHER_ARMOUR", "Plain Leather Armour", tier * 5);
            case 1:
                return new Armour("STEEL_ARMOUR", "Plain Steel Armour", tier * 7 + 2);
            case 2:
                return new Armour("GILDED_LEATHER_ARMOUR", "Improved Leather Armour", tier * 5 + 2);
            case 3:
                return new Armour("STRONG_STEEL_ARMOUR", "Strong Steel Armour", tier * 7 + 4);
            case 4:
                return new Armour("BLOOD_ARMOUR", "Blood Armour", tier * 9 + 1);
        }
        return new Armour("LEATHER_ARMOUR", "hOl' uP", 0);
    }

    public Wand createWand(int tier) {
        Wand wand = new Wand(Util.randomColour(tier));
        wand.damage =  4 + 9 * tier + (int)(game.randomGaussian() * tier);
        wand.range = 6;
        wand.bulletSpeed = 25 + game.abs((int)(game.randomGaussian() * tier * 2));
        wand.fireRate = Util.roundTo(0.6f - tier * game.abs(game.randomGaussian()) / 32, 100);
        wand.accuracy = Util.roundTo(0.05f + tier * game.randomGaussian() / 80, 100);
        wand.tier = tier;
        return wand;
    }

    public Wand createWand(int tier, String ability) {
        Wand wand = createWand(tier);
        wand.statusEffects.add(new Pair("ALL", ability));
        return wand;
    }

    public Staff createStaff(int tier) {
        Staff staff = new Staff(Util.randomColour(tier));
        staff.damage =  4 + 10 * tier + (int)(game.randomGaussian() * tier);
        staff.range = 6;
        staff.bulletSpeed = 8 + game.abs((int)(game.randomGaussian() * tier));
        staff.fireRate = Util.roundTo(0.6f - tier * game.abs(game.randomGaussian()) / 32, 100);
        staff.accuracy = Util.roundTo(0.03f + tier * game.randomGaussian() / 160, 100);
        staff.tier = tier;
        return staff;
    }

    public Staff createStaff(int tier, String ability) {
        Staff staff = createStaff(tier);
        staff.statusEffects.add(new Pair("ALL", ability));
        return staff;
    }

    public Spear createSpear(int tier) {
        Spear spear = new Spear(Util.randomColour(tier));
        spear.damage =  4 + 12 * tier + (int)(game.randomGaussian() * tier);
        spear.range = 3;
        spear.bulletSpeed = 8 + game.abs((int)(game.randomGaussian() * tier));
        spear.fireRate = Util.roundTo(0.5f - tier * game.abs(game.randomGaussian()) / 32, 100);
        spear.accuracy = Util.roundTo(0.03f + tier * game.randomGaussian() / 400, 100);
        spear.tier = tier;
        return spear;
    }

    public Spear createSpear(int tier, String ability) {
        Spear spear = createSpear(tier);
        spear.statusEffects.add(new Pair("ALL", ability));
        return spear;
    }

    public Bow createBow(int tier) {
        Bow bow = new Bow(Util.randomColour(tier));
        bow.damage =  2 + 8 * tier + (int)(game.randomGaussian() * tier);
        bow.range = 10;
        bow.bulletSpeed = 15 + game.abs((int)(game.randomGaussian() * tier));
        bow.fireRate = Util.roundTo(0.38f - tier * game.abs(game.randomGaussian()) / 40, 100);
        bow.accuracy = Util.roundTo(0.05f + tier * game.randomGaussian() / 160, 100);
        bow.tier = tier;
        return bow;
    }

    public Bow createBow(int tier, String ability) {
        Bow bow = createBow(tier);
        bow.statusEffects.add(new Pair("ALL", ability));
        return bow;
    }

}