class Weapon extends Item {
  
  public int damage, numBullets, range, bulletSpeed;
  
  public float fireRate, accuracy;
  
  public String bulletSprite;
  
  public ArrayList<Pair> statusEffects;
  
  Weapon(String sprite, String name, ArrayList<Pair> statusEffects) {
    super(sprite, name);
    this.type = "Weapon";
    this.statusEffects = statusEffects;
  }
  
  public void playSound() {}
}

class Shotgun extends Weapon{
  
  Shotgun() {
    super("SHOTGUN", "Shotgun", new ArrayList<Pair>());
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
    super("PISTOL", "Pistol", new ArrayList<Pair>());
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
    super("MACHINE_GUN", "Machine Gun", new ArrayList<Pair>());
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
    super("SNIPER", "Sniper", new ArrayList<Pair>());
    this.damage = 100;
    this.fireRate = 1;
    this.numBullets = 1;
    this.range = 10;
    this.accuracy = 0.01;
    this.bulletSpeed = 20;
    this.bulletSprite = "SNIPER";
    
  }
  
  @Override
  public void playSound() {
    // soundFiles.get("WHOOSH").play();
  }

}
