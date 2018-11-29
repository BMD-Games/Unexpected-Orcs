class Chomp extends MeleeEnemy implements Enemy {

  public Chomp(float x, float y, int tier) {
    super(x, y, tier);
    radius = 0.25;
    range = 6;
    sprite = charSprites.get("CHOMP_BLACK_SMALL");
    stats.health = 14 * tier;
    stats.attack = 5 * tier;
    stats.speed = 1.3 * tier;
    stats.defence = 2 * tier;
  }
  
  /* Enemies need to update on tics */
  public boolean update(double delta) {
    if(Utility.distance(x, y, engine.player.x, engine.player.y) < range) {
      attackWait += delta;
      if(Utility.distance(x, y, engine.player.x, engine.player.y) < radius) {
        attack();
      } else {
        move(delta);
      }
    }
    return super.update(delta);
  }
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset){
    screen.pushMatrix();
    screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
    if((angle < PI/2) && (angle > -PI/2)) {
      screen.rotate(angle);
      screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
    } else {
      screen.scale(-1.0, 1.0);
      screen.rotate(PI - angle);
      screen.image(sprite, sprite.width * SCALE/2, -sprite.height * SCALE/2, -sprite.width * SCALE, sprite.height * SCALE);
    }
    screen.popMatrix();
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box){
    return false;
  }
}

class BigChomp extends Chomp {
  
  public BigChomp(float x, float y, int tier) {
    super(x, y, tier);
    radius = 0.5;
    sprite = charSprites.get("CHOMP_BLACK");
    stats.health = 25 * tier;
    stats.attack = 11 * tier;
    stats.speed = 1.2 * tier;
    stats.defence = 3 * tier; 
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return (Utility.distance(x, y, pointX, pointY) < radius);
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
  }
}

class BossChomp extends Chomp {
  
  public BossChomp(float x, float y, int tier) {
    super(x, y, tier);
    radius = 1;
    sprite = charSprites.get("CHOMP_BOSS");
    stats.health = 45 * tier;
    stats.attack = 20 * tier;
    stats.speed = 1.1 * tier;
    stats.defence = 8 * tier; 
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return (Utility.distance(x, y, pointX, pointY) < radius);
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
    engine.addDrop(new CavePortal(x, y));
  }
  
}
