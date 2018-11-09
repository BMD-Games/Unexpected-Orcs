class Projectile {
  
  private PVector direction;
  private float x, y, speed;
  private int damage;
  private boolean hit = false;
  
  Projectile(float x, float y, PVector direction, float speed, int damage) {
    this.x = x;
    this.y = y;
    this. direction = direction;
    this.speed = speed;
    this.damage = damage;
  }
  
  public boolean update(double delta) {
    //need to add speed * direction * delta to the x and y
    if(hit) return false;
    
    return true;
  }
  
}
