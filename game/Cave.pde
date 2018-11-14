class Cave extends Level{
  
  float chance = 0.4; //chance the a cell will be a wall
  int iterations = 5;
  
  Cave(int w, int h) {
    super(w, h);
    super.name = "Cave";
    
    //--set tiles in tileset--
    tileset = caveTileset();
    
    this.setTiles(generateCave(w, h, iterations, chance));
  }
  
}