class Player {
  
  public float x, y, w = 0.5, h = 0.5;
  private float ang;
  private int size = TILE_SIZE/2;
  
  private PImage sprite;
  private String[] sprites = {"PLAYER_BACK", "PLAYER_RIGHT", "PLAYER_FRONT", "PLAYER_LEFT"};
  
  public PlayerStats stats = new PlayerStats();
  
  private Inventory inv = new Inventory();
  
  private AABB bound;

  boolean slowed = false;
  
  Player(float x, float y) {
    this.x = x; //in tile space, not screen space;
    this.y = y;
    bound = new AABB(x, y, w, h);
    stats.addStatusEffect("BEZERK", 4);
  }
  
  public void move(double delta, int[] neighbours) {
    PVector movement = new PVector(getDX(delta, neighbours), getDY(delta, neighbours));
    movement.normalize();
    movement.mult((float)delta * stats.getSpeed());
    x += movement.x; 
    y += movement.y;
  }
  
  private float getDX(double delta, int[] neighbours) {
    float dx = (float)((keys[right] - keys[left]) * (delta * stats.getSpeed()));
    int xpos = (int)x;
    int ypos = (int)y;
    if(dx < 0 && neighbours[left] <= WALL) {
      AABB tile = new AABB(xpos - 1, ypos, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    } else if(dx > 0 && neighbours[right] <= WALL) {
      AABB tile = new AABB(xpos + 1, ypos, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    }    
    return dx;
  }

  private float getDY(double delta, int[] neighbours) {
    float dy = (float)((keys[down] - keys[up]) * (delta * stats.getSpeed()));
    int xpos = (int)x;
    int ypos = (int)y;
    if(dy < 0 && neighbours[up] <= WALL) {
      AABB tile = new AABB(xpos, ypos - 1, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    } else if(dy > 0 && neighbours[down] <= WALL) {
      AABB tile = new AABB(xpos, ypos + 1, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    }  
    return dy;
  }
  
  public void damage(int amount){
    if(amount > stats.getDefence()) {
      stats.setHealth(stats.getHealth() - (amount - stats.getDefence()));
    }
  }
  
  public void update(double delta, int[] neighbours) {
    ang = atan2(mouseY - height/2, mouseX - (width/2 + GUI_WIDTH/2));

    if (inv.active[1] != null ){
      inv.currentAbility().updateCooldown();
    }
    ability();
    getFacing();
    move(delta, neighbours);
    updateBound();
    
    stats.update(delta);
    
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    screen.popMatrix();
  }
  
  private void updateBound() {
    bound.x = x - bound.w/2;
    bound.y = y - bound.h/2; 
  }
  
  private void ability() {
    if (keys[ability] == 1) {
      inv.currentAbility().buff(this);
    }
  }
  
  private int getFacing() {
    //gets the direction that the player is looking in -> decides which sprite to use
    float dir = ang + (3 * PI/4);
    while(dir < 0) dir += TAU;
    int face = (int)(dir/(PI/2)) % 4;
    sprite = charSprites.get(sprites[face]);
    return face;
  }
  
  public AABB getAABB() {
    return bound;
  }
  
  Weapon currentWeapon() { return inv.currentWeapon(); }
  Ability currentSpecial() { return inv.currentAbility(); }
  Armour currentArmour() { return inv.currentArmour(); }
  
  Item[] active() { return inv.active(); }
  Item[] inv() { return inv.inv(); }
  
}
