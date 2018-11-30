class Scroll extends Item {
  
  public ArrayList<Pair> statusEffects;
  
  Scroll(String sprite, String name, ArrayList<Pair> statusEffects) {
    super(sprite, name);
    this.type = "Scroll";
    this.statusEffects = statusEffects;
  }
  
}

class LitScroll extends Scroll {
 
  LitScroll() {
    super("SCROLL_1", "Lit Scroll", new ArrayList<Pair>());
  }
  
}
