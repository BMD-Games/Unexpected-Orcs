class Cave extends Level{
  
  float chance = 0.5; //chance the a cell will be a wall
  int iterations = 5;
  
  Cave(int w, int h) {
    super(w, h);
    super.name = "Cave";
    
    //--set tiles in tileset--
    tileset.floor = STONE;
    tileset.wall = ROCK_WALL;
    tileset.bottomWall = ROCK_BOTTOM_WALL;
    tileset.innerWall = DARK_ROCK_WALL;
    tileset.spawn = X_STONE;
    tileset.extras.add(RUBBLE_STONE);
    tileset.extras.add(SKULL_STONE);  
    
    this.setTiles(generateCave(w, h, iterations, chance, tileset));
  }
  
}
