class Cave extends Level{
  
  float chance = 0.5; //chance the a cell will be a wall
  
  Cave(int w, int h) {
    super(w, h);
    generateCave(w, h, 5);
  }
  
  private void generateCave(int w, int h, int iterations) {
    int[][] tiles = new int[w][h];
    int[][] oldTiles = new int[w][h];
    for(int i = 0; i < w; i ++) {
      for(int j = 0; j < h; j ++) {
        if(random(1) < chance) tiles[i][j] = 0;
        else { tiles[i][j] = 1; }
      }
    }
    for(int i = 0; i < iterations; i ++) {
      iterateGeneration(tiles, oldTiles, w, h, i < 4);
    }
    while(start == null) {
      int i = floor(random(2, w-3));
      int j = floor(random(2, h-3));
      if(tiles[i][j] == 1) {
        tiles[i][j] = 2;
        start = new PVector(i, j);
      }
    }
    this.setTiles(tiles);
  }
  
  private void iterateGeneration(int[][] tiles, int[][] oldTiles, int w, int h, boolean firstPhase) {
    for(int i = 0; i < w; i ++) {
      for(int j = 0; j < h; j ++) {
        oldTiles[i][j] = tiles[i][j];
      }
    }
    for(int i = 0; i < w; i ++) {
      for(int j = 0; j < h; j ++) {
        int num = numNeighbours(oldTiles, i, j, 1); //neighbours in one step
        int num2 = numNeighbours(oldTiles, i, j, 2); //neighbours in two steps
        tiles[i][j] = 1;
        if(num >= 5 || (num2 <= 2 && firstPhase)) {
            tiles[i][j] = 0;
        }
        if(i < 2 || i >= w-2 || j < 2 || j >= h-2) { //edge tiles
          tiles[i][j] = 0;
        }
      }
    }
  }
  
  private int numNeighbours(int[][] tiles, int x, int y, int dist) {
    //counts the number of walls in a square centred at x, y, spanning dist in each direction
    int num = 0;
    for(int i = -dist; i <= dist; i ++) {
      for(int j = -dist; j <= dist; j ++) {
        if(i == 0 && j == 0) continue; //skip if on the centre tile
        try {
          if(tiles[x + i][y + j] == 0) num ++; //if the tile is a wall
        } catch(Exception e) {}
      }
    }
    return num;
  }
  
}
