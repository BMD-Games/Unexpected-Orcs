//Tiles you cannont walk through are <= 0
public final static int BORDER = -2;
public final static int WALL   = 0;


//Tiles you can walk through are > 0
public final static int FLOOR  = 1;
public final static int SPAWN  = 2;
public final static int CRACK  = 3;

public void loadAssets() {
  tileSprites = new HashMap<Integer, PImage>();
  itemSprites = new HashMap<Integer, PImage>();
  sprites = new HashMap<String, PImage>();
  saveChars = new HashMap<Integer, Character>();
  
  //-----TILE SPRITES-----
  PImage tilesheet = loadImage("/assets/sprites/tilesheet.png");
  
  tileSprites.put(BORDER, getSprite(tilesheet, 0, 0, 1, 1));
  tileSprites.put(WALL,   getSprite(tilesheet, 1, 0, 1, 1));
  tileSprites.put(FLOOR,  getSprite(tilesheet, 2, 0, 1, 1));
  tileSprites.put(SPAWN,  getSprite(tilesheet, 3, 0, 1, 1));
  tileSprites.put(CRACK,  getSprite(tilesheet, 4, 0, 1, 1));
  
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
  saveChars.put(BORDER, '#');
  saveChars.put(WALL, '@');
  saveChars.put(FLOOR, '.');
  saveChars.put(SPAWN, 'X');
  saveChars.put(CRACK, '~');
}

public PImage getSprite(PImage image, int x, int y, int w, int h) {
  return image.get(x * SPRITE_SIZE, y * SPRITE_SIZE, w * SPRITE_SIZE, h * SPRITE_SIZE);
}
