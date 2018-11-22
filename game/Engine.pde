class Engine {
  /**
  This class is used to update and track all the game objects (eg player, monsters, levels)
  **/
  private double lastUpdate = 0;
  
  private Level currentLevel;
  
  private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
  private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
  
  private PVector camera = new PVector(0, 0);
  
  private PGraphics screen;
  
  private Player player;
  
  Engine() {
    //Can initialise stuff here (eg generate the first cave)
    //currentLevel = new Dungeon(60, 45);
    
    currentLevel = new Cave(120, 90);
    player = new Player(currentLevel.start.x + 0.5, currentLevel.start.y + 0.5);
    player.setWeapon(new Sniper());
    
    screen = createGraphics(width - GUI_WIDTH, height);
  }
  
  public void update() {
    //updates all game objects
    double delta = (millis() - lastUpdate)/1000; //seconds passed since last update
    
    if(mousePressed) handleMouse();
    
    player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
    player.increaseFireCount();
    updateCamera(player.x, player.y);
    currentLevel.update(screen, camera.x, camera.y);
    
    updateEnemies(delta, player.x, player.y);
    updateProjectiles(delta);  
    
    lastUpdate = millis();
    
    if (player.stats.healthCurr < player.stats.health) {
   
      player.stats.healthCurr++;
    }
  }
  
  public void show() {
    screen.beginDraw();
    currentLevel.show(screen, getRenderOffset());
    player.show(screen, getRenderOffset());
    
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      //currentLevel.enemies.get(i).show(getRenderOffset());
    }
    
    for(int i = enemyProjectiles.size() - 1; i >= 0; i --) {
      enemyProjectiles.get(i).show(screen, getRenderOffset());
    }
    for(int i = playerProjectiles.size() - 1; i >= 0; i --) {
      playerProjectiles.get(i).show(screen, getRenderOffset()); //if update function returns false, the projectile is dead
    }
    screen.endDraw();
    image(screen, GUI_WIDTH, 0);
  }
  
  public PVector getRenderOffset() {
    //gets the position of the camera relative to the centre of the screen
    return new PVector(camera.x * TILE_SIZE - (screen.width/2), camera.y * TILE_SIZE - (screen.height/2));
  }
  
  public void handleMouse() {
    //handle mouse released
    //need to do something like add(player.getCurrentWeapon().newProjectile());
    // PVector.fromAngle(player.ang)
    
    if (player.hasWeapon()) {
      Weapon weapon = player.getWeapon();
      if (player.getFireCount() >= weapon.fireRate) {
        
        for (int i = 0; i < weapon.numBullets; i++) {
          playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-weapon.accuracy, weapon.accuracy)), 
              weapon.bulletSpeed, weapon.range, weapon.damageMulti * player.stats.attack, "BULLET"));
        }
        player.resetFireCount();
      }
      
      
    }
    
    /*
    if (player.getFireCount() >= 10) {
      
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-0.10, 0.10)), 10, 6, 10));
      player.resetFireCount();
      
    }
    */
    
  }
  
  private void updateCamera(float x, float y) {
    camera.x = x;
    camera.y = y;
  }
  
  private void showCamera() {
    fill(0, 0, 255);
    ellipse(camera.x * TILE_SIZE - getRenderOffset().x, camera.y * TILE_SIZE - getRenderOffset().y, 5, 5);
  }
  
  private void updateEnemies(double delta, float x, float y) {
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      /*if(!currentLevel.enemies.get(i).update(delta, x, y)) { //if update function returns false, the enemy is dead
        currentLevel.enemies.remove(i); //remove enemy
      }*/
    }
  }
  
  private void updateProjectiles(double delta) {
    
    //---enemy projectiles
    for(int i = enemyProjectiles.size() - 1; i >= 0; i --) {
      enemyProjectiles.get(i).update(delta);
      if(projectileIsDead(enemyProjectiles.get(i))) { //if the projectile is dead
        enemyProjectiles.remove(i); //remove projectile
      }
    }
    
    
    //---player projectiles
    for(int i = playerProjectiles.size() - 1; i >= 0; i --) {
      playerProjectiles.get(i).update(delta);
      if(projectileIsDead(playerProjectiles.get(i))) { //if the projectile is dead
        playerProjectiles.remove(i); //remove projectile
      }
    }
  }
  
  private boolean projectileIsDead(Projectile proj) {
    boolean hitWall = currentLevel.getTile((int)proj.x, (int)proj.y) <= WALL;
    return !proj.alive() || hitWall;
  }
  
  public Player getPlayer() {
    return player;
  }
  
}
