package Sprites;

import Tiles.Tile;
import Utility.GenericPair;

import java.util.ArrayList;

import static Tiles.Tiles.*;
import static Utility.Constants.game;

public class TileSet {

    //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
    public Tile wall, floor, spawn, treasureFloor, connectionPath;
    public ArrayList<GenericPair<Tile, Float>> extras = new ArrayList<GenericPair<Tile, Float>>();  //Extras are variations of floor tiles to spice shit up a bit

    public float chance = 0.05f;

    public Tile wall() {
        return new Tile(wall);
    }

    public Tile floor() {
        return new Tile(floor);
    }

    public Tile treasureFloor() {
        return new Tile(treasureFloor);
    }

    public Tile connectionPath() {
        return new Tile(connectionPath);
    }

    public Tile extrasTiles(int index) {
        return new Tile(extras.get(index).a());
    }

    public float totalChance() {
        float total = 0;
        for(GenericPair<Tile, Float> p : extras) {
            total += p.b();
        }
        return total;
    }

    public Tile randomTile() {
        float val = game.random(totalChance());
        float total = 0;
        for(GenericPair<Tile, Float> p : extras) {
            total += p.b();
            if(total >= val) {
                return p.a();
            }
        }
        return extras.get(extras.size() - 1).a();
    }

    public Tile spawn() {
        return new Tile(spawn);
    }

    public static TileSet grassTileset() {
        TileSet tileset = new TileSet();

        tileset.wall = HEDGE;

        tileset.floor = GRASS;
        tileset.spawn = PATCH_GRASS;
        tileset.extras.add(new GenericPair<Tile, Float>(MUSHROOM_GRASS, 1f));
        tileset.extras.add(new GenericPair<Tile, Float>(GRASS_TUFT, 2f));
        tileset.extras.add(new GenericPair<Tile, Float>(GRASS_LEAF, 3f));

        return tileset;
    }

    public static TileSet caveTileset() {
        TileSet tileset = new TileSet();

        tileset.wall = ROCK;

        tileset.floor = STONE;
        tileset.spawn = X_STONE;

        tileset.connectionPath = WOOD;
        tileset.treasureFloor = STONE_TILE;

        tileset.extras.add(new GenericPair<Tile, Float>(RUBBLE_STONE, 10f));
        tileset.extras.add(new GenericPair<Tile, Float>(SKULL_STONE, 1f));

        return tileset;
    }

    public static TileSet cellarTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = STONE_BRICK;

        tileset.floor = WOOD;
        tileset.spawn = STAR_WOOD;

        tileset.connectionPath = WOOD;
        tileset.treasureFloor = STONE_TILE;

        tileset.extras.add(new GenericPair<Tile, Float>(BROKEN_WOOD, 1f));
        tileset.extras.add(new GenericPair<Tile, Float>(LONG_WOOD, 2f));

        return tileset;
    }

    public static TileSet desertTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = SANDSTONE;

        tileset.floor = SAND;
        tileset.spawn = SAND;

        tileset.connectionPath = COMPACT_SAND;
        tileset.treasureFloor = SAND_TILE;

        tileset.extras.add(new GenericPair<Tile, Float>(SAND_ROCK, 3f));
        tileset.extras.add(new GenericPair<Tile, Float>(SAND_CACTUS, 1f));

        return tileset;
    }

    public static TileSet bloodTileSet() {
        TileSet tileset = new TileSet();

        tileset.wall = BLOOD_SPONGE;

        tileset.floor = BLOOD;
        tileset.spawn = BLOOD_SHINE;

        tileset.connectionPath = WOOD;
        tileset.treasureFloor = SAND;

        tileset.extras.add(new GenericPair<Tile, Float>(BLOOD_EYE, 1f));

        tileset.chance = 0.01f;

        return tileset;
    }

}
