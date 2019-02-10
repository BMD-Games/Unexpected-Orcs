package Sprites;

import java.util.ArrayList;
import static Tiles.Tiles.*;

public class TileSet {

    //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
    public int floor, spawn;
    public ArrayList<Integer> extras = new ArrayList<Integer>();  //Extras are variations of floor tiles to spice shit up a bit
    public int[] walls = new int[16];

    public static int[] testWalls() {
        int[] wall = new int[16];

        wall[0] = TEST_00;
        wall[1] = TEST_01;
        wall[2] = TEST_02;
        wall[3] = TEST_03;
        wall[4] = TEST_04;
        wall[5] = TEST_05;
        wall[6] = TEST_06;
        wall[7] = TEST_07;
        wall[8] = TEST_08;
        wall[9] = TEST_09;
        wall[10] = TEST_10;
        wall[11] = TEST_11;
        wall[12] = TEST_12;
        wall[13] = TEST_13;
        wall[14] = TEST_14;
        wall[15] = TEST_15;

        return wall;
    }

    public static int[] stoneBrickWalls() {
        int[] wall = new int[16];

        wall[0] = STONE_BRICK_00;
        wall[1] = STONE_BRICK_01;
        wall[2] = STONE_BRICK_02;
        wall[3] = STONE_BRICK_03;
        wall[4] = STONE_BRICK_04;
        wall[5] = STONE_BRICK_05;
        wall[6] = STONE_BRICK_06;
        wall[7] = STONE_BRICK_07;
        wall[8] = STONE_BRICK_08;
        wall[9] = STONE_BRICK_09;
        wall[10] = STONE_BRICK_10;
        wall[11] = STONE_BRICK_11;
        wall[12] = STONE_BRICK_12;
        wall[13] = STONE_BRICK_13;
        wall[14] = STONE_BRICK_14;
        wall[15] = STONE_BRICK_15;

        return wall;
    }

    public static int[] rockWalls() {
        int[] wall = new int[16];

        wall[0] = ROCK_00;
        wall[1] = ROCK_01;
        wall[2] = ROCK_02;
        wall[3] = ROCK_03;
        wall[4] = ROCK_04;
        wall[5] = ROCK_05;
        wall[6] = ROCK_06;
        wall[7] = ROCK_07;
        wall[8] = ROCK_08;
        wall[9] = ROCK_09;
        wall[10] = ROCK_10;
        wall[11] = ROCK_11;
        wall[12] = ROCK_12;
        wall[13] = ROCK_13;
        wall[14] = ROCK_14;
        wall[15] = ROCK_15;

        return wall;
    }

    public static int[] hedgeWalls() {
        int[] wall = new int[16];

        wall[0] = HEDGE_00;
        wall[1] = HEDGE_01;
        wall[2] = HEDGE_02;
        wall[3] = HEDGE_03;
        wall[4] = HEDGE_04;
        wall[5] = HEDGE_05;
        wall[6] = HEDGE_06;
        wall[7] = HEDGE_07;
        wall[8] = HEDGE_08;
        wall[9] = HEDGE_09;
        wall[10] = HEDGE_10;
        wall[11] = HEDGE_11;
        wall[12] = HEDGE_12;
        wall[13] = HEDGE_13;
        wall[14] = HEDGE_14;
        wall[15] = HEDGE_15;

        return wall;
    }

    public static int[] sandstoneWalls() {
        int[] wall = new int[16];

        wall[0] = SANDSTONE_00;
        wall[1] = SANDSTONE_01;
        wall[2] = SANDSTONE_02;
        wall[3] = SANDSTONE_03;
        wall[4] = SANDSTONE_04;
        wall[5] = SANDSTONE_05;
        wall[6] = SANDSTONE_06;
        wall[7] = SANDSTONE_07;
        wall[8] = SANDSTONE_08;
        wall[9] = SANDSTONE_09;
        wall[10] = SANDSTONE_10;
        wall[11] = SANDSTONE_11;
        wall[12] = SANDSTONE_12;
        wall[13] = SANDSTONE_13;
        wall[14] = SANDSTONE_14;
        wall[15] = SANDSTONE_15;

        return wall;
    }

    public static int[] bloodSpongeWalls() {
        int[] wall = new int[16];

        wall[0] = BLOOD_SPONGE_00;
        wall[1] = BLOOD_SPONGE_01;
        wall[2] = BLOOD_SPONGE_02;
        wall[3] = BLOOD_SPONGE_03;
        wall[4] = BLOOD_SPONGE_04;
        wall[5] = BLOOD_SPONGE_05;
        wall[6] = BLOOD_SPONGE_06;
        wall[7] = BLOOD_SPONGE_07;
        wall[8] = BLOOD_SPONGE_08;
        wall[9] = BLOOD_SPONGE_09;
        wall[10] = BLOOD_SPONGE_10;
        wall[11] = BLOOD_SPONGE_11;
        wall[12] = BLOOD_SPONGE_12;
        wall[13] = BLOOD_SPONGE_13;
        wall[14] = BLOOD_SPONGE_14;
        wall[15] = BLOOD_SPONGE_15;

        return wall;
    }

    public static TileSet testTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = testWalls();
        tileset.floor = FLOOR;
        tileset.spawn = FLOOR;

        return tileset;
    }

    public static TileSet grassTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = hedgeWalls();

        tileset.floor = GRASS;
        tileset.spawn = PATCH_GRASS;
        tileset.extras.add(MUSHROOM_GRASS);
        tileset.extras.add(GRASS_TUFT);
        tileset.extras.add(GRASS_LEAF);

        return tileset;
    }

    public static TileSet caveTileset() {
        TileSet tileset = new TileSet();

        tileset.walls = rockWalls();

        tileset.floor = STONE;
        tileset.spawn = X_STONE;
        tileset.extras.add(RUBBLE_STONE);
        tileset.extras.add(SKULL_STONE);

        return tileset;
    }

    public static TileSet cellarTileSet() {
        TileSet tileset = new TileSet();

        tileset.walls = stoneBrickWalls();

        tileset.floor = WOOD;
        tileset.spawn = STAR_WOOD;
        tileset.extras.add(BROKEN_WOOD);
        tileset.extras.add(LONG_WOOD);

        return tileset;
    }

    public static TileSet desertTileSet() {
        TileSet tileset = new TileSet();

        tileset.walls = sandstoneWalls();

        tileset.floor = SAND;
        tileset.spawn = SAND;
        tileset.extras.add(SAND_ROCK);
        tileset.extras.add(SAND_CACTUS);

        return tileset;
    }

    public static TileSet bloodTileSet() {
        TileSet tileset = new TileSet();

        tileset.walls = bloodSpongeWalls();

        tileset.floor = BLOOD;
        tileset.spawn = BLOOD_SHINE;
        tileset.extras.add(BLOOD_EYE);

        return tileset;
    }

}
