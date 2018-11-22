class InventoryObject {
  
  int itemID;
  
  InventoryObject(int itemID) {
   this.itemID = itemID; 
  }
  
  InventoryObject() {
    this.itemID = 4;
  }
  
  public int getItemID() {
    return itemID;
  }  
  
}


class Weapon extends InventoryObject {
  
  public int damageMulti, fireRate, numBullets, range, bulletSpeed;
  
  public float accuracy;
  
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
    
    this.damageMulti = 4;
    this.fireRate = 40;
    this.numBullets = 10;
    this.range = 4;
    this.accuracy = 0.15;
    this.bulletSpeed = 10;
    
  }
}

class Pistol extends Weapon{
  
  Pistol() {
    
    this.damageMulti = 10;
    this.fireRate = 10;
    this.numBullets = 1;
    this.range = 6;
    this.accuracy = 0.05;
    this.bulletSpeed = 15;
    
  }
}

class Sniper extends Weapon{
  
  Sniper() {
    
    this.damageMulti = 20;
    this.fireRate = 50;
    this.numBullets = 1;
    this.range = 10;
    this.accuracy = 0.01;
    this.bulletSpeed = 20;
    
  }
}
