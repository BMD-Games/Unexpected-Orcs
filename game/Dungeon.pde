class GrassDungeon extends Level {
  
  private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
  private float straightChance = 0.9, loopChance = 0.05;
  
  GrassDungeon() {
    super(60, 45);
    super.name = "DungeonWindy"; 
     
    //--set tiles in tileset--
    tileset = grassTileset();
    
    this.setTiles(generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance));
    //this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
    
    generateEnemies();
  }
  
  void generateEnemies() {
   //Add enemies to level
    Chomp chomp;
    for(int i = 0; i < 40; i ++) {
      chomp = new Chomp(random(w), random(h), 1);
      validSpawn(chomp);
      addEnemy(chomp);
    }
    for(int i = 0; i < 10; ++i) {
      chomp = new BigChomp(random(w), random(h), 2);
      validSpawn(chomp);
      addEnemy(chomp);
    }
    chomp = new BossChomp(random(w), random(h), 3);
    validSpawn(chomp);
    addEnemy(chomp);
  }
  
  void validSpawn(Chomp enemy) {
    while(!enemy.validPosition(this, enemy.x, enemy.y)) {
      enemy.x = random(w);
      enemy.y = random(h);
    }
  }
  
}

class CellarDungeon extends Level {
  
  private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
  
  CellarDungeon() {
    super(60, 45);
    super.name = "DungeonWindy"; 
     
    //--set tiles in tileset--
    tileset = dungeonTileset();
    
    //this.setTiles(generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance));
    this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
    
    generateEnemies();
  }
  
  void generateEnemies() {
   //Add enemies to level
    Chomp chomp;
    for(int i = 0; i < 40; i ++) {
      chomp = new Chomp(random(w), random(h), 1);
      validSpawn(chomp);
      addEnemy(chomp);
    }
    for(int i = 0; i < 10; ++i) {
      chomp = new BigChomp(random(w), random(h), 2);
      validSpawn(chomp);
      addEnemy(chomp);
    }
    chomp = new BossChomp(random(w), random(h), 3);
    validSpawn(chomp);
    addEnemy(chomp);
  }
  
  void validSpawn(Chomp enemy) {
    while(!enemy.validPosition(this, enemy.x, enemy.y)) {
      enemy.x = random(w);
      enemy.y = random(h);
    }
  }
  
}
