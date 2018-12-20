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
      itemBag.addItem(itemFactory.createRandomWeapon(tier));
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
      itemBag.addItem(itemFactory.createRandomWeapon(tier));
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
      itemBag.addItem(itemFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.3) {
      itemBag.addItem(itemFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itemBag);
    engine.addDrop(new CavePortal(x, y));
  }
}

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

public class KingElemental extends Elemental implements Enemy {
  
  private float summonWait;
  
  public KingElemental(float x, float y, int tier) {
    super(x, y, tier);
    radius = 1;
    stats.health = 30 + 60 * tier;
    stats.attack = 20 + 60 * tier;
    stats.speed = 0.2 * tier;
    stats.defence = 2 + 8 * tier;
    attackWaitTime = 1;
    statusEffect = "DAZED";
    sprite = charSprites.get("KING_ELEMENTAL");
    sprites[0] = getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_1"));
    sprites[1] = getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_2"));
    sprites[2] = getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_3"));
    sprites[3] = getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_4"));
  }
  
  public boolean update(double delta) {
    summonWait += delta;
    if(summonWait > (3 - 0.1 * tier) && Util.distance(x, y, engine.player.x, engine.player.y) < range) {
      summon();
      summonWait = 0;
    }
    return super.update(delta);
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "MANA"));
    engine.addDrop(new CavePortal(x, y));
  }
  
  private void summon() {
    Elemental elemental = null;
      switch((int)random(4)) {
        case 0:
          elemental = new FireElemental(x + 0.25, y - 0.25, tier);
          break;
        case 1:
          elemental = new IceElemental(x + 0.25, y + 0.25, tier);
          break;
        case 2:
          elemental = new MagicElemental(x - 0.25, y - 0.25, tier);
          break;
        case 3:
          elemental = new PoisonElemental(x - 0.25, y, tier);
          break;
      }
      engine.currentLevel.addEnemy(elemental);
  }
  
}

public class GoblinArcher extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.4, h = 0.5;
  
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
    accuracy = 0.04;
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
      itembag.addItem(itemFactory.createBow(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinMage extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.4, h = 0.5;
  
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
    predictAim = true;
    accuracy = 0.02;
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
      itembag.addItem(itemFactory.createStaff(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinSpearman extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.4, h = 0.5;
  
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
      itembag.addItem(itemFactory.createSpear(tier));
    }
    engine.addDrop(itembag);
  }

}

public class GoblinWarrior extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.4, h = 0.5;
  
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
      itembag.addItem(itemFactory.createWand(tier));
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
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.5) {
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
    engine.addDrop(new CellarPortal(x, y));
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
    sprite = charSprites.get("BASILISK");
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
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    if(random(1) < 0.5) {
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
    engine.addDrop(new GrassPortal(x, y));
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
    accuracy = 0.05;
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
      itembag.addItem(itemFactory.createRandomWeapon(tier));
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
    accuracy = 0.03;
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
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }

}

public class Spider extends RangedEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public Spider(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 0.6 + 0.1 * tier;
    stats.attack = 12 + 12 * tier;
    stats.defence = 2 * tier;
    stats.health = 8 + 6 * tier;
    stats.vitality = 1;
    shotWaitTime = 1.2 - abs(0.03 * tier * randomGaussian());
    shootDistance = 2;
    retreatDistance = 0.3;
    accuracy = 0.01;
    sprite = charSprites.get("SPIDER");
    projectileSprite = projectileSprites.get("STAFF");
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
      itembag.addItem(itemFactory.createRandomWeapon(tier));
    }
    engine.addDrop(itembag);
  }
  
  protected void attack() {
    if((stats.fireTimer > shotWaitTime) && (engine.currentLevel.canSee((int)x, (int)y, (int)engine.player.x, (int)engine.player.y))) {
      stats.fireTimer = 0;
      ArrayList<Pair> statusEffects = new ArrayList(1);
      statusEffects.add(new Pair("ALL", "SLOWED"));
      engine.enemyProjectiles.add(new Projectile(x, y, new PVector(cos(angle), sin(angle)), stats.speed * 3, range, stats.attack, projectileSprite, statusEffects));
    }
  }

}

public class Crawler extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  
  public Crawler(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 1.2 + 0.2 * tier;
    stats.attack = 8 + 12 * tier;
    stats.defence = 4 * tier;
    stats.health = 25 + 25 * tier;
    stats.vitality = 2;
    attackWaitTime = 0.5;
    sprite = charSprites.get("CRAWLER");
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
    if(random(1) < 0.3) {
      itembag.addItem(itemFactory.createRandomArmour(tier));
    }
    engine.addDrop(itembag);
  }

}

public class Bat extends MeleeEnemy implements Enemy, RectangleObject {
  
  private float w = 0.5, h = 0.5;
  private float animationTime = 0;
  private int lastAnim = 1;
  protected PImage[] sprites = {charSprites.get("BAT_SPREAD"), charSprites.get("BAT_FLAPPING")};
  
  public Bat(float x, float y, int tier) {
    super(x, y, tier);
    stats.speed = 2.2 + 0.3 * tier;
    stats.attack = 8 + 12 * tier;
    stats.defence = 4 * tier;
    stats.health = 25 + 25 * tier;
    stats.vitality = 2;
    attackWaitTime = 0.5;
    sprite = charSprites.get("BAT_SPREAD");
  }
  
  public float getWidth() {
    return w;
  }
  
  public float getHeight() {
    return h;
  }
  
  public boolean update(double delta) {
    animationTime += delta + random((float)delta / 2);
    animationTime %= 0.3;
    int currentAnim = (int)(animationTime / 0.1);
    if((currentAnim == 0) && (lastAnim != 0)) {
      y += 0.05;
      sprite = sprites[1];
    } else if((currentAnim != 0) && (currentAnim != lastAnim)) {
      y -= 0.025;
      sprite = sprites[0];
    }
    lastAnim = currentAnim;
    return super.update(delta);
  }
  
  public void onDeath() {
    engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
    ItemBag itembag = new ItemBag(x, y, tier);
    if(random(1) < 0.1) {
      itembag.addItem(new DebuffScroll(new String[] {"DAZED"}));
    }
    engine.addDrop(itembag);
  }
  
  protected void attack() {
    super.attack();
    engine.player.stats.addStatusEffect("DAZED", 5);
  }

}
