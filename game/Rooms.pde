class Room {

  public int x, y, w, h;
  public int[][] tiles;

  Room() {}
  
  Room(Room room) { //create a room from another room
    this.x = room.x;
    this.y = room.y;
    this.w = room.w;
    this.h = room.h;
    this.tiles = room.tiles;    
  }

  public boolean collides(Room room) {
    return (x + w >= room.x  &&   // r1 right edge past r2 left
      x <= room.x + room.w &&   // r1 left edge past r2 right
      y + h >= room.y      &&   // r1 top edge past r2 bottom
      y <= room.y + room.h);    // r1 bottom edge past r2 top
  }

  public boolean inRoom(int px, int py) {
    return Util.pointInBox(px, py, x, y, w, h);
  }

  public void setTiles(int[][] tiles) {
    this.tiles = tiles;
    w = tiles.length;
    h = tiles[0].length;
  }
}

public Room randomRoom(int minSize, int maxSize, int w, int h) {  
  Room room = new Room();
  room.w = floor(random(minSize, maxSize)); //room width
  room.h = floor(random(minSize, maxSize)); //room height
  room.x = floor(random(edgeSize, w - room.w - edgeSize)); //room x pos - avoid edges
  room.y = floor(random(edgeSize, h - room.h - edgeSize)); //toom y pos - avoid edges

  return new Room();
}


public Room randomlyPlaceRoom(Room room, int w, int h) {
  room.x = (int)random(edgeSize, w - room.w - edgeSize);
  room.y = (int)random(edgeSize, h - room.h - edgeSize);
  return room;
}

public Room testSpawn() {
  Room room = new Room();
  room.setTiles(new int[][]{
    {2, 2, 2, 4, 2, 2, 2, 5, 2}, 
    {2, 5, 6, 6, 6, 6, 6, 2, 2}, 
    {2, 6, 6, 10, 10, 10, 6, 6, 2}, 
    {2, 6, 10, 10, 13, 10, 10, 6, 2}, 
    {4, 6, 10, 13, 11, 13, 10, 6, 2}, 
    {2, 6, 10, 10, 13, 10, 10, 6, 2}, 
    {2, 6, 6, 10, 10, 10, 6, 6, 2}, 
    {5, 2, 6, 6, 6, 6, 6, 2, 5}, 
    {2, 2, 2, 2, 2, 4, 2, 2, 2}
  });
  return room;
}

public Room testRoom() {
  Room room = new Room();
  room.setTiles(new int[][]{
    {2, 2, 2, 2, 2}, 
    {2, 2, 2, 2, 2}, 
    {2, 2, 8, 2, 2}, 
    {2, 2, 2, 2, 2}, 
    {2, 2, 2, 2, 2}
  });
  return room;
}

public Room testBoss() {
  Room room = new Room();
  room.setTiles(new int[][]{
    {9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9}, 
    {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8}, 
    {8, 6, 0, 0, 0, 2, 0, 0, 0, 6, 8}, 
    {8, 6, 0, 2, 2, 2, 2, 2, 0, 6, 8}, 
    {8, 6, 0, 2, 2, 2, 2, 2, 0, 6, 8}, 
    {8, 6, 2, 2, 2, 3, 2, 2, 2, 6, 8}, 
    {8, 6, 0, 2, 2, 2, 2, 2, 0, 6, 8}, 
    {8, 6, 0, 2, 2, 2, 2, 2, 0, 6, 8}, 
    {8, 6, 0, 0, 0, 2, 0, 0, 0, 6, 8}, 
    {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8}, 
    {9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9}
  });
  return room;
}
