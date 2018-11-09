public int edgeSize = 2;

//------CAVE GENERATION--------
public int[][] generateCave(int w, int h, int iterations, float chance) {
  int[][] tiles = new int[w][h];
  int[][] oldTiles = new int[w][h];
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      if (random(1) < chance) tiles[i][j] = 0;
      else { 
        tiles[i][j] = 1;
      }
    }
  }
  for (int i = 0; i < iterations; i ++) {
    iterateGeneration(tiles, oldTiles, w, h, i < iterations - 1);
  }
  return tiles;
}

public void iterateGeneration(int[][] tiles, int[][] oldTiles, int w, int h, boolean firstPhase) {
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      oldTiles[i][j] = tiles[i][j];
    }
  }
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      int num = numNeighbours(oldTiles, i, j, 1); //neighbours in one step
      int num2 = numNeighbours(oldTiles, i, j, 2); //neighbours in two steps
      tiles[i][j] = 1;
      if (num >= 5 || (firstPhase && num2 <= 2)) {
        tiles[i][j] = 0;
      }
      if (i < edgeSize || i >= w-edgeSize || j < edgeSize || j >= h-edgeSize) { //edge tiles
        tiles[i][j] = 0;
      }
    }
  }
}  

public int numNeighbours(int[][] tiles, int x, int y, int dist) {
  //counts the number of walls in a square centred at x, y, spanning dist in each direction
  int num = 0;
  for (int i = -dist; i <= dist; i ++) {
    for (int j = -dist; j <= dist; j ++) {
      if (i == 0 && j == 0) continue; //skip if on the centre tile
      try {
        if (tiles[x + i][y + j] == 0) num ++; //if the tile is a wall
      } 
      catch(Exception e) {
      }
    }
  }
  return num;
}

public int numNeighboursSimple(int[][] tiles, int x, int y) {
  //counts the number of walls in a square centred at x, y, spanning dist in each direction
  int num = 0;
  try { if (tiles[x-1][y] == 0) num ++; } catch(Exception e) {}  
  try { if (tiles[x+1][y] == 0) num ++; } catch(Exception e) {}
  try { if (tiles[x][y-1] == 0) num ++; } catch(Exception e) {}
  try { if (tiles[x][y+1] == 0) num ++; } catch(Exception e) {} 
  return num;
}

//------DUNGEON GENERATION---------

public int[][] generateDungeon(int w, int h, int roomAttempts, int minSize, int maxSize, float straight) {
  //http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/

  int[][] tiles = new int[w][h];
  int[][] region = new int[w][h];
  int regionCount = 1;
  ArrayList<int[]> rooms = new ArrayList<int[]>(); //stores all rooms
  ArrayList<int[]> stack = new ArrayList<int[]>();

  //generate rooms
  for (int i = 0; i < roomAttempts; i ++) {
    int x, y, xl, yl; //room position and dimensions
    xl = floor(random(minSize, maxSize)) * 2 - 1; //always odd number
    yl = floor(random(minSize, maxSize)) * 2 - 1; //always odd number
    x = floor(random(edgeSize, w - xl - edgeSize)/2) * 2; //avoid edges
    y = floor(random(edgeSize, h - yl - edgeSize)/2) * 2; //avoid edges
    int[] room = {x, y, xl, yl};
    boolean hit = false;
    for (int j = 0; j < rooms.size(); j ++) {
      if (roomOverlap(rooms.get(j), room)) { 
        hit = true; 
        break;
      }
    }
    if (hit) continue;
    rooms.add(room);
  }

  //add rooms to tile map
  for (int r = 0; r < rooms.size(); r ++) {
    int[] room = rooms.get(r);
    for (int i = room[0]; i < room[0] + room[2]; i ++) {
      for (int j = room[1]; j < room[1] + room[3]; j ++) {
        tiles[i][j] = 1;
        region[i][j] = regionCount;
      }
    }
    regionCount ++;
  }

  //find maze seed points and run each maze to completion
  for (int i = edgeSize; i < w-edgeSize; i ++) {
    for (int j = edgeSize; j < h-edgeSize; j ++) {
      if (numNeighbours(tiles, i, j, 1) == 8 && tiles[i][j] == 0) { //seed point found
        int x, y, dir = 0;
        stack.add(0, new int[] {i, j});
        while (stack.size() > 0) { //while there are still valid points
          x = stack.get(0)[0]; //get the most recent one
          y = stack.get(0)[1];
          tiles[x][y] = 1;
          region[x][y] = regionCount;
          ArrayList<int[]> valid = validNeighbours(tiles, x, y);
          if (valid.size() == 0) {
            stack.remove(0);
            continue;
          } else {
            int n = 0, d = -1;
            for(int v = 0; v < valid.size(); v ++) {
              if(dir == direction(new int[] {x, y}, new int[] {valid.get(v)[0], valid.get(v)[0]}) &&
               random(1) < straight) {
                d = dir;
                n = v;
                break;
              }
            }
            if(d == -1) {
              n = (int)random(valid.size());
              d = direction(new int[] {x, y}, new int[] {valid.get(n)[0], valid.get(n)[0]});
            }
            stack.add(0, new int[] {valid.get(n)[0], valid.get(n)[1]});
            dir = d;
          }
        }
      }
      regionCount ++;
    }
  }
  
  //get all connectors
  ArrayList<int[]> connectors = new ArrayList<int[]>();
  for (int i = edgeSize; i < w-edgeSize; i ++) {
    for (int j = edgeSize; j < h-edgeSize; j ++) {
      if(isConnector(tiles, region, i, j)) {
        connectors.add(new int[] {i, j});
        
      }
    }
  }
  //connect all rooms to the maze
  for(int i = 0; i < connectors.size(); i ++) {
    tiles[connectors.get(i)[0]][connectors.get(i)[1]] = 2;
  }

  return tiles;
}

