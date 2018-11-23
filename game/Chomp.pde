class Chomp implements Enemy {
  
  public int tier = 0;
  public float x = 0;
  public float y = 0;
  
  private float angle;
  private boolean isLeft;
  
  protected Stats stats;
  protected PImage sprite = charSprites.get("CHOMP_WHITE");
  
  public Chomp(float x, float y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
    stats = new Stats();
    stats.setHealth(2 * tier);
    stats.setAttack(1 * tier);
    stats.setSpeed(2 * tier);
    stats.setDefence(1 * tier);
  }
  
  /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY) {
    //If player in range attack.
    if(sqrt(pow(x - playerX, 2) + pow(y - playerY, 2)) < 6) {
      angle = atan2(playerY - y, playerX - x);
      print("Angle: ");
      println(angle);
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
  public void damage(int amount){}
  
  private void move(double delta) {
    x += stats.getSpeed() * cos(angle) * delta;
    y += stats.getSpeed() * sin(angle) * delta;
  }
  
}

class BossChomp extends Chomp {
  
  public BossChomp(int x, int y, int tier) {
    super(x, y, tier);
    stats.setHealth(4);
    stats.setAttack(3);
    stats.setSpeed(1.5);
    stats.setDefence(3); 
  }
  
}
