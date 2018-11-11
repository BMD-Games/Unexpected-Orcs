//Tiles you cannont walk through are <= 0
//Tiles you can walk through are > 0
//Avoid using -1 as it can mean errors

//----Generic Tiles----- Used for map generation
public final static int WALL   = 0;
public final static int FLOOR  = 1;

//----Wall Tiles------
public final static int STONE_BRICK_WALL        = -2;
public final static int STONE_BRICK_BOTTOM_WALL = -3;
public final static int DARK_STONE_BRICK_WALL   = -4;

public final static int ROCK_WALL          = -5;
public final static int ROCK_BOTTOM_WALL   = -6; //lmao feels
public final static int DARK_ROCK_WALL     = -7;

public final static int HEDGE_WALL          = -8;
public final static int HEDGE_BOTTOM_WALL   = -9; //lmao feels
public final static int DARK_HEDGE_WALL     = -10;

//----Floor Tiles------
public final static int WOOD         = 3;
public final static int STAR_WOOD    = 4;
public final static int LONG_WOOD    = 5;
public final static int BROKEN_WOOD  = 6;


public final static int STONE        = 7;
public final static int X_STONE      = 9;
public final static int RUBBLE_STONE = 9;
public final static int SKULL_STONE  = 10;

public final static int GRASS          = 11;
public final static int PATCH_GRASS    = 12;
public final static int MUSHROOM_GRASS = 13;
public final static int GRASS_TUFT     = 14;
public final static int GRASS_LEAF     = 15;

public void loadAssets() {
  
  tileSprites = new HashMap<Integer, PImage>();
  itemSprites = new HashMap<Integer, PImage>();
  guiSprites = new HashMap<String, PImage>();
  charSprites = new HashMap<String, PImage>();
  
  //-----TILE SPRITES-----
  PImage tilesheet = loadImage("/assets/sprites/tilesheet.png");
  
  tileSprites.put(WALL, getSprite(tilesheet, 2, 0, 1, 1));
  tileSprites.put(FLOOR, getSprite(tilesheet, 0, 1, 1, 1));
  
  //--Wall--
  tileSprites.put(STONE_BRICK_WALL, getSprite(tilesheet, 0, 0, 1, 1));
  tileSprites.put(STONE_BRICK_BOTTOM_WALL, getSprite(tilesheet, 1, 0, 1, 1));
  tileSprites.put(DARK_STONE_BRICK_WALL, getSprite(tilesheet, 2, 0, 1, 1));
  
  tileSprites.put(ROCK_WALL, getSprite(tilesheet, 0, 2, 1, 1));
  tileSprites.put(ROCK_BOTTOM_WALL, getSprite(tilesheet, 1, 2, 1, 1));
  tileSprites.put(DARK_ROCK_WALL, getSprite(tilesheet, 2, 2, 1, 1));
  
  tileSprites.put(HEDGE_WALL, getSprite(tilesheet, 0, 4, 1, 1));
  tileSprites.put(HEDGE_BOTTOM_WALL, getSprite(tilesheet, 1, 4, 1, 1));
  tileSprites.put(DARK_HEDGE_WALL, getSprite(tilesheet, 2, 4, 1, 1));

  //--Floor--
  tileSprites.put(WOOD, getSprite(tilesheet, 0, 1, 1, 1));
  tileSprites.put(STAR_WOOD, getSprite(tilesheet, 1, 1, 1, 1));
  tileSprites.put(BROKEN_WOOD,  getSprite(tilesheet, 2, 1, 1, 1));
  tileSprites.put(LONG_WOOD, getSprite(tilesheet, 3, 1, 1, 1));
  
  tileSprites.put(STONE, getSprite(tilesheet, 0, 3, 1, 1));
  tileSprites.put(X_STONE, getSprite(tilesheet, 1, 3, 1, 1));
  tileSprites.put(RUBBLE_STONE,  getSprite(tilesheet, 2, 3, 1, 1));
  tileSprites.put(SKULL_STONE, getSprite(tilesheet, 3, 3, 1, 1));
  
  tileSprites.put(GRASS, getSprite(tilesheet, 0, 5, 1, 1));
  tileSprites.put(PATCH_GRASS, getSprite(tilesheet, 1, 5, 1, 1));
  tileSprites.put(MUSHROOM_GRASS,  getSprite(tilesheet, 2, 5, 1, 1));
  tileSprites.put(GRASS_TUFT, getSprite(tilesheet, 3, 5, 1, 1));
  tileSprites.put(GRASS_LEAF, getSprite(tilesheet, 4, 5, 1, 1));
  
  
  //-----ITEM SPRITES-----
  PImage itemsheet = loadImage("/assets/sprites/itemsheet.png");
  
  //-----GUI SPRITES----
  PImage sheet = loadImage("/assets/sprites/spritesheet.png");
  guiSprites.put("PLAY", getSprite(sheet, 0, 0, 2, 1));  
  guiSprites.put("MENU", getSprite(sheet, 2, 0, 2, 1));
  guiSprites.put("BACK", getSprite(sheet, 4, 0, 2, 1));
  guiSprites.put("OPTIONS", getSprite(sheet, 6, 0, 2, 1));
  guiSprites.put("PAUSE", getSprite(sheet, 0, 1, 1, 1));
  guiSprites.put("EXIT", getSprite(sheet, 1, 1, 2, 1));
  guiSprites.put("CURSOR", getSprite(sheet, 6, 6, 2, 2));
  
  //-----CHAR SPRITES-----
  PImage charsheet = loadImage("/assets/sprites/charsheet.png");
  
}

public PImage getSprite(PImage image, int x, int y, int w, int h) {
  return image.get(x * SPRITE_SIZE, y * SPRITE_SIZE, w * SPRITE_SIZE, h * SPRITE_SIZE);
}


class TileSet {
  //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
  public int innerWall, wall, bottomWall, floor, spawn;
  public ArrayList<Integer> extras = new ArrayList<Integer>();  //Extras are variations of floor tiles to spice shit up a bit
}

class CharTileSet {
  String[][] movement = new String[4][2]; //first index is for the direction they're facing, second is for frame of animation
  String[] idle = new String[4]; //each direction
    
}

public TileSet grassTileset() {
  TileSet tileset = new TileSet();

  tileset.floor = GRASS;
  tileset.wall = HEDGE_WALL;
  tileset.bottomWall = HEDGE_BOTTOM_WALL;
  tileset.innerWall = DARK_HEDGE_WALL;
  tileset.spawn = PATCH_GRASS;
  tileset.extras.add(MUSHROOM_GRASS);
  tileset.extras.add(GRASS_TUFT);
  tileset.extras.add(GRASS_LEAF);
   
  return tileset;
}

public TileSet caveTileset() {
  TileSet tileset = new TileSet();

  tileset.floor = STONE;
  tileset.wall = ROCK_WALL;
  tileset.bottomWall = ROCK_BOTTOM_WALL;
  tileset.innerWall = DARK_ROCK_WALL;
  tileset.spawn = X_STONE;
  tileset.extras.add(RUBBLE_STONE);
  tileset.extras.add(SKULL_STONE); 
  
  return tileset;
}

public TileSet dungeonTileset() {
  TileSet tileset = new TileSet();

  tileset.floor = WOOD;
  tileset.wall = STONE_BRICK_WALL;
  tileset.bottomWall = STONE_BRICK_BOTTOM_WALL;
  tileset.innerWall = DARK_STONE_BRICK_WALL;
  tileset.spawn = STAR_WOOD;
  tileset.extras.add(BROKEN_WOOD);
  tileset.extras.add(LONG_WOOD);
  
  return tileset;
}
    
