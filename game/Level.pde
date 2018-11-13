class Level {
  
  private int[][] tilesRaw;
  private int[][] tileMap;
  public int w, h;
  public PVector start;
  private String name;
  public ArrayList<Enemy> enemies  = new ArrayList<Enemy>();
  public TileSet tileset  = new TileSet();
  private int xTileOffset, yTileOffset, renderW, renderH, buffer = 2, tileBuffer = width/TILE_SIZE/2;
  
  private PGraphics background, tiles;
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;
    renderW = width/TILE_SIZE + 2 * buffer;
    renderH = height/TILE_SIZE + 2 * buffer;
    
    tiles = createGraphics(width, height);
  }
  
    public PGraphics generateImage() {
    PGraphics pg = createGraphics(SPRITE_SIZE * (w + tileBuffer * 2), SPRITE_SIZE * (h + tileBuffer * 2));
    pg.beginDraw();
    pg.background(0, 0);
    for(int i = 0; i < w + tileBuffer * 2; i ++) {
      for(int j = 0; j < h + tileBuffer * 2; j ++) {
        int tile = tileset.walls[15];
        try{ tile = tileMap[i - tileBuffer][j - tileBuffer]; } catch(Exception e) {}
        pg.image(tileSprites.get(tile), i * SPRITE_SIZE, j * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
      }
    }
    pg.endDraw();
    return pg;
  }
  
  public void update(float x, float y) {
    xTileOffset = (int)x - (width/2)/TILE_SIZE;
    yTileOffset = (int)y - (height/2)/TILE_SIZE;
  }
  
  public void show(PVector renderOffset) {    
    //generate an image based off the tile map;
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int x = 0; x < renderW; x ++) {
      for(int y = 0; y < renderH; y ++) {
        int i = (x + xTileOffset) - buffer;
        int j = (y + yTileOffset) - buffer;
        int tile = tileset.walls[15];
        try{ tile = tileMap[i][j]; } catch(Exception e) {}
        PImage sprite = tileSprites.get(tile);
        tiles.image(sprite, i * TILE_SIZE - renderOffset.x, j * TILE_SIZE - renderOffset.y, (sprite.width * SCALE), (sprite.height * SCALE));
      }
    }
    tiles.endDraw();
    image(tiles, 0, 0);
  }
  
  public boolean isEdge(int[][] tiles, int i, int j) {
    return (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[0].length);
  }
  
  public int[] getNeighbours(int i, int j) {
    int[] n = new int[4];
    try { n[up] = tileMap[i][j-1]; } catch(Exception e) {} //up
    try { n[down] = tileMap[i][j+1]; } catch(Exception e) {} //down
    try { n[left] = tileMap[i-1][j]; } catch(Exception e) {} //left
    try { n[right] = tileMap[i+1][j]; } catch(Exception e) {} //right
    return n;
  }
  
  public void generateStart() {
    while(start == null) {
      int i = floor(random(2, w-3));
      int j = floor(random(2, h-3));
      if(tilesRaw[i][j] > WALL) {
        tilesRaw[i][j] = tileset.spawn;
        start = new PVector(i, j);
      }
    }
  }
  
  private void applyTileSet() {
    tileMap = finishingPass(tilesRaw, tileset);
  }
  
  //-----Getters and setters------
  public void setTiles(int[][] tiles) {
    tilesRaw = tiles;
    generateStart();
    applyTileSet();
    saveLevel();
  }
  
  public int[][] getTiles() {
    return tileMap;
  }
  
  public void setTile(int t, int i, int j) {
    tileMap[i][j] = t;
  }
  
  public int getTile(int i, int j) {
    int tile = WALL;
    try { tile = tileMap[i][j]; } catch(Exception e) {}
    return tile;
  }
  
  public int getWidth() {
    return w;
  }
  
  public int getHeight() {
    return h;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void saveLevel() {
    generateImage().save("/out/image" + name + ".png");
    PrintWriter file = createWriter("/out/" + name + ".txt");
    for(int j = 0; j < h; j ++) {
      for(int i = 0; i < w; i ++) {
        file.print(tilesRaw[i][j]);
        if(i < w -1) file.print(',');
      }
      file.println();
    }
    file.flush();
    file.close();
  }
}


//-----OLD/DEAD CODE THAT MAY BE USEFUL--------
/**background.pushMatrix();
background.translate(i * TILE_SIZE + TILE_SIZE/2, j * TILE_SIZE + TILE_SIZE/2);
background.rotate(random(PI));
for(int k = -9; k <= 9; k++) {
  background.fill(50);
  if(k%2 == 0) background.fill(255);
  background.rect(k * TILE_SIZE/8, -TILE_SIZE * 2, TILE_SIZE/8, TILE_SIZE * 4);
}
background.popMatrix();
**/

/**public void show(PVector renderOffset) {
  image(background.get((int)renderOffset.x + tileBuffer * TILE_SIZE, (int)renderOffset.y + tileBuffer * TILE_SIZE, width, height), 0, 0);
  image(tiles.get((int)renderOffset.x + tileBuffer * TILE_SIZE, (int)renderOffset.y + tileBuffer * TILE_SIZE, width, height), 0, 0);
}**/