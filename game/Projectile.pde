class Projectile {
  
  private PVector direction;
  private float startX, startY, speed, range;
  public float x, y;
  private int damage;
  
  Projectile(float x, float y, PVector direction, float speed, float range, int damage) {
    this.x = x;
    this.y = y;
    this.startX = x;
    this.startY = y;
    this. direction = direction;
    this.speed = speed;
    this.range = range;
    this.damage = damage;
  }
  
  public void update(double delta) {
    //need to add speed * direction * delta to the x and y
    x += direction.x * speed * delta;
    y += direction.y * speed * delta;
  }
  
  public void show(PVector renderOffset) {
    fill(255, 0, 0);
    noStroke();
    ellipse(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y, 6, 6);
  }
  
  private boolean alive() {
    if(dist(startX, startY, x, y) >= range) return false;
    return true;
  }
  
}
