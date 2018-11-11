class Enemy {
  private int health, level;
  private float x, y, speed;
  
  PVector direction;
  
  Enemy(float x, float y, float speed, int health, int level) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.health = health;
    this.level = level;
  }
  
  public boolean update(double delta, float playerX, float playerY) {
    if(health <= 0) return false;
    
    return true;
  }
  
  public void show(PVector renderOffset) {
    
  }
  
}
