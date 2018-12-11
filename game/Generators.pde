public int edgeSize = 1;

//------CAVE GENERATION--------
public int[][] generateCave(int w, int h, int iterations, float chance) {
  int[][] tiles = new int[w][h];
  int[][] oldTiles = new int[w][h];
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      if (random(1) < chance) tiles[i][j] = WALL;
      else { 
        tiles[i][j] = FLOOR;
      }
    }
  }
  for (int i = 0; i < iterations; i ++) {
    iterateGeneration(tiles, oldTiles, w, h, i < iterations - 2);
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
      tiles[i][j] = FLOOR;
      if (num >= 5 || (firstPhase && num2 <= 2)) {
        tiles[i][j] = WALL;
      }
      if (i < edgeSize || i >= w-edgeSize || j < edgeSize || j >= h-edgeSize) { //edge tiles
        tiles[i][j] = WALL;
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
        if (tiles[x + i][y + j] == WALL) num ++; //if the tile is a wall
      } 
      catch(Exception e) {
      }
    }
  }
  return num;
}

public int numNeighboursSimple(int[][] tiles, int x, int y) {
  //counts the number of walls in a square centred at x, y, only looks in cardianl directions
  int num = 0;
  try { if (tiles[x-1][y] == WALL) num ++; } catch(Exception e) {}  
  try { if (tiles[x+1][y] == WALL) num ++; } catch(Exception e) {}
  try { if (tiles[x][y-1] == WALL) num ++; } catch(Exception e) {}
  try { if (tiles[x][y+1] == WALL) num ++; } catch(Exception e) {} 
  return num;
}

//------DUNGEON GENERATION---------