public int direction(int[] t1, int[] t2) {
  //returns the direction from t1 cell to t2
  if(t1[1] < t2[1] && t1[0] == t2[0]) return 0; //up
  if(t1[1] > t2[1] && t1[0] == t2[0]) return 2; //down
  if(t1[1] == t2[1] && t1[0] < t2[0]) return 3; //left
  if(t1[1] == t2[1] && t1[0] > t2[0]) return 1; //right
  return -1;
}

public boolean roomOverlap(int[] r1, int[] r2) {
  //x, y, xl, yl
  return (r1[0] +  r1[2] >= r2[0] &&   // r1 right edge past r2 left
    r1[0] <= r2[0] +  r2[2] &&   // r1 left edge past r2 right
    r1[1] +  r1[3] >= r2[1] &&   // r1 top edge past r2 bottom
    r1[1] <= r2[1] +  r2[3]);    // r1 bottom edge past r2 top
}

public ArrayList<int[]> validNeighbours(int[][] tiles, int i, int j) {
  ArrayList<int[]> neighbours = new ArrayList<int[]>();
  if (validTile(tiles, i, j, 0, 1)) {  //up    dx =  0; dy =  1
    neighbours.add(new int[] {i, j - 1});
  }
  if (validTile(tiles, i, j, 0, -1)) { //down  dx =  0; dy = -1
    neighbours.add(new int[] {i, j + 1});
  }
  if (validTile(tiles, i, j, 1, 0)) {  //left  dx =  1; dy =  0 
    neighbours.add(new int[] {i - 1, j});
  }
  if (validTile(tiles, i, j, -1, 0)) { //right dx = -1; dy =  0 
    neighbours.add(new int[] {i + 1, j});
  }
  return neighbours;
}

public boolean validTile(int[][] tiles, int x, int y, int dx, int dy) {  
  int num = 0;
  x -= dx;
  y -= dy;
  if(isEdgeTile(tiles, x, y)) return false;
  for (int i = -1; i <= 1; i ++) {
    for (int j = -1; j <= 1; j ++) {
      if (dx != 0 && dx == i) continue;      
      if (dy != 0 && dy == j) continue;
      try {
        if (tiles[x + i][y + j] != 0) return false; //if the tile is a wall
      } 
      catch(Exception e) { 
        return false;
      }
    }
  }
  return true;
}

public boolean isConnector(int[][] tiles, int[][] region, int i, int j) {
  if(tiles[i][j] != 0) return false;
  if(numNeighboursSimple(tiles, i, j) != 2) return false;
  int t = -1, b = -1, l = -1, r = -1;
  int tr = -1, br = -1, lr = -1, rr = -1;
  try { t = tiles[i][j-1]; tr = region[i][j-1]; } catch(Exception e) {}  
  try { b = tiles[i][j+1]; br = region[i][j+1]; } catch(Exception e) {}  
  try { l = tiles[i-1][j]; lr = region[i-1][j]; } catch(Exception e) {}
  try { r = tiles[i+1][j]; rr = region[i+1][j]; } catch(Exception e) {}
  
  return(((t > 0 && b > 0) && tr != br) || ((l > 0 && r > 0) && lr != rr));
}

public boolean isEdgeTile(int[][] tiles, int i, int j) {
  return (i < edgeSize || j < edgeSize || i >= tiles.length - edgeSize || j >= tiles[0].length - edgeSize);
}
