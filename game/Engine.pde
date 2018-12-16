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

  public boolean finishedLoadingLevel = false;

  public int closestBag = -1;
  public int closestPortal = -1;
  private float closestBagDist = (float)Double.POSITIVE_INFINITY;
  private float closestPortalDist = (float)Double.POSITIVE_INFINITY;

  private PVector camera = new PVector(0, 0);

  private PGraphics screen;

  private Player player;

  Engine() {
    //Can initialise stuff here (eg generate the first cave)
    currentLevel = new CellarDungeon(); //CellarDungeon(); //Cave() //GrassDungeon;
    finishedLoadingLevel = true;

    player = new Player(currentLevel.start.x + 0.5, currentLevel.start.y + 0.5);

    screen = createGraphics(width - GUI_WIDTH, height);
    screen.beginDraw();
    screen.noSmooth();
    screen.endDraw();
  }

  public void update() {
    //updates all game objects

    double delta = (millis() - lastUpdate)/1000; //seconds passed since last update
    if (mousePressed && !inMenu) handleMouse();

    player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
    if (player.stats.getHealth() <= 0) {
      //absolutely get hack3d loser
      //setState("DEAD");
    }
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

    for (Drop drop : drops) {
      drop.show(screen, getRenderOffset());
    }

    ArrayList<Integer> chunks = currentLevel.getChunks((int)player.x, (int)player.y);
    for (int i = 0; i < chunks.size(); i ++) {
      for (int j = 0; j < currentLevel.enemies[chunks.get(i)].size(); j ++) {
        currentLevel.enemies[chunks.get(i)].get(j).show(screen, getRenderOffset());
      }
    }

    for (Projectile projectile : enemyProjectiles) {
      projectile.show(screen, getRenderOffset());
    }
    for (Projectile projectile : playerProjectiles) {
      projectile.show(screen, getRenderOffset());
    }

    player.show(screen, getRenderOffset());

    for (Text txt : text) {
      txt.show(screen, getRenderOffset());
    }
    screen.image(vingette, 0, 0, screen.width, screen.height);
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
        weapon.playSound();

        ArrayList<Pair> projectileStats = weapon.statusEffects == null ? new ArrayList<Pair>() : weapon.statusEffects;
        projectileStats.addAll(engine.player.inv.currentScroll().statusEffects);

        for (int i = 0; i < weapon.numBullets; i++) {
          playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + random(-weapon.accuracy, weapon.accuracy)), 
            weapon.bulletSpeed, weapon.range, weapon.damage + player.stats.getAttack(), weapon.bulletSprite, projectileStats));
        }
        player.stats.fireTimer = 0;
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
    Projectile projectile;
    for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {
      projectile = enemyProjectiles.get(i);
      projectile.update(delta);
      if (Rectangle.lineCollides(projectile.x, projectile.y, projectile.px, projectile.py, player.x, player.y, player.w, player.h)) {
        player.damage(projectile.damage);
        enemyProjectiles.remove(i);
        continue;
      }
      if (projectileIsDead(projectile)) { //if the projectile is dead
        enemyProjectiles.remove(i); //remove projectile
      }
    }

    //---player projectiles
    for (int i = playerProjectiles.size() - 1; i >= 0; i--) {
      projectile = playerProjectiles.get(i);
      projectile.update(delta);
      if (projectileIsDead(projectile)) { //if the projectile is dead
        playerProjectiles.remove(i); //remove projectile
        break;
      }
      int chunk = currentLevel.getChunk((int)projectile.x, (int)projectile.y);
      for (Enemy enemy : currentLevel.enemies[chunk]) {
        if (enemy.lineCollides(projectile.x, projectile.y, projectile.px, projectile.py)) {
          enemy.damage(projectile.damage, projectile.statusEffects);
          enemy.knockback(projectile);
          playerProjectiles.remove(i);
          break;
        }
      }
    }
  }

  private void updateEnemies(double delta, float px, float py) {
    ArrayList<Integer> chunks = currentLevel.getChunks((int)player.x, (int)player.y);
    for (int i = 0; i < chunks.size(); i ++) {
      for (int j = currentLevel.enemies[chunks.get(i)].size() - 1; j >= 0; j --) {
        StandardEnemy enemy = (StandardEnemy)currentLevel.enemies[chunks.get(i)].get(j);
        if (!enemy.update(delta)) { //if update function returns false, the enemy is dead
          enemy.onDeath();
          currentLevel.enemies[chunks.get(i)].remove(j); //remove enemy
        } else if (currentLevel.getChunk((int)enemy.x, (int)enemy.y)  != chunks.get(i)) {
          currentLevel.enemies[chunks.get(i)].remove(j);
          currentLevel.addEnemy(enemy);
        }
      }
    }
  }

  private boolean projectileIsDead(Projectile proj) {
    boolean hitWall = currentLevel.getTile((int)proj.x, (int)proj.y) <= WALL;
    return !proj.alive() || hitWall;
  }

  private void updateDrops(double delta, float px, float py) {
    closestBag = -1;
    closestPortal = -1;
    closestBagDist = (int)Double.POSITIVE_INFINITY;
    closestPortalDist = (int)Double.POSITIVE_INFINITY;

    for (int i = drops.size() - 1; i >= 0; i --) {
      //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
      if (!drops.get(i).update(delta, player.x, player.y)) { //if update function returns false, the drop is dead
        drops.remove(i); //remove drop
        continue;
      } else if (drops.get(i) instanceof ItemBag) {
        float dist = drops.get(i).getDist(px, py);
        if (drops.get(i).inRange(px, py) && dist < closestBagDist) {
          closestBag = i;
          closestBagDist = dist;
        }
      } else if (drops.get(i) instanceof Portal) {
        float dist = drops.get(i).getDist(px, py);
        if (drops.get(i).inRange(px, py) && dist < closestPortalDist) {
          closestPortal = i;
          closestPortalDist = dist;
        }
      }
    }
  }

  private void updateText(double delta) {
    for (int i = text.size() - 1; i >= 0; i --) {
      if (!text.get(i).update(delta)) {
        text.remove(i);
      }
    }
  }


  public void addDrop(Drop drop) {
    drops.add(drop);
  }

  public void clearDrops() {
    drops = new ArrayList<Drop>();
  }

  public void addText(String cooldown, float xp, float yp, float life, color c) {
    text.add( new Text(cooldown, xp, yp, life, c));
  }

  public Item[] getClosestBagItems() {
    if (closestBag >= 0) try { 
      return ((ItemBag)drops.get(closestBag)).items;
    } 
    catch(Exception e) { 
      return null;
    }
    return null;
  }

  public ItemBag getClosestBag() {
    if (closestBag >= 0) try { 
      return (ItemBag)drops.get(closestBag);
    } 
    catch(Exception e) { 
      return null;
    }
    return null;
  }

  public Portal getClosestPortal() {
    if (closestPortal >= 0) try { 
      return (Portal)drops.get(closestPortal);
    } 
    catch(Exception e) { 
      return null;
    }
    return null;
  }

  public void enterClosestPortal() {
    //empty drops, enemies etc
    setState("LOADING");
    loadMessage = "Generating " + getClosestPortal().name;
    thread("loadClosestPortal");
    while (!finishedLoadingLevel) gui.drawLoading();
    this.player.x = currentLevel.start.x;
    this.player.y = currentLevel.start.y;
  }
}

