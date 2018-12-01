
class Projectile {
  
  private PVector direction;
  private float startX, startY, speed, range;
  public float x, y;
  private int damage;
  private PImage sprite;
  private ArrayList<Pair> statusEffects = new ArrayList();
  
  
  Projectile(float x, float y, PVector direction, float speed, float range, int damage, String sprite, ArrayList<Pair> statusEffects) {
    this.x = x;
    this.y = y;
    this.startX = x;
    this.startY = y;
    this. direction = direction;
    this.speed = speed;
    this.range = range;
    this.damage = damage;
    this.sprite = projectileSprites.get(sprite);
    this.statusEffects = statusEffects;
  }
  
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
    this.statusEffects = new ArrayList();
  }
  
  public void update(double delta) {
    //need to add speed * direction * delta to the x and y
    x += direction.x * speed * delta;
    y += direction.y * speed * delta;
  }
  
  public void show(PGraphics screen, PVector renderOffset) {
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    screen.rotate(atan2(direction.y, direction.x));
    screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    screen.popMatrix();
  }
  
  private boolean alive() {
    if(dist(startX, startY, x, y) >= range) return false;
    return true;
  }
  
  private int getDamage() {
    return damage;
  }
}

class Pair {
  
  public String a;
  public String b;
  
  Pair(String a, String b) {
    this.a = a;
    this.b = b;
  }  
  
}
