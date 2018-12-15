class Chomp extends MeleeEnemy implements Enemy, CircleObject {
  
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
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
    ItemBag itemBag = new ItemBag(x, y, tier);
    if(random(1) < 0.12) {
      itemBag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itemBag);
  }
  
  public float getRadius() {
    return radius;
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
    ItemBag itemBag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itemBag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itemBag);
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
    ItemBag itemBag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itemBag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.3) {
      itemBag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itemBag);
    engine.addDrop(new CavePortal(x, y));
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public abstract class Elemental extends MeleeEnemy implements Enemy, CircleObject {
  
  protected float animationTime = 0;
  protected PImage[] sprites = {charSprites.get("FIRE_ELEMENTAL"), charSprites.get("ICE_ELEMENTAL"), charSprites.get("MAGIC_ELEMENTAL"), charSprites.get("POISON_ELEMENTAL")};
  protected String statusEffect;
  
  public Elemental(float x, float y, int tier) {
    super(x, y, tier);
    radius = 0.25;
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
  
  public float getRadius() {
    return radius;
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
    engine.addDrop(new StatOrb(x, y, tier, "VITALITY"));
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

public class GoblinArcher extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public GoblinArcher(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.7 + 0.2 * tier;
    stats.attack = 5 + 15 * tier;
    stats.defence = 2 + 2 * tier;
    stats.health = 10 + 8 * tier;
    stats.vitality = 1;
    shotWaitTime = 0.9 - abs(0.03 * tier * randomGaussian());
    shootDistance = 2.6;
    retreatDistance = 2;
    sprite = charSprites.get("GOBLIN_ARCHER");
    projectileSprite = getCombinedSprite(projectileSprites.get("ARROW"), projectileSprites.get("ARROW_TIP"), color(50,50,50));
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itembag.addItem(weaponFactory.createBow(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinMage extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public GoblinMage(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.6 + 0.1 * tier;
    stats.attack = 10 + 20 * tier;
    stats.defence = 2 * tier;
    stats.health = 8 + 6 * tier;
    stats.vitality = 1;
    shotWaitTime = 1.2 - abs(0.03 * tier * randomGaussian());
    shootDistance = 4;
    retreatDistance = 2.6;
    sprite = charSprites.get("GOBLIN_MAGE");
    projectileSprite = applyColourToImage(projectileSprites.get("STAFF"), color(124, 10, 10));
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "HEALTH"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itembag.addItem(weaponFactory.createStaff(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinSpearman extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public GoblinSpearman(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 1.2 + 0.2 * tier;
    stats.attack = 8 + 12 * tier;
    stats.defence = 3 * tier;
    stats.health = 20 + 15 * tier;
    stats.vitality = 2;
    attackWaitTime = 0.6;
    sprite = charSprites.get("GOBLIN_SPEARMAN");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "VITALITY"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itembag.addItem(weaponFactory.createSpear(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinWarrior extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public GoblinWarrior(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.9 + 0.2 * tier;
    stats.attack = 12 + 20 * tier;
    stats.defence = 5 * tier;
    stats.health = 30 + 20 * tier;
    stats.vitality = 3;
    sprite = charSprites.get("GOBLIN_WARRIOR");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "DEFENCE"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.05) {
      itembag.addItem(weaponFactory.createWand(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinBoxer extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 1, h = 1;
  
  public GoblinBoxer(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.6 + 0.15 * tier;
    stats.attack = 20 + 30 * tier;
    stats.defence = 8 * tier;
    stats.health = 40 + 35 * tier;
    stats.vitality = 6;
    sprite = charSprites.get("GOBLIN_BOXER");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "ATTACK"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.5) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.5) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }

}

public class Basilisk extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 1, h = 1;
  
  public Basilisk(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 1.2 + 0.2 * tier;
    stats.attack = 20 + 30 * tier;
    stats.defence = 3 * tier;
    stats.health = 30 + 25 * tier;
    stats.vitality = 3;
    sprite = charSprites.get("BASILSIK");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.5) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.5) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }

}

public class Rose extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public Rose(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.6 + 0.1 * tier;
    stats.attack = 10 + 12 * tier;
    stats.defence = 2 * tier;
    stats.health = 8 + 6 * tier;
    stats.vitality = 1;
    shotWaitTime = 0.6 - abs(0.03 * tier * randomGaussian());
    shootDistance = 4;
    retreatDistance = 0.3;
    sprite = charSprites.get("ROSE");
    projectileSprite = projectileSprites.get("THORN");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "WISDOM"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }

}

public class Daisy extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public Daisy(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.6 + 0.1 * tier;
    stats.attack = 24 + 20 * tier;
    stats.defence = 2 * tier;
    stats.health = 8 + 6 * tier;
    stats.vitality = 1;
    shotWaitTime = 1.2 - abs(0.03 * tier * randomGaussian());
    shootDistance = 4;
    retreatDistance = 0.3;
    sprite = charSprites.get("DAISY");
    projectileSprite = projectileSprites.get("LEAF");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "MANA"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.2) {
      itembag.addItem(weaponFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }

}
