class Dungeon extends Level {
  
  private int roomAttempts = 50, minRoomSize = 2, maxRoomSize = 10;
  private float straight = 0.8;
  
  Dungeon(int w, int h) {
     super(w, h);
     
     this.setTiles(generateDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straight));
  }
  
}
