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
    StandardEnemy enemy;
    for(int i = 0; i < 12; i ++) {
      enemy = new FireElemental(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 12; i ++) {
      enemy = new IceElemental(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 12; i ++) {
      enemy = new MagicElemental(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 12; i ++) {
      enemy = new PoisonElemental(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for (int i = 0; i < 40; i ++) {
      enemy = new Chomp(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for (int i = 0; i < 10; ++i) {
      enemy = new BigChomp(random(w), random(h), 2);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    enemy = new BossChomp(random(w), random(h), 3);
    validSpawn(enemy);
    addEnemy(enemy);
    bosses.add(enemy);
    for (int i = 0; i < 30; i ++) {
      enemy = new Rose(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for (int i = 0; i < 30; ++i) {
      enemy = new Daisy(random(w), random(h), 2);
      validSpawn(enemy);
      addEnemy(enemy);
    }
  }

  void validSpawn(StandardEnemy enemy) {
    while (!enemy.validPosition(this, enemy.x, enemy.y)) {
      enemy.x = random(w);
      enemy.y = random(h);
    }
  }
}

class CellarDungeon extends Level {

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

  CellarDungeon() {
    super(60, 45, "Cellar", cellarTileset());
    generateConnectedDungeon(this, 20, PI/4, 10, 15, testSpawn(), testBoss(), new Room[]{testRoom()});
  }
  
  
}

class Cave extends Level {
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
    StandardEnemy enemy;
    for(int i = 0; i < 30; ++i) {
      enemy = new GoblinArcher(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new GoblinMage(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new GoblinSpearman(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new GoblinWarrior(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new Spider(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new Crawler(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
    for(int i = 0; i < 30; ++i) {
      enemy = new Bat(random(w), random(h), 1);
      validSpawn(enemy);
      addEnemy(enemy);
    }
  }
  
  void validSpawn(StandardEnemy enemy) {
    while(!enemy.validPosition(this, enemy.x, enemy.y)) {
      enemy.x = random(w);
      enemy.y = random(h);
    }
  }
}
