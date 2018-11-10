//Tiles you cannont walk through are <= 0
//Tiles you can walk through are > 0
//Avoid using -1 as it can mean errors

//----Generic Tiles-----
public final static int WALL   = 0;
public final static int FLOOR  = 1;

//----Wall Tiles------
public final static int STONE_BRICK_WALL        = -2;
public final static int STONE_BRICK_BOTTOM_WALL = -3;
public final static int DARK_STONE_BRICK_WALL   = -4;

public final static int ROCK_WALL          = -5;
public final static int ROCK_BOTTOM_WALL   = -6; //lmao feels
public final static int DARK_ROCK_WALL     = -7;

//----Floor Tiles------
public final static int WOOD         = 3;
public final static int STAR_WOOD    = 4;
public final static int LONG_WOOD    = 5;
public final static int BROKEN_WOOD  = 6;


public final static int STONE        = 7;
public final static int RUBBLE_STONE = 8;
public final static int X_STONE      = 9;
public final static int SKULL_STONE  = 10;

public void loadAssets() {
  tileSprites = new HashMap<Integer, PImage>();
  itemSprites = new HashMap<Integer, PImage>();
  sprites = new HashMap<String, PImage>();
  saveChars = new HashMap<Integer, Character>();
  
  //-----TILE SPRITES-----
  PImage tilesheet = loadImage("/assets/sprites/tilesheet.png");
  
  tileSprites.put(WALL, getSprite(tilesheet, 2, 0, 1, 1));
  tileSprites.put(FLOOR, getSprite(tilesheet, 0, 1, 1, 1));
  
  //--Wall--
  tileSprites.put(STONE_BRICK, getSprite(tilesheet, 0, 0, 1, 1));
  tileSprites.put(STONE_BRICK_BOTTOM, getSprite(tilesheet, 1, 0, 1, 1));
  tileSprites.put(DARK_STONE_BRICK, getSprite(tilesheet, 2, 0, 1, 1));
  
  tileSprites.put(ROCK, getSprite(tilesheet, 0, 2, 1, 1));
  tileSprites.put(ROCK_BOTTOM, getSprite(tilesheet, 1, 2, 1, 1));
  tileSprites.put(DARK_ROCK, getSprite(tilesheet, 2, 2, 1, 1));

  //--Floor--
  tileSprites.put(WOOD, getSprite(tilesheet, 0, 1, 1, 1));
  tileSprites.put(STAR_WOOD, getSprite(tilesheet, 1, 1, 1, 1));
  tileSprites.put(BROKEN_WOOD,  getSprite(tilesheet, 2, 1, 1, 1));
  tileSprites.put(LONG_WOOD, getSprite(tilesheet, 3, 1, 1, 1));
  
  tileSprites.put(STONE, getSprite(tilesheet, 0, 3, 1, 1));
  tileSprites.put(X_STONE, getSprite(tilesheet, 1, 3, 1, 1));
  tileSprites.put(RUBBLE_STONE,  getSprite(tilesheet, 2, 3, 1, 1));
  tileSprites.put(SKULL_STONE, getSprite(tilesheet, 3, 3, 1, 1));
  
  
  //-----ITEM SPRITES-----
  PImage itemsheet = loadImage("/assets/sprites/itemsheet.png");
  
  //-----OTHER SPRITES----
  PImage sheet = loadImage("/assets/sprites/spritesheet.png");
  sprites.put("PLAY", getSprite(sheet, 0, 0, 2, 1));  
  sprites.put("MENU", getSprite(sheet, 2, 0, 2, 1));
  sprites.put("BACK", getSprite(sheet, 4, 0, 2, 1));
  sprites.put("OPTIONS", getSprite(sheet, 6, 0, 2, 1));
  sprites.put("PAUSE", getSprite(sheet, 0, 1, 1, 1));
  sprites.put("CURSOR", getSprite(sheet, 6, 6, 2, 2));
  
  //-----SAVECHARS-----
  //saveChars.put(BORDER, '#');
  //saveChars.put(WALL, '@');
  //saveChars.put(FLOOR, '.');
  //saveChars.put(SPAWN, 'X');
  //saveChars.put(CRACK, '~');
}

public PImage getSprite(PImage image, int x, int y, int w, int h) {
  return image.get(x * SPRITE_SIZE, y * SPRITE_SIZE, w * SPRITE_SIZE, h * SPRITE_SIZE);
}


class TileSet {
  //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
  
  public int innerWall, wall, bottomWall, floor, spawn;
  public ArrayList<Integer> extras = new ArrayList<Integer>();  //Extras are variations of floor tiles to spice shit up a bit
  
}
