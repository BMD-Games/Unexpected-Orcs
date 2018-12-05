class GrassDungeon extends Level {
  
  private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
  private float straightChance = 0.9, loopChance = 0.05;
  
  GrassDungeon() {
    super(60, 45, "DungeonGrass", grassTileset());
    
    this.setTiles(finishingPass(generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance), tileset));
    //this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
    generateStart();
    generateEnemies();
  }
  
  void generateEnemies() {
    //Add enemies to level
    /*Elemental elemental;
    for(int i = 0; i < 20; i ++) {
      elemental = new FireElemental(random(w), random(h), 1);
      validSpawn(elemental);
      addEnemy(elemental);
    }
    for(int i = 0; i < 20; i ++) {
      elemental = new IceElemental(random(w), random(h), 1);
      validSpawn(elemental);
      addEnemy(elemental);
    }
    for(int i = 0; i < 20; i ++) {
      elemental = new MagicElemental(random(w), random(h), 1);
      validSpawn(elemental);
      addEnemy(elemental);
    }
    for(int i = 0; i < 20; i ++) {
      elemental = new PoisonElemental(random(w), random(h), 1);
      validSpawn(elemental);
      addEnemy(elemental);
    }*/
   
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
    //chomp = new BossChomp(random(w), random(h), 3);
    //validSpawn(chomp);
    //addEnemy(chomp);
  }
  
  void validSpawn(StandardEnemy enemy) {
    while(!enemy.validPosition(this, enemy.x, enemy.y)) {
      enemy.x = random(w);
      enemy.y = random(h);
    }
  }
  
}

class CellarDungeon extends Level {
  
  private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 9;
  private float straightChance = 1, loopChance = 0.05;
  
  CellarDungeon() {
    super(60, 45, "DungeonCellar", dungeonTileset());
    
    this.setTiles(finishingPass(generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance), tileset));
    //this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
    generateStart();
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

class DankDungeon extends Level {
  
  DankDungeon() {
    super(60, 45, "Dannnk", testTileset());
    generateConnectedDungeon(this, 5, 5, testSpawn(), testBoss(), new Room[]{testRoom()}, 0.75);
  }
  
}

class Cave extends Level{
  
  float chance = 0.4; //chance the a cell will be a wall
  int iterations = 5;
  
  Cave() {    
    super(120, 90, "Cave", caveTileset());
    
    this.setTiles(finishingPass(generateCave(w, h, iterations, chance), tileset));
    generateStart();
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
