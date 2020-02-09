package Items;

import Items.Weapons.*;
import Stats.StatusEffectType;
import Utility.StatusEffect;
import Utility.Util;

import static Utility.Constants.*;

public class ItemFactory {

    //Weapons: Wand, Staff, Spear, Bow
    //Scrolls: Debuff Scroll
    //Armours: Leather,
    //Abilities: Telescope, FireBomb, SpellBomb, SwiftBoots

    public Weapon createRandomWeapon(int tier) {
        Weapon weapon = null;
        switch((int)game.random(6)) {
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
            case 4:
                weapon = createAxe(tier);
                break;
            case 5:
                weapon = createSword(tier);
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
        wand.range = 8;
        wand.bulletSpeed = 25 + game.abs((int)(game.randomGaussian() * tier * 2));
        wand.fireRate = Util.roundTo(0.6f - tier * game.abs(game.randomGaussian()) / 32, 100);
        wand.spread = 0;
        wand.tier = tier;
        return wand;
    }

    public Wand createWand(int tier, StatusEffectType ability) {
        Wand wand = createWand(tier);
        wand.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return wand;
    }

    public Staff createStaff(int tier) {
        Staff staff = new Staff(Util.randomColour(tier));
        staff.damage =  4 + 10 * tier + (int)(game.randomGaussian() * tier);
        staff.range = 5;
        staff.bulletSpeed = 8 + game.abs((int)(game.randomGaussian() * tier));
        staff.fireRate = Util.roundTo(0.51f - tier * game.abs(game.randomGaussian()) / 32, 100);
        staff.spread = 0;
        staff.tier = tier;
        return staff;
    }

    public Staff createStaff(int tier, StatusEffectType ability) {
        Staff staff = createStaff(tier);
        staff.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return staff;
    }

    public Spear createSpear(int tier) {
        Spear spear = new Spear(Util.randomColour(tier));
        spear.damage =  4 + 8 * tier + (int)(game.randomGaussian() * tier);
        spear.range = 8;
        spear.bulletSpeed = 15 + game.abs((int)(game.randomGaussian() * tier));
        spear.fireRate = Util.roundTo(0.5f - tier * game.abs(game.randomGaussian()) / 32, 100);
        spear.spread = 0;
        spear.tier = tier;
        return spear;
    }

    public Spear createSpear(int tier, StatusEffectType ability) {
        Spear spear = createSpear(tier);
        spear.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return spear;
    }

    public Bow createBow(int tier) {
        Bow bow = new Bow(Util.randomColour(tier));
        bow.damage =  2 + 6 * tier + (int)(game.randomGaussian() * tier);
        bow.range = 10;
        bow.bulletSpeed = 15 + game.abs((int)(game.randomGaussian() * tier));
        bow.fireRate = Util.roundTo(0.38f - tier * game.abs(game.randomGaussian()) / 40, 100);
        bow.spread = Util.roundTo(0.2f + tier * game.randomGaussian() / 160, 100);
        bow.tier = tier;
        return bow;
    }

    public Bow createBow(int tier, StatusEffectType ability) {
        Bow bow = createBow(tier);
        bow.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return bow;
    }

    public Axe createAxe(int tier) {
        Axe axe = new Axe(Util.randomColour(tier));
        axe.damage =  2 + 10 * tier + (int)(game.randomGaussian() * tier);
        axe.range = 3;
        axe.bulletSpeed = 5 + game.abs((int)(game.randomGaussian() * tier));
        axe.fireRate = Util.roundTo(0.38f - tier * game.abs(game.randomGaussian()) / 40, 100);
        axe.spread = Util.roundTo(0.3f + tier * game.randomGaussian() / 160, 100);
        axe.tier = tier;
        return axe;
    }

    public Axe createAxe(int tier, StatusEffectType ability) {
        Axe axe = createAxe(tier);
        axe.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return axe;
    }

    public Sword createSword(int tier) {
        Sword sword = new Sword(Util.randomColour(tier));
        sword.damage =  2 + 12 * tier + (int)(game.randomGaussian() * tier);
        sword.range = 4;
        sword.bulletSpeed = 5 + game.abs((int)(game.randomGaussian() * tier));
        sword.fireRate = Util.roundTo(0.38f - tier * game.abs(game.randomGaussian()) / 40, 100);
        sword.spread = 0;
        sword.tier = tier;
        return sword;
    }

    public Sword createSword(int tier, StatusEffectType ability) {
        Sword sword = createSword(tier);
        sword.statusEffects.add(new StatusEffect(StatusEffectType.ALL, ability, 1));
        return sword;
    }

}