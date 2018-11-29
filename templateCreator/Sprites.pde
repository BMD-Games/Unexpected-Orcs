public void loadAssets() {
  
  tileSprites = new HashMap<Integer, PImage>();
  
  //-----TILE SPRITES-----
  PImage tilesheet = loadImage("../game/assets/sprites/tilesheet.png");
  
  tileSprites.put(WALL, getSprite(tilesheet, 0, 0, 1, 1, SPRITE_SIZE));
  tileSprites.put(FLOOR, getSprite(tilesheet, 1, 0, 1, 1, SPRITE_SIZE));
  
  //--TEST--
  tileSprites.put(TEST_00, getSprite(tilesheet, 0, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_01, getSprite(tilesheet, 1, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_02, getSprite(tilesheet, 2, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_03, getSprite(tilesheet, 3, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_04, getSprite(tilesheet, 4, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_05, getSprite(tilesheet, 5, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_06, getSprite(tilesheet, 6, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_07, getSprite(tilesheet, 7, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_08, getSprite(tilesheet, 8, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_09, getSprite(tilesheet, 9, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_10, getSprite(tilesheet, 10, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_11, getSprite(tilesheet, 11, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_12, getSprite(tilesheet, 12, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_13, getSprite(tilesheet, 13, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_14, getSprite(tilesheet, 14, 31, 1, 1, SPRITE_SIZE));
  tileSprites.put(TEST_15, getSprite(tilesheet, 15, 31, 1, 1, SPRITE_SIZE));
  
  //--STONE BRICK--
  tileSprites.put(STONE_BRICK_00, getSprite(tilesheet, 0, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_01, getSprite(tilesheet, 1, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_02, getSprite(tilesheet, 2, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_03, getSprite(tilesheet, 3, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_04, getSprite(tilesheet, 4, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_05, getSprite(tilesheet, 5, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_06, getSprite(tilesheet, 6, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_07, getSprite(tilesheet, 7, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_08, getSprite(tilesheet, 8, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_09, getSprite(tilesheet, 9, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_10, getSprite(tilesheet, 10, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_11, getSprite(tilesheet, 11, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_12, getSprite(tilesheet, 12, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_13, getSprite(tilesheet, 13, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_14, getSprite(tilesheet, 14, 30, 1, 1, SPRITE_SIZE));
  tileSprites.put(STONE_BRICK_15, getSprite(tilesheet, 15, 30, 1, 1, SPRITE_SIZE));
  
  //--ROCK--
  tileSprites.put(ROCK_00, getSprite(tilesheet, 0, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_01, getSprite(tilesheet, 1, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_02, getSprite(tilesheet, 2, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_03, getSprite(tilesheet, 3, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_04, getSprite(tilesheet, 4, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_05, getSprite(tilesheet, 5, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_06, getSprite(tilesheet, 6, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_07, getSprite(tilesheet, 7, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_08, getSprite(tilesheet, 8, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_09, getSprite(tilesheet, 9, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_10, getSprite(tilesheet, 10, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_11, getSprite(tilesheet, 11, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_12, getSprite(tilesheet, 12, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_13, getSprite(tilesheet, 13, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_14, getSprite(tilesheet, 14, 29, 1, 1, SPRITE_SIZE));
  tileSprites.put(ROCK_15, getSprite(tilesheet, 15, 29, 1, 1, SPRITE_SIZE));
  
  //--HEDGE--
  tileSprites.put(HEDGE_00, getSprite(tilesheet, 0, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_01, getSprite(tilesheet, 1, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_02, getSprite(tilesheet, 2, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_03, getSprite(tilesheet, 3, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_04, getSprite(tilesheet, 4, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_05, getSprite(tilesheet, 5, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_06, getSprite(tilesheet, 6, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_07, getSprite(tilesheet, 7, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_08, getSprite(tilesheet, 8, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_09, getSprite(tilesheet, 9, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_10, getSprite(tilesheet, 10, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_11, getSprite(tilesheet, 11, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_12, getSprite(tilesheet, 12, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_13, getSprite(tilesheet, 13, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_14, getSprite(tilesheet, 14, 28, 1, 1, SPRITE_SIZE));
  tileSprites.put(HEDGE_15, getSprite(tilesheet, 15, 28, 1, 1, SPRITE_SIZE));

  //--Floor--
  tileSprites.put(WOOD, getSprite(tilesheet, 0, 1, 1, 1, SPRITE_SIZE));
  tileSprites.put(STAR_WOOD, getSprite(tilesheet, 1, 1, 1, 1, SPRITE_SIZE));
  tileSprites.put(BROKEN_WOOD,  getSprite(tilesheet, 2, 1, 1, 1, SPRITE_SIZE));
  tileSprites.put(LONG_WOOD, getSprite(tilesheet, 3, 1, 1, 1, SPRITE_SIZE));
  
  tileSprites.put(STONE, getSprite(tilesheet, 0, 2, 1, 1, SPRITE_SIZE));
  tileSprites.put(X_STONE, getSprite(tilesheet, 1, 2, 1, 1, SPRITE_SIZE));
  tileSprites.put(RUBBLE_STONE,  getSprite(tilesheet, 2, 2, 1, 1, SPRITE_SIZE));
  tileSprites.put(SKULL_STONE, getSprite(tilesheet, 3, 2, 1, 1, SPRITE_SIZE));
  
  tileSprites.put(GRASS, getSprite(tilesheet, 0, 3, 1, 1, SPRITE_SIZE));
  tileSprites.put(PATCH_GRASS, getSprite(tilesheet, 1, 3, 1, 1, SPRITE_SIZE));
  tileSprites.put(MUSHROOM_GRASS,  getSprite(tilesheet, 2, 3, 1, 1, SPRITE_SIZE));
  tileSprites.put(GRASS_TUFT, getSprite(tilesheet, 3, 3, 1, 1, SPRITE_SIZE));
  tileSprites.put(GRASS_LEAF, getSprite(tilesheet, 4, 3, 1, 1, SPRITE_SIZE));
}

public PImage getSprite(PImage image, int x, int y, int w, int h, int spriteSize) {
  return image.get(x * spriteSize, y * spriteSize, w * spriteSize, h * spriteSize);
}

class TileSet {
  //this tile holds reference to a collection of Sprites, these can be used when generating a dungeon, to make them look nice
  public int floor, spawn;
  public ArrayList<Integer> extras = new ArrayList<Integer>();  //Extras are variations of floor tiles to spice shit up a bit
  public int[] walls = new int[16];
}
