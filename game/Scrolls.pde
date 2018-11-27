class Scroll extends Item {
  
  String[] stats;
  
  Scroll(String sprite, String name) {
    super(sprite, name);
    this.type = "Scroll";
  }
  
}

class LitScroll extends Scroll {
 
  LitScroll() {
    super("SCROLL_1", "Lit Scroll");
  }
  
}
