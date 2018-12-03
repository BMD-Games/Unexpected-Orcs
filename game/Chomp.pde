class Chomp extends MeleeEnemy implements Enemy {

  public Chomp(float x, float y, int tier) {
    super(x, y, tier);
    radius = 0.25;
    range = 6;
    rectangleBB = false;
    sprite = charSprites.get("CHOMP_BLACK_SMALL");
    stats.health = 14 * tier;
    stats.attack = 5 * tier;
    stats.speed = 1.3 * tier;
    stats.defence = 2 * tier;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
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
    return (Util.distance(x, y, pointX, pointY) < radius);
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
    return (Util.distance(x, y, pointX, pointY) < radius);
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
    engine.addDrop(new CavePortal(x, y));
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public abstract class Elemental extends MeleeEnemy implements Enemy {
  
  protected float animationTime = 0;
  protected PImage[] sprites = {charSprites.get("FIRE_ELEMENTAL"), charSprites.get("ICE_ELEMENTAL"), charSprites.get("MAGIC_ELEMENTAL"), charSprites.get("POISON_ELEMENTAL")};
  protected String statusEffect;
  
  public Elemental(float x, float y, int tier) {
    super(x, y, tier);
    radius = 0.25;
    rectangleBB = false;
    stats.health = 6 * tier;
    stats.attack = 10 * tier;
    stats.speed = 2 * tier;
    stats.defence = 2 * tier; 
  }
  
  public boolean update(double delta) {
    animationTime += delta + random((float)delta / 2);
    animationTime %= 0.8;
    sprite = sprites[(int)(animationTime / 0.2)];
    return super.update(delta);
  }
  
  protected void attack() {
    super.attack();
    engine.player.stats.addStatusEffect(statusEffect, 3);
  }
  
}

public class FireElemental extends Elemental implements Enemy {
  
  public FireElemental(float x, float y, int tier) {
    super(x, y, tier);
    statusEffect = "SICK";
    sprite = charSprites.get("FIRE_ELEMENTAL");
    sprites[0] = charSprites.get("FIRE_ELEMENTAL");
    sprites[1] = charSprites.get("FIRE_ELEMENTAL_2");
    sprites[2] = charSprites.get("FIRE_ELEMENTAL_3"); 
    sprites[3] = charSprites.get("FIRE_ELEMENTAL_4");
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
    sprites[0] = charSprites.get("ICE_ELEMENTAL");
    sprites[1] = charSprites.get("ICE_ELEMENTAL_2");
    sprites[2] = charSprites.get("ICE_ELEMENTAL_3"); 
    sprites[3] = charSprites.get("ICE_ELEMENTAL_4");
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
    sprites[0] = charSprites.get("MAGIC_ELEMENTAL");
    sprites[1] = charSprites.get("MAGIC_ELEMENTAL_2");
    sprites[2] = charSprites.get("MAGIC_ELEMENTAL_3"); 
    sprites[3] = charSprites.get("MAGIC_ELEMENTAL_4");
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
    sprites[0] = charSprites.get("POISON_ELEMENTAL");
    sprites[1] = charSprites.get("POISON_ELEMENTAL_2");
    sprites[2] = charSprites.get("POISON_ELEMENTAL_3"); 
    sprites[3] = charSprites.get("POISON_ELEMENTAL_4");
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "DEFENCE"));
  }
  
}
