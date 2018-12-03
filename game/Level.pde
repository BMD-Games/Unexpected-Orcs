class Level {

  protected int[][] tilesRaw;
  protected int[][] tileMap;

  protected boolean[][] visited;
  protected boolean[][] visitedCalcLocations;
  protected int visitRadius = 8;  
  
  private final int CHUNK_SIZE = 7;
  private int CHUNK_W, CHUNK_H;
  public ArrayList<Enemy>[] enemies;

  public int w, h;
  public PVector start;
  protected String name;
  public TileSet tileset  = new TileSet();
  protected int xTileOffset, yTileOffset, renderW, renderH, buffer = 2, tileBuffer = width/TILE_SIZE/2;


  private HashMap<PVector, Boolean> smoothBeenVisited = new HashMap<PVector, Boolean>();
  private PriorityQueue<PVector> smoothQueue = new PriorityQueue<PVector>();

  private PGraphics tiles, miniMap, miniMapOverlay;
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;

    initialiseChunks();

    visited = new boolean[w][h];
    visitedCalcLocations = new boolean[w][h];

    renderW = width/TILE_SIZE + 2 * buffer;
    renderH = height/TILE_SIZE + 2 * buffer;

    //Initialise minimap
    tiles = createGraphics(width - GUI_WIDTH, height);
    miniMapOverlay = createGraphics(w, h);
    miniMap = createGraphics(w, h);
    miniMap.beginDraw();
    miniMap.background(0);
    miniMap.noStroke();
    miniMap.endDraw();
  }

  public PGraphics generateImage() {
    PGraphics pg = createGraphics(SPRITE_SIZE * w, SPRITE_SIZE * h);
    pg.beginDraw();
    pg.background(0, 0);
    for (int i = 0; i < w; i ++) {
      for (int j = 0; j < h; j ++) {
        int tile = tileset.walls[15];
        try { 
          tile = tileMap[i][j];
        } 
        catch(Exception e) {
        }
        pg.image(tileSprites.get(tile), i * SPRITE_SIZE, j * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
      }
    }
    pg.endDraw();
    return pg;
  }

  public void update(PGraphics screen, float x, float y) {
    xTileOffset = (int)x - (screen.width/2)/TILE_SIZE;
    yTileOffset = (int)y - (screen.height/2)/TILE_SIZE;
    updateVisited((int)x, (int)y, visitRadius, false);
    updateVisitedSmooth();
    updateMapEntities((int)x, (int)y);
  }

  public void show(PGraphics screen, PVector renderOffset) {    
    //generate an image based off the tile map;
    tiles.beginDraw();
    tiles.background(0);
    for (int x = 0; x < renderW; x ++) {
      for (int y = 0; y < renderH; y ++) {
        int i = (x + xTileOffset) - buffer;
        int j = (y + yTileOffset) - buffer;
        int tile = tileset.walls[15];
        boolean visit = false;
        try { 
          visit = visited[i][j];
        } 
        catch(Exception e) {
        }
        if (visit) {
          try { 
            tile = tileMap[i][j];
          } 
          catch(Exception e) {
          }
          PImage sprite = tileSprites.get(tile);
          tiles.image(sprite, i * TILE_SIZE - renderOffset.x, j * TILE_SIZE - renderOffset.y, (sprite.width * SCALE), (sprite.height * SCALE));
        }
      }
    }
    tiles.endDraw();
    screen.image(tiles, 0, 0);
  }

  public boolean isEdge(int[][] tiles, int i, int j) {
    return (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[0].length);
  }

  public int[] getNeighbours(int i, int j) {
    int[] n = new int[8];
    try { 
      n[up] = tileMap[i][j-1];
    } 
    catch(Exception e) {
    } //up
    try { 
      n[down] = tileMap[i][j+1];
    } 
    catch(Exception e) {
    } //down
    try { 
      n[left] = tileMap[i-1][j];
    } 
    catch(Exception e) {
    } //left
    try { 
      n[right] = tileMap[i+1][j];
    } 
    catch(Exception e) {
    } //right
    try { 
      n[topLeft] = tileMap[i-1][j-1];
    } 
    catch(Exception e) {
    } // up left
    try { 
      n[topRight] = tileMap[i+1][j-1];
    } 
    catch(Exception e) {
    } // up right
    try { 
      n[bottomLeft] = tileMap[i-1][j+1];
    } 
    catch(Exception e) {
    } // down left
    try { 
      n[bottomRight] = tileMap[i+1][j+1];
    } 
    catch(Exception e) {
    } // down right

    return n;
  }

  public void generateStart() {
    while (start == null) {
      int i = floor(random(edgeSize, w-edgeSize));
      int j = floor(random(edgeSize, h-edgeSize));
      if (tilesRaw[i][j] > WALL) {
        tileMap[i][j] = tileset.spawn;
        tilesRaw[i][j] = tileset.spawn;
        start = new PVector(i, j);
      }
    }
  }

  protected void applyTileSet() {
    tileMap = finishingPass(tilesRaw, tileset);
  }

  protected void updateMapEntities(int playerX, int playerY) {
    miniMapOverlay.beginDraw();
    miniMapOverlay.background(0, 0);
    miniMapOverlay.stroke(0, 0, 255);
    miniMapOverlay.point(playerX, playerY);
    //can add monsters here too;
    miniMapOverlay.endDraw();
  }

  public void newSmoothUncover(int i, int j, int radius) {
    smoothQueue = new PriorityQueue<PVector>(new PVectorZComparator());
    smoothBeenVisited = new HashMap<PVector, Boolean>();
    smoothQueue.add(new PVector(i, j, radius));
  }

  protected void updateVisitedSmooth() {
    if(smoothQueue.size() <= 0) return;
    int level = (int)smoothQueue.peek().z;
    while(smoothQueue.size() > 0 && (int)smoothQueue.peek().z == level) {
      PVector tile = smoothQueue.remove();
      visitTileSmooth((int)tile.x, (int)tile.y, (int)tile.z);
    }
  }

  protected void visitTileSmooth(int i, int j, int level) {
    if (!smoothBeenVisited.getOrDefault(new PVector(i, j), false)) {
      visitTileFull(i, j);
      smoothBeenVisited.put(new PVector(i, j), true);

      if (level != 0) {
        int[] nb = getNeighbours(i, j);
        if (nb[up] > WALL) smoothQueue.add(new PVector(i, j - 1, level - 1));
        if (nb[down] > WALL) smoothQueue.add(new PVector(i, j + 1, level - 1));
        if (nb[left] > WALL) smoothQueue.add(new PVector(i - 1, j, level - 1));
        if (nb[right] > WALL) smoothQueue.add(new PVector(i + 1, j, level - 1));
      }
    }
    smoothQueue.remove(0);
  }

  protected void updateVisited(int x0, int y0, int radius, boolean force) {

    boolean calcLocation = true; //default to true so if out-of-bounds we will still skip the calcs
    try { 
      calcLocation = visitedCalcLocations[x0][y0];
    } 
    catch(Exception e) {
    };
    if (!calcLocation || force) {
      HashMap<PVector, Boolean> beenVisited = new HashMap<PVector, Boolean>();
      ArrayList<PVector> queue = new ArrayList<PVector>();
      queue.add(new PVector(x0, y0, radius));
      visitTile(x0, y0, radius, beenVisited, queue); //Flood fill
      visitedCalcLocations[x0][y0] = true;
    }
  }

  ///*FLOOD FILL
  protected void visitTile(int i, int j, int level, HashMap<PVector, Boolean> beenVisited, ArrayList<PVector> queue) {

    if (!beenVisited.getOrDefault(new PVector(i, j), false)) {
      visitTileFull(i, j);
      beenVisited.put(new PVector(i, j), true);

      if (level != 0) {
        int[] nb = getNeighbours(i, j);
        if (nb[up] > WALL) queue.add(new PVector(i, j - 1, level - 1));
        if (nb[down] > WALL) queue.add(new PVector(i, j + 1, level - 1));
        if (nb[left] > WALL) queue.add(new PVector(i - 1, j, level - 1));
        if (nb[right] > WALL) queue.add(new PVector(i + 1, j, level - 1));
      }
    }
    queue.remove(0);
    if (queue.size() > 0) {
      visitTile((int)queue.get(0).x, (int)queue.get(0).y, (int)queue.get(0).z, beenVisited, queue);
    }
  }//*/

  protected void visitTileFull(int x, int y) {
    for (int i = -1; i <= 1; i ++) {
      for (int j = -1; j <= 1; j ++) {
        visitTile(x + i, y + j);
      }
    }
  }

  protected void visitTile(int i, int j) {
    try {
      if (!visited[i][j]) drawVisitedTile(i, j);
      visited[i][j] = true;
    } 
    catch (Exception e) {
    }
  }

  protected void drawVisitedTile(int i, int j) {
    miniMap.beginDraw();
    int tile = WALL;
    try { 
      tile = tileMap[i][j];
    } 
    catch(Exception e) {
    }
    miniMap.stroke(tileSprites.get(tile).get(1, 1)); //set the colour to a pixel from the tile

    miniMap.point(i, j);
    miniMap.endDraw();
  }

  public boolean canSee(int x1, int y1, int x2, int y2) {
    int dist = (int)max(fastAbs(x2 - x1), fastAbs(y2 - y1));
    for (int i = 0; i < dist; i ++) {
      int tX = (int)map(i, 0, dist, x1, x2);
      int tY = (int)map(i, 0, dist, y1, y2);
      int tile = WALL;
      try { 
        tile = tileMap[tX][tY];
      } 
      catch(Exception e) {
      }
      if (tile <= WALL) return false;
    }    
    return true;
  }

  public void visitTilesInLine(int x1, int y1, int x2, int y2) {
    int dist = (int)max(fastAbs(x2 - x1), fastAbs(y2 - y1));
    for (int i = 0; i < dist; i ++) {
      int tX = (int)map(i, 0, dist, x1, x2);
      int tY = (int)map(i, 0, dist, y1, y2);

      int tile = WALL;
      try { 
        tile = tileMap[tX][tY];
      } 
      catch(Exception e) {
      }

      boolean visit = false;
      try { 
        visit = visited[tX][tY];
      } 
      catch(Exception e) {
      }

      if (!visit) {
        if (tile <= WALL) visitTile(tX, tY);
        else visitTileFull(tX, tY);
      }
      if (tile <= WALL) {
        break;
      }
    }
  }

  private void initialiseChunks() {
    CHUNK_W = ceil(w/(float)CHUNK_SIZE);
    CHUNK_H = ceil(h/(float)CHUNK_SIZE);
    enemies = new ArrayList[CHUNK_W * CHUNK_H];
    for(int i = 0; i < enemies.length; i ++) {
      enemies[i] = new ArrayList<Enemy>();
    }
  }
  
  public void addEnemy(StandardEnemy enemy) {
    if(enemies[getChunk((int)enemy.x, (int)enemy.y)] == null) enemies[getChunk((int)enemy.x, (int)enemy.y)] = new ArrayList<Enemy>();
    enemies[getChunk((int)enemy.x, (int)enemy.y)].add(enemy);
  }
  
  public ArrayList<Integer> getChunks(int x, int y) {
    ArrayList<Integer> chunks = new ArrayList<Integer>();
    for(int i = x - CHUNK_SIZE; i <= x + CHUNK_SIZE; i += CHUNK_SIZE) {
      for(int j = y - CHUNK_SIZE; j <= y + CHUNK_SIZE; j += CHUNK_SIZE) {
        int c = getChunk(i, j);
        if(c >= 0 && c < enemies.length) {
          chunks.add(getChunk(i, j));
        }
      }
    }
    return chunks;
  }
  
  public int getChunk(int x, int y) {
    return (x/CHUNK_SIZE) + (y/CHUNK_SIZE) * CHUNK_W;
  }
  
  public PVector getChunkPos(int x, int y) {
    return new PVector(x/CHUNK_SIZE, y/CHUNK_SIZE);
  }
  
  public void setTilesRaw(int[][] tiles) { //Raw tiles using 0s and 1s
    tilesRaw = tiles;
  }
  
    public void setTileMap(int[][] tiles) { //Tiles with tileset
    tileMap = tiles;
  }
  
  public void setTiles(int[][] tiles) {
    tilesRaw = tiles;
    applyTileSet();
    generateStart();
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
    try { 
      tile = tileMap[i][j];
    } 
    catch(Exception e) {
    }
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

  public PGraphics getMiniMap() {
    return miniMap;
  }

  public PGraphics getOverlay() {
    return miniMapOverlay;
  }

  public void saveLevel() {
    generateImage().save("/out/image" + name + ".png");
    PrintWriter file = createWriter("/out/" + name + ".txt");
    for (int j = 0; j < h; j ++) {
      for (int i = 0; i < w; i ++) {
        file.print(tilesRaw[i][j]);
        if (i < w -1) file.print(',');
      }
      file.println();
    }
    file.flush();
    file.close();
  }
}

class PVectorZComparator implements Comparator<PVector> {
  @Override
  public int compare(PVector a, PVector b) {
    if (a.z < b.z) return 1;
    if (a.z > b.z) return -1;
    return 0;
  }
}


/*
int x = visitRadius - 1;
 int y = 0;
 int dx = 1;
 int dy = 1;
 int err = dx - (visitRadius << 1);
 while (x >= y) {
 visitTilesInLine(x0, y0, x0 + x, y0 + y); //need to do it for all 8 octants 
 visitTilesInLine(x0, y0, x0 + y, y0 + x); //  
 visitTilesInLine(x0, y0, x0 - y, y0 + x); //  \|/
 visitTilesInLine(x0, y0, x0 - x, y0 + y); //  -+-
 visitTilesInLine(x0, y0, x0 - x, y0 - y); //  /|\
 visitTilesInLine(x0, y0, x0 - y, y0 - x); //
 visitTilesInLine(x0, y0, x0 + y, y0 - x);
 visitTilesInLine(x0, y0, x0 + x, y0 - y);
 
 if (err <= 0) {
 y++;
 err += dy;
 dy += 2;
 }
 if (err > 0) {
 x--;
 dx += 2;
 err += dx - (visitRadius << 1);
 }
 }
 try { visitedCalcLocations[x0][y0] = true; } catch(Exception e) {} //should always work but good to be safe
 */
