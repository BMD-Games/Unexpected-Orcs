class Armour extends Item {
  
  protected int defence;
  
  Armour(String sprite, String name) {
    super(sprite, name);
    this.type = "Armour";
  }
}

class LeatherArmour extends Armour {
 
  LeatherArmour() {
    super("LEATHER_ARMOUR", "Leather Armour");
    this.defence = 5;
  }
  
}
