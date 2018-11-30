class Cave extends Level{
  
  float chance = 0.4; //chance the a cell will be a wall
  int iterations = 5;
  
  Cave() {    
    super(120, 90);
    name = "Cave";
    
    //--set tiles in tileset--
    tileset = caveTileset();
    //tileset = testTileset();
    
    this.setTiles(generateCave(w, h, iterations, chance));
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
