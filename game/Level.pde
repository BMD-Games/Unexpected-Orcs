class Level {
  
  private int[][] tileMap;
  public int w, h;
  public PVector start;
  private String name;
  public ArrayList<Enemy> enemies  = new ArrayList<Enemy>();
  
  private float xRenderOffset, yRenderOffset;
  private int xTileOffset, yTileOffset, renderW, renderH, buffer = 4;
  
  PGraphics background, tiles;
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;
    renderW = width/TILE_SIZE + buffer;
    renderH = height/TILE_SIZE + buffer;
    background = createGraphics(width, height);
    tiles = createGraphics(width, height);
  }
  
  public void update(float x, float y) {
    xRenderOffset = x * TILE_SIZE - (width/2);
    yRenderOffset = y * TILE_SIZE - (height/2);
    xTileOffset = (int)x - (width/2)/TILE_SIZE;
    yTileOffset = (int)y - (height/2)/TILE_SIZE;
  }
  
  public void show() {    
    //generate an image based off the tile map;
    background.beginDraw();
    background.background(0);
    background.noStroke();
    background.fill(50);
    
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int x = 0; x < renderW; x ++) {
      for(int y = 0; y < renderH; y ++) {
        int i = (x + xTileOffset) - buffer/2;
        int j = (y + yTileOffset) - buffer/2;
        int tile = 0;
        try{ tile = tileMap[i][j]; } catch(Exception e) {}
        if(tile == WALL) continue;
        else if(tile == FLOOR) { //basic ground
          tiles.stroke(150);
          tiles.fill(255);
        } else if(tile == SPAWN) { //player start
          tiles.stroke(100);
          tiles.fill(255, 0, 0);
        }        
        tiles.rect(i * TILE_SIZE - xRenderOffset, j * TILE_SIZE - yRenderOffset, TILE_SIZE, TILE_SIZE);        
      }
    }
    background.endDraw();
    tiles.endDraw();
    image(background, 0, 0);
    image(tiles, 0, 0);
  }
  
  public boolean isBorder(int[][]tiles, int i, int j) {
    boolean edge = isEdge(tiles, i, j);
    boolean border = false;
    try {
      border = ((numNeighbours(tiles, i, j, 1) < 8 && tiles[i][j] == WALL) || (numNeighbours(tiles, i, j, 1) > 0 && tiles[i][j] != WALL));
    } catch(Exception e) {}
    return (!edge && border);
  }
  
  public boolean isEdge(int[][] tiles, int i, int j) {
    return (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[0].length);
  }
  
  public int numNeighbours(int[][] tiles, int x, int y, int dist) {
    //counts the number of walls in a square centred at x, y, spanning dist in each direction
    int num = 0;
    for(int i = -dist; i <= dist; i ++) {
      for(int j = -dist; j <= dist; j ++) {
        if(i == 0 && j == 0) continue; //skip if on the centre tile
        try {
          if(tiles[x + i][y + j] == WALL) num ++; //if the tile is a wall
        } catch(Exception e) {}
      }
    }
    return num;
  }
  
  public int[] getNeighbours(int i, int j) {
    int[] n = new int[4];
    try { n[0] = tileMap[i][j-1]; } catch(Exception e) {} //up
    try { n[2] = tileMap[i][j+1]; } catch(Exception e) {} //down
    try { n[3] = tileMap[i-1][j]; } catch(Exception e) {} //left
    try { n[1] = tileMap[i+1][j]; } catch(Exception e) {} //right
    return n;
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
  }
  
  public int[][] getTiles() {
    return tileMap;
  }
  
  public void setTile(int t, int i, int j) {
    tileMap[i][j] = t;
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
