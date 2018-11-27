class GUI {
  /**
   This class is used for drawing and handeling all UI related screens and elements  
   **/

  private Button play, back, options, menu, exit, pause, newGame, load;
  private Button keyUp, keyDown, keyLeft, keyRight, keyAbility;
  private DisplayBar healthBar, manaBar;
  private PImage title = loadImage("/assets/sprites/title.png");
  private PGraphics screen;
  private color c = 100;

  //Inventory drag and drop stuff
  private final int invBuff = 5, invScale = 2, itemOffset = 1, invSize = SPRITE_SIZE * invScale + 2 * itemOffset;
  private final int invX = (GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH+ itemOffset)))/2, invY = 8 * TILE_SIZE/2;
  private boolean prevSelection = false, currSelection = false;
  private int b1Type, b2Type, menuType; // if inv box is in active or not
  private int b1 = -1, b2 = -1, itemOver, active = 0, inv = 1, bag = 2, out = 3;; //inv box 1 and 2 for drag and swap
  private Item mouseOverItem;

  GUI() {
    //-----Main Buttons
    play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
    options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 0, "OPTIONS");
    menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "MENU");    
    exit = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "EXIT");
    back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "BACK");
    pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, "PAUSE");
    newGame = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "NEW");
    load = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE, "LOAD");
    
    
    
    //-----Settings buttons
    keyUp = new Button(width/2, height/2 - TILE_SIZE * 4, "BLANK_1x1");
    keyDown = new Button(width/2, height/2 - TILE_SIZE * 3, "BLANK_1x1");
    keyLeft = new Button(width/2, height/2 - TILE_SIZE * 2, "BLANK_1x1");
    keyRight = new Button(width/2, height/2 - TILE_SIZE * 1, "BLANK_1x1");
    keyAbility = new Button(width/2, height/2 - TILE_SIZE * 0, "BLANK_1x1");

    healthBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5 + 4, TILE_SIZE/2, color(230, 100, 100));
    manaBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5 + 4, 3 * TILE_SIZE/2, color(153, 217, 234));

    screen = createGraphics(width, height);
  }

  public void clearScreen() {
    screen.background(0, 0);
  }

  public void drawMenu() {
    //Draws the main menu
    screen.beginDraw();
    clearScreen();
    screen.image(title, 0, 0, width, height);
    //screen.background(c);
    play.show(screen);
    load.show(screen);
    options.show(screen);
    exit.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawOptions() {
    //Draws the options menu
    screen.beginDraw();
    screen.textAlign(CENTER, CENTER);
    screen.textSize(TILE_SIZE/2);
    screen.background(c);
    back.show(screen);
    keyUp.show(screen);
    keyDown.show(screen);
    keyLeft.show(screen);
    keyRight.show(screen);
    keyAbility.show(screen);
    screen.fill(0, 112, 188);
    if(!remapNextKey || remapAction != up) screen.text(getKeyString(up), keyUp.x + keyUp.w/2, keyUp.y + keyUp.h/2);
    if(!remapNextKey || remapAction != down) screen.text(getKeyString(down), keyDown.x + keyDown.w/2, keyDown.y + keyDown.h/2);
    if(!remapNextKey || remapAction != left) screen.text(getKeyString(left), keyLeft.x + keyLeft.w/2, keyLeft.y + keyLeft.h/2);
    if(!remapNextKey || remapAction != right) screen.text(getKeyString(right), keyRight.x + keyRight.w/2, keyRight.y + keyRight.h/2);
    if(!remapNextKey || remapAction != ability) screen.text(getKeyString(ability), keyAbility.x + keyAbility.w/2, keyAbility.y + keyAbility.h/2);
    screen.fill(255);
    screen.textAlign(RIGHT, CENTER);
    screen.text("Forward", keyUp.x, keyUp.y + keyUp.h/2);
    screen.text("Back", keyDown.x, keyDown.y + keyDown.h/2);
    screen.text("Left", keyLeft.x, keyLeft.y + keyLeft.h/2);
    screen.text("Right",  keyRight.x, keyRight.y + keyRight.h/2);
    screen.text("Ability", keyAbility.x, keyAbility.y + keyAbility.h/2);
    
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawPaused() {
    //Draws the paused overlay
    drawUnpaused(engine.player);
    screen.beginDraw();
    //clearScreen();
    screen.fill(0, 100);
    screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
    menu.show(screen);
    options.show(screen);
    play.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawUnpaused(Player player) {

    //Draws the pause button during gameplay

    healthBar.updateBar(player.stats.getHealth(), player.stats.getHealthMax());
    manaBar.updateBar(player.stats.getMana(), player.stats.getManaMax());
    screen.beginDraw();
    clearScreen();
    screen.fill(217);
    screen.rect(0, 0, GUI_WIDTH, height);

    pause.show(screen);
    healthBar.show(screen);
    manaBar.show(screen);
    renderInv();
    renderMiniMap();
    drawCooldown();
    drawStats();
    screen.endDraw();
    image(screen, 0, 0);
    
    if(pointInBox(mouseX, mouseY, 0, 0, GUI_WIDTH, height)) {
      inMenu = true;
    } else {
      inMenu = false;
    }
    
  }
  
  public void drawDead() {
    
    screen.beginDraw();
    clearScreen();
    
    screen.image(title, 0, 0, width, height);
    
    play.show(screen);
    options.show(screen);
    exit.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
    
  }
  
  public void drawLoad() {
    
  }

  public void handleMouseReleased() {
    //------Main Buttons
    if (play.pressed() && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("PLAYING");
      engine.updateMillis();
    } else if (options.pressed() && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("OPTIONS");
    } else if (menu.pressed() && STATE == "PAUSED") {
      //SAVE GAME HERE!!!!
      //saveGame();
      setState("MENU");
    } else if (pause.pressed() && STATE == "PLAYING") {
      setState("PAUSED");
    } else if (exit.pressed() && STATE == "MENU") {
      quitGame();
    } else if (back.pressed() && (STATE == "OPTIONS")) {
      revertState();
    }
    
    //-----Settings Buttons
    else if(STATE == "OPTIONS") {
      if(keyUp.pressed())      { remapNextKey = true; remapAction = up; }
      if(keyDown.pressed())    { remapNextKey = true; remapAction = down; }
      if(keyLeft.pressed())    { remapNextKey = true; remapAction = left; }
      if(keyRight.pressed())   { remapNextKey = true; remapAction = right; }
      if(keyAbility.pressed()) { remapNextKey = true; remapAction = ability; } 
    }
  }
  
  private void drawCooldown(){
    if (engine.player.inv.currentAbility() != null ) {
      float percentFull = engine.player.inv.currentAbility().getPercentCooldown();
      screen.fill(0, 100);
      screen.noStroke();
      screen.arc(invBuff + invX + (invSize + invBuff) + itemOffset + SPRITE_SIZE * invScale/2, invBuff + invY + itemOffset + SPRITE_SIZE * invScale/2, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale, -PI/2, 2 * PI * percentFull - PI/2, PIE);
    }
  }
  
  private void drawStats() {
    String wisdom = "Wisdom: " + engine.player.stats.wisdom;
    String vitality = "Vitality: " + engine.player.stats.vitality;
    String defence = "Defence: " + engine.player.stats.defence;
    String attack = "Attack: " + engine.player.stats.attack;
    String speed = "Speed: " + engine.player.stats.speed;
    String stats = wisdom + " \t| " + vitality + "\n" + attack + " \t| " + defence + "\n" + speed;
    
    screen.textAlign(LEFT);
    screen.textSize(15);
    screen.fill(50);
    screen.text(stats, invBuff * 3, 5 * TILE_SIZE/2, GUI_WIDTH - invBuff * 2, 5 * TILE_SIZE);
    
  }



  private void renderMiniMap() {
    
    float w = engine.currentLevel.getMiniMap().width;
    float h = engine.currentLevel.getMiniMap().height;
    
    float scale = (GUI_WIDTH - (2 * invBuff))/w;
    
    screen.image(engine.currentLevel.getMiniMap(), invBuff, height - h * scale - invBuff, w * scale, h * scale);
    screen.image(engine.currentLevel.getOverlay(), invBuff, height - h * scale - invBuff, w * scale, h * scale);
  }

  private void renderInv() {
    
    prevSelection = currSelection;
    currSelection = mousePressed;
    
    ItemBag itemBag = engine.getClosestBag();
    Item[] items = engine.getClosestBagItems();
    
    drawBack(items != null, items);
    
    if (itemOver != -1 || b1 != -1) { 
      if (currSelection && !prevSelection) { 
        b1 = itemOver; 
        b1Type = menuType;
      }
      if (!currSelection && prevSelection) {
        b2 = itemOver; 
        b2Type = menuType;
        if (b1Type == active && b2Type == inv) { //----INV/ACTIVE
          engine.player.inv.swapItemsActive(b1, b2);
        } else if (b2Type == active && b1Type == inv) {
          engine.player.inv.swapItemsActive(b2, b1);
        } else if(b1Type == inv && b2Type == inv) {
          engine.player.inv.swapItemsInv(b1, b2);
        } else if(b1Type == inv && b2Type == bag) { //----INV/BAG
          Item bagItem = itemBag.takeItem(b2);
          itemBag.addItem(engine.player.inv.addItemInv(bagItem, b1));
        } else if(b1Type == bag && b2Type == inv) {
          Item bagItem = itemBag.takeItem(b1);
          itemBag.addItem(engine.player.inv.addItemInv(bagItem, b2));
        } else if(b1Type == active && b2Type == bag) { //-----ACTIVE/BAG
          Item bagItem = itemBag.takeItem(b2);
          itemBag.addItem(engine.player.inv.addItemActive(bagItem, b1));
        } else if(b1Type == bag && b2Type == active) {
          Item bagItem = itemBag.takeItem(b1);
          itemBag.addItem(engine.player.inv.addItemActive(bagItem, b2));
        } else if(b1Type == active && b2Type == out && !inMenu) { //----ACTIVE/GROUND
          if(itemBag == null || itemBag.isFull()) {
            ItemBag newBag = new ItemBag(engine.player.x, engine.player.y, 0);
            newBag.addItem(engine.player.inv.addItemActive(null, b1));
            engine.addDrop(newBag);
          } else {
            itemBag.addItem(engine.player.inv.addItemActive(null, b1));
          }
        } else if(b1Type == inv && b2Type == out && !inMenu) { //----INV/GROUND
          if(itemBag == null || itemBag.isFull()) {
            ItemBag newBag = new ItemBag(engine.player.x, engine.player.y, 0);
            newBag.addItem(engine.player.inv.addItemInv(null, b1));
            engine.addDrop(newBag);
          } else {
            itemBag.addItem(engine.player.inv.addItemInv(null, b1));
          }
        }
        b1 = -1;
        b2 = -1;
      }
    } else {
      if (!currSelection && prevSelection) {
        b1 = -1;
        b2 = -1;
      }
    }

    for (int i = 0; i < engine.player.active().length; i++) {
      if (engine.player.active()[i] != null) {
        screen.stroke(0);
        screen.strokeWeight(1);
        if (currSelection && b1Type == active && b1 == i) { 
          screen.image(itemSprites.get(engine.player.active()[i].sprite), mouseX - invSize/2+ itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        } else {
          screen.image(itemSprites.get(engine.player.active()[i].sprite), invBuff + invX + i * (invSize + invBuff) + itemOffset, invBuff + invY+ itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        }
      }
    }
    int j = 0;
    for (int i = 0; i < engine.player.inv().length; i++) {
      j = (int)(i/Inventory.WIDTH);
      if (engine.player.inv()[i] != null) {
        screen.stroke(0);
        screen.strokeWeight(1);
        if (currSelection && b1Type == inv && b1 == i) { 
          screen.image(itemSprites.get(engine.player.inv()[i].sprite), mouseX - invSize/2 + itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        } else {
          screen.image(itemSprites.get(engine.player.inv()[i].sprite),invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + itemOffset, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        }
      }
    }
    
    if(items != null) {
      for (int i = 0; i < items.length; i++) {
        if (items[i] != null) {
          screen.stroke(0);
          screen.strokeWeight(1);
          if (currSelection && b1Type == bag && b1 == i) { 
            screen.image(itemSprites.get(items[i].sprite), mouseX - invSize/2 + itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
            screen.noStroke();
          } else {
            screen.image(itemSprites.get(items[i].sprite), invBuff + invX + i * (invSize + invBuff) + itemOffset, 3 * invBuff + invY + 4 * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
            screen.noStroke();
          }
        }
      }
    }
    if (inMenu && itemOver != -1 && mouseOverItem != null) { 
      mouseOver(mouseX, mouseY, mouseOverItem);
    }
  }

  void drawBack(boolean showBag, Item[] items) {
    menuType = out;
    screen.fill(51);
    if(showBag) {
      screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, (Inventory.WIDTH + 1) * (invSize + invBuff) + invBuff * 3);
    } else {
      screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, Inventory.WIDTH * (invSize + invBuff) + invBuff * 2);
    }
    
    itemOver = -1;
    for (int i = 0; i < engine.player.active().length; i++) {
      screen.fill(150);
      screen.rect(invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize);
      if (pointInBox(mouseX, mouseY, invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
        itemOver = i;
        mouseOverItem = engine.player.active()[i];
        menuType = active;
      }
    }
    int j = 0;
    for (int i = 0; i < engine.player.inv().length; i++) {
      j = (int)(i/Inventory.WIDTH);
      screen.fill(150);
      screen.rect(invBuff + invX + (i%4) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize);
      if (pointInBox(mouseX, mouseY, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) { 
        itemOver = i;        
        mouseOverItem = engine.player.inv()[i];
        menuType = inv;
      }
    }
    if(showBag) {
      for (int i = 0; i < engine.player.active().length; i++) {
        screen.fill(150);
        screen.rect(invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize);
        if (pointInBox(mouseX, mouseY, invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize)) {
          itemOver = i;
          mouseOverItem = items[i];
          menuType = bag;
        }
      }
    }
  }

  void mouseOver(float x, float y, Item item) {
   
    String desc = "";
    String type = item.type;
    if (type == "Weapon") {
      desc += "Fire rate:" + ((Weapon)item).fireRate + "\n";
      desc += "Range:" + ((Weapon)item).range + "\n";
      desc += "Accuracy:" + ((Weapon)item).accuracy + "\n";
      desc += "Damage:" + ((Weapon)item).damage + "\n";
    } else if (type == "Ability") {
      screen.rect(x, y, 100, 110);
      desc += "Mana cost:" + ((Ability)item).manaCost + "\n";
      desc += "Cooldown:" + ((Ability)item).cooldown + "s\n";
    } else if (type == "Armour") {
      desc += "Defence:" + ((Armour)item).defence + "\n";
    } else if (type == "Scroll") {
      desc += "Does nothing" + "\n";
    }
    screen.textSize(15);
    screen.textAlign(LEFT);
    int mouseOverWidth = max((int)(screen.textWidth(item.name) + 20), 100), mouseOverHeight = 120;
    screen.fill(100);
    screen.rect(x, y, mouseOverWidth, mouseOverHeight);
    screen.fill(200);
    screen.text(item.name, x + 5, y + 5, mouseOverWidth - 10, mouseOverHeight);
    screen.textSize(12);
    screen.textLeading(12);
    screen.text(type, x + 5, y + 25, mouseOverWidth - 10, mouseOverHeight);
    screen.fill(255);
    screen.text(desc, x + 5, y + 55, mouseOverWidth - 10, mouseOverHeight);
  }
}

class HUDElement {

  public float x, y, w, h;
  public String spriteName;

  HUDElement(float x, float y, String spriteName) {
    this.x = x;
    this.y = y;
    this.w = guiSprites.get(spriteName).width * SCALE;
    this.h = guiSprites.get(spriteName).height * SCALE;
    this.spriteName = spriteName;
  }

  public void show(PGraphics screen) {
    PImage sprite = guiSprites.get(spriteName);
    screen.image(sprite, x, y, sprite.width * SCALE, sprite.height * SCALE);
  }
}

class Button extends HUDElement {


  Button(float x, float y, String spriteName) {
    super(x, y, spriteName);
  }

  public boolean pressed() {
    return pointInBox(mouseX, mouseY, x, y, w, h);
  }
}

class DisplayBar {

  private float x, y, w, h, percentFull;
  int current, total;
  private color c;
  private HUDElement element;

  DisplayBar(float x, float y, color c) {
    this.x = x;
    this.y = y;
    this.h = TILE_SIZE / 2;
    this.w = TILE_SIZE * 3 - 8;
    this.c = c;
    percentFull = 1.0;

    element = new HUDElement(x - TILE_SIZE/16, y, "BAR");
  }

  public void show(PGraphics screen) {
    screen.fill(c);
    screen.textSize(15);
    screen.textAlign(CENTER, CENTER);
    screen.noStroke();
    screen.rect(x, y, w * percentFull, h);
    screen.fill(255);
    screen.text(current + "/" + total, x + w/2, y + h/2);
    element.show(screen);
  }

  public void updateBar(float current, float total) {
    this.current = (int)current;
    this.total = (int)total;
    percentFull = current / total;
  }
}
