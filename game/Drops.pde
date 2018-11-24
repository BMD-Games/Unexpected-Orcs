class Drop {
  
  public float x, y, radius;
  public String sprite;
  public float lifeTime;
  
  Drop(float x, float y, float radius, float lifeTime) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.lifeTime = lifeTime;
  }
  
  public boolean update(double delta) {
    lifeTime -= delta;
    return lifeTime > 0;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    PImage sp = dropSprites.get(sprite);
    screen.image(sp, x * TILE_SIZE - renderOffset.x - (sp.width * SCALE/2), y * TILE_SIZE - renderOffset.y - (sp.height * SCALE/2), sp.width * SCALE, sp.height * SCALE);
  }
  
  public boolean inRange(float xPos, float yPos) {
    return(dist(xPos, yPos, x, y) < radius);
  }
  
}

class ItemBag extends Drop {
  
  Item[] items = new Item[4];
  
  ItemBag(float x, float y, int tier) {
    super(x, y, 2, 20);
    
    String spriteTMP = "BAG_BROWN";
    if(tier == 1) spriteTMP = "BAG_BLUE";
    if(tier == 2) spriteTMP = "BAG_WHITE"; 
    this.sprite = spriteTMP;
  } 
  
  public Item takeItem(int pos) {
    if(pos >= items.length) return null;
    else {
      Item item = items[pos];
      items[pos] = null;
      return item;
    }
  }
  
  public boolean addItem(Item item) {
    for(int i = 0; i < items.length; i ++) {
      if(items[i] == null) {
        items[i] = item;
        return true;
      }
    }
    return false;
  }
  
}
