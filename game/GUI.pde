class GUI {
  /**
   This class is used for drawing and handeling all UI related screens and elements  
   **/

  private Button play, back, options, menu, exit, pause, newGame, load, save, save1, save2, save3;
  private Button keyUp, keyDown, keyLeft, keyRight, keyAbility;
  private DisplayBar healthBar, manaBar;
  private Button enterPortal;
  private PImage title = loadImage("/assets/sprites/title.png");
  private PGraphics screen;
  private color c = 100;
  private String playerName = "";
  
  public ScrollWindow loadScroll = new ScrollWindow(width/4, height/4, width/2, height/2, new ScrollElement[]{new ScrollElement("Nigga","nigga", 120), new ScrollElement("","",100), new ScrollElement("","",200)});

  //Stat sprites
  private PImage attackSprite = itemSprites.get("ATTACK_ICON");
  private PImage defenceSprite = itemSprites.get("DEFENCE_ICON");
  private PImage vitalitySprite = itemSprites.get("VITALITY_ICON");
  private PImage wisdomSprite = itemSprites.get("WISDOM_ICON");
  private PImage speedSprite = itemSprites.get("SPEED_ICON");

  //Inventory drag and drop stuff
  private final int invBuff = 5, invScale = 2, itemOffset = 1, invSize = SPRITE_SIZE * invScale + 2 * itemOffset;
  private final int invX = (GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH+ itemOffset)))/2, invY = 7 * TILE_SIZE/2;
  private boolean prevSelection = false, currSelection = false, showingPortal = false;
  private int b1Type, b2Type, menuType; // if inv box is in active or not
  private int b1 = -1, b2 = -1, itemOver, active = 0, inv = 1, bag = 2, out = 3;
  ; //inv box 1 and 2 for drag and swap
  private Item mouseOverItem;

  int buff = 6; //for mouseOver stuff;

  GUI() {
    //-----Main
    play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
    load = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 0, "LOAD");
    options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "OPTIONS");
    menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "MENU");    
    exit = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "EXIT");  
    back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");
    pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, "PAUSE");
    save = new Button(width/2 - TILE_SIZE, height/2, "SAVE");
    save1 = new Button(width/2 - TILE_SIZE * 3.5, height/2, "SAVE1");
    save2 = new Button(width/2 - TILE_SIZE * 0.5, height/2, "SAVE2");
    save3 = new Button(width/2 + TILE_SIZE * 2.5, height/2, "SAVE3");

    //newGame = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "NEW");



    //-----Settings
    keyUp = new Button(width/2, height/2 - TILE_SIZE * 4, "BLANK_1x1");
    keyDown = new Button(width/2, height/2 - TILE_SIZE * 3, "BLANK_1x1");
    keyLeft = new Button(width/2, height/2 - TILE_SIZE * 2, "BLANK_1x1");
    keyRight = new Button(width/2, height/2 - TILE_SIZE * 1, "BLANK_1x1");
    keyAbility = new Button(width/2, height/2 - TILE_SIZE * 0, "BLANK_1x1");

    //-----Gameplay
    healthBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5 + 4, TILE_SIZE/2 - invBuff, color(230, 100, 100));
    manaBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5 + 4, 2 * TILE_SIZE/2, color(153, 217, 234));
    enterPortal = new Button(GUI_WIDTH/2 - TILE_SIZE, 14 * TILE_SIZE/2, "BLANK_2x1");

    screen = createGraphics(width, height);
    screen.beginDraw();
    screen.noSmooth();
    screen.textFont(bitcell);
    screen.endDraw();
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
    if (!remapNextKey || remapAction != up) screen.text(getKeyString(up), keyUp.x + keyUp.w/2, keyUp.y + keyUp.h/2);
    if (!remapNextKey || remapAction != down) screen.text(getKeyString(down), keyDown.x + keyDown.w/2, keyDown.y + keyDown.h/2);
    if (!remapNextKey || remapAction != left) screen.text(getKeyString(left), keyLeft.x + keyLeft.w/2, keyLeft.y + keyLeft.h/2);
    if (!remapNextKey || remapAction != right) screen.text(getKeyString(right), keyRight.x + keyRight.w/2, keyRight.y + keyRight.h/2);
    if (!remapNextKey || remapAction != ability) screen.text(getKeyString(ability), keyAbility.x + keyAbility.w/2, keyAbility.y + keyAbility.h/2);
    screen.fill(255);
    screen.textAlign(RIGHT, CENTER);
    screen.text("Forward", keyUp.x, keyUp.y + keyUp.h/2);
    screen.text("Back", keyDown.x, keyDown.y + keyDown.h/2);
    screen.text("Left", keyLeft.x, keyLeft.y + keyLeft.h/2);
    screen.text("Right", keyRight.x, keyRight.y + keyRight.h/2);
    screen.text("Ability", keyAbility.x, keyAbility.y + keyAbility.h/2);

    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawPaused() {
    //Draws the paused overlay
    drawPlay(engine.player);
    screen.beginDraw();
    //clearScreen();
    screen.fill(0, 100);
    screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
    menu.show(screen);
    options.show(screen);
    play.show(screen);
    save.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawSave() {

    screen.beginDraw();
    screen.background(c);
    screen.fill(0, 100);
    screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
    back.show(screen);
    save1.show(screen);
    save2.show(screen);
    save3.show(screen);

    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawPlay(Player player) {

    //Draws the GUI during gameplay

    healthBar.updateBar(player.stats.health, player.stats.healthMax);
    manaBar.updateBar(player.stats.mana, player.stats.manaMax);
    screen.beginDraw();
    clearScreen();
    screen.fill(217);
    screen.rect(0, 0, GUI_WIDTH, height);

    pause.show(screen);
    screen.textAlign(CENTER);
    screen.fill(50, 50, 50);
    screen.textSize(TILE_SIZE / 2);
    screen.text(loadedPlayerName, GUI_WIDTH / 2, 20);
    healthBar.show(screen);
    manaBar.show(screen);
    showStatusEffects();
    drawQuest();
    renderMiniMap();
    drawPortal();
    drawStatProgress();
    renderInv();
    drawStats();
    drawCooldown();
    screen.endDraw();
    image(screen, 0, 0);

    if (Util.pointInBox(mouseX, mouseY, 0, 0, GUI_WIDTH, height)) {
      inMenu = true;
    } else {
      inMenu = false;
    }
  }

  public void drawLoading() {
    screen.beginDraw();
    clearScreen();
    screen.fill(0);
    screen.rect(0, 0, screen.width, screen.height);
    screen.fill(255);
    screen.textAlign(CENTER, CENTER);
    screen.text("Loading", width/2, height/2);
    screen.text(loadMessage, width/2, height/2 + TILE_SIZE);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawDead() {

    screen.beginDraw();
    clearScreen();

    screen.image(title, 0, 0, width, height);
    screen.fill(200, 0, 0);
    screen.text("Nibba u dead", width/2, height/2);
    back.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void drawLoad() {
    
    screen.beginDraw();
    image(title, 0, 0, width, height);
    loadScroll.show(screen);
    
    
    screen.endDraw();
    image(screen, 0, 0);
    
    /*
    screen.beginDraw();
    clearScreen();
    println("here");
    loadScroll.show(screen);
    screen.fill(255, 0, 0);
    screen.rect(100, 100, 100, 100);
    
    screen.endDraw();
    image(screen, 0, 0);
    */
    
  }

  public void drawNewGame() {
    screen.beginDraw();
    clearScreen();

    screen.image(title, 0, 0, width, height);
    characterNaming = true;
    back.show(screen);
    screen.fill(200, 200, 200);
    screen.rect(width/2 - TILE_SIZE * 2, height/2 - TILE_SIZE / 4 * 3, TILE_SIZE * 4, TILE_SIZE);
    screen.fill(50, 50, 50);
    screen.textSize(TILE_SIZE);
    screen.textAlign(CENTER);
    screen.text(playerName, width/2, height/2);
    screen.textSize(TILE_SIZE/2);
    if (checkFileAlreadyExists(playerName)) {
      screen.text("A hero with that name already exists.", width/2, height/2 + TILE_SIZE);
    }
    play.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }

  public void handleMouseReleased() {
    //------Main Buttons
    if (STATE == "MENU" && play.pressed()) {
      setState("NEWGAME");
    }
    if ((STATE == "MENU" || STATE == "PAUSED") && play.pressed()) {
      setState("PLAYING");
      engine.updateMillis();
    } else if ((STATE == "MENU" || STATE == "PAUSED") && options.pressed()) {
      setState("OPTIONS");
    } else if ((STATE == "MENU") && load.pressed()) {
      setState("LOAD");
    } else if (STATE == "PAUSED" && menu.pressed()) {
      //SAVE GAME HERE!!!!
      //saveGame();
      setState("MENU");
    } else if (STATE == "PLAYING" && pause.pressed()) {
      setState("PAUSED");
    } else if (STATE == "MENU" && exit.pressed()) {
      quitGame();
    } else if ((STATE == "OPTIONS") && back.pressed()) {
      revertState();
    } else if (STATE == "PLAYING" && showingPortal && enterPortal.pressed()) {
      engine.enterClosestPortal();
    } else if (STATE == "DEAD" && back.pressed()) {
      setState("MENU");
      //Add reset function;
    } else if (STATE == "PAUSED" && save.pressed()) {
      setState("SAVE");
    } else if (STATE == "SAVE" && back.pressed()) {
      setState("PAUSED");
    } else if (STATE == "SAVE" && save1.pressed()) {
      saveStats(loadedPlayerName);
      setState("PAUSED");
    } else if (STATE == "NEWGAME" && back.pressed()) {
      setState("MENU"); 
      playerName = "";
    } else if (STATE == "NEWGAME" && play.pressed()) {
      if (playerName.length() > 0 && !checkFileAlreadyExists(playerName)) {
        loadedPlayerName = playerName;
        setState("PLAYING");
      }
    }
    //-----Settings Buttons
    else if (STATE == "OPTIONS") {
      if (keyUp.pressed()) { 
        remapNextKey = true; 
        remapAction = up;
      }
      if (keyDown.pressed()) { 
        remapNextKey = true; 
        remapAction = down;
      }
      if (keyLeft.pressed()) { 
        remapNextKey = true; 
        remapAction = left;
      }
      if (keyRight.pressed()) { 
        remapNextKey = true; 
        remapAction = right;
      }
      if (keyAbility.pressed()) { 
        remapNextKey = true; 
        remapAction = ability;
      }
    }
  }

  private void drawCooldown() {

    if (engine.player.inv.currentAbility() != null ) {
      float percentFull = engine.player.getPercentCooldown();
      screen.fill(0, 100);
      screen.noStroke();
      screen.arc(invBuff + invX + (invSize + invBuff) + itemOffset + SPRITE_SIZE * invScale/2, invBuff + invY + itemOffset + SPRITE_SIZE * invScale/2, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale, -PI/2, 2 * PI * percentFull - PI/2, PIE);
    }
  }

  private void drawStatProgress() {

    float attackFloat = engine.player.stats.calcStatValue(engine.player.stats.attackKills, engine.player.stats.baseAttack, 1, 0.1);
    attackFloat = attackFloat % 1;
    screen.fill(150, 150, 150);
    screen.rect(GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2, 10, SPRITE_SIZE * 2);
    screen.fill(statColours.get("ATTACK"));
    screen.rect(GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2 + SPRITE_SIZE * 2, 10, - SPRITE_SIZE * 2 * attackFloat);

    float defenceFloat = engine.player.stats.calcStatValue(engine.player.stats.defenceKills, engine.player.stats.baseDefence, 1, 0.1);
    defenceFloat = defenceFloat % 1;
    screen.fill(150, 150, 150);
    screen.rect(GUI_WIDTH * 4 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2, 10, SPRITE_SIZE * 2);
    screen.fill(statColours.get("DEFENCE"));
    screen.rect(GUI_WIDTH * 4 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2 + SPRITE_SIZE * 2, 10, - SPRITE_SIZE * 2 * defenceFloat);

    float vitalityFloat = engine.player.stats.calcStatValue(engine.player.stats.vitalityKills, engine.player.stats.baseVitality, 1, 0.1);
    vitalityFloat = vitalityFloat % 1;
    screen.fill(150, 150, 150);
    screen.rect(GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 79 + TILE_SIZE, 10, SPRITE_SIZE * 2);
    screen.fill(statColours.get("VITALITY"));
    screen.rect(GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 79 + TILE_SIZE + SPRITE_SIZE * 2, 10, - SPRITE_SIZE * 2 * vitalityFloat);

    float wisdomFloat = engine.player.stats.calcStatValue(engine.player.stats.wisdomKills, engine.player.stats.baseWisdom, 1, 0.1);
    wisdomFloat = wisdomFloat % 1;
    screen.fill(150, 150, 150);
    screen.rect(GUI_WIDTH * 4 / 5 - TILE_SIZE * 9 / 8, 79 + TILE_SIZE, 10, SPRITE_SIZE * 2);
    screen.fill(statColours.get("WISDOM"));
    screen.rect(GUI_WIDTH * 4 / 5 - TILE_SIZE * 9 / 8, 79 + TILE_SIZE + SPRITE_SIZE * 2, 10, - SPRITE_SIZE * 2 * wisdomFloat);
  }


  private void drawStats() {
    screen.pushMatrix();

    screen.textAlign(LEFT);
    screen.textSize(TILE_SIZE/2);
    screen.fill(30);

    //Draw stat values
    screen.text(engine.player.stats.attack, GUI_WIDTH * 2 / 5 - TILE_SIZE / 8, 100 + TILE_SIZE / 2);
    screen.text(engine.player.stats.defence, GUI_WIDTH * 4 / 5 - TILE_SIZE / 8, 100 + TILE_SIZE / 2);
    screen.text(engine.player.stats.vitality, GUI_WIDTH * 2 / 5 - TILE_SIZE / 8, 106 + TILE_SIZE);
    screen.text(engine.player.stats.wisdom, GUI_WIDTH * 4 / 5 - TILE_SIZE / 8, 106 + TILE_SIZE);
    screen.text((int)(engine.player.stats.speed * 100), GUI_WIDTH * 2 / 5 - TILE_SIZE / 8, 112 + TILE_SIZE * 3 / 2);

    //Draw stat sprites
    screen.image(attackSprite, GUI_WIDTH / 5 - TILE_SIZE / 8, 104, attackSprite.width * 2, attackSprite.height * 2);
    screen.image(defenceSprite, GUI_WIDTH * 3 / 5 - TILE_SIZE / 8, 104, defenceSprite.width * 2, defenceSprite.height * 2);
    screen.image(vitalitySprite, GUI_WIDTH / 5 - TILE_SIZE / 8, 112 + TILE_SIZE / 2, vitalitySprite.width * 2, vitalitySprite.height * 2);
    screen.image(wisdomSprite, GUI_WIDTH * 3 / 5 - TILE_SIZE / 8, 112 + TILE_SIZE / 2, wisdomSprite.width * 2, wisdomSprite.height * 2);
    screen.image(speedSprite, GUI_WIDTH / 5 - TILE_SIZE / 8, 120 + TILE_SIZE, speedSprite.width * 2, speedSprite.height * 2);
    screen.popMatrix();

    mouseOverStat();
  }

  private void drawPortal() {
    Portal portal = engine.getClosestPortal();
    if (portal == null) {
      showingPortal = false;
      return;
    }
    showingPortal = true;
    enterPortal.show(screen);
    screen.textAlign(CENTER, CENTER);
    screen.text("Enter " + portal.name, enterPortal.x, enterPortal.y, enterPortal.w, enterPortal.h);
  }

  private void renderMiniMap() {

    float vw = GUI_WIDTH - (2 * invBuff); //width of the view
    float vh = vw * 0.8;

    int minScale = max(ceil(vw/engine.currentLevel.w), ceil(vh/engine.currentLevel.h));
    int scale = max((int)((vw/engine.currentLevel.w) * miniMapZoom), minScale);

    int sx = (int)((engine.player.x * scale) - vw/2); //get the x-cord to start 
    int sy = (int)((engine.player.y * scale) - vh/2); //get the y-cord to start

    //when you get close to the edges - stop centering on the player
    if (sx < 0) sx = 0;
    if (sy < 0) sy = 0;
    if (sx > (engine.currentLevel.w * scale) - vw) sx = (int)((engine.currentLevel.w * scale) - vw);
    if (sy > (engine.currentLevel.h * scale) - vh) sy = (int)((engine.currentLevel.h * scale) - vh);

    PImage map = scaleImage(engine.currentLevel.getMiniMap().get(), (int)scale);
    PImage over = scaleImage(engine.currentLevel.getOverlay().get(), (int)scale);

    screen.fill(150);
    screen.rect(0, height - vh - invBuff * 2, vw + invBuff * 2, vh + invBuff * 2);
    screen.fill(0);
    screen.rect(invBuff, height - vh - invBuff, vw, vh);
    screen.image(map.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
    screen.image(over.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
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
        } else if (b1Type == inv && b2Type == inv) {
          engine.player.inv.swapItemsInv(b1, b2);
        } else if (b1Type == inv && b2Type == bag) { //----INV/BAG
          Item bagItem = itemBag.takeItem(b2);
          itemBag.addItem(engine.player.inv.addItemInv(bagItem, b1));
        } else if (b1Type == bag && b2Type == inv) {
          Item bagItem = itemBag.takeItem(b1);
          itemBag.addItem(engine.player.inv.addItemInv(bagItem, b2));
        } else if (b1Type == active && b2Type == bag) { //-----ACTIVE/BAG
          Item bagItem = itemBag.takeItem(b2);
          itemBag.addItem(engine.player.inv.addItemActive(bagItem, b1));
        } else if (b1Type == bag && b2Type == active) {
          Item bagItem = itemBag.takeItem(b1);
          itemBag.addItem(engine.player.inv.addItemActive(bagItem, b2));
        } else if (b1Type == active && b2Type == out && !inMenu) { //----ACTIVE/GROUND
          if (itemBag == null || itemBag.isFull()) {
            ItemBag newBag = new ItemBag(engine.player.x, engine.player.y, 0);
            newBag.addItem(engine.player.inv.addItemActive(null, b1));
            engine.addDrop(newBag);
          } else {
            itemBag.addItem(engine.player.inv.addItemActive(null, b1));
          }
        } else if (b1Type == inv && b2Type == out && !inMenu) { //----INV/GROUND
          if (itemBag == null || itemBag.isFull()) {
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
        if (currSelection && b1Type == active && b1 == i) { 
          screen.image(engine.player.active()[i].sprite, mouseX - invSize/2+ itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
        } else {
          screen.image(engine.player.active()[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, invBuff + invY+ itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
        }
      }
    }
    int j = 0;
    for (int i = 0; i < engine.player.inv().length; i++) {
      j = (int)(i/Inventory.WIDTH);
      if (engine.player.inv()[i] != null) {
        if (currSelection && b1Type == inv && b1 == i) { 
          screen.image(engine.player.inv()[i].sprite, mouseX - invSize/2 + itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
        } else {
          screen.image(engine.player.inv()[i].sprite, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + itemOffset, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
        }
      }
    }

    if (items != null) {
      for (int i = 0; i < items.length; i++) {
        if (items[i] != null) {
          if (currSelection && b1Type == bag && b1 == i) { 
            screen.image(items[i].sprite, mouseX - invSize/2 + itemOffset, mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
          } else {
            screen.image(items[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, 3 * invBuff + invY + 4 * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
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
    if (showBag) {
      screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, (Inventory.WIDTH + 1) * (invSize + invBuff) + invBuff * 3);
    } else {
      screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, Inventory.WIDTH * (invSize + invBuff) + invBuff * 2);
    }

    itemOver = -1;
    for (int i = 0; i < engine.player.active().length; i++) {
      screen.fill(150);
      screen.rect(invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize);
      if (Util.pointInBox(mouseX, mouseY, invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
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
      if (Util.pointInBox(mouseX, mouseY, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) { 
        itemOver = i;        
        mouseOverItem = engine.player.inv()[i];
        menuType = inv;
      }
    }
    if (showBag) {
      for (int i = 0; i < engine.player.active().length; i++) {
        screen.fill(150);
        screen.rect(invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize);
        if (Util.pointInBox(mouseX, mouseY, invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize)) {
          itemOver = i;
          mouseOverItem = items[i];
          menuType = bag;
        }
      }
    }
  }

  private void mouseOver(float x, float y, Item item) {

    String desc = "";
    String type = item.type;
    if (type == "Weapon") {
      int fireRate = (int)(60 / ((Weapon)item).fireRate);
      desc += "Fire rate: " + fireRate + "\n";
      desc += "Range: " + ((Weapon)item).range + "\n";
      float accuracy = 1 - ((Weapon)item).accuracy;
      desc += "Accuracy: " + accuracy + "\n";
      desc += "Damage: " + ((Weapon)item).damage + "\n";
    } else if (type == "Ability") {
      desc += "Mana cost: " + ((Ability)item).manaCost + "\n";
      desc += "Cooldown: " + ((Ability)item).cooldown + "s\n";
    } else if (type == "Armour") {
      desc += "Defence: " + ((Armour)item).defence + "\n";
    } else if (type == "Scroll") {
      desc += ((Scroll)item).description;
    }
    int mouseOverWidth = 3 * GUI_WIDTH/4;
    WrappedText title = wrapText(item.name, mouseOverWidth - buff * 4, TILE_SIZE/2);
    WrappedText subtitle = wrapText("Tier " + item.tier + " " + type, mouseOverWidth - buff * 4, TILE_SIZE/3);
    WrappedText description = wrapText(desc, mouseOverWidth - buff * 4, TILE_SIZE/3);

    drawMouseOverText(x, y, title, subtitle, description);
  }

  private void mouseOverStat() {

    int x = mouseX;
    int y = mouseY;

    String statName = "";
    String type = "";
    String desc = "";

    if (Util.pointInBox(x, y, GUI_WIDTH / 5 - TILE_SIZE / 8, 104, TILE_SIZE / 2, TILE_SIZE / 2)) { // attack sprite hover
      statName = "Attack";
      type = String.valueOf(engine.player.stats.getAttack());
      desc = "Increases Damage dealt by player projectiles";
    } else if (Util.pointInBox(x, y, GUI_WIDTH * 3 / 5 - TILE_SIZE / 8, 104, TILE_SIZE / 2, TILE_SIZE / 2)) { // defence sprite hover
      statName = "Defence";
      type = String.valueOf(engine.player.stats.getDefence());
      desc = "Decreases damage taken from enemies";
    } else if (Util.pointInBox(x, y, GUI_WIDTH / 5 - TILE_SIZE / 8, 112 + TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2)) { // vitality hover
      statName = "Vitality";
      type = String.valueOf(engine.player.stats.getVitality());
      desc = "Increases health regeneration rate";
    } else if (Util.pointInBox(x, y, GUI_WIDTH * 3 / 5 - TILE_SIZE / 8, 112 + TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2)) { // wisdom hover
      statName = "Wisdom";
      type = String.valueOf(engine.player.stats.getVitality());
      desc = "Increases mana regeneration rate";
    } else if (Util.pointInBox(x, y, GUI_WIDTH / 5 - TILE_SIZE / 8, 120 + TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2)) { // speed hover
      statName = "Speed";
      type = String.valueOf((int)(engine.player.stats.speed * 100));
      desc = "Increases player speed";
    }

    if (!statName.equals("")) {

      int mouseOverWidth = 3 * GUI_WIDTH/4;
      WrappedText title = wrapText(statName, mouseOverWidth - buff * 4, TILE_SIZE/2);
      WrappedText subtitle = wrapText(type, mouseOverWidth - buff * 4, TILE_SIZE/3);
      WrappedText description = wrapText(desc, mouseOverWidth - buff * 4, TILE_SIZE/3);

      drawMouseOverText(x, y, title, subtitle, description);
    }
  }

  private void drawMouseOverText(float x, float y, WrappedText title, WrappedText subtitle, WrappedText description) {
    int mouseOverWidth = 3 * GUI_WIDTH/4;
    int mouseOverHeight = title.textHeight + subtitle.textHeight + description.textHeight + (buff * 5);

    if (x + mouseOverWidth > screen.width) x = screen.width - mouseOverWidth;
    if (y + mouseOverHeight > screen.height) y = screen.height - mouseOverHeight;

    screen.textAlign(LEFT, TOP);

    screen.fill(100);
    screen.rect(x, y, mouseOverWidth, mouseOverHeight);
    screen.noFill();
    screen.stroke(130);
    screen.rect(x + buff, y + buff, mouseOverWidth - buff * 2, mouseOverHeight - buff * 2);
    screen.line(x + buff, y + title.textHeight + buff/2, x + mouseOverWidth - buff, y + title.textHeight + buff/2);
    screen.line(x + buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2, x + mouseOverWidth - buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2);

    screen.fill(210);
    screen.textSize(title.textSize);
    screen.textLeading(title.textSize);
    screen.text(title.string, x + buff * 2, y + buff);

    screen.textSize(subtitle.textSize);
    screen.textLeading(subtitle.textSize);
    screen.text(subtitle.string, x + buff * 2, y + title.textHeight + (buff * 2));

    screen.fill(255);
    screen.textSize(description.textSize);
    screen.textLeading(description.textSize);
    screen.text(description.string, x + buff * 2, (y + mouseOverHeight) - description.textHeight - buff);
  }

  private void showStatusEffects() {
    int i = 0;
    String mouseOverEffect = "";
    for (String effect : engine.player.stats.statusEffects.keySet()) {
      i++;
      screen.image(playerStatusSprites.get(effect), screen.width - i * TILE_SIZE, screen.height - TILE_SIZE, TILE_SIZE, TILE_SIZE);
      if (Util.pointInBox(mouseX, mouseY, screen.width - i * TILE_SIZE, screen.height - TILE_SIZE, TILE_SIZE, TILE_SIZE)) {
        mouseOverEffect = effect;
      }
    }

    if (!mouseOverEffect.equals("")) {
      int mouseOverWidth = 3 * GUI_WIDTH/4;
      WrappedText title = wrapText(mouseOverEffect, mouseOverWidth - buff * 4, TILE_SIZE/2);
      WrappedText subtitle = wrapText(Util.roundTo(engine.player.stats.statusEffects.get(mouseOverEffect), 10) + "s remaining", mouseOverWidth - buff * 4, TILE_SIZE/2);
      WrappedText description = wrapText("", mouseOverWidth - buff * 4, 0);
      drawMouseOverText(mouseX, mouseY, title, subtitle, description);
    }
  }

  private void drawQuest() {
    float x = (width - GUI_WIDTH)/2 + GUI_WIDTH;
    float y = height/2;
    float r = min(x, y) - TILE_SIZE/2;
    PImage sprite = null;
    for (Enemy boss : engine.currentLevel.bosses) {
      float bx = ((StandardEnemy)boss).x;
      float by = ((StandardEnemy)boss).y;
      if (engine.currentLevel.visited((int)bx, (int)by) && dist(bx, by, engine.player.x, engine.player.y) < min(x, y)/TILE_SIZE) continue;
      float ang = atan2(by - engine.player.y, bx - engine.player.x);
      float dx = x + cos(ang) * r;
      float dy = y + sin(ang) * r;
      screen.pushMatrix();
      screen.translate(dx, dy);
      screen.rotate(ang);
      screen.image(guiSprites.get("QUEST"), -TILE_SIZE/4, -TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
      screen.popMatrix();
      if (dist(mouseX, mouseY, dx, dy) < TILE_SIZE/2) {
        sprite = ((StandardEnemy)boss).sprite;
      }
    }
    if (sprite != null) {
      drawMouseOverSprite(mouseX, mouseY, sprite);
    }
  }

  private void keyPressed(char key) {
    if (key == ENTER || key == RETURN && playerName.length() > 0) {
      if (checkFileAlreadyExists(playerName)) {
        screen.text("That name already exists", width/2, height/2 - TILE_SIZE);
      } else {
        loadedPlayerName = playerName;
        setState("PLAYING");
        characterNaming = false;
      }
    } else if (Character.isLetter(key) && playerName.length() < 10) {
      playerName = playerName + key;
    } else if (key == BACKSPACE) {
      playerName = playerName.substring(0, playerName.length() - 1);
    }
  }

  private Boolean checkFileAlreadyExists(String fileName) {

    java.io.File folder = new java.io.File(sketchPath() + "/saves/");
    String[] listOfFiles = folder.list();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].equals(fileName + ".txt")) {
        return true;
      }
    }   
    return false;
  }

  private void drawMouseOverSprite(float x, float y, PImage sprite) {
    int mouseOverSize = TILE_SIZE + buff * 4;

    if (x + mouseOverSize > screen.width) x = screen.width - mouseOverSize;
    if (y + mouseOverSize > screen.height) y = screen.height - mouseOverSize;

    screen.fill(100);
    screen.rect(x, y, mouseOverSize, mouseOverSize);
    screen.noFill();
    screen.stroke(130);
    screen.rect(x + buff, y + buff, mouseOverSize - buff * 2, mouseOverSize - buff * 2);
    screen.image(sprite, x + buff * 2, y + buff * 2, TILE_SIZE, TILE_SIZE);
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
    return Util.pointInBox(mouseX, mouseY, x, y, w, h);
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
    screen.textSize(TILE_SIZE/2);
    screen.textAlign(CENTER, CENTER);
    screen.noStroke();
    screen.rect(x, y, w * percentFull, h);
    screen.fill(255);
    screen.text(current + "/" + total, x + w/2, y + h/2 - 5);
    element.show(screen);
  }

  public void updateBar(float current, float total) {
    this.current = (int)current;
    this.total = (int)total;
    percentFull = current / total;
  }
}

class WrappedText {

  public String string;
  public int lines;
  public int textSize;
  public int textHeight;

  WrappedText(String string, int lines, int textSize) {
    this.string = string;
    this.lines = lines;
    this.textSize = textSize;
    this.textHeight = lines * textSize;
  }
}

/*Manually wrap text that goes over a certain width
 returns the number of lines it will take.
 */
public WrappedText wrapText(String string, float w, int textSize) {
  if (textSize == 0) {
    return new WrappedText("", 0, 1);
  }
  textSize(textSize);
  float charSize = textWidth("A"); //get width of a single character
  String newString = "";
  int numLines = 1;
  int lastSpace = 0;
  int lastNewLine = 0;

  for (int i = 0; i < string.length(); i ++) {
    if (string.charAt(i) == '\n') {
      newString += string.substring(lastNewLine, i + 1);
      numLines += 1;
      lastNewLine = i + 1;
    } else if (string.charAt(i) == ' ') {
      lastSpace = i;
    } else if (i == string.length() - 1) {
      newString += string.substring(lastNewLine, i + 1);
    } else if ((i - lastNewLine) * charSize >= w) {
      if (lastSpace > lastNewLine) {
        newString += string.substring(lastNewLine, lastSpace);
        lastNewLine = lastSpace + 1;
      } else {
        newString += string.substring(lastNewLine, i + 1);
        lastNewLine = i + 1;
      }
      newString += '\n';
      numLines += 1;
    }
  }

  return new WrappedText(newString, numLines, textSize);
}

class ScrollWindow {
  
  private int x, y, w, h;
  public int scrollPosition = 0;
  public int maxScrollPosition;
  private int buffer = 10;
  private ScrollElement[] scrollElements;
  
  
  public ScrollWindow(int x, int y, int w, int h, ScrollElement[] scrollElements) {
     this.x = x;
     this.y = y;
     this.w = w;
     this.h = h;
     this.scrollElements = scrollElements;
     
     calcMaxHeight();
  }
  
  
  private void calcMaxHeight() {
    
    int maxHeight = 0;
    for(ScrollElement scrollElement : scrollElements) {
      maxHeight += scrollElement.h + buffer;
    }
    this.maxScrollPosition = maxHeight - this.h + buffer;
  }
  
  
  
  public void show(PGraphics screen) {
    
    int currentHeight = buffer;
    
    
    
    for(ScrollElement scrollElement : scrollElements) {
      if(currentHeight + scrollElement.h >= scrollPosition || currentHeight <= scrollPosition) {
        scrollElement.show(screen, x + buffer, y + currentHeight - scrollPosition, w - 2 * buffer);
      }
      currentHeight += scrollElement.h + buffer;
    }
    
    screen.noStroke();
    screen.fill(0,0);
    screen.blendMode(REPLACE);
    screen.rect(0, 0, screen.width, y + buffer);
    screen.rect(0, 0, x, screen.height);
    screen.rect(0, y + h - buffer, screen.width, screen.height - y - h);
    screen.rect(x + w, 0, screen.width - x - w, screen.height);
    screen.blendMode(BLEND);
    screen.noFill();
    screen.stroke(255);
    screen.rect(x, y, w, h);
  }
  
  
}

class ScrollElement {
  
  String title, text;
  int h;
  
  public ScrollElement(String title, String text, int h) {
    this.title = title;
    this.text = text;
    this.h = h;
  }
  
  public void show(PGraphics screen, int xpos, int ypos, int w) {
    
    println("niggadrawn");
    screen.fill(255, 0 , 0);
    screen.rect(xpos, ypos, w, h);
    
  }
   
}
