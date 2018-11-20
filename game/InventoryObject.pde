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
