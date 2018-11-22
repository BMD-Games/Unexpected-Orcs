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
    ang = atan2(mouseY - height/2, mouseX - (width/2 + GUI_WIDTH/2));

    ability();
    getFacing();
    move(delta, neighbours);
    updateBound();
    
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    screen.rotate(ang);
    screen.fill(255, 0, 0);
    screen.stroke(0);
    screen.rect(-size/2, -size/2, size, size);
    screen.line(0, 0, size, 0);
    screen.popMatrix();
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
  
  public int getHealth() {
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
  private int health, healthMax;
  private int mana, manaMax;
  
  private int vitality;
  private int attack;
  private int wisdom;
  private int defence;
  private int fireTimer;
  
  private float speed;
  
  public Stats() {
    health = 0;
    healthMax = 0;
    mana = 0;
    manaMax = 0;
    vitality = 0;
    attack = 0;
    wisdom = 0;
    defence = 0;
    fireTimer = 0;
    speed = 0;
  }
  
  public int getHealth() { return health; }
  public int getHealthMax() { return healthMax; }
  public int getMana() { return mana; }
  public int getManaMax() { return manaMax; }
  public int getVitality() { return vitality; }
  public int getAttack() { return attack; }
  public int getWisdom() { return wisdom; }
  public int getDefence() { return defence; }
  public int getFireTimer() { return fireTimer; }
  public float getSpeed() { return speed; }
  
  public void setHealth(int value) { health = value; }
  public void setHealthMax(int value) { healthMax = value; }
  public void setMana(int value) { mana = value; }
  public void setManaMax(int value) { manaMax = value; }
  public void setVitality(int value) { vitality = value; }
  public void setAttack(int value) { attack = value; }
  public void setWisdom(int value) { wisdom = value; }
  public void setDefence(int value) { defence = value; }
  public void setFireTimer(int value) { fireTimer = value; }
  public void setSpeed(float value) { speed = value; }
  
}

class PlayerStats extends Stats {
  
  PlayerStats() {
    super();
    setHealth(10); 
    setHealthMax(30);
    setMana(10);
    setManaMax(20);
    setVitality(1);
    setAttack(1);
    setWisdom(3);
    setSpeed(4);
  }
  
}
