public interface Enemy {
  
  /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY);
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset);
  
  /* This mob takes damage */
  public void damage(int amount);
  
  /* Drop what on death */
  public void onDeath();
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY);
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box);
  
  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos);
  
}

abstract class StandardEnemy implements Enemy {
  
  public int tier;
  public float x;
  public float y;
  
  protected int range = 10;
  protected float radius = 1;
  protected PImage sprite;
  protected Stats stats = new Stats();
  
  public StandardEnemy(float x, float y, int tier) {
    this.tier = tier;
    this.x = x;
    this.y = y;
  }
    
   /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY) {
    return stats.health > 0;
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset) {}
  
  /* Takes damage */
  public void damage(int amount){
    int damage = amount - stats.defence;
    if(damage > 0) {
      stats.health -= damage;
      engine.addText(String.valueOf(damage), x, y - radius, 0.5, color(200, 0 , 0));
    }
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return false;
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box) {
    return false;
  }
  
  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos) {
    return true;
  }
}

abstract class MeleeEnemy extends StandardEnemy implements Enemy {
  
  public MeleeEnemy(float x, float y, int tier) {
    super(x, y, tier);
  }
  
}
