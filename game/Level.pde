class Level {
  
  private int[][] tileMap;
  public int w, h;
  public PVector start;
  private String name;
  public ArrayList<Enemy> enemies  = new ArrayList<Enemy>();
  
  PGraphics background, tiles;
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;
    background = createGraphics(w * TILE_SIZE, h * TILE_SIZE);
    tiles = createGraphics(w * TILE_SIZE, h * TILE_SIZE);
  }
  
  public void show() {
    //will need to offset this by the player's position
    image(background, 0, 0);
    image(tiles, 0, 0);
  }
  
  private void generateImages() {

    //generate an image based off the tile map;
    background.beginDraw();
    //background.background(0);
    background = crossHatch(background);
    background.endDraw();
    
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int i = 0; i < w; i ++) {
      for(int j = 0; j < h; j ++) {
        if(tileMap[i][j] == 0) continue;
        else if(tileMap[i][j] == 1) { //basic ground
          tiles.stroke(100);
          tiles.fill(255);
        } else if(tileMap[i][j] == 2) { //player start
          tiles.stroke(100);
          tiles.fill(255, 0, 0);
        }        
        tiles.rect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        
      }
    }
    tiles.endDraw();
  }
  
  public void setTiles(int[][] tiles) {
    tileMap = tiles;
    generateImages();
  }
  
  public int[][] getTiles() {
    return tileMap;
  }
  
  public void setTile(int t, int i, int j) {
    tileMap[i][j] = t;
    generateImages();
  }
  
  public int getTile(int i, int j) {
    return tileMap[i][j];
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
}
