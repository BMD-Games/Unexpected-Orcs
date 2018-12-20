class Armour extends Item {
  
  protected int defence;
  
  Armour(String sprite, String name, int defence) {
    super(sprite, name);
    this.type = "Armour";
    this.defence = defence;
  }
}
