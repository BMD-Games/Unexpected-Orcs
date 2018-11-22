class Chomp implements Enemy {
  
  public int tier = 0;
  public int x = 0;
  public int y = 0;
  
  protected Stats stats;
  protected PImage sprite = charSprites.get("CHOMP_WHITE");
  
  public Chomp(int x, int y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
    stats = new Stats();
    stats.setHealth(2);
    stats.setAttack(1);
    stats.setSpeed(2);
    stats.setDefence(1);
  }
  
  /* Enemies need to update on tics */
  void update(Player player) {}
  
  /* Displays enemy to screen */
  void show(PGraphics screen, PVector renderOffset){
    screen.image(sprite, x - sprite.width * SCALE/2, y - sprite.height * SCALE/2, x + sprite.width * SCALE, y + sprite.height * SCALE);
  }
  
  /* This mob takes damage */
  void damage(int amount){}
  
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
