class Chomp implements Enemy {
  
  public int tier = 0;
  public float x = 0;
  public float y = 0;
  
  private float angle;
  private boolean isLeft;
  
  protected Stats stats;
  protected PImage sprite;
  
  public Chomp(float x, float y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
    if(random(2) < 1) {
      sprite = charSprites.get("CHOMP_WHITE_SMALL");
    } else {
      sprite = charSprites.get("CHOMP_BLACK_SMALL");
    }
    stats = new Stats();
    stats.setHealth(14 * tier);
    stats.setAttack(5 * tier);
    stats.setSpeed(1.3 * tier);
    stats.setDefence(1 * tier);
  }
  
  /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY) {
    //If player in range attack.
    if(distanceFrom(playerX, playerY) < 6) {
      angle = atan2(playerY - y, playerX - x);
      move(delta);
    }
    
    //Return true if chomp is alive
    return stats.getHealth() > 0;
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset){
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    screen.rotate(angle);
    screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    screen.popMatrix();
  }
  
  /* This mob takes damage */
  public void damage(int amount){
    if(amount > stats.getDefence()) {
      stats.setHealth(stats.getHealth() - (amount - stats.getDefence()));
    }
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return (distanceFrom(pointX, pointY) < 0.25);
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box){
    return false;
  }
  
  private void move(double delta) {
    x += stats.getSpeed() * cos(angle) * delta;
    y += stats.getSpeed() * sin(angle) * delta;
  }
  
  protected float distanceFrom(float pointX, float pointY) {
    return sqrt(pow(x - pointX, 2) + pow(y - pointY, 2));
  }
  
}

class BigChomp extends Chomp {
  
  public BigChomp(int x, int y, int tier) {
    super(x, y, tier);
    if(random(2) < 1) {
      sprite = charSprites.get("CHOMP_WHITE");
    } else {
      sprite = charSprites.get("CHOMP_BLACK");
    }
    stats.setHealth(25 * tier);
    stats.setAttack(11 * tier);
    stats.setSpeed(1.2 * tier);
    stats.setDefence(3 * tier); 
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return (distanceFrom(pointX, pointY) < 0.5);
  }
  
}

class BossChomp extends Chomp {
  
  public BossChomp(int x, int y, int tier) {
    super(x, y, tier);
    sprite = charSprites.get("CHOMP_BOSS");
    stats.setHealth(45 * tier);
    stats.setAttack(20 * tier);
    stats.setSpeed(1.1 * tier);
    stats.setDefence(8 * tier); 
  }
  
}