public void loadClosestPortal() {
  engine.currentLevel = engine.getClosestPortal().getLevel();
  engine.clearDrops();
  setState("PLAYING");
}

public PVector screenToTileCoords(float x, float y) {
  x -= GUI_WIDTH; //remove gui offset
  PVector renderOff = engine.getRenderOffset();
  return new PVector((x + renderOff.x)/TILE_SIZE, (y + renderOff.y)/TILE_SIZE);
}

public float screenToTileCoordX(float x) {
  x -= GUI_WIDTH; //remove gui offset
  PVector renderOff = engine.getRenderOffset();
  return (x + renderOff.x)/TILE_SIZE;
}

public float screenToTileCoordY(float y) {
  PVector renderOff = engine.getRenderOffset();
  return (y + renderOff.y)/TILE_SIZE;
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
    screen.textFont(bitcell);
    float a = 255 - (255 * time / life);
    screen.textSize(TILE_SIZE/2);
    outlineText(screen, message, x * TILE_SIZE - renderOffset.x, (y - (time/life)/2) * TILE_SIZE - renderOffset.y, color(red(c), green(c), blue(c), a), color(255, a));
  }

  public void outlineText(PGraphics screen, String s, float x, float y, color text, color outline) {
    screen.fill(outline);
    for (int i = -1; i < 2; i++) {
      screen.text(s, x + i, y);
      screen.text(s, x, y + i);
    }
    screen.fill(text);  
    screen.text(s, x, y);
  }
}
