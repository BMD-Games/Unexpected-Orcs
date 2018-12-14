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
  
  protected float angle;
  protected PImage sprite;
  protected Stats stats = new Stats();
  
  public ArrayList<String> typeList = new ArrayList();
  
  public StandardEnemy(float x, float y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
  }
    
   /* Enemies need to update on tics */
  public boolean update(double delta) {
    angle = atan2(engine.player.y - y, engine.player.x - x);
    active = Util.distance(x, y, engine.player.x, engine.player.y) < range;
    stats.update(delta);
    return stats.health > 0;
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset) {
    if(!engine.currentLevel.visited[(int)x][(int)y]) return;
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    
    // display status effects of the enemy
    int i = 0;
    PImage statusSprite;
    for(String status : stats.statusEffects.keySet()) {
      statusSprite = statusSprites.get(status);
      screen.image(statusSprite, x + radius / 2 + TILE_SIZE * i / 4 , y + SPRITE_SIZE / 2, statusSprite.width, statusSprite.height);
      i++;
    }
    if((angle < PI/2) && (angle > -PI/2)) {
      screen.rotate(angle);
      screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    } else {
      screen.scale(-1.0, 1.0);
      screen.rotate(PI-angle);
      screen.image(sprite, sprite.width * SCALE/2, -sprite.height * SCALE/2, -sprite.width * SCALE, sprite.height * SCALE);
    }
    screen.popMatrix();
  }
  
  /* Takes damage */
  public void damage(int amount, ArrayList<Pair> statusEffects){
    for (Pair pair : statusEffects) {
      if (typeList.contains(pair.a) || pair.a.equals("ALL")) {
        this.stats.addStatusEffect(pair.b, 1);
      }
    }
    damage(amount);
  }
  
  public void damage(int amount) {
    int damage = amount - stats.defence;
    if(damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0 , 0));
    }
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
  
  protected float attackWait = 0;
  protected float attackWaitTime = 0.8;
  
  public MeleeEnemy(float x, float y, int tier) {
    super(x, y, tier);
    type = "MELEE";
  }
  
  public boolean update(double delta) {
    if(Util.distance(x, y, engine.player.x, engine.player.y) < range) {
      attackWait += delta;
      if(Util.distance(x, y, engine.player.x, engine.player.y) < radius) {
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
  protected float shootDistance = 3;
  
  public RangedEnemy(float x, float y, int tier) {
    super(x, y, tier);
    type = "RANGED";
  }
  
  public boolean update(double delta) {
    if(active) {
      move(delta);
      attack();
    }
    return super.update(delta);
  }
  
  protected void move(double delta) {
    float moveX = 0;
    float moveY = 0;
    float playerDistance = Util.distance(x, y, engine.player.x, engine.player.y);
    if(playerDistance > shootDistance + 0.2) {
      moveX = cos(angle);
      moveY = sin(angle);
    } else if(playerDistance < shootDistance - 0.3) {
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
    if((stats.fireTimer > shotWaitTime) && (engine.currentLevel.canSee((int)x, (int)y, (int)engine.player.x, (int)engine.player.y))) {
      stats.fireTimer = 0;
      engine.enemyProjectiles.add(new Projectile(x, y, new PVector(cos(angle), sin(angle)), stats.speed * 8, range, stats.attack, projectileSprite));
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
    int xDir = Util.sign(moveX);
    int yDir = Util.sign(moveY);
    if(xDir == 1 && !validRight(level, x, y, w, h)) {
      x = ceil(x) - w/2;
    }
    if(xDir == -1 && !validLeft(level, x, y, w, h)) {
      x = floor(x) + w/2;
    }
    if(yDir == 1 && !validBottom(level, x, y, w, h)) {
      y = ceil(y) - h/2;
    }
    if(yDir == -1 && !validTop(level, x, y, w, h)) {
      y = floor(y) + h/2;
    }
    return new float[] {x, y};
  }
  
  public static boolean pointCollides(float xPos, float yPos, float x, float y, float w, float h) {
    return (xPos < x + w/2) && (xPos > x - w/2) && (yPos < y + h/2) && (yPos > y - h/2);
  }
  
}

public interface CircleObject {
   
  public float getRadius();
  
}

public interface RectangleObject {
   
  public float getWidth();
  
  public float getHeight();
  
}
