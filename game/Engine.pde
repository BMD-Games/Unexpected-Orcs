class Engine {
  /**
  This class is used to update and track all the game objects (eg player, monsters, levels)
  **/
  private double lastUpdate = 0;
  
  private Level currentLevel;
  
  private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
  private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
  
  private Player player;
  
  Engine() {
    //Can initialise stuff here (eg generate the first cave)
    //currentLevel = new Cave();
    currentLevel = new Cave(120, 90);
    player = new Player(currentLevel.start.x + 0.5, currentLevel.start.y + 0.5);
  }
  
  public void update() {
    //updates all game objects
    double delta = (millis() - lastUpdate)/1000; //seconds passed since last update
    println(delta, frameRate);
    
    player.update(delta);
    currentLevel.update(player.x, player.y);
    
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      if(!currentLevel.enemies.get(i).update(delta)) { //if update function returns false, the enemy is dead
        currentLevel.enemies.remove(i); //remove enemy
      }
    }
    for(int i = enemyProjectiles.size() - 1; i >= 0; i --) {
      if(!enemyProjectiles.get(i).update(delta)) { //if update function returns false, the projectile is dead
        enemyProjectiles.remove(i); //remove projectile
      }
    }
    for(int i = playerProjectiles.size() - 1; i >= 0; i --) {
      if(!playerProjectiles.get(i).update(delta)) { //if update function returns false, the projectile is dead
        playerProjectiles.remove(i); //remove projectile
      }
    }    
    
    lastUpdate = millis();
  }
  
  public void show() {
    background(255);
    currentLevel.show();
    player.show();
    //enemies.show();
  }
  
  public void handleMouseReleased() {
    //handle mouse released
  }
  
}
