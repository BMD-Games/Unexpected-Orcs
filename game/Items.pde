class Inventory {
  
  private int MAX_SIZE = 9;
  private Item[] active = new Item[3];
  private Item[] inv = new Item[MAX_SIZE];
  
  /*
         ________________
  ACTIVE:|Weap|Abil|Arm |
         ________________
  INVENT:|____|____|____|
         |____|____|____|
         |____|____|____|  
  */
  
  
  Inventory() {
    active[0] = new Sniper();
    inv[0] = new Pistol();
    inv[1] = new Shotgun();
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
  
}

class Item {
  
  private int itemID;
  public String type, name;
  
  Item(int itemID, String name) {
   this.itemID = itemID; 
   this.name = name;
  }
  
  Item() {
    this.itemID = 4;
  }
  
  public int getItemID() {
    return itemID;
  }  
  
  public String type() {
    return type;
  }
}

class Ability extends Item {
  
  Ability(int itemID, String name) {
    super(itemID, name);
    this.type = "Ability";
  }
}

class Armour extends Item {
  
  public int defence;
  
  Armour(int itemID, String name) {
    super(itemID, name);
    this.type = "Armour";
  }
}

class Weapon extends Item {
  
  public int damage, fireRate, numBullets, range, bulletSpeed;
  
  public float accuracy;
  
  public String bulletSprite;
  
  Weapon(int itemID, String name) {
    super(itemID, name);
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
    super(0, "Shotgun");
    this.damage = 4;
    this.fireRate = 40;
    this.numBullets = 5;
    this.range = 4;
    this.accuracy = 0.15;
    this.bulletSpeed = 10;
    this.bulletSprite = "SHOTGUN";
    
  }
}

class Pistol extends Weapon{
  
  Pistol() {
    super(1, "Pistol");
    this.damage = 10;
    this.fireRate = 10;
    this.numBullets = 1;
    this.range = 6;
    this.accuracy = 0.05;
    this.bulletSpeed = 15;
    this.bulletSprite = "PISTOL";
    
  }
}

class Sniper extends Weapon{
  
  Sniper() {
    super(2, "Sniper");
    this.damage = 20;
    this.fireRate = 50;
    this.numBullets = 1;
    this.range = 10;
    this.accuracy = 0.01;
    this.bulletSpeed = 20;
    this.bulletSprite = "SNIPER";
    
  }
}
