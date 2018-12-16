class Weapon extends Item {
  
  //Bulletspeed in  tiles per second?
  public int damage = 1, numBullets = 1, range = 1, bulletSpeed = 1;
  
  //FireRate, is the wait time in seconds between shots
  public float fireRate = 1;
  //Accuracy, angle in radians the weapon will shoot within
  public float accuracy = 0;
  
  public PImage bulletSprite;
  public color tipColour = color(50, 50, 50);
  
  public ArrayList<Pair> statusEffects = new ArrayList<Pair>();
  
  Weapon(String spriteName, String name, ArrayList<Pair> statusEffects) {
    super(spriteName, name);
    this.type = "Weapon";
    this.statusEffects = statusEffects;
  }
  
  Weapon(PImage sprite, String name, ArrayList<Pair> statusEffects) {
    super(sprite, name);
    this.type = "Weapon";
    this.statusEffects = statusEffects;
  }
  
  public void playSound() {}
}



public class WeaponFactory {
  
  public String[] weaponTypes = new String[] {"Wand", "Staff", "Spear", "Bow"};
  
  public Weapon createRandomWeapon(int tier) {
    Weapon weapon = null;
    switch((int)random(4)) {
      case 0:
        weapon = createWand(tier);
        break;
      case 1:
        weapon = createStaff(tier);
        break;
      case 2:
        weapon = createSpear(tier);
        break;
      case 3:
        weapon = createBow(tier);
        break;
    }
    return weapon;
  }
  
  public Wand createWand(int tier) {
    Wand wand = new Wand(color(random(255), random(255), random(255)));
    wand.damage =  4 + 6 * tier + (int)(randomGaussian() * tier);
    wand.range = 6;
    wand.bulletSpeed = 25 + abs((int)(randomGaussian() * tier * 2));
    wand.fireRate = Util.roundTo(0.6 - tier * abs(randomGaussian()) / 32, 100);
    wand.accuracy = Util.roundTo(0.05 + tier * randomGaussian() / 80, 100);  
    wand.tier = tier;
    return wand;
  }
  
  public Wand createWand(int tier, String ability) {
    Wand wand = createWand(tier);
    wand.statusEffects.add(new Pair("ALL", ability));
    return wand;
  }
  
  public Staff createStaff(int tier) {
    Staff staff = new Staff(color(random(255), random(255), random(255)));
    staff.damage =  4 + 8 * tier + (int)(randomGaussian() * tier);
    staff.range = 6;
    staff.bulletSpeed = 12 + abs((int)(randomGaussian() * tier));
    staff.fireRate = Util.roundTo(0.6 - tier * abs(randomGaussian()) / 32, 100);
    staff.accuracy = Util.roundTo(0.03 + tier * randomGaussian() / 160, 100);
    staff.tier = tier;
    return staff;
  }
  
  public Staff createStaff(int tier, String ability) {
    Staff staff = createStaff(tier);
    staff.statusEffects.add(new Pair("ALL", ability));
    return staff;
  }
  
  public Spear createSpear(int tier) {
    Spear spear = new Spear(color(random(255), random(255), random(255)));
    spear.damage =  4 + 10 * tier + (int)(randomGaussian() * tier);
    spear.range = 3;
    spear.bulletSpeed = 8 + abs((int)(randomGaussian() * tier));
    spear.fireRate = Util.roundTo(0.5 - tier * abs(randomGaussian()) / 20, 100);
    spear.accuracy = Util.roundTo(0.03 + tier * randomGaussian() / 400, 100); 
    spear.tier = tier;
    return spear;
  }
  
  public Spear createSpear(int tier, String ability) {
    Spear spear = createSpear(tier);
    spear.statusEffects.add(new Pair("ALL", ability));
    return spear;
  }
  
  public Bow createBow(int tier) {
    Bow bow = new Bow(color(random(255), random(255), random(255)));
    bow.damage =  16 * tier + (int)(randomGaussian() * tier);
    bow.range = 10;
    bow.bulletSpeed = 20 + abs((int)(randomGaussian() * tier));
    bow.fireRate = Util.roundTo(0.3 - tier * abs(randomGaussian()) / 20, 100);
    bow.accuracy = Util.roundTo(0.05 + tier * randomGaussian() / 160, 100);    
    bow.tier = tier;
    return bow;
  }
  
  public Bow createBow(int tier, String ability) {
    Bow bow = createBow(tier);
    bow.statusEffects.add(new Pair("ALL", ability));
    return bow;
  }
  
}

public class Wand extends Weapon {
  
  public Wand(color colour) {
    super(getCombinedSprite(itemSprites.get("WAND"), itemSprites.get("WAND_TIP"), colour), "Wand", null);
    bulletSprite = applyColourToImage(projectileSprites.get("WAND"), colour);
    tipColour = colour;
  }
  
}

public class Staff extends Weapon {
  
  public Staff(color colour) {
    super(getCombinedSprite(itemSprites.get("STAFF"), itemSprites.get("STAFF_TIP"), colour), "Staff", null);
    bulletSprite = applyColourToImage(projectileSprites.get("STAFF"), colour);
    tipColour = colour;
  }
  
}

public class Spear extends Weapon {
  
  public Spear(color colour) {
    super(getCombinedSprite(itemSprites.get("SPEAR"), itemSprites.get("SPEAR_TIP"), colour), "Spear", null);
    bulletSprite = getCombinedSprite(projectileSprites.get("SPEAR"), projectileSprites.get("SPEAR_TIP"), colour);
    tipColour = colour;
  }
  
}

public class Bow extends Weapon {
  
  public Bow(color colour) {
    super(getCombinedSprite(itemSprites.get("BOW"), itemSprites.get("BOW_TIP"), colour), "Bow", null);
    bulletSprite = getCombinedSprite(projectileSprites.get("ARROW"), projectileSprites.get("ARROW_TIP"), colour);
    tipColour = colour;
  }
  
}

class GreenRod extends Weapon {
  
  GreenRod() {
    super("GREENROD", "Green Rod", new ArrayList<Pair>());
    this.damage = 5;
    this.fireRate = 0.5;
    this.numBullets = 1;
    this.range = 8;
    this.accuracy = 0.03;
    this.bulletSpeed = 10;
    this.bulletSprite = projectileSprites.get("GREENROD");
    
  }
  
  @Override
  public void playSound() {
    // soundFiles.get("WHOOSH").play();
  }
}

private PImage getCombinedSprite(PImage baseImage, PImage tintImage, color colour) {
  PGraphics temp = createGraphics(baseImage.width, baseImage.height);
  temp.beginDraw();
  temp.image(baseImage, 0, 0);
  temp.image(applyColourToImage(tintImage, colour), 0, 0);
  temp.endDraw();
  return temp.get();
}
