interface Enemy {
  
  public int tier = 0;
  public int xPos = 0;
  public int yPos = 0;
  
  /* Enemies need to update on tics */
  void update();
  
  /* Displays enemy to screen */
  void show();
  
  /* This mob takes damage */
  void damage(int amount);
  
}

class EnemyStats extends Stats {
  
  EnemyStats() {
    super();
    setHealth(2);
    setAttack(1);
    setSpeed(2);
    setDefence(1); 
  }
  
}
