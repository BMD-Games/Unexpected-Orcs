class Engine {
  /**
  This class is used to update and track all the game objects (eg player, monsters, levels)
  **/
  private double lastUpdate = 0;
  
  public Level currentLevel;
  
  private ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
  private ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
  
  private ArrayList<Drop> drops = new ArrayList<Drop>();  
  private ArrayList<Text> text = new ArrayList<Text>();
  
  public int closestBag = -1;
  private int closestBagDist = (int)Double.POSITIVE_INFINITY;
  
  private PVector camera = new PVector(0, 0);
  
  private PGraphics screen;
  
  private Player player;
  
  Engine() {
    //Can initialise stuff here (eg generate the first cave)
    currentLevel = new Dungeon(60, 45);
    //currentLevel = new Cave(120, 90);
    
    player = new Player(currentLevel.start.x + 0.5, currentLevel.start.y + 0.5);
    
    screen = createGraphics(width - GUI_WIDTH, height);
    
  }
  
  public void update() {
    //updates all game objects
    double delta = (millis() - lastUpdate)/1000; //seconds passed since last update
    if(mousePressed && !inMenu) handleMouse();
    
    player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
    updateCamera(player.x, player.y);
    currentLevel.update(screen, camera.x, camera.y);
    
    updateProjectiles(delta); 
    updateEnemies(delta, player.x, player.y);
    updateDrops(delta, player.x, player.y);
    
    updateText(delta);
    
    lastUpdate = millis();
  }
  
  public void updateMillis() {
    lastUpdate = millis();
  }
  
  public void show() {
    screen.beginDraw();
    currentLevel.show(screen, getRenderOffset());
    
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      currentLevel.enemies.get(i).show(screen, getRenderOffset());
    }
    
    for(int i = enemyProjectiles.size() - 1; i >= 0; i --) {
      enemyProjectiles.get(i).show(screen, getRenderOffset());
    }
    for(int i = playerProjectiles.size() - 1; i >= 0; i --) {
      playerProjectiles.get(i).show(screen, getRenderOffset());
    }
    
    for(int i = drops.size() - 1; i >= 0; i --) {
      drops.get(i).show(screen, getRenderOffset());
    }
    
    player.show(screen, getRenderOffset());
    
    for(int i = text.size() - 1; i >= 0; i --) {
      text.get(i).show(screen, getRenderOffset());
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
    
    Weapon weapon = player.currentWeapon();
    if (weapon != null) {
      if (player.stats.getFireTimer() >= weapon.fireRate) {
        
        for (int i = 0; i < weapon.numBullets; i++) {
          playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-weapon.accuracy, weapon.accuracy)), 
              weapon.bulletSpeed, weapon.range, weapon.damage + player.stats.getAttack(), weapon.bulletSprite));
        }
        player.stats.setFireTimer(0);
      } 
    }
  }
  
  private void updateCamera(float x, float y) {
    camera.x = x;
    camera.y = y;
  }
  
  private void showCamera() {
    fill(0, 0, 255);
    ellipse(camera.x * TILE_SIZE - getRenderOffset().x, camera.y * TILE_SIZE - getRenderOffset().y, 5, 5);
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
      Projectile projectile = playerProjectiles.get(i);
      projectile.update(delta);
      if(projectileIsDead(playerProjectiles.get(i))) { //if the projectile is dead
        playerProjectiles.remove(i); //remove projectile
        break;
      }
      for(Enemy enemy : currentLevel.enemies) {
        if(enemy.pointCollides(projectile.x, projectile.y)) {
          enemy.damage(projectile.getDamage());
          playerProjectiles.remove(i);
          break;
        }
      }
      
    }
  }
  
  private void updateEnemies(double delta, float x, float y) {
    for(int i = currentLevel.enemies.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      Enemy enemy = currentLevel.enemies.get(i);
      if(!enemy.update(delta, x, y)) { //if update function returns false, the enemy is dead
        enemy.onDeath();
        currentLevel.enemies.remove(i); //remove enemy
      }
    }
  }
  
  private boolean projectileIsDead(Projectile proj) {
    boolean hitWall = currentLevel.getTile((int)proj.x, (int)proj.y) <= WALL;
    return !proj.alive() || hitWall;
  }
  
  private void updateDrops(double delta, float px, float py) {
    closestBag = -1;
    closestBagDist = (int)Double.POSITIVE_INFINITY;
    
    for(int i = drops.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      if(!drops.get(i).update(delta, player.x, player.y)) { //if update function returns false, the drop is dead
        drops.remove(i); //remove drop
        continue;
      } else if(drops.get(i) instanceof ItemBag) {
        if(drops.get(i).inRange(px, py) && drops.get(i).getDist(px, py) < closestBagDist) {
          closestBag = i;
        }
      }
    }
  }
  
  private void updateText(double delta) {
    for(int i = text.size() - 1; i >= 0; i --) {
      if(!text.get(i).update(delta)) {
        text.remove(i);
      }
    }
  }
  

  public void addDrop(Drop drop) {
    drops.add(drop); 
  }
  
  public void addText(String cooldown, float xp, float yp, float life, color c) {
    text.add( new Text(cooldown, xp, yp, life, c));
  }
  
  public Item[] getClosestBagItems() {
    if(closestBag >= 0) try { return ((ItemBag)drops.get(closestBag)).items; } catch(Exception e) { return null; }
    return null;
  }
  
  public ItemBag getClosestBag() {
    if(closestBag >= 0) try { return (ItemBag)drops.get(closestBag); } catch(Exception e) { return null; }
    return null;
  }
  
}

class Text {
  
  float x, y, life, time = 0;
  String message;
  color c;
  
  Text(String message, float xp, float yp, float lifeTime, color c) {
    this.message = message;
    this.life = lifeTime;
    x = xp;
    y = yp;
    this.c = c;
  }
  
  public boolean update(double delta) {
    time += delta;
    return time < life;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    
    float a = 255 - (255 * time / life);
    screen.fill(red(c), green(c), blue(c), a);
    screen.textSize(SPRITE_SIZE);
    screen.text(message, x * TILE_SIZE - renderOffset.x, (y - (time/life)) * TILE_SIZE - renderOffset.y);
    
  }
  
}
