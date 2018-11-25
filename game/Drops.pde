class Drop {
  
  public float x, y, radius, lifeTime;
  public PImage sprite;
  public boolean alive = true;
  
  Drop(float x, float y, float radius, float lifeTime) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.lifeTime = lifeTime;
  }
  
  public boolean update(double delta) {
    lifeTime -= delta;
    return (lifeTime > 0) && alive;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.image(sprite, x * TILE_SIZE - renderOffset.x - (sprite.width * SCALE/2), y * TILE_SIZE - renderOffset.y - (sprite.height * SCALE/2), sprite.width * SCALE, sprite.height * SCALE);
  }
  
  public boolean inRange(float xPos, float yPos) {
    return(dist(xPos, yPos, x, y) < radius);
  }
  
  public float getDist(float xPos, float yPos) {
    return dist(xPos, yPos, x, y);
  }
  
}

class StatOrb extends Drop {
  
  String stat;
  int tier;
  
  StatOrb(float x, float y, int tier, String stat) {
    super(x, y, 4, 10);
    this.stat = stat;
    this.tier = tier;
    this.sprite = applyColourToImage(dropSprites.get("ORB").copy(), statColours.get(stat));
  }
  
}

class ItemBag extends Drop {
  
  public Item[] items = new Item[4];
  
  ItemBag(float x, float y, int tier) {
    super(x, y, 0.5, 120);
    this.sprite = dropSprites.get("BAG_" + tier);
    items[0] = new Sniper();
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
