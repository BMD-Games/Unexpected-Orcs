package GUI;

import Enemies.Enemy;
import Enemies.StandardEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.Portal;
import Entities.Player;
import File.GameFile;
import Items.*;
import Utility.Util;
import processing.core.PGraphics;
import processing.core.PImage;

import static Settings.Settings.*;
import static Sprites.Sprites.*;
import static Utility.Colour.*;
import static Utility.Constants.*;

public class GUI {
    /**
     This class is used for drawing and handeling all UI related screens and elements
     **/

    private Button play, back, options, menu, exit, pause, newGame, load, save, play2, deleteSave;
    private Button keyUp, keyDown, keyLeft, keyRight, keyAbility;
    private DisplayBar healthBar, manaBar;
    private Button enterPortal;
    private PImage title = game.loadImage("/assets/sprites/title.png");
    public PGraphics screen;
    private int c = 100;
    private String playerName = "";

    public ScrollWindow loadScroll = new ScrollWindow(width/8, height/4, width/4 * 3, height/2, GameFile.loadSaves());

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

    public int buff = 6; //for mouseOver stuff;

    public GUI() {
        //-----Main
        play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
        load = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 0, "LOAD");
        options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "OPTIONS");
        menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "MENU");
        exit = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "EXIT");
        back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");
        pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, "PAUSE");
        save = new Button(width/2 - TILE_SIZE, height/2, "SAVE");
        play2 = new Button(width/4 * 3, height/2 + TILE_SIZE * 3, "PLAY");
        deleteSave = new Button(width/2 - TILE_SIZE * 0.5f, height/2, "SAVE2");

        //newGame = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "NEW");



        //-----Settings
        keyUp = new Button(width/2, height/2 - TILE_SIZE * 4, "BLANK_1x1");
        keyDown = new Button(width/2, height/2 - TILE_SIZE * 3, "BLANK_1x1");
        keyLeft = new Button(width/2, height/2 - TILE_SIZE * 2, "BLANK_1x1");
        keyRight = new Button(width/2, height/2 - TILE_SIZE * 1, "BLANK_1x1");
        keyAbility = new Button(width/2, height/2 - TILE_SIZE * 0, "BLANK_1x1");

        //-----Gameplay
        healthBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, TILE_SIZE/2 - invBuff, colour(230, 100, 100));
        manaBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, 2 * TILE_SIZE/2, colour(153, 217, 234));
        enterPortal = new Button(GUI_WIDTH/2 - TILE_SIZE, 14 * TILE_SIZE/2, "BLANK_2x1");

        screen = game.createGraphics(width, height);
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
        game.image(screen, 0, 0);
    }

    public void drawOptions() {
        //Draws the options menu
        screen.beginDraw();
        screen.textAlign(game.CENTER, game.CENTER);
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
        screen.textAlign(game.RIGHT, game.CENTER);
        screen.text("Forward", keyUp.x, keyUp.y + keyUp.h/2);
        screen.text("Back", keyDown.x, keyDown.y + keyDown.h/2);
        screen.text("Left", keyLeft.x, keyLeft.y + keyLeft.h/2);
        screen.text("Right", keyRight.x, keyRight.y + keyRight.h/2);
        screen.text("Ability", keyAbility.x, keyAbility.y + keyAbility.h/2);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public void drawPaused() {
        //Draws the paused overlay
        drawPlay();
        screen.beginDraw();
        //clearScreen();
        screen.fill(0, 100);
        screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
        menu.show(screen);
        options.show(screen);
        play.show(screen);
        save.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public void drawSave() {

        screen.beginDraw();
        screen.background(c);
        screen.fill(0, 100);
        screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
        back.show(screen);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public void drawPlay() {
        //Draws the GUI during gameplay

        healthBar.updateBar(engine.player.stats.health, engine.player.stats.healthMax);
        manaBar.updateBar(engine.player.stats.mana, engine.player.stats.manaMax);
        screen.beginDraw();
        clearScreen();
        screen.fill(217);
        screen.rect(0, 0, GUI_WIDTH, height);

        pause.show(screen);
        screen.textAlign(game.CENTER);
        screen.fill(50, 50, 50);
        screen.textSize(TILE_SIZE / 2);
        screen.text(loadedPlayerName, GUI_WIDTH / 2, 20);
        healthBar.show(screen);
        manaBar.show(screen);
        showStatusEffects();
        drawQuest();
        renderMiniMap();
        drawPortal();
        renderInv();
        engine.player.stats.show(screen, GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2);

        drawCooldown();
        screen.endDraw();
        game.image(screen, 0, 0);

        if (Util.pointInBox(game.mouseX, game.mouseY, 0, 0, GUI_WIDTH, height)) {
            inMenu = true;
        } else {
            inMenu = false;
        }
    }

    public void drawDead() {

        screen.beginDraw();
        clearScreen();

        screen.image(title, 0, 0, width, height);
        screen.fill(200, 0, 0);
        screen.text("Nibba u dead", width/2, height/2);
        back.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public void drawLoad() {

        screen.beginDraw();
        clearScreen();
        game.image(title, 0, 0, width, height);
        loadScroll.update();
        loadScroll.show(screen);
        back.show(screen);
        play2.show(screen);

        screen.endDraw();
        game.image(screen, 0, 0);

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
        screen.textAlign(game.CENTER);
        screen.text(playerName, width/2, height/2);
        screen.textSize(TILE_SIZE/2);
        if (checkFileAlreadyExists(playerName)) {
            screen.text("A hero with that name already exists.", width/2, height/2 + TILE_SIZE);
        }
        play.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public void handleMouseReleased() {
        //------Main Buttons
        if (game.STATE == "MENU" && play.pressed()) {
            game.setState("NEWGAME");
        }
        if ((game.STATE == "MENU" || game.STATE == "PAUSED") && play.pressed()) {
            game.setState("PLAYING");
            engine.updateMillis();
        } else if ((game.STATE == "MENU" || game.STATE == "PAUSED") && options.pressed()) {
            game.setState("OPTIONS");
        } else if ((game.STATE == "MENU") && load.pressed()) {
            game.setState("LOAD");
        } else if (game.STATE == "PAUSED" && menu.pressed()) {
            //SAVE GAME HERE!!!!
            //saveGame();
            game.setState("MENU");
        } else if (game.STATE == "PLAYING" && pause.pressed()) {
            game.setState("PAUSED");
        } else if (game.STATE == "MENU" && exit.pressed()) {
            game.quitGame();
        } else if ((game.STATE == "OPTIONS" || game.STATE == "LOAD") && back.pressed()) {
            game.revertState();
        } else if (game.STATE == "PLAYING" && showingPortal && enterPortal.pressed()) {
            engine.enterClosestPortal();
        } else if (game.STATE == "DEAD" && back.pressed()) {
            game.setState("MENU");
            //Add reset function;
        } else if (game.STATE == "PAUSED" && save.pressed()) {
            game.setState("SAVE");
        } else if (game.STATE == "SAVE" && back.pressed()) {
            game.setState("PAUSED");
        } else if (game.STATE == "LOAD" && play2.pressed()) {
            if (loadScroll.selectedElement != -1) {
                engine.player = loadedPlayers[loadScroll.selectedElement];
                loadedPlayerName = loadScroll.scrollElements[loadScroll.selectedElement].title;
                game.setState("PLAYING");
            }
        } else if (game.STATE == "NEWGAME" && back.pressed()) {
            game.setState("MENU");
            playerName = "";
        } else if (game.STATE == "NEWGAME" && play.pressed()) {
            if (playerName.length() > 0 && !checkFileAlreadyExists(playerName)) {
                loadedPlayerName = playerName;
                GameFile.saveGame();
                game.setState("PLAYING");
            }
        }
        //-----Settings Buttons
        else if (game.STATE == "OPTIONS") {
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

        if (game.STATE == "LOAD") {
            loadScroll.handleMouse();
        }
    }



    private void drawCooldown() {

        if (engine.player.inv.currentAbility() != null ) {
            float percentFull = engine.player.getPercentCooldown();
            screen.fill(0, 100);
            screen.noStroke();
            screen.arc(invBuff + invX + (invSize + invBuff) + itemOffset + SPRITE_SIZE * invScale/2, invBuff + invY + itemOffset + SPRITE_SIZE * invScale/2, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale, -game.PI/2, 2 * game.PI * percentFull - game.PI/2, game.PIE);
        }
    }

    private void drawPortal() {
        Portal portal = engine.getClosestPortal();
        if (portal == null) {
            showingPortal = false;
            return;
        }
        showingPortal = true;
        enterPortal.show(screen);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text("Enter " + portal.name, enterPortal.x, enterPortal.y, enterPortal.w, enterPortal.h);
    }

    private void renderMiniMap() {

        float vw = GUI_WIDTH - (2 * invBuff); //width of the view
        float vh = vw * 0.8f;

        int minScale = game.max(game.ceil(vw/engine.currentLevel.w), game.ceil(vh/engine.currentLevel.h));
        int scale = game.max((int)((vw/engine.currentLevel.w) * miniMapZoom), minScale);

        int sx = (int)((engine.player.x * scale) - vw/2); //get the x-cord to start
        int sy = (int)((engine.player.y * scale) - vh/2); //get the y-cord to start

        //when you get close to the edges - stop centering on the player
        if (sx < 0) sx = 0;
        if (sy < 0) sy = 0;
        if (sx > (engine.currentLevel.w * scale) - vw) sx = (int)((engine.currentLevel.w * scale) - vw);
        if (sy > (engine.currentLevel.h * scale) - vh) sy = (int)((engine.currentLevel.h * scale) - vh);

        PImage map = Util.scaleImage(engine.currentLevel.getMiniMap().get(), (int)scale);
        PImage over = Util.scaleImage(engine.currentLevel.getOverlay().get(), (int)scale);

        screen.fill(150);
        screen.rect(0, height - vh - invBuff * 2, vw + invBuff * 2, vh + invBuff * 2);
        screen.fill(0);
        screen.rect(invBuff, height - vh - invBuff, vw, vh);
        screen.image(map.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
        screen.image(over.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
    }

    private void renderInv() {

        prevSelection = currSelection;
        currSelection = game.mousePressed;

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
                    screen.image(engine.player.active()[i].sprite, game.mouseX - invSize/2+ itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
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
                    screen.image(engine.player.inv()[i].sprite, game.mouseX - invSize/2 + itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                } else {
                    screen.image(engine.player.inv()[i].sprite, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + itemOffset, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                }
            }
        }

        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    if (currSelection && b1Type == bag && b1 == i) {
                        screen.image(items[i].sprite, game.mouseX - invSize/2 + itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                    } else {
                        screen.image(items[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, 3 * invBuff + invY + 4 * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                    }
                }
            }
        }
        if (inMenu && itemOver != -1 && mouseOverItem != null) {
            mouseOver(game.mouseX, game.mouseY, mouseOverItem);
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
            if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
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
            if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) {
                itemOver = i;
                mouseOverItem = engine.player.inv()[i];
                menuType = inv;
            }
        }
        if (showBag) {
            for (int i = 0; i < engine.player.active().length; i++) {
                screen.fill(150);
                screen.rect(invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize);
                if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize)) {
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
        WrappedText title = WrappedText.wrapText(item.name, mouseOverWidth - buff * 4, TILE_SIZE/2);
        WrappedText subtitle = WrappedText.wrapText("Tier " + item.tier + " " + type, mouseOverWidth - buff * 4, TILE_SIZE/3);
        WrappedText description = WrappedText.wrapText(desc, mouseOverWidth - buff * 4, TILE_SIZE/3);

        drawMouseOverText(x, y, title, subtitle, description);
    }

    public void drawMouseOverText(float x, float y, WrappedText title, WrappedText subtitle, WrappedText description) {
        int mouseOverWidth = 3 * GUI_WIDTH/4;
        int mouseOverHeight = title.textHeight + subtitle.textHeight + description.textHeight + (buff * 5);

        if (x + mouseOverWidth > screen.width) x = screen.width - mouseOverWidth;
        if (y + mouseOverHeight > screen.height) y = screen.height - mouseOverHeight;

        screen.textAlign(game.LEFT, game.TOP);

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
            if (Util.pointInBox(game.mouseX, game.mouseY, screen.width - i * TILE_SIZE, screen.height - TILE_SIZE, TILE_SIZE, TILE_SIZE)) {
                mouseOverEffect = effect;
            }
        }

        if (!mouseOverEffect.equals("")) {
            int mouseOverWidth = 3 * GUI_WIDTH/4;
            WrappedText title = WrappedText.wrapText(mouseOverEffect, mouseOverWidth - buff * 4, TILE_SIZE/2);
            WrappedText subtitle = WrappedText.wrapText(Util.roundTo(engine.player.stats.statusEffects.get(mouseOverEffect), 10) + "s remaining", mouseOverWidth - buff * 4, TILE_SIZE/2);
            WrappedText description = WrappedText.wrapText("", mouseOverWidth - buff * 4, 0);
            drawMouseOverText(game.mouseX, game.mouseY, title, subtitle, description);
        }
    }

    private void drawQuest() {
        float x = (width - GUI_WIDTH)/2 + GUI_WIDTH;
        float y = height/2;
        float r = game.min(x, y) - TILE_SIZE/2;
        PImage sprite = null;
        for (Enemy boss : engine.currentLevel.bosses) {
            float bx = ((StandardEnemy)boss).x;
            float by = ((StandardEnemy)boss).y;
            if (engine.currentLevel.visited((int)bx, (int)by) && game.dist(bx, by, engine.player.x, engine.player.y) < game.min(x, y)/TILE_SIZE) continue;
            float ang = game.atan2(by - engine.player.y, bx - engine.player.x);
            float dx = x + game.cos(ang) * r;
            float dy = y + game.sin(ang) * r;
            screen.pushMatrix();
            screen.translate(dx, dy);
            screen.rotate(ang);
            screen.image(guiSprites.get("QUEST"), -TILE_SIZE/4, -TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
            screen.popMatrix();
            if (game.dist(game.mouseX, game.mouseY, dx, dy) < TILE_SIZE/2) {
                sprite = ((StandardEnemy)boss).sprite;
            }
        }
        if (sprite != null) {
            drawMouseOverSprite(game.mouseX, game.mouseY, sprite);
        }
    }

    public void keyPressed(char key) {
        if (key == game.ENTER || key == game.RETURN && playerName.length() > 0) {
            if (checkFileAlreadyExists(playerName)) {
                screen.text("That name already exists", width/2, height/2 - TILE_SIZE);
            } else {
                loadedPlayerName = playerName;
                game.setState("PLAYING");
                characterNaming = false;
            }
        } else if (Character.isLetter(key) && playerName.length() < 10) {
            playerName = playerName + key;
        } else if (key == game.BACKSPACE) {
            playerName = playerName.substring(0, playerName.length() - 1);
        }
    }

    private Boolean checkFileAlreadyExists(String fileName) {

        java.io.File folder = new java.io.File(game.sketchPath() + "/saves/");
        String[] listOfFiles = folder.list();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].equals(fileName)) {
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