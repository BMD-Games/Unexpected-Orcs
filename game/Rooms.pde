class Room {

  public int x, y, w, h;
  public int[][] tiles;
  public int doorx, doory;


  Room(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    tiles = new int[w][h];
  }

  public boolean collides(Room room) {
    return (x + w >= room.x  &&   // r1 right edge past r2 left
      x <= room.x + room.w &&   // r1 left edge past r2 right
      y + h >= room.y      &&   // r1 top edge past r2 bottom
      y <= room.y + room.h);    // r1 bottom edge past r2 top
  }

  public boolean inRoom(int px, int py) {
    return Utility.pointInBox(px, py, x, y, w, h);
  }
  
  public void setTiles(int[][] tiles) {
    this.tiles = tiles;
    w = tiles.length;
    h = tiles[0].length;
  }
  
}

public Room randomRoom(int minSize, int maxSize, int w, int h) {
  int rw = floor(random(minSize, maxSize)); //room width
  int rh = floor(random(minSize, maxSize)); //room height
  int rx = floor(random(edgeSize, w - rw - edgeSize)); //room x pos - avoid edges
  int ry = floor(random(edgeSize, h - rh - edgeSize)); //toom y pos - avoid edges
  return new Room(rx, ry, rw, rh);
}
