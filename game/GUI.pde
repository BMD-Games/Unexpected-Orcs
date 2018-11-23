class GUI {
  /**
   This class is used for drawing and handeling all UI related screens and elements  
   **/

  private Button play, back, options, menu, exit, pause;
  private DisplayBar healthBar, manaBar;
  private PImage title = loadImage("/assets/sprites/title.png");
  private PGraphics screen;
  private color c = 100;

  //Inventory drag and drop stuff
  private final int invBuff = 5, invScale = 2, invSize = SPRITE_SIZE * invScale + 2;
  private final int invX = (GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH + 1)))/2, invY = 7* TILE_SIZE/2;
  private boolean prevSelection = false, currSelection = false;
  private boolean b1Active = false, b2Active = false, menuType; // if inv box is in active or not
  private int b1 = -1, b2 = -1, itemOver; //inv box 1 and 2 for drag and swap

  GUI() {
    //need to set buttons and whatnot here
    play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
    options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 0, "OPTIONS");
    menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "MENU");
    back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "BACK");
    exit = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "EXIT");
    pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, "PAUSE");

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
    screen.image(title, 0, 0, width, height);
    //screen.background(c);
    play.show(screen);
    options.show(screen);
    exit.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawOptions() {
    //Draws the options menu
    screen.beginDraw();
    screen.background(c);
    back.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawPaused() {
    //Draws the paused overlay
    screen.beginDraw();
    clearScreen();
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
    screen.endDraw();
    image(screen, 0, 0);
    
    if(pointInBox(mouseX, mouseY, 0, 0, GUI_WIDTH, height)) {
      inMenu = true;
    } else {
      inMenu = false;
    }
    
  }

  public void handleMouseReleased() {
    //used for checking input
    if (play.pressed(mouseX, mouseY) && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("PLAYING");
      engine.updateMillis();
    } else if (options.pressed(mouseX, mouseY) && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("OPTIONS");
    } else if (menu.pressed(mouseX, mouseY) && STATE == "PAUSED") {
      //SAVE GAME HERE!!!!
      //saveGame();
      setState("MENU");
    } else if (pause.pressed(mouseX, mouseY) && STATE == "PLAYING") {
      setState("PAUSED");
    } else if (exit.pressed(mouseX, mouseY) && STATE == "MENU") {
      quitGame();
    } else if (back.pressed(mouseX, mouseY) && (STATE == "OPTIONS")) {
      revertState();
    }
  }

  void renderInv() {
    
    prevSelection = currSelection;
    currSelection = mousePressed;
    
    drawBack();
    
    if (itemOver != -1) { 
      if (currSelection && !prevSelection) { 
        b1 = itemOver; 
        b1Active = menuType;
      }
      if (!currSelection && prevSelection) {
        b2 = itemOver; 
        b2Active = menuType;
        if (b1Active && b2Active) {
          //doNothing
        } else if (b1Active) {
          engine.player.inv.swapItemsActive(b1, b2);
        } else if (b2Active) {
          engine.player.inv.swapItemsActive(b2, b1);
        } else {
          engine.player.inv.swapItemsInv(b1, b2);
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
        if (currSelection && b1Active && b1 == i) { 
          screen.image(itemSprites.get(engine.player.active()[i].sprite), mouseX - invSize/2 + 1, mouseY - invSize/2 + 1, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        } else {
          screen.image(itemSprites.get(engine.player.active()[i].sprite), invBuff + invX + i * (invSize + invBuff) + 1, invBuff + invY + 1, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
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
        if (currSelection && !b1Active && b1 == i) { 
          screen.image(itemSprites.get(engine.player.inv()[i].sprite), mouseX - invSize/2 + 1, mouseY - invSize/2 + 1, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        } else {
          screen.image(itemSprites.get(engine.player.inv()[i].sprite),invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + 1, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + 1, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          screen.noStroke();
        }
      }
    }
    if (inMenu && itemOver != -1 && ((menuType && engine.player.active()[itemOver] != null) || (!menuType && engine.player.inv()[itemOver] != null))) { 
      mouseOver(mouseX, mouseY, itemOver, menuType);
    }
  }

  void drawBack() {
    screen.fill(51);
    screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, Inventory.WIDTH * (invSize + invBuff) + invBuff * 2);

    itemOver = -1;
    for (int i = 0; i < engine.player.active().length; i++) {
      screen.fill(150);
      screen.rect(invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize);
      if (pointInBox(mouseX, mouseY, invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
        itemOver = i; 
        menuType = true;
      }
    }
    int j = 0;
    for (int i = 0; i < engine.player.inv().length; i++) {
      j = (int)(i/Inventory.WIDTH);
      screen.fill(150);
      screen.rect(invBuff + invX + (i%4) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize);
      if (pointInBox(mouseX, mouseY, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) { 
        itemOver = i; 
        menuType = false;
      }
    }
  }

  void mouseOver(float x, float y, int i, boolean act) {
    screen.textSize(12);
    screen.fill(100);
    if (act && engine.player.active()[i] != null) {
      String type = engine.player.active()[i].type;
      if (type == "Weapon") {
        screen.rect(x, y, 100, 127);
        screen.fill(255);
        screen.text("Fire rate:" + ((Weapon)engine.player.active()[i]).fireRate, x + 5, y + 54);
        screen.text("Range:" + ((Weapon)engine.player.active()[i]).range, x + 5, y + 71);
        screen.text("Accuracy:" + ((Weapon)engine.player.active()[i]).accuracy, x + 5, y + 88);
        screen.text("Damage:" + ((Weapon)engine.player.active()[i]).damage, x + 5, y + 105);
      } else if (type == "Special") {
        screen.rect(x, y, 100, 110);
        screen.fill(255);
        screen.text("Ability", x + 5, y + 54);
      } else if (type == "Armour") {
        screen.rect(x, y, 100, 59);
        screen.fill(255);
        screen.text("Defense:" + ((Armour)engine.player.active()[i]).defence, x + 5, y + 54);
      }
      screen.fill(200);
      screen.text(type, x + 5, y + 37);
      screen.textSize(15);
      screen.text(engine.player.active()[i].name, x + 5, y + 20);
    } else if (!act && engine.player.inv()[i] != null) {
      String type = engine.player.inv()[i].type;

      if (type == "Weapon") {
        screen.rect(x, y, 100, 127);
        screen.fill(255);
        screen.text("Fire rate:" + ((Weapon)engine.player.inv()[i]).fireRate, x + 5, y + 54);
        screen.text("Range:" + ((Weapon)engine.player.inv()[i]).range, x + 5, y + 71);
        screen.text("Accuracy:" + ((Weapon)engine.player.inv()[i]).accuracy, x + 5, y + 88);
        screen.text("Damage:" + ((Weapon)engine.player.inv()[i]).damage, x + 5, y + 105);
      } else if (type == "Ability") {
        screen.rect(x, y, 100, 110);
        screen.fill(255);
        screen.text("Ability", x + 5, y + 54);
      } else if (type == "Armour") {
        screen.rect(x, y, 100, 59);
        screen.fill(255);
        screen.text("Defense:" + ((Armour)engine.player.inv()[i]).defence, x + 5, y + 54);
      }
      screen.fill(200);
      screen.text(type, x + 5, y + 37);
      screen.textSize(15);
      screen.text(engine.player.inv()[i].name, x + 5, y + 20);
    }
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

  public boolean pressed(float mX, float mY) {
    return pointInBox(mX, mY, x, y, w, h);
  }
}

class DisplayBar {

  private float x, y, w, h, percentFull;
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
    screen.noStroke();
    screen.rect(x, y, w * percentFull, h);
    element.show(screen);
  }

  public void updateBar(float current, float total) {
    percentFull = current / total;
  }
}
