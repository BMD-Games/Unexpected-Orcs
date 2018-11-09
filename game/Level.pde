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
    if(show) image(tiles, 0, 0);
  }
  
  private void generateImages() {
    //generate an image based off the tile map;
    int x = TILE_SIZE;
    int TILE_SIZE = x/2;
    
    background.beginDraw();
    background.background(0);
    background.noStroke();
    background.fill(50);
    
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int i = 0; i < w; i ++) {
      for(int j = 0; j < h; j ++) {
        if(tileMap[i][j] == 0) {
          if(isBorder(tileMap, i, j)) {
            //Below was used to make shitty hatch pattern
            /**background.pushMatrix();
            background.translate(i * TILE_SIZE + TILE_SIZE/2, j * TILE_SIZE + TILE_SIZE/2);
            background.rotate(random(PI));
            for(int k = -9; k <= 9; k++) {
              background.fill(50);
              if(k%2 == 0) background.fill(255);
              background.rect(k * TILE_SIZE/8, -TILE_SIZE * 2, TILE_SIZE/8, TILE_SIZE * 4);
            }
            background.popMatrix();**/
            background.rect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
          }
          continue;
        } else if(tileMap[i][j] == 1) { //basic ground
          tiles.stroke(150);
          tiles.fill(255);
        } else if(tileMap[i][j] == 2) { //player start
          tiles.stroke(100);
          tiles.fill(255, 0, 0);
        }        
        tiles.rect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        
      }
    }
    background.endDraw();
    tiles.endDraw();
  }
  
  public boolean isBorder(int[][]tiles, int i, int j) {
    boolean edge = isEdge(tiles, i, j);
    boolean border = ((numNeighbours(tiles, i, j, 1) < 8 && tiles[i][j] == 0) || (numNeighbours(tiles, i, j, 1) > 0 && tiles[i][j] != 0));
    return (!edge && border);
  }
  
  public boolean isEdge(int[][] tiles, int i, int j) {
    return (i < 1 || j < 1 || i >= w - 1 || j >= h - 1);
  }
  public void generateStart() {
    while(start == null) {
      int i = floor(random(2, w-3));
      int j = floor(random(2, h-3));
      if(tileMap[i][j] == 1) {
        tileMap[i][j] = 2;
        start = new PVector(i, j);
      }
    }
  }
  
  
  //-----Getters and setters------
  public void setTiles(int[][] tiles) {
    tileMap = tiles;
    generateStart();
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
  
  public void setLevelName(String name) {
    this.name = name;
  }
  
  public String getLevelName() {
    return name;
  }
}
