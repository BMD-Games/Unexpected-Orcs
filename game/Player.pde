class Player {
  
  public float x, y, w = 0.5, h = 0.5;
  private float ang;
  private int size = TILE_SIZE/2;
  
  private CharTileSet sprites;
  private PlayerStats stats = new PlayerStats();
  
  private InventoryObject currentInventory;
  
  private AABB bound;

  boolean slowed = false;
  
  Player(float x, float y) {
    this.x = x; //in tile space, not screen space;
    this.y = y;
    bound = new AABB(x, y, w, h);
  }
  
  public void move(double delta, int[] neighbours) {
    x += getDX(delta, neighbours); 
    y += getDY(delta, neighbours);
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

  public void update(double delta, int[] neighbours) {
    ability();
    ang = atan2(mouseY - height/2, mouseX - width/2);
    getFacing();
    move(delta, neighbours);
    updateBound();
    
  }
  
  public void show(PVector renderOffset) {
    pushMatrix();
    translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    rotate(ang);
    fill(255, 0, 0);
    stroke(0);
    rect(-size/2, -size/2, size, size);
    line(0, 0, size, 0);
    popMatrix();
  }
  
  private void updateBound() {
    bound.x = x - bound.w/2;
    bound.y = y - bound.h/2; 
  }
  
  private void ability() {
    if (keys[ability] == 1 && this.stats.manaCurr >= 0) {
      this.stats.speed = 4;
      this.stats.manaCurr--;
    } else {
      this.stats.speed = 2;
      if (this.stats.manaCurr < this.stats.mana ) {
        this.stats.manaCurr++;
      }
    }
      
  }
  
  private int getFacing() {
    //gets the direction that the player is looking in -> decides which sprite to use
    float dir = ang + (3 * PI/4);
    while(dir < 0) dir += TAU;
    return(int)(dir/(PI/2)) % 4;
  }
  
  public int getHealthCurr() {
    return this.stats.healthCurr;
  }
  
  public int getHealthTotal() {
    return this.stats.health;
  }
  
  public int getFireCount() {
    return stats.fireCount;
  }
  
  public void increaseFireCount() {
    stats.fireCount++;
  }
  
  public void resetFireCount() {
    stats.fireCount = 0;
  }
  
  public int getMana() {
    return this.stats.manaCurr;
  }
  
  public int getManaMax() {
    return this.stats.mana;
  }
    
  
  public Boolean hasWeapon() {
    return currentInventory instanceof Weapon;
  }
  
  public Weapon getWeapon() {
    if(this.hasWeapon()) {
      return (Weapon)currentInventory;
    } else {
      return null;
    }
  }
  
  public void setWeapon(Weapon weapon) {
    currentInventory = weapon;
  }
  
  
}

class Stats {
  public int healthCurr, health, healthMax;
  public int manaCurr, mana, manaMax;
  
  public int vitality, vitalityMax;
  public int attack, attackMax;
  public int wisdom, wisdomMax;
  
  public int defence;
  
  public int fireCount;
  
  public float speed, speedMax;  
  
  public float getSpeed() { return speed; }
  
}

class PlayerStats extends Stats {
  
  PlayerStats() {
    healthCurr = 10; health = 100; healthMax = 300;
    manaCurr = 100; mana = 100; manaMax = 200;
    vitality = 10; vitalityMax = 25;
    attack = 1; attackMax = 25;
    wisdom = 10; wisdomMax = 25;
    speed = 2; speedMax = 10;
    defence = 0; fireCount = 0;
  }
  
}
