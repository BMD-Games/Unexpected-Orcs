package Tiles;

public class Tiles {
    //----ZONES-----
    public final static int NO_SPAWN = 0;
    public final static int BOSS     = 1;
    public final static int GENERAL  = 2;



//Tiles you cannont walk through are <= 0
//Tiles you can walk through are > 0
//Avoid using -1 as it can mean errors

    //----Generic Tiles----- Used for map generation
    public final static int WALL   = 0;
    public final static int FLOOR  = 1;

    //----Wall Tiles------
    public final static Tile WALL_TILE = new WallTile("WALL");
    public final static Tile STONE_BRICK = new WallTile("STONE_BRICK");
    public final static Tile ROCK = new WallTile("ROCK");
    public final static Tile HEDGE = new WallTile("HEDGE");
    public final static Tile SANDSTONE = new WallTile("SANDSTONE");
    public final static Tile BLOOD_SPONGE = new WallTile("BLOOD_SPONGE");

    //----Floor Tiles------
    public final static Tile FLOOR_TILE = new FloorTile("FLOOR");

    public final static Tile WOOD = new FloorTile("WOOD");
    public final static Tile STAR_WOOD = new FloorTile("STAR_WOOD");
    public final static Tile LONG_WOOD = new FloorTile("LONG_WOOD");
    public final static Tile BROKEN_WOOD = new FloorTile("BROKEN_WOOD");

    public final static Tile STONE = new FloorTile("STONE");
    public final static Tile X_STONE = new FloorTile("X_STONE");
    public final static Tile RUBBLE_STONE = new FloorTile("RUBBLE_STONE");
    public final static Tile SKULL_STONE = new FloorTile("SKULL_STONE");

    public final static Tile GRASS = new FloorTile("GRASS");
    public final static Tile PATCH_GRASS = new FloorTile("PATCH_GRASS");
    public final static Tile MUSHROOM_GRASS = new FloorTile("MUSHROOM_GRASS");
    public final static Tile GRASS_TUFT = new FloorTile("GRASS_TUFT");
    public final static Tile GRASS_LEAF = new FloorTile("GRASS_LEAF");

    public final static Tile SAND = new FloorTile("SAND");
    public final static Tile SAND_ROCK = new FloorTile("SAND_ROCK");
    public final static Tile SAND_CACTUS = new FloorTile("SAND_CACTUS");

    public final static Tile BLOOD = new FloorTile("BLOOD");
    public final static Tile BLOOD_SHINE = new FloorTile("BLOOD_SHINE");
    public final static Tile BLOOD_EYE = new FloorTile("BLOOD_EYE");


    public final static Tile COBBLE = new FloorTile("COBBLE");
    public final static Tile STONE_TILE = new FloorTile("STONE_TILE");
}
