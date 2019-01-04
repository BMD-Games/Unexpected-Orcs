package com.bmd.Tiles;

import java.util.ArrayList;

public class TileSet {
    //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
    public int floor, spawn;
    public ArrayList<Integer> extras = new ArrayList<Integer>();  //Extras are variations of floor tiles to spice shit up a bit
    public int[] walls = new int[16];

    public static int[] testWalls() {
        int[] wall = new int[16];

        wall[0] = Tiles.TEST_00;
        wall[1] = Tiles.TEST_01;
        wall[2] = Tiles.TEST_02;
        wall[3] = Tiles.TEST_03;
        wall[4] = Tiles.TEST_04;
        wall[5] = Tiles.TEST_05;
        wall[6] = Tiles.TEST_06;
        wall[7] = Tiles.TEST_07;
        wall[8] = Tiles.TEST_08;
        wall[9] = Tiles.TEST_09;
        wall[10] = Tiles.TEST_10;
        wall[11] = Tiles.TEST_11;
        wall[12] = Tiles.TEST_12;
        wall[13] = Tiles.TEST_13;
        wall[14] = Tiles.TEST_14;
        wall[15] = Tiles.TEST_15;

        return wall;
    }

    public static int[] stoneBrickWalls() {
        int[] wall = new int[16];

        wall[0] = Tiles.STONE_BRICK_00;
        wall[1] = Tiles.STONE_BRICK_01;
        wall[2] = Tiles.STONE_BRICK_02;
        wall[3] = Tiles.STONE_BRICK_03;
        wall[4] = Tiles.STONE_BRICK_04;
        wall[5] = Tiles.STONE_BRICK_05;
        wall[6] = Tiles.STONE_BRICK_06;
        wall[7] = Tiles.STONE_BRICK_07;
        wall[8] = Tiles.STONE_BRICK_08;
        wall[9] = Tiles.STONE_BRICK_09;
        wall[10] = Tiles.STONE_BRICK_10;
        wall[11] = Tiles.STONE_BRICK_11;
        wall[12] = Tiles.STONE_BRICK_12;
        wall[13] = Tiles.STONE_BRICK_13;
        wall[14] = Tiles.STONE_BRICK_14;
        wall[15] = Tiles.STONE_BRICK_15;

        return wall;
    }

    public static int[] rockWalls() {
        int[] wall = new int[16];

        wall[0] = Tiles.ROCK_00;
        wall[1] = Tiles.ROCK_01;
        wall[2] = Tiles.ROCK_02;
        wall[3] = Tiles.ROCK_03;
        wall[4] = Tiles.ROCK_04;
        wall[5] = Tiles.ROCK_05;
        wall[6] = Tiles.ROCK_06;
        wall[7] = Tiles.ROCK_07;
        wall[8] = Tiles.ROCK_08;
        wall[9] = Tiles.ROCK_09;
        wall[10] = Tiles.ROCK_10;
        wall[11] = Tiles.ROCK_11;
        wall[12] = Tiles.ROCK_12;
        wall[13] = Tiles.ROCK_13;
        wall[14] = Tiles.ROCK_14;
        wall[15] = Tiles.ROCK_15;

        return wall;
    }

    public static int[] hedgeWalls() {
        int[] wall = new int[16];

        wall[0] = Tiles.HEDGE_00;
        wall[1] = Tiles.HEDGE_01;
        wall[2] = Tiles.HEDGE_02;
        wall[3] = Tiles.HEDGE_03;
        wall[4] = Tiles.HEDGE_04;
        wall[5] = Tiles.HEDGE_05;
        wall[6] = Tiles.HEDGE_06;
        wall[7] = Tiles.HEDGE_07;
        wall[8] = Tiles.HEDGE_08;
        wall[9] = Tiles.HEDGE_09;
        wall[10] = Tiles.HEDGE_10;
        wall[11] = Tiles.HEDGE_11;
        wall[12] = Tiles.HEDGE_12;
        wall[13] = Tiles.HEDGE_13;
        wall[14] = Tiles.HEDGE_14;
        wall[15] = Tiles.HEDGE_15;

        return wall;
    }

    public static int[] sandstoneWalls() {
        int[] wall = new int[16];

        wall[0] = Tiles.SANDSTONE_00;
        wall[1] = Tiles.SANDSTONE_01;
        wall[2] = Tiles.SANDSTONE_02;
        wall[3] = Tiles.SANDSTONE_03;
        wall[4] = Tiles.SANDSTONE_04;
        wall[5] = Tiles.SANDSTONE_05;
        wall[6] = Tiles.SANDSTONE_06;
        wall[7] = Tiles.SANDSTONE_07;
        wall[8] = Tiles.SANDSTONE_08;
        wall[9] = Tiles.SANDSTONE_09;
        wall[10] = Tiles.SANDSTONE_10;
        wall[11] = Tiles.SANDSTONE_11;
        wall[12] = Tiles.SANDSTONE_12;
        wall[13] = Tiles.SANDSTONE_13;
        wall[14] = Tiles.SANDSTONE_14;
        wall[15] = Tiles.SANDSTONE_15;

        return wall;
    }

    public static TileSet testTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = testWalls();
        tileset.floor = Tiles.FLOOR;
        tileset.spawn = Tiles.FLOOR;

        return tileset;
    }

    public static TileSet grassTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = hedgeWalls();

        tileset.floor = Tiles.GRASS;
        tileset.spawn = Tiles.PATCH_GRASS;
        tileset.extras.add(Tiles.MUSHROOM_GRASS);
        tileset.extras.add(Tiles.GRASS_TUFT);
        tileset.extras.add(Tiles.GRASS_LEAF);

        return tileset;
    }

    public static TileSet caveTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = rockWalls();

        tileset.floor = Tiles.STONE;
        tileset.spawn = Tiles.X_STONE;
        tileset.extras.add(Tiles.RUBBLE_STONE);
        tileset.extras.add(Tiles.SKULL_STONE);

        return tileset;
    }

    public static TileSet cellarTileSet() {
        TileSet tileset = new TileSet();

        tileset.walls = stoneBrickWalls();

        tileset.floor = Tiles.WOOD;
        tileset.spawn = Tiles.STAR_WOOD;
        tileset.extras.add(Tiles.BROKEN_WOOD);
        tileset.extras.add(Tiles.LONG_WOOD);

        return tileset;
    }

    public static TileSet desertTileSet() {
        TileSet tileset = new TileSet();

        tileset.walls = sandstoneWalls();

        tileset.floor = Tiles.SAND;
        tileset.spawn = Tiles.SAND;
        tileset.extras.add(Tiles.SAND_ROCK);
        tileset.extras.add(Tiles.SAND_CACTUS);

        return tileset;
    }
}