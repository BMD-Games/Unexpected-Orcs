class Scroll extends Item {
  
  public ArrayList<Pair> statusEffects;
  public String description;
  
  Scroll(String sprite, String name) {
    super(sprite, name);
    this.type = "Scroll";
  }
  
}

class DebuffScroll extends Scroll {
 
  DebuffScroll(String[] debuffNames) {
    super("SCROLL_1", "");
    ArrayList<Pair> debuffs = new ArrayList();
    String[] effectNames = new String[debuffNames.length];
    String[] debuffDesc = new String[debuffNames.length];
    for(int i = 0; i < debuffNames.length; ++i) {
      debuffs.add(new Pair("ALL", debuffNames[i]));
      effectNames[i] = debuffToVerb(debuffNames[i]);
      debuffDesc[i] = debuffToPresentVerb(debuffNames[i]);
    }
    this.name = "Scroll of " + linkWords(effectNames);
    this.statusEffects = debuffs;
    this.description = capFirstLetter(linkWords(debuffDesc)) + " all enemies hit for one second";
  }
  
}
