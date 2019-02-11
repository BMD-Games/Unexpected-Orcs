public interface Enemy {

  /* Enemies need to update on tics */
  public boolean update(double delta);

  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset);

  /* This mob takes damage */
  public void damage(int amount, ArrayList<Pair> statusEffects);
  
  public void damage(int amount);
  
  /* Drop what on death */
  public void onDeath();

  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY);

  /* Checks collision with point */
  public boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2);

  /* Checks collision with area  */
  public boolean AABBCollides(AABB box);

  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos);
  
  public void knockback(Projectile projectile);
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


abstract class StandardEnemy implements Enemy {

  public int tier;
  
  public float x, y, knockbackX, knockbackY;
  public String type;
  
  protected int range = 10;
  protected float radius = 0.5;
  protected boolean active = false;
  
  protected boolean hasSeen = false;
  
  private boolean tookDamage = false;
  private float damageTime = 0;
  private float damageMax = 0.25;
  
  protected float angle;
  protected PImage sprite;
  private PImage damageSprite;
  protected Stats stats = new Stats();

  public ArrayList<String> typeList = new ArrayList();

  public StandardEnemy(float x, float y, int tier, PImage sprite) {
    this.tier = tier;
    this.x = x;
    this.y = y;
    
    this.sprite = sprite;
    this.damageSprite = applyColourToImage(sprite, color(200, 0, 0));
    
    if(this instanceof RectangleObject) radius = max(((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight()) / 2;
  }

  /* Enemies need to update on tics */
  public boolean update(double delta) {
    angle = atan2(engine.player.y - y, engine.player.x - x);
    active = Util.distance(x, y, engine.player.x, engine.player.y) < range;
    stats.update(delta);
    if(tookDamage) {
      damageTime += delta;
    }
    return stats.health > 0;
  }

  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset) {
    if(!engine.currentLevel.visited((int)x, (int)y)) return;
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);

    // display status effects of the enemy
    int i = 0;
    PImage statusSprite;
    for (String status : stats.statusEffects.keySet()) {
      statusSprite = statusSprites.get(status);
      screen.image(statusSprite, radius / 2 + TILE_SIZE * i / 4, SPRITE_SIZE / 2, statusSprite.width, statusSprite.height);
      i++;
    }
    
    PImage currSprite = sprite;
    if(tookDamage) {
      currSprite = damageSprite;
      tookDamage = damageTime < damageMax;
    }

    if((angle < PI/2) && (angle > -PI/2)) {
      screen.rotate(angle);
      screen.image(currSprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    } else {
      screen.scale(-1.0, 1.0);
      screen.rotate(PI-angle);
      screen.image(currSprite, sprite.width * SCALE/2, -sprite.height * SCALE/2, -sprite.width * SCALE, sprite.height * SCALE);
    }
    screen.popMatrix();
  }

  /* Takes damage */
  public void damage(int amount, ArrayList<Pair> statusEffects) {
    for (Pair pair : statusEffects) {
      if (typeList.contains(pair.a) || pair.a.equals("ALL")) {
        this.stats.addStatusEffect(pair.b, 1);
      }
    }
    damage(amount);
  }

  public void damage(int amount) {
    int damage = amount - stats.defence;
    if (damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0, 0));
    }
    tookDamage = true;
    damageTime = 0;
  }


  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    if(this instanceof RectangleObject) {
      return Rectangle.pointCollides(pointX, pointY, x, y, ((RectangleObject) this).getWidth(), ((RectangleObject) this).getHeight());
    } else if(this instanceof CircleObject) {
      return Circle.pointCollides(x, y, pointX, pointY, ((CircleObject) this).getRadius());
    }
    return false;
  }

  /* Checks collision with line */
  public boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2) {
    if(this instanceof RectangleObject) {
      float w = ((RectangleObject) this).getWidth();
      float h = ((RectangleObject) this).getHeight();
      if(drawDebug) {
        debugScreen.beginDraw();
        debugScreen.noFill();
        debugScreen.stroke(255);
        debugScreen.line(tileToScreenCoordX(lineX1), tileToScreenCoordY(lineY1), tileToScreenCoordX(lineX2), tileToScreenCoordY(lineY2));
        debugScreen.rect(tileToScreenCoordX(x-w/2), tileToScreenCoordY(y-h/2), w * TILE_SIZE, h * TILE_SIZE);
        debugScreen.endDraw();
      }
      return Rectangle.lineCollides(lineX1, lineY1,lineX2, lineY2, x - w/2, y - h/2, w, h);
    } else if(this instanceof CircleObject) {
      return Circle.lineCollides(lineX1, lineY1,lineX2, lineY2, x, y, ((CircleObject) this).getRadius());
    }
    return false;
  }

  /* Checks collision with area  */
  public boolean AABBCollides(AABB box) {
    return false;
  }  

  public boolean validPosition(Level level, float xPos, float yPos) {
    if(this instanceof RectangleObject) {
      return Rectangle.validPosition(level, xPos, yPos, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight());
    } else if(this instanceof CircleObject) {
      return Circle.validPosition(level, xPos, yPos, radius);
    }
    return true;
  }
  
  public void knockback(Projectile projectile) {
    if(stats.health > 0) {
      knockbackX = projectile.direction.x * projectile.damage / stats.defence;
      knockbackY = projectile.direction.y * projectile.damage / stats.defence;
    }
  }
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


