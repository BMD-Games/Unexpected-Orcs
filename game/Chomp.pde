class Chomp implements Enemy {
  
  public int tier = 0;
  public int xPos = 0;
  public int yPos = 0;
  
  private EnemyStats stats;
  
  public Chomp(int x, int y, int tier) {
    this.tier = tier;
    xPos = x;
    yPos = y;
    stats = new EnemyStats();
  }
  
  /* Enemies need to update on tics */
  void update() {}
  
  /* Displays enemy to screen */
  void show(){}
  
  /* This mob takes damage */
  void damage(int amount){}
  
}

class BossChomp implements Enemy {
  
  public BossChomp(int x, int y, int tier) {
    
  }
  
  /* Enemies need to update on tics */
  void update() {}
  
  /* Displays enemy to screen */
  void show(){}
  
  /* This mob takes damage */
  void damage(int amount){}
  
}
