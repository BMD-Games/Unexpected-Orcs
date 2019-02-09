package Sprites;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

import static Tiles.Tiles.*;
import static Utility.Constants.SPRITE_SIZE;

public class Sprites {
    public static HashMap<Integer, PImage> tileSprites;
    public static HashMap<String, PImage> itemSprites;
    public static HashMap<String, PImage> guiSprites;
    public static HashMap<String, PImage> charSprites;
    public static HashMap<String, PImage> projectileSprites;
    public static HashMap<String, PImage> dropSprites;
    public static HashMap<String, PImage> statusSprites;
    public static HashMap<String, PImage> playerStatusSprites;

    public static void loadAssets(PApplet app) {
        
        tileSprites = new HashMap<Integer, PImage>();
        itemSprites = new HashMap<String, PImage>();
        guiSprites = new HashMap<String, PImage>();
        charSprites = new HashMap<String, PImage>();
        projectileSprites = new HashMap<String, PImage>();
        dropSprites = new HashMap<String, PImage>();
        statusSprites = new HashMap<String, PImage>();
        playerStatusSprites = new HashMap<String, PImage>();

        //-----TILE SPRITES-----
        PImage tilesheet = app.loadImage("/assets/sprites/tilesheet.png");

        tileSprites.put(WALL, getSprite(tilesheet, 0, 0, 1, 1, SPRITE_SIZE));
        tileSprites.put(FLOOR, getSprite(tilesheet, 1, 0, 1, 1, SPRITE_SIZE));

        //--TEST--
        tileSprites.put(TEST_00, getSprite(tilesheet, 0, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_01, getSprite(tilesheet, 1, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_02, getSprite(tilesheet, 2, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_03, getSprite(tilesheet, 3, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_04, getSprite(tilesheet, 4, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_05, getSprite(tilesheet, 5, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_06, getSprite(tilesheet, 6, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_07, getSprite(tilesheet, 7, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_08, getSprite(tilesheet, 8, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_09, getSprite(tilesheet, 9, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_10, getSprite(tilesheet, 10, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_11, getSprite(tilesheet, 11, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_12, getSprite(tilesheet, 12, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_13, getSprite(tilesheet, 13, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_14, getSprite(tilesheet, 14, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(TEST_15, getSprite(tilesheet, 15, 31, 1, 1, SPRITE_SIZE));

        //--STONE BRICK--
        tileSprites.put(STONE_BRICK_00, getSprite(tilesheet, 0, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_01, getSprite(tilesheet, 1, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_02, getSprite(tilesheet, 2, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_03, getSprite(tilesheet, 3, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_04, getSprite(tilesheet, 4, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_05, getSprite(tilesheet, 5, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_06, getSprite(tilesheet, 6, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_07, getSprite(tilesheet, 7, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_08, getSprite(tilesheet, 8, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_09, getSprite(tilesheet, 9, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_10, getSprite(tilesheet, 10, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_11, getSprite(tilesheet, 11, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_12, getSprite(tilesheet, 12, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_13, getSprite(tilesheet, 13, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_14, getSprite(tilesheet, 14, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(STONE_BRICK_15, getSprite(tilesheet, 15, 30, 1, 1, SPRITE_SIZE));

        //--ROCK--
        tileSprites.put(ROCK_00, getSprite(tilesheet, 0, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_01, getSprite(tilesheet, 1, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_02, getSprite(tilesheet, 2, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_03, getSprite(tilesheet, 3, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_04, getSprite(tilesheet, 4, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_05, getSprite(tilesheet, 5, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_06, getSprite(tilesheet, 6, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_07, getSprite(tilesheet, 7, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_08, getSprite(tilesheet, 8, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_09, getSprite(tilesheet, 9, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_10, getSprite(tilesheet, 10, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_11, getSprite(tilesheet, 11, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_12, getSprite(tilesheet, 12, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_13, getSprite(tilesheet, 13, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_14, getSprite(tilesheet, 14, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(ROCK_15, getSprite(tilesheet, 15, 29, 1, 1, SPRITE_SIZE));

        //--HEDGE--
        tileSprites.put(HEDGE_00, getSprite(tilesheet, 0, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_01, getSprite(tilesheet, 1, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_02, getSprite(tilesheet, 2, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_03, getSprite(tilesheet, 3, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_04, getSprite(tilesheet, 4, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_05, getSprite(tilesheet, 5, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_06, getSprite(tilesheet, 6, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_07, getSprite(tilesheet, 7, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_08, getSprite(tilesheet, 8, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_09, getSprite(tilesheet, 9, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_10, getSprite(tilesheet, 10, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_11, getSprite(tilesheet, 11, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_12, getSprite(tilesheet, 12, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_13, getSprite(tilesheet, 13, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_14, getSprite(tilesheet, 14, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(HEDGE_15, getSprite(tilesheet, 15, 28, 1, 1, SPRITE_SIZE));

        tileSprites.put(SANDSTONE_00, getSprite(tilesheet, 0, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_01, getSprite(tilesheet, 1, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_02, getSprite(tilesheet, 2, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_03, getSprite(tilesheet, 3, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_04, getSprite(tilesheet, 4, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_05, getSprite(tilesheet, 5, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_06, getSprite(tilesheet, 6, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_07, getSprite(tilesheet, 7, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_08, getSprite(tilesheet, 8, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_09, getSprite(tilesheet, 9, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_10, getSprite(tilesheet, 10, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_11, getSprite(tilesheet, 11, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_12, getSprite(tilesheet, 12, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_13, getSprite(tilesheet, 13, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_14, getSprite(tilesheet, 14, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(SANDSTONE_15, getSprite(tilesheet, 15, 27, 1, 1, SPRITE_SIZE));

        tileSprites.put(BLOOD_SPONGE_00, getSprite(tilesheet, 0, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_01, getSprite(tilesheet, 1, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_02, getSprite(tilesheet, 2, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_03, getSprite(tilesheet, 3, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_04, getSprite(tilesheet, 4, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_05, getSprite(tilesheet, 5, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_06, getSprite(tilesheet, 6, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_07, getSprite(tilesheet, 7, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_08, getSprite(tilesheet, 8, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_09, getSprite(tilesheet, 9, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_10, getSprite(tilesheet, 10, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_11, getSprite(tilesheet, 11, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_12, getSprite(tilesheet, 12, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_13, getSprite(tilesheet, 13, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_14, getSprite(tilesheet, 14, 26, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SPONGE_15, getSprite(tilesheet, 15, 26, 1, 1, SPRITE_SIZE));

        //--Floor--
        tileSprites.put(WOOD, getSprite(tilesheet, 0, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(STAR_WOOD, getSprite(tilesheet, 1, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(BROKEN_WOOD,  getSprite(tilesheet, 2, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(LONG_WOOD, getSprite(tilesheet, 3, 1, 1, 1, SPRITE_SIZE));

        tileSprites.put(STONE, getSprite(tilesheet, 0, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(X_STONE, getSprite(tilesheet, 1, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(RUBBLE_STONE,  getSprite(tilesheet, 2, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(SKULL_STONE, getSprite(tilesheet, 3, 2, 1, 1, SPRITE_SIZE));

        tileSprites.put(GRASS, getSprite(tilesheet, 0, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(PATCH_GRASS, getSprite(tilesheet, 1, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(MUSHROOM_GRASS,  getSprite(tilesheet, 2, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(GRASS_TUFT, getSprite(tilesheet, 3, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(GRASS_LEAF, getSprite(tilesheet, 4, 3, 1, 1, SPRITE_SIZE));

        tileSprites.put(SAND, getSprite(tilesheet, 0, 4, 1, 1, SPRITE_SIZE));
        tileSprites.put(SAND_ROCK, getSprite(tilesheet, 1, 4, 1, 1, SPRITE_SIZE));
        tileSprites.put(SAND_CACTUS,  getSprite(tilesheet, 2, 4, 1, 1, SPRITE_SIZE));

        tileSprites.put(BLOOD, getSprite(tilesheet, 0, 5, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_SHINE, getSprite(tilesheet, 1, 5, 1, 1, SPRITE_SIZE));
        tileSprites.put(BLOOD_EYE, getSprite(tilesheet, 2, 5, 1, 1, SPRITE_SIZE));


        //-----ITEM SPRITES-----
        PImage itemsheet = app.loadImage("/assets/sprites/itemsheet.png");
        int itemSize = 16;

        //Stats
        itemSprites.put("SPEED_ICON", getSprite(itemsheet, 0, 0, 1, 1, itemSize));
        itemSprites.put("DEFENCE_ICON", getSprite(itemsheet, 1, 0, 1, 1, itemSize));
        itemSprites.put("ATTACK_ICON", getSprite(itemsheet, 2, 0, 1, 1, itemSize));
        itemSprites.put("VITALITY_ICON", getSprite(itemsheet, 3, 0, 1, 1, itemSize));
        itemSprites.put("WISDOM_ICON", getSprite(itemsheet, 4, 0, 1, 1, itemSize));

        //Scrolls
        itemSprites.put("SCROLL_1", getSprite(itemsheet, 0, 1, 1, 1, itemSize));
        itemSprites.put("SCROLL_2", getSprite(itemsheet, 1, 1, 1, 1, itemSize));
        itemSprites.put("SCROLL_3", getSprite(itemsheet, 2, 1, 1, 1, itemSize));
        itemSprites.put("SCROLL_4", getSprite(itemsheet, 3, 1, 1, 1, itemSize));

        //Weapons
        itemSprites.put("WAND", getSprite(itemsheet, 0, 2, 1, 1, itemSize));
        itemSprites.put("STAFF", getSprite(itemsheet, 1, 2, 1, 1, itemSize));
        itemSprites.put("SPEAR", getSprite(itemsheet, 2, 2, 1, 1, itemSize));
        itemSprites.put("BOW", getSprite(itemsheet, 3, 2, 1, 1, itemSize));
        itemSprites.put("WAND_TIP", getSprite(itemsheet, 4, 2, 1, 1, itemSize));
        itemSprites.put("STAFF_TIP", getSprite(itemsheet, 5, 2, 1, 1, itemSize));
        itemSprites.put("SPEAR_TIP", getSprite(itemsheet, 6, 2, 1, 1, itemSize));
        itemSprites.put("BOW_TIP", getSprite(itemsheet, 7, 2, 1, 1, itemSize));

        itemSprites.put("GREENROD", getSprite(itemsheet, 0, 5, 1, 1, itemSize));
        itemSprites.put("REDROD", getSprite(itemsheet, 1, 5, 1, 1, itemSize));

        //Abilities
        itemSprites.put("BOOTS", getSprite(itemsheet, 0, 3, 1, 1, itemSize));
        itemSprites.put("FIREBOMB", getSprite(itemsheet, 1, 3, 1, 1, itemSize));
        itemSprites.put("TELESCOPE", getSprite(itemsheet, 2, 3, 1, 1, itemSize));
        itemSprites.put("TELEPORT", getSprite(itemsheet, 3, 3, 1, 1, itemSize));
        itemSprites.put("SPELLBOMB", getSprite(itemsheet, 4, 3, 1, 1, itemSize));

        //Armour
        itemSprites.put("LEATHER_ARMOUR", getSprite(itemsheet, 0, 4, 1, 1, itemSize));
        itemSprites.put("STEEL_ARMOUR", getSprite(itemsheet, 1, 4, 1, 1, itemSize));
        itemSprites.put("GILDED_LEATHER_ARMOUR", getSprite(itemsheet, 2, 4, 1, 1, itemSize));
        itemSprites.put("STRONG_STEEL_ARMOUR", getSprite(itemsheet, 3, 4, 1, 1, itemSize));
        itemSprites.put("BLOOD_ARMOUR", getSprite(itemsheet, 4, 4, 1, 1, itemSize));

        //Guns
        itemSprites.put("SHOTGUN", getSprite(itemsheet, 0, 7, 1, 1, itemSize));
        itemSprites.put("PISTOL", getSprite(itemsheet, 1, 7, 1, 1, itemSize));
        itemSprites.put("SNIPER", getSprite(itemsheet, 2, 7, 1, 1, itemSize));
        itemSprites.put("MACHINE_GUN", getSprite(itemsheet, 7, 2, 1, 1, itemSize));

        //-----STAT SPRITES-----

        PImage statusSheet = app.loadImage("/assets/sprites/statussheet.png");
        int statusSize = 8;

        statusSprites.put("SWIFT", getSprite(statusSheet, 0, 0, 1, 1, statusSize));
        statusSprites.put("SLOWED", getSprite(statusSheet, 1, 0, 1, 1, statusSize));
        statusSprites.put("HEALING", getSprite(statusSheet, 2, 0, 1, 1, statusSize));
        statusSprites.put("SICK", getSprite(statusSheet, 3, 0, 1, 1, statusSize));
        statusSprites.put("DAMAGING", getSprite(statusSheet, 0, 1, 1, 1, statusSize));
        statusSprites.put("WEAK", getSprite(statusSheet, 1, 1, 1, 1, statusSize));
        statusSprites.put("ARMOURED", getSprite(statusSheet, 2, 1, 1, 1, statusSize));
        statusSprites.put("ARMOURBREAK", getSprite(statusSheet, 3, 1, 1, 1, statusSize));
        statusSprites.put("BEZERK", getSprite(statusSheet, 0, 2, 1, 1, statusSize));
        statusSprites.put("DAZED", getSprite(statusSheet, 1, 2, 1, 1, statusSize));
        statusSprites.put("SMART", getSprite(statusSheet, 2, 2, 1, 1, statusSize));
        statusSprites.put("CURSED", getSprite(statusSheet, 3, 2, 1, 1, statusSize));
        statusSprites.put("QUEST", getSprite(statusSheet, 0, 3, 1, 1, statusSize));


        //-----PLAYER EFFECT SPRITES-----

        PImage playerStatusSheet = app.loadImage("/assets/sprites/playerstatussheet.png");
        int playerStatusSize = 16;

        playerStatusSprites.put("DAMAGING", getSprite(playerStatusSheet, 0, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("WEAK", getSprite(playerStatusSheet, 0, 1, 1, 1, playerStatusSize));
        playerStatusSprites.put("ARMOURED", getSprite(playerStatusSheet, 1, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("ARMOURBREAK", getSprite(playerStatusSheet, 1, 1, 1, 1, playerStatusSize));
        playerStatusSprites.put("HEALING", getSprite(playerStatusSheet, 2, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("SICK", getSprite(playerStatusSheet, 2, 1, 1, 1, playerStatusSize));
        playerStatusSprites.put("SMART", getSprite(playerStatusSheet, 3, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("CURSED", getSprite(playerStatusSheet, 3, 1, 1, 1, playerStatusSize));
        playerStatusSprites.put("SWIFT", getSprite(playerStatusSheet, 4, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("SLOWED", getSprite(playerStatusSheet, 4, 1, 1, 1, playerStatusSize));
        playerStatusSprites.put("BEZERK", getSprite(playerStatusSheet, 5, 0, 1, 1, playerStatusSize));
        playerStatusSprites.put("DAZED", getSprite(playerStatusSheet, 5, 1, 1, 1, playerStatusSize));

        //-----DROP SPRITES-----
        PImage dropsheet = app.loadImage("/assets/sprites/dropsheet.png");
        int dropSize = 8;

        dropSprites.put("BAG_0", getSprite(dropsheet, 0, 0, 1, 1, dropSize));
        dropSprites.put("BAG_1", getSprite(dropsheet, 1, 0, 1, 1, dropSize));
        dropSprites.put("BAG_2", getSprite(dropsheet, 2, 0, 1, 1, dropSize));
        dropSprites.put("ORB", getSprite(dropsheet, 0, 1, 1, 1, dropSize));
        dropSprites.put("PACK", getSprite(dropsheet, 1, 1, 1, 1, dropSize));


        dropSprites.put("BLOOD_0", getSprite(dropsheet, 2, 1, 1, 1, dropSize));
        dropSprites.put("BLOOD_1", getSprite(dropsheet, 3, 1, 1, 1, dropSize));
        dropSprites.put("BLOOD_2", getSprite(dropsheet, 4, 1, 1, 1, dropSize));
        dropSprites.put("BLOOD_3", getSprite(dropsheet, 5, 1, 1, 1, dropSize));
        dropSprites.put("BLOOD_4", getSprite(dropsheet, 6, 1, 1, 1, dropSize));

        dropSprites.put("PORTAL_CAVE", getSprite(dropsheet, 0, 2, 2, 2, dropSize));
        dropSprites.put("PORTAL_CAVE2", getSprite(dropsheet, 0, 4, 2, 2, dropSize));
        dropSprites.put("PORTAL_CAVE3", getSprite(dropsheet, 0, 6, 2, 2, dropSize));
        dropSprites.put("PORTAL_CAVE_ACTIVE", getSprite(dropsheet, 0, 8, 2, 2, dropSize));

        dropSprites.put("PORTAL_GRASS", getSprite(dropsheet, 2, 2, 2, 2, dropSize));
        dropSprites.put("PORTAL_GRASS2", getSprite(dropsheet, 2, 4, 2, 2, dropSize));
        dropSprites.put("PORTAL_GRASS3", getSprite(dropsheet, 2, 6, 2, 2, dropSize));
        dropSprites.put("PORTAL_GRASS_ACTIVE", getSprite(dropsheet, 2, 8, 2, 2, dropSize));

        dropSprites.put("PORTAL_CELLAR", getSprite(dropsheet, 4, 2, 2, 2, dropSize));
        dropSprites.put("PORTAL_CELLAR2", getSprite(dropsheet, 4, 4, 2, 2, dropSize));
        dropSprites.put("PORTAL_CELLAR3", getSprite(dropsheet, 4, 6, 2, 2, dropSize));
        dropSprites.put("PORTAL_CELLAR_ACTIVE", getSprite(dropsheet, 4, 8, 2, 2, dropSize));

        dropSprites.put("PORTAL_DESERT", getSprite(dropsheet, 6, 2, 2, 2, dropSize));
        dropSprites.put("PORTAL_DESERT2", getSprite(dropsheet, 6, 4, 2, 2, dropSize));
        dropSprites.put("PORTAL_DESERT3", getSprite(dropsheet, 6, 6, 2, 2, dropSize));
        dropSprites.put("PORTAL_DESERT_ACTIVE", getSprite(dropsheet, 6, 8, 2, 2, dropSize));

        dropSprites.put("PORTAL_BLOOD", getSprite(dropsheet, 8, 2, 2, 2, dropSize));
        dropSprites.put("PORTAL_BLOOD2", getSprite(dropsheet, 8, 4, 2, 2, dropSize));
        dropSprites.put("PORTAL_BLOOD3", getSprite(dropsheet, 8, 6, 2, 2, dropSize));
        dropSprites.put("PORTAL_BLOOD_ACTIVE", getSprite(dropsheet, 8, 8, 2, 2, dropSize));

        //-----GUI SPRITES----
        PImage sheet = app.loadImage("/assets/sprites/spritesheet.png");
        int guiSize = 16;

        guiSprites.put("PLAY", getSprite(sheet, 0, 0, 2, 1, guiSize));
        guiSprites.put("MENU", getSprite(sheet, 2, 0, 2, 1, guiSize));
        guiSprites.put("BACK", getSprite(sheet, 4, 0, 2, 1, guiSize));
        guiSprites.put("OPTIONS", getSprite(sheet, 6, 0, 2, 1, guiSize));
        guiSprites.put("PAUSE", getSprite(sheet, 0, 1, 1, 1, guiSize));
        guiSprites.put("EXIT", getSprite(sheet, 1, 1, 2, 1, guiSize));
        guiSprites.put("LOAD", getSprite(sheet, 5, 1, 2, 1, guiSize));
        guiSprites.put("DELETE", getSprite(sheet, 7, 1, 1, 1, guiSize));
        guiSprites.put("CURSOR", getSprite(sheet, 6, 6, 2, 2, guiSize));
        guiSprites.put("BAR", getSprite(sheet, 0, 2, 3, 0.5f, guiSize));
        guiSprites.put("BAR_SMOOTH", getSprite(sheet, 0, 2.5f, 3, 0.5f, guiSize));
        guiSprites.put("BOSS_BAR", getSprite(sheet, 0, 3, 8, 1, guiSize));
        guiSprites.put("QUEST", getSprite(sheet, 0, 5, 1, 1, guiSize));
        guiSprites.put("BLANK_2x1", getSprite(sheet, 0, 7, 2, 1, guiSize));
        guiSprites.put("BLANK_1x1", getSprite(sheet, 2, 7, 1, 1, guiSize));
        guiSprites.put("QUICK", getSprite(sheet, 3, 1, 2, 1, guiSize));
        guiSprites.put("YES", getSprite(sheet, 3, 3, 2, 1, guiSize));
        guiSprites.put("NO", getSprite(sheet, 5, 2, 2, 1, guiSize));
        guiSprites.put("TICK", getSprite(sheet, 7, 2, 1, 1, guiSize));

        //-----CHAR SPRITES-----
        PImage charsheet = app.loadImage("/assets/sprites/charsheet.png");
        int charSize = 8;

        //Load player sprites
        charSprites.put("FACE_FRONT", getSprite(charsheet, 12, 0, 1, 1, charSize));
        charSprites.put("FACE_BACK", getSprite(charsheet, 13, 0, 1, 1, charSize));
        charSprites.put("FACE_LEFT", getSprite(charsheet, 12, 1, 1, 1, charSize));
        charSprites.put("FACE_RIGHT", getSprite(charsheet, 13, 1, 1, 1, charSize));

        charSprites.put("BODY_FRONT_DEFAULT", getSprite(charsheet, 14, 0, 1, 1, charSize));
        charSprites.put("BODY_BACK_DEFAULT", getSprite(charsheet, 15, 0, 1, 1, charSize));
        charSprites.put("BODY_LEFT_DEFAULT", getSprite(charsheet, 14, 1, 1, 1, charSize));
        charSprites.put("BODY_RIGHT_DEFAULT", getSprite(charsheet, 15, 1, 1, 1, charSize));

        charSprites.put("BODY_FRONT", getSprite(charsheet, 14, 2, 1, 1, charSize));
        charSprites.put("BODY_BACK", getSprite(charsheet, 15, 2, 1, 1, charSize));
        charSprites.put("BODY_LEFT", getSprite(charsheet, 14, 3, 1, 1, charSize));
        charSprites.put("BODY_RIGHT", getSprite(charsheet, 15, 3, 1, 1, charSize));

        //Load chomp sprites
        charSprites.put("CHOMP_BLACK_SMALL", getSprite(charsheet, 6, 0, 1, 1, charSize));
        charSprites.put("CHOMP_WHITE_SMALL", getSprite(charsheet, 6, 1, 1, 1, charSize));
        charSprites.put("CHOMP_BLACK", getSprite(charsheet, 4, 0, 2, 2, charSize));
        charSprites.put("CHOMP_WHITE", getSprite(charsheet, 4, 2, 2, 2, charSize));
        charSprites.put("CHOMP_BOSS", getSprite(charsheet, 0, 0, 4, 4, charSize));

        //Load grass mobs
        charSprites.put("ROSE_SHORT", getSprite(charsheet, 7, 0, 1, 1, charSize));
        charSprites.put("ROSE_LONG", getSprite(charsheet, 7, 1, 1, 1, charSize));
        charSprites.put("DAISY_SHORT", getSprite(charsheet, 8, 0, 1, 1, charSize));
        charSprites.put("DAISY_LONG", getSprite(charsheet, 9, 0, 1, 1, charSize));

        //Load cave mobs
        charSprites.put("SPIDER", getSprite(charsheet, 6, 2, 1, 1, charSize));
        charSprites.put("SPIDER_JUMPING", getSprite(charsheet, 7, 2, 1, 1, charSize));
        charSprites.put("SPIDER_SPITTING", getSprite(charsheet, 8, 2, 1, 1, charSize));
        charSprites.put("CRAWLER_LEFT", getSprite(charsheet, 10, 4, 1, 1, charSize));
        charSprites.put("CRAWLER_RIGHT", getSprite(charsheet, 11, 4, 1, 1, charSize));
        charSprites.put("CRAWLER", getSprite(charsheet, 12, 4, 1, 1, charSize));
        charSprites.put("CRAWLER_BITING", getSprite(charsheet, 13, 4, 1, 1, charSize));
        charSprites.put("BAT_SPREAD", getSprite(charsheet, 6, 3, 1, 1, charSize));
        charSprites.put("BAT_FLAPPING", getSprite(charsheet, 7, 3, 1, 1, charSize));
        charSprites.put("BAT_BITING", getSprite(charsheet, 8, 3, 1, 1, charSize));

        //Load desert mobs
        charSprites.put("CACTUS", getSprite(charsheet, 10, 0, 1, 1, charSize));
        charSprites.put("CACTUS_TALL", getSprite(charsheet, 11, 0, 1, 1, charSize));
        charSprites.put("ANTLION_LEFT", getSprite(charsheet, 9, 2, 1, 1, charSize));
        charSprites.put("ANTLION_RIGHT", getSprite(charsheet, 9, 3, 1, 1, charSize));
        charSprites.put("SCORPION", getSprite(charsheet, 8, 1, 2, 1, charSize));
        charSprites.put("SCORPION_BITING", getSprite(charsheet, 10, 1, 2, 1, charSize));

        //Load element sprites
        charSprites.put("FIRE_ELEMENTAL", getSprite(charsheet, 0, 4, 1, 1, charSize));
        charSprites.put("ICE_ELEMENTAL", getSprite(charsheet, 1, 4, 1, 1, charSize));
        charSprites.put("MAGIC_ELEMENTAL", getSprite(charsheet, 0, 5, 1, 1, charSize));
        charSprites.put("POISON_ELEMENTAL", getSprite(charsheet, 1, 5, 1, 1, charSize));

        charSprites.put("FIRE_ELEMENTAL_2", getSprite(charsheet, 2, 4, 1, 1, charSize));
        charSprites.put("ICE_ELEMENTAL_2", getSprite(charsheet, 3, 4, 1, 1, charSize));
        charSprites.put("MAGIC_ELEMENTAL_2", getSprite(charsheet, 2, 5, 1, 1, charSize));
        charSprites.put("POISON_ELEMENTAL_2", getSprite(charsheet, 3, 5, 1, 1, charSize));

        charSprites.put("FIRE_ELEMENTAL_3", getSprite(charsheet, 4, 4, 1, 1, charSize));
        charSprites.put("ICE_ELEMENTAL_3", getSprite(charsheet, 5, 4, 1, 1, charSize));
        charSprites.put("MAGIC_ELEMENTAL_3", getSprite(charsheet, 4, 5, 1, 1, charSize));
        charSprites.put("POISON_ELEMENTAL_3", getSprite(charsheet, 5, 5, 1, 1, charSize));

        charSprites.put("FIRE_ELEMENTAL_4", getSprite(charsheet, 6, 4, 1, 1, charSize));
        charSprites.put("ICE_ELEMENTAL_4", getSprite(charsheet, 7, 4, 1, 1, charSize));
        charSprites.put("MAGIC_ELEMENTAL_4", getSprite(charsheet, 6, 5, 1, 1, charSize));
        charSprites.put("POISON_ELEMENTAL_4", getSprite(charsheet, 7, 5, 1, 1, charSize));

        charSprites.put("KING_ELEMENTAL", getSprite(charsheet, 0, 8, 4, 4, charSize));
        charSprites.put("ELEMENTAL_BODYGUARDS_1", getSprite(charsheet, 0, 12, 4, 4, charSize));
        charSprites.put("ELEMENTAL_BODYGUARDS_2", getSprite(charsheet, 4, 12, 4, 4, charSize));
        charSprites.put("ELEMENTAL_BODYGUARDS_3", getSprite(charsheet, 8, 12, 4, 4, charSize));
        charSprites.put("ELEMENTAL_BODYGUARDS_4", getSprite(charsheet, 12, 12, 4, 4, charSize));

        //Load goblin sprites
        charSprites.put("GOBLIN_ARCHER", getSprite(charsheet, 6, 8, 1, 1, charSize));
        charSprites.put("GOBLIN_ARCHER_WALKING", getSprite(charsheet, 7, 8, 1, 1, charSize));
        charSprites.put("GOBLIN_WARRIOR", getSprite(charsheet, 4, 8, 1, 1, charSize));
        charSprites.put("GOBLIN_WARRIOR_WALKING", getSprite(charsheet, 5, 8, 1, 1, charSize));
        charSprites.put("GOBLIN_MAGE", getSprite(charsheet, 4, 9, 1, 1, charSize));
        charSprites.put("GOBLIN_MAGE_WALKING", getSprite(charsheet, 5, 9, 1, 1, charSize));
        charSprites.put("GOBLIN_SPEARMAN", getSprite(charsheet, 6, 9, 1, 1, charSize));
        charSprites.put("GOBLIN_SPEARMAN_WALKING", getSprite(charsheet, 7, 9, 1, 1, charSize));

        //Load mini-boss sprites
        charSprites.put("GOBLIN_BOXER", getSprite(charsheet, 0, 6, 2, 2, charSize));
        charSprites.put("GOBLIN_BOXER_WALKING", getSprite(charsheet, 2, 6, 2, 2, charSize));
        charSprites.put("GOBLIN_BOXER_ATTACKING", getSprite(charsheet, 4, 6, 2, 2, charSize));
        charSprites.put("BASILISK", getSprite(charsheet, 6, 6, 2, 2, charSize));
        charSprites.put("BASILISK_MOVING", getSprite(charsheet, 8, 6, 2, 2, charSize));
        charSprites.put("BASILISK_BITING", getSprite(charsheet, 10, 6, 2, 2, charSize));
        charSprites.put("BLOOD_MONSTER", getSprite(charsheet, 12, 6, 2, 2, charSize));
        charSprites.put("BLOOD_MONSTER_MOVING", getSprite(charsheet, 14, 6, 2, 2, charSize));

        //Load blood monsters
        charSprites.put("DEMON_CAMEL", getSprite(charsheet, 12, 2, 2, 1, charSize));
        charSprites.put("DEMON_CAMEL_WALKING", getSprite(charsheet, 12, 3, 2, 1, charSize));
        charSprites.put("DEMON_NIBBLER", getSprite(charsheet, 10, 2, 1, 1, charSize));
        charSprites.put("DEMON_NIBBLER_CROUCHING", getSprite(charsheet, 10, 3, 1, 1, charSize));
        charSprites.put("DEMON_BITER", getSprite(charsheet, 11, 2, 1, 1, charSize));
        charSprites.put("DEMON_BITER_BITING", getSprite(charsheet, 11, 3, 1, 1, charSize));

        //Load misc mobs
        charSprites.put("GHOST_BALL_WHITE", getSprite(charsheet, 14, 4, 1, 1, charSize));
        charSprites.put("GHOST_BALL_BLACK", getSprite(charsheet, 15, 4, 1, 1, charSize));
        charSprites.put("SLUG", getSprite(charsheet, 10, 5, 2, 1, charSize));
        charSprites.put("SLUG_COILED", getSprite(charsheet, 12, 5, 2, 1, charSize));
        charSprites.put("SLUG_BITING", getSprite(charsheet, 14, 5, 2, 1, charSize));
        charSprites.put("WOLF", getSprite(charsheet, 8, 4, 2, 1, charSize));
        charSprites.put("WOLF_BITING", getSprite(charsheet, 8, 5, 2, 1, charSize));

        //-----BULLET SPRITES-----
        PImage projectilesheet = app.loadImage("/assets/sprites/projectilesheet.png");
        int projectileSize = 8;

        projectileSprites.put("GREENROD", getSprite(projectilesheet, 0, 0, 1, 1, projectileSize));
        projectileSprites.put("FIREBALL", getSprite(projectilesheet, 1, 0, 1, 1, projectileSize));
        projectileSprites.put("SPEAR_TIP", getSprite(projectilesheet, 2, 0, 1, 1, projectileSize));
        projectileSprites.put("ARROW_TIP", getSprite(projectilesheet, 3, 0, 1, 1, projectileSize));

        projectileSprites.put("WAND", getSprite(projectilesheet, 0, 1, 1, 1, projectileSize));
        projectileSprites.put("STAFF", getSprite(projectilesheet, 1, 1, 1, 1, projectileSize));
        projectileSprites.put("SPEAR", getSprite(projectilesheet, 2, 1, 1, 1, projectileSize));
        projectileSprites.put("ARROW", getSprite(projectilesheet, 3, 1, 1, 1, projectileSize));

        projectileSprites.put("LEAF", getSprite(projectilesheet, 0, 2, 1, 1, projectileSize));
        projectileSprites.put("THORN", getSprite(projectilesheet, 1, 2, 1, 1, projectileSize));
        projectileSprites.put("STINGER", getSprite(projectilesheet, 2, 2, 1, 1, projectileSize));

        app.cursor(guiSprites.get("CURSOR"));
    }

    public static PImage getSprite(PImage image, float x, float y, float w, float h, int spriteSize) {
        return image.get((int)(x * spriteSize), (int)(y * spriteSize), (int)(w * spriteSize), (int)(h * spriteSize));
    }
}
