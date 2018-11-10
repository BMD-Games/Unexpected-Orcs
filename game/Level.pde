class Level {
  
  private int[][] tileMap;
  public int w, h;
  public PVector start;
  private String name;
  public ArrayList<Enemy> enemies  = new ArrayList<Enemy>();
  
  private int xTileOffset, yTileOffset, renderW, renderH, buffer = 4, tileBuffer = width/TILE_SIZE/2;
  
  PGraphics background, tiles;
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;
    renderW = width/TILE_SIZE + buffer;
    renderH = height/TILE_SIZE + buffer;
    background = createGraphics(TILE_SIZE * (w + tileBuffer * 2), TILE_SIZE * (h + tileBuffer * 2));
    tiles = createGraphics(TILE_SIZE * (w + tileBuffer * 2), TILE_SIZE * (h + tileBuffer * 2));
  }
  
    public void generateImages() {
    background.beginDraw();
    background.background(200);
    background.noStroke();
    background.fill(100);
    
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int i = 0; i < w + tileBuffer * 2; i ++) {
      for(int j = 0; j < h + tileBuffer * 2; j ++) {
        int tile = 0;
        try{ tile = tileMap[i - tileBuffer][j - tileBuffer]; } catch(Exception e) {}
        if(tile <= WALL) {
          background.image(tileSprites.get(tile), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        else {          
          tiles.image(tileSprites.get(tile), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
      }
    }
    background.endDraw();
    tiles.endDraw();
  }
  
  public void show(PVector renderOffset) {
    image(background.get((int)renderOffset.x + tileBuffer * TILE_SIZE, (int)renderOffset.y + tileBuffer * TILE_SIZE, width, height), 0, 0);
    image(tiles.get((int)renderOffset.x + tileBuffer * TILE_SIZE, (int)renderOffset.y + tileBuffer * TILE_SIZE, width, height), 0, 0);
  }
  
  public boolean isEdge(int[][] tiles, int i, int j) {
    return (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[0].length);
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
    generateStart();
    generateImages();
    saveLevel("out.txt");
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
  
  public void saveLevel(String filename) {
    PrintWriter file = createWriter(filename);
    for(int j = 0; j < h; j ++) {
      for(int i = 0; i < w; i ++) {
        file.print(saveChars.get(tileMap[i][j]));
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



  public void update(float x, float y) {
    xTileOffset = (int)x - (width/2)/TILE_SIZE;
    yTileOffset = (int)y - (height/2)/TILE_SIZE;
  }
  
  public void show(PVector renderOffset) {    
    //generate an image based off the tile map;
    background.beginDraw();
    background.background(200);
    background.noStroke();
    background.fill(100);
    
    tiles.beginDraw();
    tiles.background(0, 0);
    for(int x = 0; x < renderW; x ++) {
      for(int y = 0; y < renderH; y ++) {
        int i = (x + xTileOffset) - buffer/2;
        int j = (y + yTileOffset) - buffer/2;
        int tile = 0;
        try{ tile = tileMap[i][j]; } catch(Exception e) {}
        if(tile <= WALL) {
          background.image(tileSprites.get(tile), i * TILE_SIZE - renderOffset.x, j * TILE_SIZE  - renderOffset.y, TILE_SIZE, TILE_SIZE);
        }
        else {          
          tiles.image(tileSprites.get(tile), i * TILE_SIZE - renderOffset.x, j * TILE_SIZE - renderOffset.y, TILE_SIZE, TILE_SIZE);
        }
      }
    }
    background.endDraw();
    tiles.endDraw();
    image(background, 0, 0);
    image(tiles, 0, 0);
  }


**/
