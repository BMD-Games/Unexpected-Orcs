package com.bmd.Sprites;

import com.bmd.App.Main;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.Util;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Sprites {
    public static HashMap<Integer, BufferedImage> tileSprites;
    public static HashMap<String, BufferedImage> itemSprites;
    public static HashMap<String, BufferedImage> guiSprites;
    public static HashMap<String, BufferedImage> charSprites;
    public static HashMap<String, BufferedImage> projectileSprites;
    public static HashMap<String, BufferedImage> dropSprites;
    public static HashMap<String, BufferedImage> statusSprites;
    public static HashMap<String, BufferedImage> playerStatusSprites;

    public final static int SPRITE_SIZE = 16;

    public static void loadAssests() {
        tileSprites = new HashMap<Integer, BufferedImage>();
        itemSprites = new HashMap<String, BufferedImage>();
        guiSprites = new HashMap<String, BufferedImage>();
        charSprites = new HashMap<String, BufferedImage>();
        projectileSprites = new HashMap<String, BufferedImage>();
        dropSprites = new HashMap<String, BufferedImage>();
        statusSprites = new HashMap<String, BufferedImage>();
        playerStatusSprites = new HashMap<String, BufferedImage>();

        //-----TILE SPRITES-----
        BufferedImage tilesheet = Util.loadImage("com/bmd/assets/sprites/tilesheet.png");

        tileSprites.put(Tiles.WALL, getSprite(tilesheet, 0, 0, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.FLOOR, getSprite(tilesheet, 1, 0, 1, 1, SPRITE_SIZE));

        //--TEST--
        tileSprites.put(Tiles.TEST_00, getSprite(tilesheet, 0, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_01, getSprite(tilesheet, 1, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_02, getSprite(tilesheet, 2, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_03, getSprite(tilesheet, 3, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_04, getSprite(tilesheet, 4, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_05, getSprite(tilesheet, 5, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_06, getSprite(tilesheet, 6, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_07, getSprite(tilesheet, 7, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_08, getSprite(tilesheet, 8, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_09, getSprite(tilesheet, 9, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_10, getSprite(tilesheet, 10, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_11, getSprite(tilesheet, 11, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_12, getSprite(tilesheet, 12, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_13, getSprite(tilesheet, 13, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_14, getSprite(tilesheet, 14, 31, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.TEST_15, getSprite(tilesheet, 15, 31, 1, 1, SPRITE_SIZE));

        //--STONE BRICK--
        tileSprites.put(Tiles.STONE_BRICK_00, getSprite(tilesheet, 0, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_01, getSprite(tilesheet, 1, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_02, getSprite(tilesheet, 2, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_03, getSprite(tilesheet, 3, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_04, getSprite(tilesheet, 4, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_05, getSprite(tilesheet, 5, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_06, getSprite(tilesheet, 6, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_07, getSprite(tilesheet, 7, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_08, getSprite(tilesheet, 8, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_09, getSprite(tilesheet, 9, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_10, getSprite(tilesheet, 10, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_11, getSprite(tilesheet, 11, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_12, getSprite(tilesheet, 12, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_13, getSprite(tilesheet, 13, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_14, getSprite(tilesheet, 14, 30, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STONE_BRICK_15, getSprite(tilesheet, 15, 30, 1, 1, SPRITE_SIZE));

        //--ROCK--
        tileSprites.put(Tiles.ROCK_00, getSprite(tilesheet, 0, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_01, getSprite(tilesheet, 1, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_02, getSprite(tilesheet, 2, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_03, getSprite(tilesheet, 3, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_04, getSprite(tilesheet, 4, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_05, getSprite(tilesheet, 5, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_06, getSprite(tilesheet, 6, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_07, getSprite(tilesheet, 7, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_08, getSprite(tilesheet, 8, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_09, getSprite(tilesheet, 9, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_10, getSprite(tilesheet, 10, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_11, getSprite(tilesheet, 11, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_12, getSprite(tilesheet, 12, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_13, getSprite(tilesheet, 13, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_14, getSprite(tilesheet, 14, 29, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.ROCK_15, getSprite(tilesheet, 15, 29, 1, 1, SPRITE_SIZE));

        //--HEDGE--
        tileSprites.put(Tiles.HEDGE_00, getSprite(tilesheet, 0, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_01, getSprite(tilesheet, 1, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_02, getSprite(tilesheet, 2, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_03, getSprite(tilesheet, 3, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_04, getSprite(tilesheet, 4, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_05, getSprite(tilesheet, 5, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_06, getSprite(tilesheet, 6, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_07, getSprite(tilesheet, 7, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_08, getSprite(tilesheet, 8, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_09, getSprite(tilesheet, 9, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_10, getSprite(tilesheet, 10, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_11, getSprite(tilesheet, 11, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_12, getSprite(tilesheet, 12, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_13, getSprite(tilesheet, 13, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_14, getSprite(tilesheet, 14, 28, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.HEDGE_15, getSprite(tilesheet, 15, 28, 1, 1, SPRITE_SIZE));

        tileSprites.put(Tiles.SANDSTONE_00, getSprite(tilesheet, 0, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_01, getSprite(tilesheet, 1, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_02, getSprite(tilesheet, 2, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_03, getSprite(tilesheet, 3, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_04, getSprite(tilesheet, 4, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_05, getSprite(tilesheet, 5, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_06, getSprite(tilesheet, 6, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_07, getSprite(tilesheet, 7, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_08, getSprite(tilesheet, 8, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_09, getSprite(tilesheet, 9, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_10, getSprite(tilesheet, 10, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_11, getSprite(tilesheet, 11, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_12, getSprite(tilesheet, 12, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_13, getSprite(tilesheet, 13, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_14, getSprite(tilesheet, 14, 27, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SANDSTONE_15, getSprite(tilesheet, 15, 27, 1, 1, SPRITE_SIZE));

        //--Floor--
        tileSprites.put(Tiles.WOOD, getSprite(tilesheet, 0, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.STAR_WOOD, getSprite(tilesheet, 1, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.BROKEN_WOOD,  getSprite(tilesheet, 2, 1, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.LONG_WOOD, getSprite(tilesheet, 3, 1, 1, 1, SPRITE_SIZE));

        tileSprites.put(Tiles.STONE, getSprite(tilesheet, 0, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.X_STONE, getSprite(tilesheet, 1, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.RUBBLE_STONE,  getSprite(tilesheet, 2, 2, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SKULL_STONE, getSprite(tilesheet, 3, 2, 1, 1, SPRITE_SIZE));

        tileSprites.put(Tiles.GRASS, getSprite(tilesheet, 0, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.PATCH_GRASS, getSprite(tilesheet, 1, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.MUSHROOM_GRASS,  getSprite(tilesheet, 2, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.GRASS_TUFT, getSprite(tilesheet, 3, 3, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.GRASS_LEAF, getSprite(tilesheet, 4, 3, 1, 1, SPRITE_SIZE));

        tileSprites.put(Tiles.SAND, getSprite(tilesheet, 0, 4, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SAND_ROCK, getSprite(tilesheet, 1, 4, 1, 1, SPRITE_SIZE));
        tileSprites.put(Tiles.SAND_CACTUS,  getSprite(tilesheet, 2, 4, 1, 1, SPRITE_SIZE));


        //-----ITEM SPRITES-----
        BufferedImage itemsheet = Util.loadImage("com/bmd/assets/sprites/itemsheet.png");
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

        BufferedImage statusSheet = Util.loadImage("com/bmd/assets/sprites/statussheet.png");
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

        BufferedImage playerStatusSheet = Util.loadImage("com/bmd/assets/sprites/playerstatussheet.png");
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
        BufferedImage dropsheet = Util.loadImage("com/bmd/assets/sprites/dropsheet.png");
        int dropSize = 8;

        dropSprites.put("BAG_0", getSprite(dropsheet, 0, 0, 1, 1, dropSize));
        dropSprites.put("BAG_1", getSprite(dropsheet, 1, 0, 1, 1, dropSize));
        dropSprites.put("BAG_2", getSprite(dropsheet, 2, 0, 1, 1, dropSize));
        dropSprites.put("ORB", getSprite(dropsheet, 0, 1, 1, 1, dropSize));
        dropSprites.put("PORTAL_CAVE", getSprite(dropsheet, 0, 2, 1, 1, dropSize));
        dropSprites.put("PORTAL_GRASS", getSprite(dropsheet, 1, 2, 1, 1, dropSize));
        dropSprites.put("PORTAL_CELLAR", getSprite(dropsheet, 2, 2, 1, 1, dropSize));

        //-----GUI SPRITES----
        BufferedImage sheet = Util.loadImage("com/bmd/assets/sprites/spritesheet.png");
        int guiSize = 16;

        guiSprites.put("PLAY", getSprite(sheet, 0, 0, 2, 1, guiSize));
        guiSprites.put("MENU", getSprite(sheet, 2, 0, 2, 1, guiSize));
        guiSprites.put("BACK", getSprite(sheet, 4, 0, 2, 1, guiSize));
        guiSprites.put("OPTIONS", getSprite(sheet, 6, 0, 2, 1, guiSize));
        guiSprites.put("PAUSE", getSprite(sheet, 0, 1, 1, 1, guiSize));
        guiSprites.put("EXIT", getSprite(sheet, 1, 1, 2, 1, guiSize));
        guiSprites.put("NEW", getSprite(sheet, 3, 1, 2, 1, guiSize));
        guiSprites.put("LOAD", getSprite(sheet, 5, 1, 2, 1, guiSize));
        guiSprites.put("BAR", getSprite(sheet, 0, 4, 3, 1, guiSize));
        guiSprites.put("QUEST", getSprite(sheet, 0, 5, 1, 1, guiSize));
        guiSprites.put("BLANK_2x1", getSprite(sheet, 0, 7, 2, 1, guiSize));
        guiSprites.put("BLANK_1x1", getSprite(sheet, 2, 7, 1, 1, guiSize));
        guiSprites.put("SAVE", getSprite(sheet, 3, 2, 2, 1, guiSize));
        guiSprites.put("SAVE1", getSprite(sheet, 5, 2, 1, 1, guiSize));
        guiSprites.put("SAVE2", getSprite(sheet, 6, 2, 1, 1, guiSize));
        guiSprites.put("SAVE3", getSprite(sheet, 7, 2, 1, 1, guiSize));

        //-----CHAR SPRITES-----
        BufferedImage charsheet = Util.loadImage("com/bmd/assets/sprites/charsheet.png");
        int charSize = 8;

        //Load player sprites
        charSprites.put("FACE_FRONT", getSprite(charsheet, 12, 0, 1, 1, charSize));
        charSprites.put("FACE_BACK", getSprite(charsheet, 13, 0, 1, 1, charSize));
        charSprites.put("FACE_LEFT", getSprite(charsheet, 12, 1, 1, 1, charSize));
        charSprites.put("FACE_RIGHT", getSprite(charsheet, 13, 1, 1, 1, charSize));

        charSprites.put("BODY_FRONT", getSprite(charsheet, 14, 0, 1, 1, charSize));
        charSprites.put("BODY_BACK", getSprite(charsheet, 15, 0, 1, 1, charSize));
        charSprites.put("BODY_LEFT", getSprite(charsheet, 14, 1, 1, 1, charSize));
        charSprites.put("BODY_RIGHT", getSprite(charsheet, 15, 1, 1, 1, charSize));

        //Load chomp sprites
        charSprites.put("CHOMP_BLACK_SMALL", getSprite(charsheet, 6, 0, 1, 1, charSize));
        charSprites.put("CHOMP_WHITE_SMALL", getSprite(charsheet, 6, 1, 1, 1, charSize));
        charSprites.put("CHOMP_BLACK", getSprite(charsheet, 4, 0, 2, 2, charSize));
        charSprites.put("CHOMP_WHITE", getSprite(charsheet, 4, 2, 2, 2, charSize));
        charSprites.put("CHOMP_BOSS", getSprite(charsheet, 0, 0, 4, 4, charSize));

        //Load grass mobs
        charSprites.put("ROSE", getSprite(charsheet, 7, 0, 1, 1, charSize));
        charSprites.put("DAISY", getSprite(charsheet, 7, 1, 1, 1, charSize));

        //Load cave mobs
        charSprites.put("SPIDER", getSprite(charsheet, 6, 2, 1, 1, charSize));
        charSprites.put("CRAWLER", getSprite(charsheet, 7, 2, 1, 1, charSize));
        charSprites.put("BAT_SPREAD", getSprite(charsheet, 6, 3, 1, 1, charSize));
        charSprites.put("BAT_FLAPPING", getSprite(charsheet, 7, 3, 1, 1, charSize));

        //Load desert mobs
        charSprites.put("CACTUS", getSprite(charsheet, 8, 0, 1, 1, charSize));
        charSprites.put("ANTLION", getSprite(charsheet, 9, 0, 1, 1, charSize));
        charSprites.put("SCORPION", getSprite(charsheet, 8, 1, 2, 1, charSize));

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
        charSprites.put("GOBLIN_ARCHER", getSprite(charsheet, 0, 6, 1, 1, charSize));
        charSprites.put("GOBLIN_SPEARMAN", getSprite(charsheet, 1, 6, 1, 1, charSize));
        charSprites.put("GOBLIN_WARRIOR", getSprite(charsheet, 0, 7, 1, 1, charSize));
        charSprites.put("GOBLIN_MAGE", getSprite(charsheet, 1, 7, 1, 1, charSize));

        charSprites.put("GOBLIN_BOXER", getSprite(charsheet, 2, 6, 2, 2, charSize));
        charSprites.put("BASILISK", getSprite(charsheet, 4, 6, 2, 2, charSize));


        //-----BULLET SPRITES-----
        BufferedImage projectilesheet = Util.loadImage("com/bmd/assets/sprites/projectilesheet.png");
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

        Main.primary.getScene().setCursor(new ImageCursor(new Image("com/bmd/assets/sprites/cursor.png")));

    }

    private static BufferedImage getSprite(BufferedImage spriteSheet, int x, int y, int w, int h, int spriteSize) {
        return spriteSheet.getSubimage(x * spriteSize, y * spriteSize, w * spriteSize, h * spriteSize);
    }


}
