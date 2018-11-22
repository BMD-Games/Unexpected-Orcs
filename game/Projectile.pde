class Projectile {
  
  private PVector direction;
  private float startX, startY, speed, range;
  public float x, y;
  private int damage;
  private PImage sprite;
  
  Projectile(float x, float y, PVector direction, float speed, float range, int damage, String sprite) {
    this.x = x;
    this.y = y;
    this.startX = x;
    this.startY = y;
    this. direction = direction;
    this.speed = speed;
    this.range = range;
    this.damage = damage;
    this.sprite = projectileSprites.get(sprite);
  }
  
  public void update(double delta) {
    //need to add speed * direction * delta to the x and y
    x += direction.x * speed * delta;
    y += direction.y * speed * delta;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    screen.rotate(PVector.angleBetween(direction, PVector.fromAngle(0)));
    screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    screen.popMatrix();
  }
  
  private boolean alive() {
    if(dist(startX, startY, x, y) >= range) return false;
    return true;
  }
  
}
