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
  try { 
    if (tiles[x-1][y] == WALL) num ++;
  } 
  catch(Exception e) {
  }  
  try { 
    if (tiles[x+1][y] == WALL) num ++;
  } 
  catch(Exception e) {
  }
  try { 
    if (tiles[x][y-1] == WALL) num ++;
  } 
  catch(Exception e) {
  }
  try { 
    if (tiles[x][y+1] == WALL) num ++;
  } 
  catch(Exception e) {
  } 
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
            for (int v = 0; v < valid.size(); v ++) {
              if (dir == direction(new int[] {x, y}, new int[] {valid.get(v)[0], valid.get(v)[0]}) &&
                random(1) < straightChance) {
                d = dir;
                n = v;
                break;
              }
            }
            if (d == -1) {
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
    if (!connected.contains(connector[2]) && connected.contains(connector[3])) c = 2;
    if (connected.contains(connector[2]) && !connected.contains(connector[3])) c = 3;
    if (c == 2 || c == 3) {
      connectors.remove(n);
      connected.add(connector[c]);
      tiles[connector[0]][connector[1]] = FLOOR;
    }
  }

  //add random connections to make dungeon more interesting
  for (int i = 0; i < connectors.size(); i ++) {
    if (random(1) < loopChance) {
      tiles[connectors.get(i)[0]][connectors.get(i)[1]] = FLOOR;
    }
  }

  //remove deadends
  for (int i = edgeSize; i < w-edgeSize; i ++) {
    for (int j = edgeSize; j < h-edgeSize; j ++) {
      if (tiles[i][j] == WALL) continue;
      int x = i;
      int y = j;
      int dir = getEndDirection(tiles, i, j);
      while (dir != -1) { //deadend found
        tiles[x][y] = WALL;
        if (dir == 0) y -= 1;
        if (dir == 2) y += 1;
        if (dir == 3) x -= 1;
        if (dir == 1) x += 1;
        dir = getEndDirection(tiles, x, y);
      }
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
  if (numNeighboursSimple(tiles, i, j) < 3 || tiles[i][j] == WALL) return -1; //not dead end
  //returns the direction of the corridor from a dead end
  if (tiles[i][j-1] == FLOOR) return 0; //up
  if (tiles[i][j+1] == FLOOR) return 2; //down
  if (tiles[i-1][j] == FLOOR) return 3; //left
  if (tiles[i+1][j] == FLOOR) return 1; //right
  return -1;
}

public int direction(int[] t1, int[] t2) {
  //returns the direction from t1 cell to t2
  if (t1[1] < t2[1] && t1[0] == t2[0]) return 0; //up
  if (t1[1] > t2[1] && t1[0] == t2[0]) return 2; //down
  if (t1[1] == t2[1] && t1[0] < t2[0]) return 3; //left
  if (t1[1] == t2[1] && t1[0] > t2[0]) return 1; //right
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
  if (isEdgeTile(tiles, x, y)) return false;
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
  if (tiles[i][j] != WALL) return;
  if (numNeighboursSimple(tiles, i, j) != 2) return;
  int t = -1, b = -1, l = -1, r = -1;
  int tr = -1, br = -1, lr = -1, rr = -1;
  try { 
    t = tiles[i][j-1]; 
    tr = region[i][j-1];
  } 
  catch(Exception e) {
  }  
  try { 
    b = tiles[i][j+1]; 
    br = region[i][j+1];
  } 
  catch(Exception e) {
  }  
  try { 
    l = tiles[i-1][j]; 
    lr = region[i-1][j];
  } 
  catch(Exception e) {
  }
  try { 
    r = tiles[i+1][j]; 
    rr = region[i+1][j];
  } 
  catch(Exception e) {
  }

  if ((t >= FLOOR && b >= FLOOR) && tr != br) { // >= FLOOR is non-solid block
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
  } 
  catch(Exception e) {
  }
  return (!edge && border);
}


/***
 Creates a dungeon and appends the tiles to the Level it's been given.
 
 level     - The level being generated for
 maxRooms  - Number of rooms the dungeon will have
 spread    - Angle variation of the rooms from existing rooms
 minRadius - minimum distance between rooms
 maxRadius - maximum distance between rooms
 spawnRoom - Preset for the spawn room
 bossRoom  - Preset for the boss romm
 rooms     - Presets for all other rooms
 ***/
public void generateConnectedDungeon(Level level, int maxRooms, float spread, int minRadius, int maxRadius, Room spawnRoom, Room bossRoom, Room[] rooms) {
  /* ---Pseudo---
   1. LOOP while num rooms != max
   - get a random room from the graph
   - create a new room within a radius of that room and connect it to the previously selected room
   - determine depth of current room
   2. Attach boss room to the deepest room in the dungeon
   3. Get the bounds of the graph (min/max for x and y) to determine the size of the level
   4. Go through list of rooms and copy their tiles into the tile map
   5. While adding tiles, add regions to the region lists.
   */
  ArrayList<PVector> bossRegions = new ArrayList<PVector>();
  ArrayList<PVector> generalRegions = new ArrayList<PVector>();
  ArrayList<Room> placedRooms = new ArrayList<Room>();
  ArrayList<Integer> depth = new ArrayList<Integer>();
  ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();

  float dir = random(TAU); //random direction for the dungeon to branch in.

  //add initial room
  Room initial = new Room(spawnRoom);
  initial.x = 0;
  initial.y = 0;
  placedRooms.add(initial);
  depth.add(0);
  graph.add(new ArrayList<Integer>());

  int minX = initial.x, minY = initial.y, maxX = initial.x + initial.w, maxY = initial.y + initial.h;

  //Generate graph
  while (placedRooms.size() < maxRooms) {
    int startPos = (int)random(placedRooms.size()); //get a random room;
    int sx = placedRooms.get(startPos).x;
    int sy = placedRooms.get(startPos).y;
    int newPos = placedRooms.size();
    boolean hit = true;
    Room room = new Room(rooms[(int)random(rooms.length)]);

    //find a place to put the room
    int tries = 0;
    boolean success = true;
    while (hit) {
      hit = false;
      float ang = random(dir - spread, dir + spread);
      float r = random(minRadius, maxRadius);
      room.x = (int)(sx + cos(ang) * r);
      room.y = (int)(sy + sin(ang) * r);
      for (int i = 0; i < placedRooms.size(); i ++) {
        if (placedRooms.get(i).collides(room)) {
          hit = true;
          break;
        }
      }
      tries ++;
      if(tries >= 100) {
        success = false;
        break;
      }
    }
    
    if(!success) continue;
    
    if (room.x < minX) minX = room.x;
    if (room.y < minY) minY = room.y;
    if (room.x + room.w > maxX) maxX = room.x + room.w;
    if (room.y + room.h > maxY) maxY = room.y + room.h;

    int minDist = maxRadius; //find closest room
    for (int i = 0; i < placedRooms.size(); i ++) {
      int dist = room.distance(placedRooms.get(i));
      if (dist < minDist) {
        minDist = dist;
        startPos = i;
      }
    }

    //add the new room to the graph;
    placedRooms.add(new Room(room));
    depth.add(depth.get(startPos) + 1);
    graph.add(new ArrayList<Integer>());

    //add the connections between rooms
    graph.get(startPos).add(newPos);
    graph.get(newPos).add(startPos);
  }

  //find deepest room and attach the boss room to it
  int deepest = -1;
  int maxDepth = -1;
  for (int i = 0; i < depth.size(); i ++) {
    if (depth.get(i) > maxDepth) {
      deepest = i;
      maxDepth = depth.get(i);
    }
  }
  if(deepest != -1) {
    int sx = placedRooms.get(deepest).x;
    int sy = placedRooms.get(deepest).y;
    int newPos = placedRooms.size();
    boolean hit = true;
    Room room = new Room(bossRoom);

    //find a place to put the room
    //DEFS COULD GET INFINITE LOOPS HERE :0
    int tries = 0;
    while (hit) { 
      hit = false;
      float ang = random(dir - spread, dir + spread);
      float r = random(minRadius, maxRadius);
      room.x = (int)(sx + cos(ang) * r);
      room.y = (int)(sy + sin(ang) * r);
      for (int i = 0; i < placedRooms.size(); i ++) {
        //Issue here
        if (placedRooms.get(i).collides(room)) {
          hit = true;
          if((tries ++) > 10) {
            minRadius += 1;
            maxRadius += 1;
          }
          break;
        }
      }
    }
    if (room.x < minX) minX = room.x;
    if (room.y < minY) minY = room.y;
    if (room.x + room.w > maxX) maxX = room.x + room.w;
    if (room.y + room.h > maxY) maxY = room.y + room.h;

    //add the new room to the graph;
    placedRooms.add(new Room(room));
    depth.add(depth.get(deepest) + 1);
    graph.add(new ArrayList<Integer>());

    //add the connections between rooms
    graph.get(deepest).add(newPos);
    graph.get(newPos).add(deepest);
  }

  minX -= 1;
  minY -= 1;
  maxX += 1;
  maxY += 1;

  int w = maxX - minX;
  int h = maxY - minY;
  
  
  int[][] tiles = new int[w][h];
  //offset all rooms to make them fit into the tile grid
  //MUST do this first so that all rooms are offset
  for(int i = 0; i < placedRooms.size(); i ++) {
    placedRooms.get(i).x -= minX;
    placedRooms.get(i).y -= minY;
  }
  
  saveRoomGraph(minX, maxX, minY, maxY, placedRooms, graph);
  
  //place rooms into tile grid
  for(int i = 0; i < placedRooms.size(); i ++) {
    Room room = placedRooms.get(i);
    for(int x = 0; x < room.w; x ++) {
      for(int y = 0; y < room.h; y ++) {
        tiles[x + room.x][y + room.y] = room.tiles[x][y];
        if(i > 0 && i < placedRooms.size() - 1) {
          //General room (not spawn or boss room)
          generalRegions.add(new PVector(x + room.x, y + room.y));
        } else if(i == placedRooms.size() - 1) {
          //Boss room
          bossRegions.add(new PVector(x + room.x, y + room.y));
        }
      }
    }
    for(int j = 0; j < graph.get(i).size(); j ++) {
      if(graph.get(i).get(j) > i) {
        tiles = connectRooms(tiles, placedRooms.get(i), placedRooms.get(graph.get(i).get(j)));
      }
    }
  }
  tiles = finishingPass(tiles, level.tileset);
  level.setTiles(tiles);
  level.setStart(placedRooms.get(0).midPoint());
  level.setZones(bossRegions, generalRegions);
}

public int[][] connectRooms(int[][] tiles, Room r1, Room r2) {
  int[] start = {(int)random(r1.x, r1.x + r1.w), (int)random(r1.y, r1.y + r1.h)}; //random point in r1
  int[] stop = {(int)random(r2.x, r2.x + r2.w), (int)random(r2.y, r2.y + r2.h)}; //random point in r2

  int x = start[0];
  int y = start[1];

  int dx = 0;
  int dy = 0;
  try { dx = Util.sign(stop[0] - start[0]); } catch(Exception e) {};
  try { dy = Util.sign(stop[1] - start[1]); } catch(Exception e) {};
  
  int[] dir = {dx, 0};
  int[] dir2 = {0, dy};
  if (fastAbs(dx) < fastAbs(dy)) { //do large axis first
    dir[0] = 0; 
    dir[1] = dy;
    dir2[0] = dx;
    dir2[1] = 0;
  }
  boolean changed = false;
  while(x != stop[0] || y != stop[1]) {
    if(tiles[x][y] == WALL) {
      tiles[x][y] = FLOOR;
    }    
    x += dir[0];
    y += dir[1];
    if(!changed && ((x == stop[0] && dx != 0) || (y == stop[1] && dy != 0))) {
      changed = true;
      dir[0] = dir2[0];
      dir[1] = dir2[1];
      if(dir[0] == 0 && dir[1] == 0) {
        break;
      }
    }
  }

  return tiles;
}

public int[][] finishingPass(int[][] tiles, TileSet tileset) {
  int w = tiles.length;
  int h = tiles[0].length;
  int[][] newTiles = new int[w][h];
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      if (tiles[i][j] == WALL) {
        newTiles[i][j] = tileset.walls[getBitMaskValue(tiles, i, j)];
      } else if (tiles[i][j] == FLOOR) {
        //use some random shit to add flavour to dungeons
        if (tileset.extras.size() > 0 && random(1) < 0.1) {
          newTiles[i][j] = tileset.extras.get((int)random(tileset.extras.size()));
        } else newTiles[i][j] = tileset.floor;
      } else {
        newTiles[i][j] = tiles[i][j];
      }
    }
  }
  return newTiles;
}