public int[][] generateWindyDungeon(int w, int h, int roomAttempts, int minSize, int maxSize, float straightChance, float loopChance) {

  int[][] tiles = new int[w][h];
  int[][] region = new int[w][h];
  int regionCount = 0;
  ArrayList<int[]> rooms = placeRooms(w, h, roomAttempts, minSize, maxSize);
  ArrayList<int[]> stack = new ArrayList<int[]>();

  //add rooms to tile map
  for (int r = 0; r < rooms.size(); r ++) {
    int[] room = rooms.get(r);
    for (int i = room[0]; i < room[0] + room[2]; i ++) {
      for (int j = room[1]; j < room[1] + room[3]; j ++) {
        tiles[i][j] = FLOOR;
        region[i][j] = regionCount;
      }
    }
    regionCount ++;
  }

  //find maze seed points and run each maze to completion
  for (int i = edgeSize; i < w-edgeSize; i ++) {
    for (int j = edgeSize; j < h-edgeSize; j ++) {
      if (numNeighbours(tiles, i, j, 1) == 8 && tiles[i][j] == WALL) { //seed point found
        int x, y, dir = 0;
        stack.add(0, new int[] {i, j});
        while (stack.size() > 0) { //while there are still valid points
          x = stack.get(0)[0]; //get the most recent one
          y = stack.get(0)[1];
          tiles[x][y] = FLOOR;
          region[x][y] = regionCount;
          ArrayList<int[]> valid = validNeighbours(tiles, x, y);
          if (valid.size() == 0) {
            stack.remove(0);
            continue;
          } else {
            int n = 0, d = -1;
            for(int v = 0; v < valid.size(); v ++) {
              if(dir == direction(new int[] {x, y}, new int[] {valid.get(v)[0], valid.get(v)[0]}) &&
               random(1) < straightChance) {
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
        regionCount ++;
      }
    }
  }
  
  //get all connectors
  ArrayList<int[]> connectors = getConnectors(tiles, region);
  ArrayList<Integer> connected = new ArrayList<Integer>(); //all regions that have been connected
  //connect all rooms to the maze
  connected.add(0);
  while (connected.size() < regionCount) {
    int n = (int)random(connectors.size());
    int[] connector = connectors.get(n);
    int c = 0;
    if(!connected.contains(connector[2]) && connected.contains(connector[3])) c = 2;
    if(connected.contains(connector[2]) && !connected.contains(connector[3])) c = 3;
    if(c == 2 || c == 3) {
      connectors.remove(n);
      connected.add(connector[c]);
      tiles[connector[0]][connector[1]] = FLOOR;
    }
  }
  
  //add random connections to make dungeon more interesting
  for(int i = 0; i < connectors.size(); i ++) {
    if(random(1) < loopChance) {
      tiles[connectors.get(i)[0]][connectors.get(i)[1]] = FLOOR;
    }
  }
  
  //remove deadends
  for (int i = edgeSize; i < w-edgeSize; i ++) {
    for (int j = edgeSize; j < h-edgeSize; j ++) {
      if(tiles[i][j] == WALL) continue;
      int x = i;
      int y = j;
      int dir = getEndDirection(tiles, i, j);
      while(dir != -1) { //deadend found
        tiles[x][y] = WALL;
        if(dir == 0) y -= 1;
        if(dir == 2) y += 1;
        if(dir == 3) x -= 1;
        if(dir == 1) x += 1;
        dir = getEndDirection(tiles, x, y);
      }
    }
  }
  
  return tiles;
}

//Generates a dungeon with corridors directly between rooms
public int[][] generateStraightDungeon(int w, int h, int roomAttempts, int minSize, int maxSize) {
  //http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/

  int[][] tiles = new int[w][h];
  int[][] region = new int[w][h];
  int regionCount = 0;
  ArrayList<int[]> rooms = placeRooms(w, h, roomAttempts, minSize, maxSize);

  //add rooms to tile map
  for (int r = 0; r < rooms.size(); r ++) {
    int[] room = rooms.get(r);
    for (int i = room[0]; i < room[0] + room[2]; i ++) {
      for (int j = room[1]; j < room[1] + room[3]; j ++) {
        tiles[i][j] = FLOOR;
        region[i][j] = regionCount;
      }
    }
    regionCount ++;
  }

  ArrayList<Integer> connected = new ArrayList<Integer>(); //all regions that have been connected
  //connect all rooms
  connected.add(0);
  while (connected.size() < rooms.size()) {
    int r1 = (int)random(rooms.size());
    int r2 = (int)random(rooms.size());
    int[] r = {r1, r2};
    int c = -1;
    if(!connected.contains(r1) && connected.contains(r2)) c = 0;
    if(connected.contains(r1) && !connected.contains(r2)) c = 1;
    if(c != -1) {
      connectRooms(tiles, rooms.get(r1), rooms.get(r2));
      connected.add(r[c]);
    }
  }
  
  return tiles;
}

public int[][] connectRooms(int[][] tiles, int[] r1, int[] r2) {
  int[] start = {(int)random(r1[0], r1[0] + r1[2]), (int)random(r1[1], r1[1] + r1[3])}; //random point in r1
  int[] stop = {(int)random(r2[0], r2[0] + r2[2]), (int)random(r2[1], r2[1] + r2[3])}; //random point in r2
  
  int x = start[0];
  int y = start[1];
  
  int dx = 0;
  try { dx = (stop[0] - start[0])/abs(stop[0] - start[0]); } catch(Exception e) {};
  int dy = 0;
  try { dy = (stop[1] - start[1])/abs(stop[1] - start[1]); } catch(Exception e) {};
  
  int[] dir = {dx, 0};
  int[] dir2 = {0, dy};
  if(random(1) < 0.5) { dir = new int[] {0, dy}; dir2 = new int[] {dx, 0}; } //random chance of starting horizontal of veritcally
  
  while(x != stop[0] || y != stop[1]) {
    tiles[x][y] = FLOOR;
    x += dir[0];
    y += dir[1];
    if(x == stop[0] || y == stop[1]) {
      dir[0] = dir2[0];
      dir[1] = dir2[1];
    }
  }
  
  return tiles;
}

public ArrayList<int[]> placeRooms(int w, int h, int roomAttempts, int minSize, int maxSize) {
   ArrayList<int[]> rooms = new ArrayList<int[]>(); //stores all rooms

  //generate rooms
  for (int i = 0; i < roomAttempts; i ++) {
    int x, y, xl, yl; //room position and dimensions
    xl = floor(random(minSize, maxSize)); //room width
    yl = floor(random(minSize, maxSize)); //room height
    x = floor(random(edgeSize, w - xl - edgeSize)); //room x pos - avoid edges
    y = floor(random(edgeSize, h - yl - edgeSize)); //toom y pos - avoid edges
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
  return rooms;
}

public int getEndDirection(int[][] tiles, int i, int j) {
  if(numNeighboursSimple(tiles, i, j) < 3 || tiles[i][j] == WALL) return -1; //not dead end
  //returns the direction of the corridor from a dead end
  if(tiles[i][j-1] == FLOOR) return 0; //up
  if(tiles[i][j+1] == FLOOR) return 2; //down
  if(tiles[i-1][j] == FLOOR) return 3; //left
  if(tiles[i+1][j] == FLOOR) return 1; //right
  return -1;
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
  x -= dx;
  y -= dy;
  if(isEdgeTile(tiles, x, y)) return false;
  for (int i = -1; i <= 1; i ++) {
    for (int j = -1; j <= 1; j ++) {
      if (dx != 0 && dx == i) continue;      
      if (dy != 0 && dy == j) continue;
      try {
        if (tiles[x + i][y + j] >= FLOOR) return false; //if the tile is a floor
      } 
      catch(Exception e) { 
        return false;
      }
    }
  }
  return true;
}

public ArrayList<int[]> getConnectors(int[][] tiles, int[][] region) {
  ArrayList<int[]> connectors = new ArrayList<int[]>();
  for (int i = edgeSize; i < tiles.length-edgeSize; i ++) {
    for (int j = edgeSize; j < tiles[0].length-edgeSize; j ++) {
      addConnector(connectors, tiles, region, i, j);
    }
  }
  return connectors;
}

public void addConnector(ArrayList<int[]> connectors, int[][] tiles, int[][] region, int i, int j) {
  if(tiles[i][j] != WALL) return;
  if(numNeighboursSimple(tiles, i, j) != 2) return;
  int t = -1, b = -1, l = -1, r = -1;
  int tr = -1, br = -1, lr = -1, rr = -1;
  try { t = tiles[i][j-1]; tr = region[i][j-1]; } catch(Exception e) {}  
  try { b = tiles[i][j+1]; br = region[i][j+1]; } catch(Exception e) {}  
  try { l = tiles[i-1][j]; lr = region[i-1][j]; } catch(Exception e) {}
  try { r = tiles[i+1][j]; rr = region[i+1][j]; } catch(Exception e) {}
  
  if((t >= FLOOR && b >= FLOOR) && tr != br) { // >= FLOOR is non-solid block
    connectors.add(new int[] {i, j, tr, br});
  } else if ((l >= FLOOR && r >= FLOOR) && lr != rr) {
    connectors.add(new int[] {i, j, lr, rr});
  }
}

public boolean isEdgeTile(int[][] tiles, int i, int j) {
  return (i < edgeSize || j < edgeSize || i >= tiles.length - edgeSize || j >= tiles[0].length - edgeSize);
}

public boolean isBorder(int[][]tiles, int i, int j) {
  boolean edge = (i < 1 || j < 1 || i >= tiles.length - 1 || j >= tiles[0].length - 1);
  boolean border = false;
  try {
    border = ((numNeighbours(tiles, i, j, 1) < 8 && tiles[i][j] == WALL));
  } catch(Exception e) {}
  return (!edge && border);
}


public void generateConnectedDungeon(Level level, int rows, int columns, Room startRoom, Room bossRoom, Room[] rooms) {
  /* ---Theory---
  1. Split the level into a grid
  2. Randomly choose a grid square and randomly add a room inside it
  3. Get the spawn point based on the startRoom
  4. Set the mob spawn zones based on the rooms
  5. Once rooms have been placed, find rooms in neighbouring cells and make a corridor to meet them
  */
  
  int w = level.w, h = level.h;  
  int[][] tiles = new int[w][h];
  
  int rw = w/rows;
  int rh = h/columns;
  
  Room[] roomGrid = new Room[rows * columns]; //grid of placed rooms
  
  ArrayList<PVector> bossRegions = new ArrayList<PVector>();
  ArrayList<PVector> generalRegions = new ArrayList<PVector>();
  ArrayList<Room> placedRooms = new ArrayList<Room>();
  
  int regions = 0;
    
  startRoom = randomlyPlaceRoom(startRoom, rw, rh); //randomly places room within the grid cell
  bossRoom = randomlyPlaceRoom(bossRoom, rw, rh); //randomly places room within the grid cell
  
  PVector start = new PVector((int)random(columns), (int)random(rows));  
  int startRoomPos = (int)start.x + (int)start.y * columns;
  startRoom.x += (int)(start.x) * rw;
  startRoom.y += (int)(start.y) * rh;
  
  
  println("obvs here");
  
  
  while(startRoom.outOfBounds(w, h)) {
    start = new PVector((int)random(rw), (int)random(rh));
    startRoomPos = (int)start.x + (int)start.y * columns;
    startRoom.x += (int)(start.x) * rw;
    startRoom.y += (int)(start.y) * rh;
  }
  roomGrid[startRoomPos] = new Room(startRoom); //place start room randomly into the grid
  placedRooms.add(startRoom);
  regions ++;
  
  PVector boss = new PVector(random(columns), random(rows));
  int bossRoomPos = (int)boss.x + (int)boss.y * columns;
  bossRoom.x += (int)(boss.x) * rw;
  bossRoom.y += (int)(boss.y) * rh;
  
  println("here-1");
  
  while(bossRoom.outOfBounds(w, h) || bossRoom.collides(startRoom)) {
    println("cunt");
    boss = new PVector((int)random(rw), (int)random(rh));
    bossRoomPos = (int)boss.x + (int)boss.y * columns;
    bossRoom.x += (int)(boss.x) * rw;
    bossRoom.y += (int)(boss.y) * rh;
  }
  roomGrid[bossRoomPos] = new Room(bossRoom); //place boss room randomly into the grid
  placedRooms.add(bossRoom);
  regions ++;
  
  println("here");
  
  //place rooms into grid
  for(int i = 0; i < columns; i ++) {
    for(int j = 0; j < rows; j ++) {
      int index = i + j * columns; 
      if(index == startRoomPos || index == bossRoomPos) continue;
      if(random(1) <= roomChance) { // % chance of a cell containing a room
        Room room = new Room(randomlyPlaceRoom(rooms[(int)random(rooms.length)], rw, rh)); //get a random room from the list of rooms and place it randomly in the current grid square
        room.x += i * rw;
        room.y += j * rh;
        boolean hit = false;
        for(int k = 0; k < roomGrid.length; k ++) {
          if(roomGrid[k] != null && (roomGrid[k].collides(room) || room.outOfBounds(w, h))) {
            hit = true;
            break;
          }
        }
        if(!hit) { 
          roomGrid[index] = room;
          placedRooms.add(room);
          regions ++;
        }
      }
    }
  }
  
  println("here1");
  
  //add their tiles to the tile map
  for(int x = 0; x < columns; x ++) {
    for(int y = 0; y < rows; y ++) {
      int index = x + y * columns;
      if(roomGrid[index] != null) {
        for(int i = 0; i < roomGrid[index].w; i ++) {
          for(int j = 0; j < roomGrid[index].h; j ++) {
            int tile = roomGrid[index].tiles[i][j];
            try { tiles[roomGrid[index].x + i][roomGrid[index].y + j] = tile; } catch(Exception e) {}
            //add their tiles to the correct zone
            PVector pos = new PVector(roomGrid[index].x + i, roomGrid[index].y + j);
            if(index == bossRoomPos) {
              bossRegions.add(pos);
            } else if(index == startRoomPos) {
              if(SPAWN_TILES.contains(tile)) level.setStart(pos);
            } else {
              generalRegions.add(pos);
            }
          }  
        }
      }
    }
  }
  
  println("here2");
  
  //go through grid cells and check if it has neighbouring cells
  ArrayList<Integer> connected = new ArrayList<Integer>(); //all regions that have been connected
  //connect all rooms
  connected.add(0);
  placedRooms.remove(0);
  while (connected.size() < placedRooms.size()) {
    int r1 = (int)random(placedRooms.size());
    int r2 = (int)random(placedRooms.size());
    if(r1 + r2 == 1) continue;
    int[] r = {r1, r2};
    int c = -1;
    if(!connected.contains(r1) && connected.contains(r2)) c = 0;
    if(connected.contains(r1) && !connected.contains(r2)) c = 1;
    if(c != -1) {
      connectRooms(tiles, placedRooms.get(r1), placedRooms.get(r2));
      connected.add(r[c]);
    }
  }

  println("here3");
  
  tiles = finishingPass(tiles, level.tileset);
  level.setTiles(tiles);
  level.setZones(new ArrayList[] {bossRegions, generalRegions});
}

public int[][] connectRooms(int[][] tiles, Room r1, Room r2) {
  int[] start = {(int)random(r1.x, r1.x + r1.w), (int)random(r1.y, r1.y + r1.h)}; //random point in r1
  int[] stop = {(int)random(r2.x, r2.x + r2.w), (int)random(r2.y, r2.y + r2.h)}; //random point in r2
  
  int x = start[0];
  int y = start[1];
  
  int dx = 0;
  try { dx = (stop[0] - start[0])/abs(stop[0] - start[0]); } catch(Exception e) {};
  int dy = 0;
  try { dy = (stop[1] - start[1])/abs(stop[1] - start[1]); } catch(Exception e) {};
  
  int[] dir = {dx, 0};
  int[] dir2 = {0, dy};
  if(random(1) < 0.5) { dir = new int[] {0, dy}; dir2 = new int[] {dx, 0}; } //random chance of starting horizontal of veritcally
  
  while(x != stop[0] || y != stop[1]) {
    if(tiles[x][y] == WALL) tiles[x][y] = FLOOR;
    x += dir[0];
    y += dir[1];
    if(x == stop[0] || y == stop[1]) {
      dir[0] = dir2[0];
      dir[1] = dir2[1];
    }
  }
  
  return tiles;
}

public int[][] finishingPass(int[][] tiles, TileSet tileset) {
  int w = tiles.length;
  int h = tiles[0].length;
  int[][] newTiles = new int[w][h];
  for(int i = 0; i < w; i ++) {
    for(int j = 0; j < h; j ++) {
      if(tiles[i][j] == WALL) {
        newTiles[i][j] = tileset.walls[getBitMaskValue(tiles, i, j)];
      } else if(tiles[i][j] == FLOOR) {
        //use some random shit to add flavour to dungeons
        if(tileset.extras.size() > 0 && random(1) < 0.1) {
          newTiles[i][j] = tileset.extras.get((int)random(tileset.extras.size()));
        }
        else newTiles[i][j] = tileset.floor;
      } else {
        newTiles[i][j] = tiles[i][j];
      }
    }
  }
  return newTiles;
}

public boolean isWall(int[][] tiles, int i, int j) {
  try { return tiles[i][j] <= WALL; } catch(Exception e) { return true; }
}

public int getBitMaskValue(int[][] tiles, int i, int j) {
  int bmValue = 0;
  if(isWall(tiles, i, j-1)) bmValue += 1;
  if(isWall(tiles, i-1, j)) bmValue += 2;
  if(isWall(tiles, i+1, j)) bmValue += 4;
  if(isWall(tiles, i, j+1)) bmValue += 8;
  
  return bmValue;
}
