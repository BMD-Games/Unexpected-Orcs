public interface Enemy {
  
  /* Enemies need to update on tics */
  public boolean update(double delta);
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset);
  
  /* This mob takes damage */
  public void damage(int amount);
  
  /* Drop what on death */
  public void onDeath();
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY);
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box);
  
  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos);
  
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



abstract class StandardEnemy implements Enemy {
  
  public int tier;
  public float x;
  public float y;
  
  protected int range = 10;
  protected float radius;
  protected float angle;
  protected PImage sprite;
  protected Stats stats = new Stats();
  
  public StandardEnemy(float x, float y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
  }
    
   /* Enemies need to update on tics */
  public boolean update(double delta) {
    angle = atan2(engine.player.y - y, engine.player.x - x);
    return stats.health > 0;
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset) {}
  
  /* Takes damage */
  public void damage(int amount){
    int damage = amount - stats.defence;
    if(damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0 , 0));
    }
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return false;
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box) {
    return false;
  }
  
  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos) {
    return true;
  }
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




abstract class MeleeEnemy extends StandardEnemy implements Enemy {
  
  protected float attackWait = 0;
  protected float attackWaitTime = 0.8;
  
  public MeleeEnemy(float x, float y, int tier) {
    super(x, y, tier);
  }
  
  public boolean update(double delta) {
    attackWait += delta;
    return super.update(delta);
  }
  
  protected void attack() {
    if(attackWait > attackWaitTime) {
      attackWait = 0;
      engine.player.damage(stats.attack * 2);
    }
  }
  
  protected void move(double delta) {
    float moveX = (float)(stats.getSpeed() * cos(angle) * delta);
    float moveY = (float)(stats.getSpeed() * sin(angle) * delta);
    x += moveX;
    y += moveY;
    if(!validPosition(engine.currentLevel, x, y)) {
      if(!validCentre(engine.currentLevel, x, y)) {
        x -= moveX;
        y -= moveY;
      } else {
        if(!validLeft(engine.currentLevel, x, y)) {
          x = floor(x) + radius;
        } else if(!validRight(engine.currentLevel, x, y)) {
          x = ceil(x) - radius;
        }
        if(!validTop(engine.currentLevel, x, y)) {
          y = floor(y) + radius;
        } else if(!validBottom(engine.currentLevel, x, y)) {
          y = ceil(y) - radius;
        }
      }
    }
  }
  
  //Calculates if coordinates mean chomp is not in a wall
  //Needs level because it's used in setup
  public boolean validPosition(Level level, float xPos, float yPos) {
    return validCentre(level, xPos, yPos) &&
        (level.getTile((int)xPos, (int)(yPos + radius)) > WALL) &&
        (level.getTile((int)xPos, (int)(yPos - radius)) > WALL) && 
        (level.getTile((int)(xPos + radius), (int)yPos) > WALL) && 
        (level.getTile((int)(xPos - radius), (int)yPos) > WALL);
  }
  
  protected boolean validCentre(Level level, float xPos, float yPos) {
    return level.getTile((int)xPos, (int)yPos) > WALL;
  }
  
  protected boolean validLeft(Level level, float xPos, float yPos) {
    return level.getTile((int)(xPos - radius), (int)yPos) > WALL;
  }
  
  protected boolean validRight(Level level, float xPos, float yPos) {
    return level.getTile((int)(xPos + radius), (int)yPos) > WALL;
  }
  
  protected boolean validTop(Level level, float xPos, float yPos) {
    return level.getTile((int)xPos, (int)(yPos - radius)) > WALL;
  }
  
  protected boolean validBottom(Level level, float xPos, float yPos) {
    return level.getTile((int)xPos, (int)(yPos + radius)) > WALL;
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return (Utility.distance(x, y, pointX, pointY) < radius);
  }
  
}
