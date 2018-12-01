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
  protected boolean active = false;
  protected boolean rectangleBB = true;
  
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
    active = Utility.distance(x, y, engine.player.x, engine.player.y) < range;
    return stats.health > 0;
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset){
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    if((angle < PI/2) && (angle > -PI/2)) {
      screen.rotate(angle);
      screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    } else {
      screen.scale(-1.0, 1.0);
      screen.rotate(PI - angle);
      screen.image(sprite, sprite.width * SCALE/2, -sprite.height * SCALE/2, -sprite.width * SCALE, sprite.height * SCALE);
    }
    screen.popMatrix();
  }
  
  /* Takes damage */
  public void damage(int amount){
    int damage = amount - stats.defence;
    if(damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0 , 0));
    }
  }
  
  /* Takes damage */
  public void damage(int amount, ArrayList<Pair> statusEffects){
    int damage = amount - stats.defence;
    if(damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0 , 0));
    }
  }
  
  public boolean pointCollides(float pointX, float pointY) {
    if(rectangleBB) {
      return false;
    } else {
      return Circle.pointCollides(x, y, pointX, pointY, radius);
    }
  }
  
  public boolean AABBCollides(AABB box){
    return false;
  }  
  
  public boolean validPosition(Level level, float xPos, float yPos) {
    if(rectangleBB) {
      return true;
    } else {
      return Circle.validPosition(level, xPos, yPos, radius);
    }
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
    if(Utility.distance(x, y, engine.player.x, engine.player.y) < range) {
      attackWait += delta;
      if(Utility.distance(x, y, engine.player.x, engine.player.y) < radius) {
        attack();
      } else {
        move(delta);
      }
    }
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
    int xDir = Utility.sign(moveX);
    int yDir = Utility.sign(moveY);
    if(abs(xDir) + abs(yDir) == 0) {
      return;
    }
    x += moveX;
    y += moveY;
    if(rectangleBB) {
      return;
    } else {
      if(!Circle.validCentre(engine.currentLevel, x, y)) {
          x -= moveX;
          y -= moveY;
      }
      if(xDir == 1) {
        if(!Circle.validRight(engine.currentLevel, x, y, radius)) {
          x = ceil(x) - radius;
        }
      } else if(xDir == -1) { 
        if (!Circle.validLeft(engine.currentLevel, x, y, radius)) {
          x = floor(x) + radius;
        }
      }
      if(yDir == 1) {
        if (!Circle.validBottom(engine.currentLevel, x, y, radius)) {
          y = ceil(y) - radius;
        }
      } else { 
        if(!Circle.validTop(engine.currentLevel, x, y, radius)) {
          y = floor(y) + radius;
        }
      }
    }
  }
}

public static class Circle {
  
  //Calculates if coordinates mean chomp is not in a wall
  //Needs level because it's used in setup
  public static boolean validPosition(Level level, float xPos, float yPos, float radius) {
    return validCentre(level, xPos, yPos) &&
        validLeft(level, xPos, yPos, radius) &&
        validRight(level, xPos, yPos, radius) && 
        validTop(level, xPos, yPos, radius) && 
        validBottom(level, xPos, yPos, radius);
  }
  
  protected static boolean validCentre(Level level, float xPos, float yPos) {
    return level.getTile((int)xPos, (int)yPos) > WALL;
  }
  
  protected static boolean validLeft(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos - radius), (int)yPos) > WALL;
  }
  
  protected static boolean validRight(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos + radius), (int)yPos) > WALL;
  }
  
  protected static boolean validTop(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos - radius)) > WALL;
  }
  
  protected static boolean validBottom(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos + radius)) > WALL;
  }
  
  protected static boolean validTopLeft(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos - radius), (int)yPos) > WALL;
  }
  
  protected static boolean validTopRight(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos + radius), (int)yPos) > WALL;
  }
  
  protected static boolean validBottomLeft(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos - radius)) > WALL;
  }
  
  protected static boolean validBottomRight(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos + radius)) > WALL;
  }
  
  /* Checks collision with point */
  public static boolean pointCollides(float x, float y, float pointX, float pointY, float radius) {
    return (Utility.distance(x, y, pointX, pointY) < radius);
  }
  
}

public static class Rectangle {
  
  //Calculates if coordinates mean chomp is not in a wall
  //Needs level because it's used in setup
  public static boolean validPosition(Level level, float xPos, float yPos, float radius) {
    return validCentre(level, xPos, yPos) &&
        validLeft(level, xPos, yPos, radius) &&
        validRight(level, xPos, yPos, radius) && 
        validTop(level, xPos, yPos, radius) && 
        validBottom(level, xPos, yPos, radius);
  }
  
  protected static boolean validCentre(Level level, float xPos, float yPos) {
    return level.getTile((int)xPos, (int)yPos) > WALL;
  }
  
  protected static boolean validLeft(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos - radius), (int)yPos) > WALL;
  }
  
  protected static boolean validRight(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)(xPos + radius), (int)yPos) > WALL;
  }
  
  protected static boolean validTop(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos - radius)) > WALL;
  }
  
  protected static boolean validBottom(Level level, float xPos, float yPos, float radius) {
    return level.getTile((int)xPos, (int)(yPos + radius)) > WALL;
  }
  
  /* Checks collision with point */
  public static boolean pointCollides(float x, float y, float pointX, float pointY, float radius) {
    return (Utility.distance(x, y, pointX, pointY) < radius);
  }
  
}
