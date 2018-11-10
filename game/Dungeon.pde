class Dungeon extends Level {
  
  private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
  private float straightChance = 0.9, loopChance = 0.1;
  
  Dungeon(int w, int h) {
     super(w, h);
     
     //this.setTiles(generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance));
     this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
  }
  
}