public boolean isWall(int[][] tiles, int i, int j) {
  try { 
    return tiles[i][j] <= WALL;
  } 
  catch(Exception e) { 
    return true;
  }
}

public int getBitMaskValue(int[][] tiles, int i, int j) {
  int bmValue = 0;
  if (isWall(tiles, i, j-1)) bmValue += 1;
  if (isWall(tiles, i-1, j)) bmValue += 2;
  if (isWall(tiles, i+1, j)) bmValue += 4;
  if (isWall(tiles, i, j+1)) bmValue += 8;

  return bmValue;
}

public void saveRoomGraph(int minX, int maxX, int minY, int maxY, ArrayList<Room> placedRooms, ArrayList<ArrayList<Integer>> graph) {
  PGraphics pg = createGraphics((maxX - minX) * 10, (maxY - minY) * 10);
  pg.beginDraw();
  pg.background(0);
  pg.textAlign(CENTER, CENTER);
  pg.textSize(20);
  pg.stroke(255);
  for (int i = 0; i < graph.size(); i ++) {
    pg.fill(255);
    Room room = placedRooms.get(i);
    pg.rect(room.x * 10, room.y * 10, room.w * 10, room.h * 10);
    for (int j = 0; j < graph.get(i).size(); j ++) {
      Room room2 = placedRooms.get(graph.get(i).get(j));
      pg.line(room.midPoint().x * 10, room.midPoint().y * 10, room2.midPoint().x * 10, room2.midPoint().y * 10);
    }
    pg.fill(0);
    pg.text(i, room.midPoint().x * 10, room.midPoint().y * 10);
  }
  pg.endDraw();
  pg.save("/out/TestGen.png");
}

/*
println(deepest, maxX - minX, maxY - minY);

PGraphics pg = createGraphics((maxX - minX) * 10, (maxY - minY) * 10);
pg.beginDraw();
pg.background(0);
pg.fill(255);
pg.stroke(255);
for (int i = 0; i < graph.size(); i ++) {
  Room room = placedRooms.get(i);
  pg.rect((room.x - minX) * 10, (room.y - minY) * 10, room.w * 10, room.h * 10);
  println(room.x, room.y);
  for (int j = 0; j < graph.get(i).size(); j ++) {
    Room room2 = placedRooms.get(graph.get(i).get(j));
    pg.line((room.midPoint().x - minX) * 10, (room.midPoint().y - minY) * 10, (room2.midPoint().x - minX) * 10, (room2.midPoint().y - minY) * 10);
  }
}
pg.endDraw();
pg.save("/out/TestGen.png");
*/
