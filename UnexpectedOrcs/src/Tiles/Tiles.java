package Tiles;

public class Tiles {
    //----ZONES-----
    public final static int NO_SPAWN = 0;
    public final static int BOSS     = 1;
    public final static int GENERAL  = 2;

    //----Tile groups for outlining----
    public final static String testWallGroup = "testWallGroup";
    public final static String testFloorGroup = "testFloorGroup";

    public final static String woodGroup = "woodGroup";
    public final static String stoneGroup = "stoneGroup";
    public final static String grassGroup = "grassGroup";
    public final static String sandGroup = "sandGroup";
    public final static String bloodGroup = "bloodGroup";
    public final static String cobbleGroup = "cobbleGroup";
    public final static String stoneTileGroup = "stoneTileGroup";

    public final static String bloodSpongeGroup = "bloodSpongeGroup";
    public final static String sandStoneGroup = "sandStoneGroup";
    public final static String hedgeGroup = "hedgeGroup";
    public final static String rockGroup = "rockGroup";
    public final static String stoneBrickGroup = "stoneBrickGroup";


//Tiles you cannont walk through are <= 0
//Tiles you can walk through are > 0
//Avoid using -1 as it can mean errors

    //----Generic Tiles----- Used for map generation
    public final static int WALL   = 0;
    public final static int FLOOR  = 1;

    //----Wall Tiles------
    public final static Tile WALL_TILE = new WallTile("WALL", testWallGroup);
    public final static Tile STONE_BRICK = new WallTile("STONE_BRICK", stoneBrickGroup);
    public final static Tile ROCK = new WallTile("ROCK", rockGroup);
    public final static Tile HEDGE = new WallTile("HEDGE", hedgeGroup);
    public final static Tile SANDSTONE = new WallTile("SANDSTONE", sandStoneGroup);
    public final static Tile BLOOD_SPONGE = new WallTile("BLOOD_SPONGE", bloodSpongeGroup);

    //----Floor Tiles------
    public final static Tile FLOOR_TILE = new FloorTile("FLOOR", testFloorGroup);

    public final static Tile WOOD = new FloorTile("WOOD", woodGroup);
    public final static Tile STAR_WOOD = new FloorTile("STAR_WOOD", woodGroup);
    public final static Tile LONG_WOOD = new FloorTile("LONG_WOOD", woodGroup);
    public final static Tile BROKEN_WOOD = new FloorTile("BROKEN_WOOD", woodGroup);

    public final static Tile STONE = new FloorTile("STONE", stoneGroup);
    public final static Tile X_STONE = new FloorTile("X_STONE", stoneGroup);
    public final static Tile RUBBLE_STONE = new FloorTile("RUBBLE_STONE", stoneGroup);
    public final static Tile SKULL_STONE = new FloorTile("SKULL_STONE", stoneGroup);

    public final static Tile GRASS = new FloorTile("GRASS", grassGroup);
    public final static Tile PATCH_GRASS = new FloorTile("PATCH_GRASS", grassGroup);
    public final static Tile MUSHROOM_GRASS = new FloorTile("MUSHROOM_GRASS", grassGroup);
    public final static Tile GRASS_TUFT = new FloorTile("GRASS_TUFT", grassGroup);
    public final static Tile GRASS_LEAF = new FloorTile("GRASS_LEAF", grassGroup);

    public final static Tile SAND = new FloorTile("SAND", sandGroup);
    public final static Tile SAND_ROCK = new FloorTile("SAND_ROCK", sandGroup);
    public final static Tile SAND_CACTUS = new FloorTile("SAND_CACTUS", sandGroup);

    public final static Tile BLOOD = new FloorTile("BLOOD", bloodGroup);
    public final static Tile BLOOD_SHINE = new FloorTile("BLOOD_SHINE", bloodGroup);
    public final static Tile BLOOD_EYE = new FloorTile("BLOOD_EYE", bloodGroup);


    public final static Tile COBBLE = new FloorTile("COBBLE", cobbleGroup);
    public final static Tile STONE_TILE = new FloorTile("STONE_TILE", stoneTileGroup);
}
