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
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box){
    return false;
  }
}

public class BigChomp extends Chomp {
  
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

public class BossChomp extends Chomp {
  
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



public abstract class Elemental extends MeleeEnemy implements Enemy {
  
  String statusEffect;
  
  public Elemental(float x, float y, int tier) {
    super(x, y, tier);
    stats.health = 6 * tier;
    stats.attack = 10 * tier;
    stats.speed = 2 * tier;
    stats.defence = 2 * tier; 
  }
  
  protected void attack() {
    super.attack();
    engine.player.stats.addStatusEffect(statusEffect, 3);
  }
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY) {
    return false;
  }
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box) {
    return false;
  }
  
}

public class FireElemental extends Elemental implements Enemy {
  
  public FireElemental(float x, float y, int tier) {
    super(x, y, tier);
    statusEffect = "SICK";
    sprite = charSprites.get("FIRE_ELEMENTAL");
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
  }
  
}

public class IceElemental extends Elemental implements Enemy {
  
  public IceElemental(float x, float y, int tier) {
    super(x, y, tier);
    statusEffect = "SLOWED";
    sprite = charSprites.get("ICE_ELEMENTAL");
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "MANA"));
  }
  
}

public class MagicElemental extends Elemental implements Enemy {
  
  public MagicElemental(float x, float y, int tier) {
    super(x, y, tier);
    statusEffect = "CURSED";
    sprite = charSprites.get("MAGIC_ELEMENTAL");
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "WISDOM"));
  }
  
}

public class PoisonElemental extends Elemental implements Enemy {
  
  public PoisonElemental(float x, float y, int tier) {
    super(x, y, tier);
    statusEffect = "ARMOURBREAK";
    sprite = charSprites.get("POISON_ELEMENTAL");
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "DEFENCE"));
  }
  
}
