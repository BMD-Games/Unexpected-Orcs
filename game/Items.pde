class Inventory {
  
  private int MAX_SIZE = 12;
  private Item[] active = new Item[4];
  private Item[] inv = new Item[MAX_SIZE];
  
  public final static int WIDTH = 4;
  
  /*
         _____________________
  ACTIVE:|Weap|Abil|Arm |Scrl|
         _____________________
  INVENT:|____|____|____|____|
         |____|____|____|____|
         |____|____|____|____|
  */
  
  
  Inventory() {
    active[0] = new Sniper();
    inv[0] = new Pistol();
    inv[1] = new Shotgun();
    inv[2] = new MachineGun();
  }
  
  void swapItemsInv(int i, int j) {
    try {
      Item save = inv[i];
      inv[i] = inv[j];
      inv[j] = save;
    } catch(Exception e) {
      //rip
    }
  }
  
  void swapItemsActive(int act, int in) {
    try {
      if(inv[in] != null) {
        if(act == 0 && inv[in].type != "Weapon") return;
        if(act == 1 && inv[in].type != "Ability") return;
        if(act == 2 && inv[in].type != "Armour") return;
        if(act == 3 && inv[in].type != "Scroll") return;
      }
      Item save = active[act];
      active[act] = inv[in];
      inv[in] = save;
    } catch(Exception e) {
      //rip
    }
  }
  
  void addItemInv(Item item) {
    for(int i = 0; i < MAX_SIZE; i++) {
      if(inv[i] == null) { inv[i] = item; return; }
    }
  }
  
  
  Item[] active() { return active; }
  Item[] inv() { return inv; }
  
  Weapon currentWeapon() { return (Weapon)active[0]; }
  Ability currentAbility() { return (Ability)active[1]; }
  Armour currentArmour() { return (Armour)active[2]; }
  Scroll currentScroll() { return (Scroll)active[3]; }
  
}

class Item {
  
  public String type, name, sprite;
  
  Item(String sprite, String name) {
   this.sprite = sprite; 
   this.name = name;
  }
}

class Scroll extends Item {
  
  Scroll(String sprite, String name) {
    super(sprite, name);
    this.type = "Scroll";
  }
}

class Ability extends Item {
  
  Ability(String sprite, String name) {
    super(sprite, name);
    this.type = "Ability";
  }
}

class Armour extends Item {
  
  public int defence;
  
  Armour(String sprite, String name) {
    super(sprite, name);
    this.type = "Armour";
  }
}

class Weapon extends Item {
  
  public int damage, numBullets, range, bulletSpeed;
  
  public float fireRate, accuracy;
  
  public String bulletSprite;
  
  Weapon(String sprite, String name) {
    super(sprite, name);
    this.type = "Weapon";
  }
  
  /*
  Weapon(int damageMulti, int fireRate, int numBullets, int range, float accuracy) {
    this.damageMulti = damageMulti;
    this.fireRate = fireRate;
    this.numBullets = numBullets;
    this.range = range;
    this.accuracy = accuracy;
  }
  */
  
}

class Shotgun extends Weapon{
  
  Shotgun() {
    super("SHOTGUN", "Shotgun");
    this.damage = 4;
    this.fireRate = 0.6;
    this.numBullets = 5;
    this.range = 4;
    this.accuracy = 0.15;
    this.bulletSpeed = 10;
    this.bulletSprite = "SHOTGUN";
    
  }
}

class Pistol extends Weapon{
  
  Pistol() {
    super("PISTOL", "Pistol");
    this.damage = 10;
    this.fireRate = 0.15;
    this.numBullets = 1;
    this.range = 6;
    this.accuracy = 0.05;
    this.bulletSpeed = 15;
    this.bulletSprite = "PISTOL";
    
  }
}

class MachineGun extends Weapon{
  
  MachineGun() {
    super("SNIPER", "Machine Gun");
    this.damage = 4;
    this.fireRate = 0.05;
    this.numBullets = 1;
    this.range = 8;
    this.accuracy = 0.1;
    this.bulletSpeed = 15;
    this.bulletSprite = "PISTOL";
    
  }
}

class Sniper extends Weapon{
  
  Sniper() {
    super("SNIPER", "Sniper");
    this.damage = 20;
    this.fireRate = 1;
    this.numBullets = 1;
    this.range = 10;
    this.accuracy = 0.01;
    this.bulletSpeed = 20;
    this.bulletSprite = "SNIPER";
    
  }
}
