class Inventory {
  
  private int MAX_SIZE = 12;
  private Item[] active = new Item[4];
  private Item[] inv = new Item[MAX_SIZE];
  
  public final static int WIDTH = 4;
  
  /*
         _____________________
  ACTIVE:|Weap|Abil|Arm |Scrl|
         _____________________
  INVENT:|____|____|____|____|
         |____|____|____|____|
         |____|____|____|____|
  */
  
  
  Inventory() {
    active[0] = new Sniper();
    active[1] = new SwiftBoots();
    active[2] = new LeatherArmour();
    active[3] = new LitScroll();
    
    inv[0] = new Pistol();
    inv[1] = new Shotgun();
    inv[2] = new MachineGun();
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
        if(act == 3 && inv[in].type != "Scroll") return;
      }
      Item save = active[act];
      active[act] = inv[in];
      inv[in] = save;
    } catch(Exception e) {
      //rip
    }
  }
  
  public Item addItemInv(Item item, int pos) {
    Item old = inv[pos];
    inv[pos] = item;
    return old;
  }
  
  public Item addItemActive(Item item, int pos) {
    if(item != null) {
      if(pos == 0 && item.type != "Weapon") return item;
      if(pos == 1 && item.type != "Ability") return item;
      if(pos == 2 && item.type != "Armour") return item;
      if(pos == 3 && item.type != "Scroll") return item;
    }
    Item old = active[pos];
    active[pos] = item;
    return old;
  }
  
  
  Item[] active() { return active; }
  Item[] inv() { return inv; }
  
  Weapon currentWeapon() { return (Weapon)active[0]; }
  Ability currentAbility() { return (Ability)active[1]; }
  Armour currentArmour() { return (Armour)active[2]; }
  Scroll currentScroll() { return (Scroll)active[3]; }
  
}

class Item {
  
  public String type, name, sprite;
  
  public int tier = 0;
  
  Item(String sprite, String name) {
   this.sprite = sprite; 
   this.name = name;
  }
}
