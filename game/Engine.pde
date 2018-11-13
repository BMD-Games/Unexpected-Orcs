
public final static int up    = 0;
public final static int down  = 2;
public final static int left  = 3;
public final static int right = 1;


class Engine {
  /**
  This class is used to update and track all the game objects (eg player, monsters, levels)
  **/
  private double lastUpdate = 0;
  
  private Level currentLevel;
  
  private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
  private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
  
  private PVector camera = new PVector(0, 0);
  
  private Player player;
  
  Engine() {
    //Can initialise stuff here (eg generate the first cave)
    currentLevel = new Dungeon(60, 45);
    //currentLevel = new Cave(120, 90);
    player = new Player(currentLevel.start.x + 0.5, currentLevel.start.y + 0.5);
  }
  
  public void update() {
    //updates all game objects
    double delta = (millis() - lastUpdate)/1000; //seconds passed since last update
    //println(delta, frameRate);
    
    if(mousePressed) handleMouse();
    
    player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
    updateCamera(player.x, player.y);
    currentLevel.update(camera.x, camera.y);
    
    updateEnemies(delta, player.x, player.y);
    updateProjectiles(delta);  
    
    lastUpdate = millis();
  }
  
  public void show() {    
    currentLevel.show(getRenderOffset());
    player.show(getRenderOffset());
    
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      currentLevel.enemies.get(i).show(getRenderOffset());
    }
    
    for(int i = enemyProjectiles.size() - 1; i >= 0; i --) {
      enemyProjectiles.get(i).show(getRenderOffset());
    }
    for(int i = playerProjectiles.size() - 1; i >= 0; i --) {
      playerProjectiles.get(i).show(getRenderOffset()); //if update function returns false, the projectile is dead
    }
  }
  
  public PVector getRenderOffset() {
    //gets the position of the camera relative to the centre of the screen
    return new PVector(camera.x * TILE_SIZE - (width/2), camera.y * TILE_SIZE - (height/2));
  }
  
  public void handleMouse() {
    //handle mouse released
    //need to do something like add(player.getCurrentWeapon().newProjectile());
    playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang), 10, 6, 10));
  }
  
  private void updateCamera(float x, float y) {
    camera.x = x + cos(player.ang) * dist(mouseX, mouseY, width/2, height/2) * 0.001;
    camera.y = y + sin(player.ang) * dist(mouseX, mouseY, width/2, height/2) * 0.001;
  }
  
  private void showCamera() {
    fill(0, 0, 255);
    ellipse(camera.x * TILE_SIZE - getRenderOffset().x, camera.y * TILE_SIZE - getRenderOffset().y, 5, 5);
  }
  
  private void updateEnemies(double delta, float x, float y) {
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      if(!currentLevel.enemies.get(i).update(delta, x, y)) { //if update function returns false, the enemy is dead
        currentLevel.enemies.remove(i); //remove enemy
      }
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
  
}
