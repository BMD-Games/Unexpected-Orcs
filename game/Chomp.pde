class Chomp implements Enemy {
  
  public int tier = 0;
  public int x = 0;
  public int y = 0;
  
  private float angle;
  
  protected Stats stats;
  protected PImage sprite = charSprites.get("CHOMP_WHITE");
  
  public Chomp(int x, int y, int tier) {
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
    float dist = sqrt(pow(x - playerX, 2) + pow(y - playerY, 2));
    print("Distance: ");
    println(dist);
    if(dist < 6) {
      angle = atan2(playerY - y/2, playerX - (x/2 + GUI_WIDTH/2));
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
