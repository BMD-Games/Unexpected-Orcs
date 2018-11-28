class Drop {
  
  public float x, y, radius, lifeTime, fadeTime = 1.5;
  public PImage sprite;
  public boolean alive = true;
  
  private int alpha = 255;
  
  Drop(float x, float y, float radius, float lifeTime) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.lifeTime = lifeTime;
  }
  
  public boolean update(double delta, float px, float py) {
    lifeTime -= delta;
    if(lifeTime <= fadeTime) alpha = (int)map(lifeTime, fadeTime, 0, 255, 0);
    return (lifeTime > 0) && alive;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.tint(255, 255, 255, alpha);
    screen.image(sprite, x * TILE_SIZE - renderOffset.x - (sprite.width * SCALE/2), y * TILE_SIZE - renderOffset.y - (sprite.height * SCALE/2), sprite.width * SCALE, sprite.height * SCALE);
    screen.tint(255);
  }
  
  public boolean inRange(float xPos, float yPos) {
    return(dist(xPos, yPos, x, y) < radius);
  }
  
  public float getDist(float xPos, float yPos) {
    return dist(xPos, yPos, x, y);
  }
  
}

class StatOrb extends Drop {
  
  private String stat;
  private int tier;
  private float pickUpRadius = 0.3;
  
  private boolean playerWasInRange = false;
  private float vel = 0, acc = 2.5;
  
  StatOrb(float x, float y, int tier, String stat) {
    super(x, y, 3, 10);
    this.stat = stat;
    this.tier = tier;
    this.sprite = applyColourToImage(dropSprites.get("ORB").copy(), statColours.get(stat));
  }
  
  
  @Override
  public boolean update(double delta, float px, float py) {
    boolean up = super.update(delta, px, py);
    if(getDist(px, py) < pickUpRadius) {
      engine.player.stats.addOrbStat(stat, tier);
      return false;
    } else if(inRange(px, py)) { //engine.currentLevel.canSee((int)x, (int)y, (int)px, (int)py) && 
      playerWasInRange = true;
    }
    if(playerWasInRange) {
      PVector dir = new PVector(px - x, py - y).normalize();
      dir.mult((float)(vel * delta));
      x += dir.x;
      y += dir.y;
      vel += acc * delta;
      lifeTime += delta;
    } else {
      vel = 0;
    }
    return up;
  }
  
}

class ItemBag extends Drop {
  
  public Item[] items = new Item[4];
  
  ItemBag(float x, float y, int tier) {
    super(x, y, 0.5, 60);
    this.sprite = dropSprites.get("BAG_" + tier);
  } 
  
  public Item takeItem(int pos) {
    if(pos >= items.length) return null;
    else {
      Item item = items[pos];
      items[pos] = null;
      checkEmpty();
      return item;
    }
  }
  
  public boolean addItem(Item item) {
    if(item != null) alive = true;
    for(int i = 0; i < items.length; i ++) {
      if(items[i] == null) {
        items[i] = item;
        return true;
      }
    }
    return false;
  }
  
  private void checkEmpty() {
    for(int i = 0; i < items.length; i ++) {
      if(items[i] != null) {
        return;
      }
    }
    alive = false;
  }
  
  private boolean isFull() {
    for(int i = 0; i < items.length; i ++) {
      if(items[i] == null) return false;
    }
    return true;
  }
  
}


class Portal extends Drop {
  
  String name;
  
  Portal(float x, float y, String name) {
    super(x, y, 0.5, 60);
    this.name = name;
  }
  
  public Level getLevel() { //Override this with the level specific to a given portal
    return null;
  }
  
}

class GrassPortal extends Portal {
  
  GrassPortal(float x, float y) {
    super(x, y, "Grass World");
    this.sprite = dropSprites.get("PORTAL_GRASS");
  }
  
  @Override
  public Level getLevel() {
    return new GrassDungeon();
  }
}

class CellarPortal extends Portal {
  
  CellarPortal(float x, float y) {
    super(x, y, "Cellar");
    this.sprite = dropSprites.get("PORTAL_CELLAR");
  }
  
  @Override
  public Level getLevel() {
    return new CellarDungeon();
  }
}

class CavePortal extends Portal {
  
  CavePortal(float x, float y) {
    super(x, y, "Cave");
    this.sprite = dropSprites.get("PORTAL_CAVE");
  }
  
  @Override
  public Level getLevel() {
    return new Cave();
  }
}
