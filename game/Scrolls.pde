class Scroll extends Item {
  
  public ArrayList<Pair> statusEffects;
  public String description;
  
  Scroll(String sprite, String name) {
    super(sprite, name);
    this.type = "Scroll";
  }
  
}

class LitScroll extends Scroll {
 
  LitScroll() {
    super("SCROLL_1", "Heavy Feet");
    ArrayList<Pair> lit = new ArrayList();
    lit.add(new Pair("ALL", "SLOWED"));
    this.statusEffects = lit;
    this.description = "Slows all enemies hit for one second";
  }
  
}
