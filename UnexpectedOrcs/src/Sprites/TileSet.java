package Sprites;

import Tiles.Tile;

import java.util.ArrayList;
import static Tiles.Tiles.*;

public class TileSet {

    //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
    public Tile wall, floor, spawn;
    public ArrayList<Tile> extras = new ArrayList<Tile>();  //Extras are variations of floor tiles to spice shit up a bit

    public float chance = 0.05f;

    public Tile wall() {
        return new Tile(wall);
    }

    public Tile floor() {
        return new Tile(floor);
    }

    public Tile extras(int index) {
        return new Tile(extras.get(index));
    }

    public Tile spawn() {
        return new Tile(spawn);
    }

    public static TileSet grassTileset() {
        TileSet tileset = new TileSet();

        tileset.wall = HEDGE;

        tileset.floor = GRASS;
        tileset.spawn = PATCH_GRASS;
        tileset.extras.add(MUSHROOM_GRASS);
        tileset.extras.add(GRASS_TUFT);
        tileset.extras.add(GRASS_LEAF);

        return tileset;
    }

    public static TileSet caveTileset() {
        TileSet tileset = new TileSet();

        tileset.wall = ROCK;

        tileset.floor = STONE;
        tileset.spawn = X_STONE;
        tileset.extras.add(RUBBLE_STONE);
        tileset.extras.add(SKULL_STONE);

        return tileset;
    }

    public static TileSet cellarTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = STONE_BRICK;

        tileset.floor = WOOD;
        tileset.spawn = STAR_WOOD;
        tileset.extras.add(BROKEN_WOOD);
        tileset.extras.add(LONG_WOOD);

        return tileset;
    }

    public static TileSet desertTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = SANDSTONE;

        tileset.floor = SAND;
        tileset.spawn = SAND;
        tileset.extras.add(SAND_ROCK);
        tileset.extras.add(SAND_CACTUS);

        return tileset;
    }

    public static TileSet bloodTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = BLOOD_SPONGE;

        tileset.floor = BLOOD;
        tileset.spawn = BLOOD_SHINE;
        tileset.extras.add(BLOOD_EYE);

        tileset.chance = 0.01f;

        return tileset;
    }

}