abstract class MeleeEnemy extends StandardEnemy implements Enemy {

  protected float attackWaitTime = 0.8;

  public MeleeEnemy(float x, float y, int tier, PImage sprite) {
    super(x, y, tier, sprite);
    type = "MELEE";
  }

  public boolean update(double delta) {
    if (Util.distance(x, y, engine.player.x, engine.player.y) < range) {
      if (pointCollides(engine.player.x, engine.player.y)) {
        attack();
      } else {
        move(delta);
      }
    }
    return super.update(delta);
  }

  protected void attack() {
    if (stats.fireTimer > attackWaitTime * stats.getFireRate()) {
      stats.fireTimer = 0;
      engine.player.damage(stats.attack * 2);
    }
  }

  protected void move(double delta) {
    float moveX = (stats.getSpeed() * cos(angle) + knockbackX) * (float)delta;
    float moveY = (stats.getSpeed() * sin(angle) + knockbackY) * (float)delta;
    knockbackX = knockbackY = 0;
    float[] coords;
    if(this instanceof RectangleObject) {
      coords = Rectangle.adjust(engine.currentLevel, x, y, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight(), moveX, moveY);
    } else if(this instanceof CircleObject) {
      coords = Circle.adjust(engine.currentLevel, x, y, radius, moveX, moveY);
    } else {
      coords = new float[] {x + moveX, y + moveY};
    }
    x = coords[0];
    y = coords[1];
  }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public abstract class RangedEnemy extends StandardEnemy implements Enemy {
  
  protected PImage projectileSprite = projectileSprites.get("WAND");
  protected float shotWaitTime = 1;
  protected float shootDistance = 3.2;
  protected float retreatDistance = 2.7;
  protected float accuracy = 0;
  protected boolean predictAim = false;
  protected float bulletSpeed = 10;
  
  public RangedEnemy(float x, float y, int tier, PImage sprite) {
    super(x, y, tier, sprite);
    type = "RANGED";
  }
  
  public boolean update(double delta) {
    boolean alive = super.update(delta);
    if(predictAim) {
      float timeAway = Util.distance(x, y, engine.player.x, engine.player.y) / bulletSpeed;
      float playerX = engine.player.x + engine.player.dirX * timeAway;
      float playerY = engine.player.y + engine.player.dirY * timeAway;
      angle = atan2(playerY - y, playerX - x);  
    }
    if(active) {
      move(delta);
      attack();
    }
    return alive;
  }
  
  protected void move(double delta) {
    float moveX = 0;
    float moveY = 0;
    float playerDistance = Util.distance(x, y, engine.player.x, engine.player.y);
    if(playerDistance > shootDistance) {
      moveX = cos(angle);
      moveY = sin(angle);
    } else if(playerDistance < retreatDistance) {
      moveX = -cos(angle);
      moveY = -sin(angle);
    }
    moveX = (moveX * stats.getSpeed() + knockbackX) * (float)delta;
    moveY = (moveY * stats.getSpeed() + knockbackY) * (float)delta;
    knockbackX = knockbackY = 0;
    float[] coords;
    if(this instanceof RectangleObject) {
      coords = Rectangle.adjust(engine.currentLevel, x, y, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight(), moveX, moveY);
    } else if(this instanceof CircleObject) {
      coords = Circle.adjust(engine.currentLevel, x, y, radius, moveX, moveY);
    } else {
      coords = new float[] {x + moveX, y + moveY};
    }
    x = coords[0];
    y = coords[1];
  }
  
  protected void attack() {
    if((stats.fireTimer > shotWaitTime * stats.getFireRate()) && (engine.currentLevel.canSee((int)x, (int)y, (int)engine.player.x, (int)engine.player.y))) {
      stats.fireTimer = 0;
      float shotAccuracy = randomGaussian() * accuracy;
      engine.enemyProjectiles.add(new Projectile(x, y, new PVector(cos(angle + shotAccuracy), sin(angle + shotAccuracy)), bulletSpeed, range, stats.attack, projectileSprite));
    }
  }
  
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public static class Circle {

  //Calculates if coordinates mean chomp is not in a wall
  //Needs level because it's used in setup
  public static boolean validPosition(Level level, float x, float y, float radius) {
    return validCentre(level, x, y) &&
      validLeft(level, x, y, radius) &&
      validRight(level, x, y, radius) && 
      validTop(level, x, y, radius) && 
      validBottom(level, x, y, radius);
  }

  protected static boolean validCentre(Level level, float x, float y) {
    return level.getTile((int)x, (int)y) > WALL;
  }

  protected static boolean validLeft(Level level, float x, float y, float radius) {
    return level.getTile((int)(x - radius), (int)y) > WALL;
  }

  protected static boolean validRight(Level level, float x, float y, float radius) {
    return level.getTile((int)(x + radius), (int)y) > WALL;
  }

  protected static boolean validTop(Level level, float x, float y, float radius) {
    return level.getTile((int)x, (int)(y - radius)) > WALL;
  }

  protected static boolean validBottom(Level level, float x, float y, float radius) {
    return level.getTile((int)x, (int)(y + radius)) > WALL;
  }

  protected static boolean validTopLeft(Level level, float x, float y, float radius) {
    return !((pointCollides(x, y, floor(x), floor(y), radius)) && (level.getTile(floor(x) - 1, floor(y) - 1) <= WALL));
  }

  protected static boolean validTopRight(Level level, float x, float y, float radius) {
    return !((pointCollides(x, y, (int)x + 1, (int)y, radius)) && (level.getTile((int)x + 1, (int)y - 1) <= WALL));
  }

  protected static boolean validBottomLeft(Level level, float x, float y, float radius) {
    return !((pointCollides(x, y, (int)x, (int)y + 1, radius)) && (level.getTile((int)x - 1, (int)y + 1) <= WALL));
  }

  protected static boolean validBottomRight(Level level, float x, float y, float radius) {
    return !((pointCollides(x, y, (int)x + 1, (int)y + 1, radius)) && (level.getTile((int)x + 1, (int)y + 1) <= WALL));
  }

  public static float[] adjust(Level level, float x, float y, float radius, float moveX, float moveY) {
    x += moveX;
    y += moveY;
    int xDir = Util.sign(moveX);
    int yDir = Util.sign(moveY);

    if(!Circle.validCentre(level, x, y)) {
        x -= moveX;
        y -= moveY;
    }
    if(xDir == 1) {
      if(!Circle.validRight(level, x, y, radius)) {
        x = ceil(x) - radius;
      } else {
        if(!Circle.validTopRight(level, x, y, radius)) {
          float attackAngle = atan2(y - floor(y), x - ceil(x));
          x = ceil(x) + cos(attackAngle) * radius;
          y = floor(y) + sin(attackAngle) * radius;
        } 
        if(!Circle.validBottomRight(level, x, y, radius)) {
          float attackAngle = atan2(y - ceil(y), x - ceil(x));
          x = ceil(x) + cos(attackAngle) * radius;
          y = ceil(y) + sin(attackAngle) * radius;
        }
      }
    } else if(xDir == -1) { 
      if (!Circle.validLeft(level, x, y, radius)) {
        x = floor(x) + radius;
      } else {
        if(!Circle.validTopLeft(level, x, y, radius)) {
          float attackAngle = atan2(y - floor(y), x - floor(x));
          x = floor(x) + cos(attackAngle) * radius;
          y = floor(y) + sin(attackAngle) * radius;
        } 
        if(!Circle.validBottomLeft(level, x, y, radius)) {
          float attackAngle = atan2(y - ceil(y), x - floor(x));
          x = floor(x) + cos(attackAngle) * radius;
          y = ceil(y) + sin(attackAngle) * radius;
        }
      }
    }
    if(yDir == 1) {
      if (!Circle.validBottom(level, x, y, radius)) {
        y = ceil(y) - radius;
      } else {
        if(!Circle.validBottomLeft(level, x, y, radius)) {
          float attackAngle = atan2(y - ceil(y), x - floor(x));
          x = floor(x) + cos(attackAngle) * radius;
          y = ceil(y) + sin(attackAngle) * radius;
        } 
        if(!Circle.validBottomRight(level, x, y, radius)) {
          float attackAngle = atan2(y - ceil(y), x - ceil(x));
          x = ceil(x) + cos(attackAngle) * radius;
          y = ceil(y) + sin(attackAngle) * radius;
        }
      }
    } else if(yDir == -1) { 
      if(!Circle.validTop(level, x, y, radius)) {
        y = floor(y) + radius;
      } else {
        if(!Circle.validTopLeft(level, x, y, radius)) {
          float attackAngle = atan2(y - floor(y), x - floor(x));
          x = floor(x) + cos(attackAngle) * radius;
          y = floor(y) + sin(attackAngle) * radius;
        } 
        if(!Circle.validTopRight(level, x, y, radius)) {
          float attackAngle = atan2(y - floor(y), x - ceil(x));
          x = ceil(x) + cos(attackAngle) * radius;
          y = floor(y) + sin(attackAngle) * radius;
        }
      }
    }
    return new float[] {x, y};
  }

  /* Checks collision with point */
  public static boolean pointCollides(float x, float y, float pointX, float pointY, float radius) {
    return (Util.distance(x, y, pointX, pointY) < radius);
  }

  public static boolean lineCollides(float lx1, float ly1, float lx2, float ly2, float cx, float cy, float r) {
    // if end points are inside the circle
    if(Circle.pointCollides(lx1, ly1, cx, cy, r)) return true;
    if(Circle.pointCollides(lx2, ly2, cx, cy, r)) return true;
    
    //line length
    float len = dist(lx1, ly1, lx2, ly2);
    //dot product of line and circle
    float dot = (((cx - lx1) * (lx2 - lx1)) + ((cy - ly1) * (ly2 - ly1)))/sq(len);
    //closest point on line
    float closestX = lx1 + (dot * (lx2 - lx1));
    float closestY = ly1 + (dot * (ly2 - ly1));
    //check if point is actually on line segment
    if(!Util.linePoint(lx1, ly1, lx2, ly2, closestX, closestY)) return false;
    
    float distance = dist(closestX, closestY, cx, cy);
    
    return distance < r;
  }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public static class Rectangle {

  public static boolean validPosition(Level level, float x, float y, float w, float h) {
    for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
      for(int j = (int)(y - h/2); j <= (int)(y + h/2); ++j) {
        if(level.getTile(i, j) <= WALL) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean validTop(Level level, float x, float y, float w, float h) {
    for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
      if(level.getTile(i, (int)(y - h/2)) <= WALL) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean validBottom(Level level, float x, float y, float w, float h) {
    for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
      if(level.getTile(i, (int)(y + h/2)) <= WALL) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean validLeft(Level level, float x, float y, float w, float h) {
    for(int i = (int)(y - h/2); i <= (int)(y + h/2); ++i) {
      if(level.getTile((int)(x - w/2), i) <= WALL) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean validRight(Level level, float x, float y, float w, float h) {
    for(int i = (int)(y - h/2); i <= (int)(y + h/2); ++i) {
      if(level.getTile((int)(x + w/2), i) <= WALL) {
        return false;
      }
    }
    return true;
  }
  
  public static float[] adjust(Level level, float x, float y, float w, float h, float moveX, float moveY) {
    x = x + moveX;
    y = y + moveY;
    if(!validPosition(level, x, y, w, h)) {
      int xDir = Util.sign(moveX);
      int yDir = Util.sign(moveY);
      float checkX = xDir == 1 ? ceil(x) - w/2 - 0.01 : floor(x) + w/2 + 0.01;
      float checkY = yDir == 1 ? ceil(y) - h/2 - 0.01 : floor(y) + h/2 + 0.01;
      boolean adjusted = false;
      if((xDir == 1 && !validRight(level, x, checkY, w, h)) || (xDir == -1 && !validLeft(level, x, checkY, w, h))) {
        x = checkX;
        adjusted = true;
      }
      if((yDir == 1 && !validBottom(level, checkX, y, w, h)) || (yDir == -1 && !validTop(level, checkX, y, w, h))) {
        y = checkY;
        adjusted = true;
      }
      if(!adjusted) {
        if((xDir == 1 && !validRight(level, x, y, w, h)) || (xDir == -1 && !validLeft(level, x, y, w, h))) {
          x = checkX;
        }
        if((yDir == 1 && !validBottom(level, x, y, w, h)) || (yDir == -1 && !validTop(level, checkX, y, w, h))) {
          y = checkY;
        }
      }
    }
    return new float[] {x, y};
  }
  
  public static boolean pointCollides(float xPos, float yPos, float x, float y, float w, float h) {
    return (xPos < x + w/2) && (xPos > x - w/2) && (yPos < y + h/2) && (yPos > y - h/2);
  }
  
  public static boolean lineCollides(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {
    //check if either end is inside the box
    if (Util.pointInBox(x1, y1, rx, ry, rw, rh) || Util.pointInBox(x2, y2, rx, ry, rw, rh)) return true;
    
    //check if the line has hit any of the rectangle's sides
    boolean left =   Util.lineLine(x1, y1, x2, y2, rx, ry, rx, ry+rh);
    boolean right =  Util.lineLine(x1, y1, x2, y2, rx+rw, ry, rx+rw, ry+rh);
    boolean top =    Util.lineLine(x1, y1, x2, y2, rx, ry, rx+rw, ry);
    boolean bottom = Util.lineLine(x1, y1, x2, y2, rx, ry+rh, rx+rw, ry+rh);

    //if ANY of the above are true, the line has hit the rectangle
    if (left || right || top || bottom) {
      return true;
    }
    return false;
  }
}

public interface CircleObject {
   
  public float getRadius();
  
}

public interface RectangleObject {
   
  public float getWidth();
  
  public float getHeight();
  
}