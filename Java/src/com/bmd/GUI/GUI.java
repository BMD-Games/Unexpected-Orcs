package com.bmd.GUI;

import com.bmd.App.Graphics;
import com.bmd.App.Main;
import com.bmd.App.State;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Entities.ItemBag;
import com.bmd.Entities.Portal;
import com.bmd.File.GameFile;
import com.bmd.Input.Input;
import com.bmd.Items.*;
import com.bmd.Player.Player;
import com.bmd.Settings.Settings;
import com.bmd.Sprites.Sprites;
import com.bmd.Stats.Stats;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.awt.image.BufferedImage;

public class GUI {
    /**
     This class is used for drawing and handeling all UI related screens and elements
     **/

    public float miniMapZoom = 1;
    public float zoomMax = 5;
    public float zoomMin = 1;
    public boolean inMenu = false;

    private Button play, back, options, menu, exit, pause, newGame, load, play2, deleteSave;
    private Button keyUp, keyDown, keyLeft, keyRight, keyAbility;
    private DisplayBar healthBar, manaBar;
    private Button enterPortal;
    private BufferedImage title = Util.loadImage("com/bmd/assets/sprites/title.png");
    private Canvas canvas;
    private GraphicsContext screen;
    private Color c = Util.gray(100);
    private String playerName = "";

    public ScrollWindow loadScroll = new ScrollWindow(Main.width/8, Main.height/4, Main.width/4 * 3, Main.height/2, GameFile.loadSaves());

    //Stat sprites
    private BufferedImage attackSprite = Sprites.itemSprites.get("ATTACK_ICON");
    private BufferedImage defenceSprite = Sprites.itemSprites.get("DEFENCE_ICON");
    private BufferedImage vitalitySprite = Sprites.itemSprites.get("VITALITY_ICON");
    private BufferedImage wisdomSprite = Sprites.itemSprites.get("WISDOM_ICON");
    private BufferedImage speedSprite = Sprites.itemSprites.get("SPEED_ICON");

    //Inventory drag and drop stuff
    private final int invBuff = 5, invScale = 2, itemOffset = 1, invSize = Sprites.SPRITE_SIZE * invScale + 2 * itemOffset;
    private final int invX = (Util.GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH + itemOffset)))/2, invY = 7 * Tiles.TILE_SIZE/2;
    private boolean prevSelection = false, currSelection = false, showingPortal = false;
    private int b1Type, b2Type, menuType; // if inv box is in active or not
    private int b1 = -1, b2 = -1, itemOver, active = 0, inv = 1, bag = 2, out = 3;
    ; //inv box 1 and 2 for drag and swap
    private Item mouseOverItem;

    int buff = 6; //for mouseOver stuff;

    public GUI() {
        //-----Main
        play = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 - Tiles.TILE_SIZE * 2, "PLAY");
        load = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 - Tiles.TILE_SIZE * 0, "LOAD");
        options = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 + Tiles.TILE_SIZE * 1, "OPTIONS");
        menu = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 + Tiles.TILE_SIZE * 2, "MENU");
        exit = new Button(Main.width/2 - Tiles.TILE_SIZE, Main.height/2 + Tiles.TILE_SIZE * 2, "EXIT");
        back = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 + Tiles.TILE_SIZE * 3, "BACK");
        pause = new Button(Main.width - 2 * Tiles.TILE_SIZE, Tiles.TILE_SIZE, "PAUSE");
        play2 = new Button(Main.width/4 * 3, Main.height/2 + Tiles.TILE_SIZE * 3, "PLAY");
        deleteSave = new Button(Main.width/2 - Tiles.TILE_SIZE * 0.5f, Main.height/2, "SAVE2");

        //newGame = new Button (Main.width/2 - Tiles.TILE_SIZE, Main.height/2 - Tiles.TILE_SIZE * 2, "NEW");



        //-----Settings
        keyUp = new Button(Main.width/2, Main.height/2 - Tiles.TILE_SIZE * 4, "BLANK_1x1");
        keyDown = new Button(Main.width/2, Main.height/2 - Tiles.TILE_SIZE * 3, "BLANK_1x1");
        keyLeft = new Button(Main.width/2, Main.height/2 - Tiles.TILE_SIZE * 2, "BLANK_1x1");
        keyRight = new Button(Main.width/2, Main.height/2 - Tiles.TILE_SIZE * 1, "BLANK_1x1");
        keyAbility = new Button(Main.width/2, Main.height/2 - Tiles.TILE_SIZE * 0, "BLANK_1x1");

        //-----Gameplay
        healthBar = new DisplayBar(Util.GUI_WIDTH/2 - Tiles.TILE_SIZE * 1.5f + 4, Tiles.TILE_SIZE/2 - invBuff, Util.color(230, 100, 100));
        manaBar = new DisplayBar(Util.GUI_WIDTH/2 - Tiles.TILE_SIZE * 1.5f + 4, 2 * Tiles.TILE_SIZE/2, Util.color(153, 217, 234));
        enterPortal = new Button(Util.GUI_WIDTH/2 - Tiles.TILE_SIZE, 14 * Tiles.TILE_SIZE/2, "BLANK_2x1");

        canvas = new Canvas(Main.width, Main.height);
        screen = canvas.getGraphicsContext2D();
    }

    public void clearScreen() {
        screen.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawMenu() {
        //Draws the main menu
        clearScreen();
        Graphics.image(screen, title, 0, 0, Main.width, Main.height);
        //screen.background(c);
        play.show(canvas);
        load.show(canvas);
        options.show(canvas);
        exit.show(canvas);
        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawOptions() {
        //Draws the options menu
        screen.setTextAlign(TextAlignment.CENTER);
        screen.setFont(Util.font("bitcell", (Tiles.TILE_SIZE/2)));
        Graphics.background(canvas, c);
        back.show(canvas);
        keyUp.show(canvas);
        keyDown.show(canvas);
        keyLeft.show(canvas);
        keyRight.show(canvas);
        keyAbility.show(canvas);
        screen.setFill(Util.color(0, 112, 188));
        if (!Settings.remapNextKey || Settings.remapAction != Util.up) screen.fillText(Settings.getKeyString(Util.up), keyUp.x + keyUp.w/2, keyUp.y + keyUp.h/2);
        if (!Settings.remapNextKey || Settings.remapAction != Util.down) screen.fillText(Settings.getKeyString(Util.down), keyDown.x + keyDown.w/2, keyDown.y + keyDown.h/2);
        if (!Settings.remapNextKey || Settings.remapAction != Util.left) screen.fillText(Settings.getKeyString(Util.left), keyLeft.x + keyLeft.w/2, keyLeft.y + keyLeft.h/2);
        if (!Settings.remapNextKey || Settings.remapAction != Util.right) screen.fillText(Settings.getKeyString(Util.right), keyRight.x + keyRight.w/2, keyRight.y + keyRight.h/2);
        if (!Settings.remapNextKey || Settings.remapAction != Util.ability) screen.fillText(Settings.getKeyString(Util.ability), keyAbility.x + keyAbility.w/2, keyAbility.y + keyAbility.h/2);
        screen.setFill(Util.gray(255));
        screen.setTextAlign(TextAlignment.RIGHT);
        screen.fillText("Forward", keyUp.x, keyUp.y + keyUp.h/2);
        screen.fillText("Back", keyDown.x, keyDown.y + keyDown.h/2);
        screen.fillText("Left", keyLeft.x, keyLeft.y + keyLeft.h/2);
        screen.fillText("Right", keyRight.x, keyRight.y + keyRight.h/2);
        screen.fillText("Ability", keyAbility.x, keyAbility.y + keyAbility.h/2);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawPaused() {
        //Draws the paused overlay
        drawPlay(Main.engine.player);
        //clearScreen();
        screen.setFill(Util.gray(0, 100));
        screen.rect(-Tiles.TILE_SIZE, -Tiles.TILE_SIZE, Main.width + Tiles.TILE_SIZE, Main.height + Tiles.TILE_SIZE);
        menu.show(canvas);
        options.show(canvas);
        play.show(canvas);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawSave() {

        Graphics.background(canvas, c);
        screen.setFill(Util.gray(0, 100));
        screen.rect(-Tiles.TILE_SIZE, -Tiles.TILE_SIZE, Main.width + Tiles.TILE_SIZE, Main.height + Tiles.TILE_SIZE);
        back.show(canvas);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawPlay(Player player) {

        //Draws the GUI during gameplay

        healthBar.updateBar(player.stats.health, player.stats.healthMax);
        manaBar.updateBar(player.stats.mana, player.stats.manaMax);

        clearScreen();
        screen.setFill(Util.gray(217));
        screen.fillRect(0, 0, Util.GUI_WIDTH, Main.height);

        pause.show(canvas);
        screen.setTextAlign(TextAlignment.CENTER);
        screen.setFill(Util.color(50, 50, 50));
        screen.setFont(Util.font("bitcell", (Tiles.TILE_SIZE / 2)));
        screen.fillText(Main.loadedPlayerName, Util.GUI_WIDTH / 2, 20);
        healthBar.show(canvas);
        manaBar.show(canvas);
        showStatusEffects();
        drawQuest();
        renderMiniMap();
        drawPortal();
        drawStatProgress();
        renderInv();
        drawStats();
        drawCooldown();

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);

        if (Util.pointInBox(Input.mouseX(), Input.mouseY(), 0, 0, Util.GUI_WIDTH, Main.height)) {
            inMenu = true;
        } else {
            inMenu = false;
        }
    }

    public void drawLoading() {
        clearScreen();
        screen.setFill(Color.BLACK);
        screen.rect(0, 0, Main.width, Main.width);
        screen.setFill(Color.WHITE);
        screen.setTextAlign(TextAlignment.CENTER);
        screen.fillText("Loading", Main.width/2, Main.height/2);
        screen.fillText(Main.loadMessage, Main.width/2, Main.height/2 + Tiles.TILE_SIZE);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawDead() {

        clearScreen();

        Graphics.image(screen, title, 0, 0, Main.width, Main.height);
        screen.setFill(Util.color(200, 0, 0));
        screen.fillText("Nibba u dead", Main.width/2, Main.height/2);
        back.show(canvas);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void drawLoad() {

        clearScreen();
        Graphics.image(Main.gc, title, 0, 0, Main.width, Main.height);
        loadScroll.update();
        loadScroll.show(canvas);
        back.show(canvas);
        play2.show(canvas);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);

    /*
    screen.beginDraw();
     clearScreen();
     println("here");
     loadScroll.show(screen);
     screen.fill(255, 0, 0);
     screen.rect(100, 100, 100, 100);

     screen.endDraw();
     Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
     */
    }

    public void drawNewGame() {
        clearScreen();

        Graphics.image(screen, title, 0, 0, Main.width, Main.height);
        Settings.characterNaming = true;
        back.show(canvas);
        screen.setFill(Util.gray(200));
        screen.fillRect(Main.width/2 - Tiles.TILE_SIZE * 2, Main.height/2 - Tiles.TILE_SIZE / 4 * 3, Tiles.TILE_SIZE * 4, Tiles.TILE_SIZE);
        screen.setFill(Util.gray(50));
        screen.setFont(Util.font("bitcell", (Tiles.TILE_SIZE)));
        screen.setTextAlign(TextAlignment.CENTER);
        screen.fillText(playerName, Main.width/2, Main.height/2);
        screen.setFont(Util.font("bitcell", (Tiles.TILE_SIZE/2)));
        if (checkFileAlreadyExists(playerName)) {
            screen.fillText("A hero with that name already exists.", Main.width/2, Main.height/2 + Tiles.TILE_SIZE);
        }
        play.show(canvas);

        Graphics.image(Main.gc, Util.getImage(canvas), 0, 0);
    }

    public void handleMouseReleased() {
        //------Main Buttons
        if (Main.STATE == State.MENU && play.pressed()) {
            Main.setState(State.NEWGAME);
        }
        if ((Main.STATE == State.MENU || Main.STATE == State.PAUSED) && play.pressed()) {
            Main.setState(State.PLAYING);
        } else if ((Main.STATE == State.MENU || Main.STATE == State.PAUSED) && options.pressed()) {
            Main.setState(State.OPTIONS);
        } else if ((Main.STATE == State.MENU) && load.pressed()) {
            Main.setState(State.LOAD);
        } else if (Main.STATE == State.PAUSED && menu.pressed()) {
            //SAVE GAME HERE!!!!
            GameFile.saveGame();
            Main.setState(State.MENU);
        } else if (Main.STATE == State.PLAYING && pause.pressed()) {
            Main.setState(State.PAUSED);
        } else if (Main.STATE == State.MENU && exit.pressed()) {
            Main.quitGame();
        } else if ((Main.STATE == State.OPTIONS || Main.STATE == State.LOAD) && back.pressed()) {
            Main.revertState();
        } else if (Main.STATE == State.PLAYING && showingPortal && enterPortal.pressed()) {
            Main.engine.enterClosestPortal();
        } else if (Main.STATE == State.DEAD && back.pressed()) {
            Main.setState(State.MENU);
            //Add reset function;
        } else if (Main.STATE == State.LOAD && play2.pressed()) {
            if (loadScroll.selectedElement != -1) {
                Main.engine.player = Main.loadedPlayers[loadScroll.selectedElement];
                Main.loadedPlayerName = loadScroll.scrollElements[loadScroll.selectedElement].title;
                Main.setState(State.PLAYING);
            }
        } else if (Main.STATE == State.NEWGAME && back.pressed()) {
            Main.setState(State.MENU);
            playerName = "";
        } else if (Main.STATE == State.NEWGAME && play.pressed()) {
            if (playerName.length() > 0 && !checkFileAlreadyExists(playerName)) {
                Main.loadedPlayerName = playerName;
                GameFile.saveGame();
                Main.setState(State.PLAYING);
            }
        }
        //-----Settings Buttons
        else if (Main.STATE == State.OPTIONS) {
            if (keyUp.pressed()) {
                Settings.remapNextKey = true;
                Settings.remapAction = Util.up;
            }
            if (keyDown.pressed()) {
                Settings.remapNextKey = true;
                Settings.remapAction = Util.down;
            }
            if (keyLeft.pressed()) {
                Settings.remapNextKey = true;
                Settings.remapAction = Util.left;
            }
            if (keyRight.pressed()) {
                Settings.remapNextKey = true;
                Settings.remapAction = Util.right;
            }
            if (keyAbility.pressed()) {
                Settings.remapNextKey = true;
                Settings.remapAction = Util.ability;
            }
        }

        if (Main.STATE == State.LOAD) {
            loadScroll.handleMouse();
        }
    }



    private void drawCooldown() {

        if (Main.engine.player.inv.currentAbility() != null ) {
            float percentFull = Main.engine.player.getPercentCooldown();
            screen.setFill(Util.gray(0, 100));
            screen.arc(invBuff + invX + (invSize + invBuff) + itemOffset + Sprites.SPRITE_SIZE * invScale/2, invBuff + invY + itemOffset + Sprites.SPRITE_SIZE * invScale/2,
                    Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale, -Math.PI/2, 2 * Math.PI * percentFull - Math.PI/2);
        }
    }

    private void drawStatProgress() {

        float attackFloat = Main.engine.player.stats.calcStatValue(Main.engine.player.stats.attackKills, Main.engine.player.stats.baseAttack, 1, 0.1f);
        attackFloat = attackFloat % 1;
        screen.setFill(Util.gray(150));
        screen.fillRect(Util.GUI_WIDTH * 2 / 5f - Tiles.TILE_SIZE * 9 / 8f, 73 + Tiles.TILE_SIZE / 2f, 10, Sprites.SPRITE_SIZE * 2);
        screen.setFill(Stats.statColours.get("ATTACK"));
        screen.rect(Util.GUI_WIDTH * 2 / 5f - Tiles.TILE_SIZE * 9 / 8f, 73 + Tiles.TILE_SIZE / 2 + Sprites.SPRITE_SIZE * 2, 10, - Sprites.SPRITE_SIZE * 2 * attackFloat);

        float defenceFloat = Main.engine.player.stats.calcStatValue(Main.engine.player.stats.defenceKills, Main.engine.player.stats.baseDefence, 1, 0.1f);
        defenceFloat = defenceFloat % 1;
        screen.setFill(Util.gray(150));
        screen.rect(Util.GUI_WIDTH * 4 / 5f - Tiles.TILE_SIZE * 9 / 8f, 73 + Tiles.TILE_SIZE / 2f, 10, Sprites.SPRITE_SIZE * 2);
        screen.setFill(Stats.statColours.get("DEFENCE"));
        screen.rect(Util.GUI_WIDTH * 4 / 5f - Tiles.TILE_SIZE * 9 / 8f, 73 + Tiles.TILE_SIZE / 2 + Sprites.SPRITE_SIZE * 2, 10, - Sprites.SPRITE_SIZE * 2 * defenceFloat);

        float vitalityFloat = Main.engine.player.stats.calcStatValue(Main.engine.player.stats.vitalityKills, Main.engine.player.stats.baseVitality, 1, 0.1f);
        vitalityFloat = vitalityFloat % 1;
        screen.setFill(Util.gray(150));
        screen.rect(Util.GUI_WIDTH * 2 / 5f - Tiles.TILE_SIZE * 9 / 8f, 79 + Tiles.TILE_SIZE, 10, Sprites.SPRITE_SIZE * 2);
        screen.setFill(Stats.statColours.get("VITALITY"));
        screen.rect(Util.GUI_WIDTH * 2 / 5f - Tiles.TILE_SIZE * 9 / 8f, 79 + Tiles.TILE_SIZE + Sprites.SPRITE_SIZE * 2, 10, - Sprites.SPRITE_SIZE * 2 * vitalityFloat);

        float wisdomFloat = Main.engine.player.stats.calcStatValue(Main.engine.player.stats.wisdomKills, Main.engine.player.stats.baseWisdom, 1, 0.1f);
        wisdomFloat = wisdomFloat % 1;
        screen.setFill(Util.gray(150));
        screen.rect(Util.GUI_WIDTH * 4 / 5f - Tiles.TILE_SIZE * 9 / 8f, 79 + Tiles.TILE_SIZE, 10, Sprites.SPRITE_SIZE * 2);
        screen.setFill(Stats.statColours.get("WISDOM"));
        screen.rect(Util.GUI_WIDTH * 4 / 5f - Tiles.TILE_SIZE * 9 / 8f, 79 + Tiles.TILE_SIZE + Sprites.SPRITE_SIZE * 2, 10, - Sprites.SPRITE_SIZE * 2 * wisdomFloat);
    }


    private void drawStats() {
        screen.save();

        screen.setTextAlign(TextAlignment.LEFT);
        screen.setFont(Util.font("bitcell", (Tiles.TILE_SIZE/2)));
        screen.setFill(Util.gray(30));

        //Draw stat values
        screen.fillText(Integer.toString(Main.engine.player.stats.attack), Util.GUI_WIDTH * 2 / 5 - Tiles.TILE_SIZE / 8, 100 + Tiles.TILE_SIZE / 2);
        screen.fillText(Integer.toString(Main.engine.player.stats.defence), Util.GUI_WIDTH * 4 / 5 - Tiles.TILE_SIZE / 8, 100 + Tiles.TILE_SIZE / 2);
        screen.fillText(Integer.toString(Main.engine.player.stats.vitality), Util.GUI_WIDTH * 2 / 5 - Tiles.TILE_SIZE / 8, 106 + Tiles.TILE_SIZE);
        screen.fillText(Integer.toString(Main.engine.player.stats.wisdom), Util.GUI_WIDTH * 4 / 5 - Tiles.TILE_SIZE / 8, 106 + Tiles.TILE_SIZE);
        screen.fillText(Integer.toString((int)(Main.engine.player.stats.speed * 100)), Util.GUI_WIDTH * 2 / 5 - Tiles.TILE_SIZE / 8, 112 + Tiles.TILE_SIZE * 3 / 2);

        //Draw stat sprites
        Graphics.image(screen, attackSprite, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 104, attackSprite.getWidth() * 2, attackSprite.getHeight() * 2);
        Graphics.image(screen, defenceSprite, Util.GUI_WIDTH * 3 / 5 - Tiles.TILE_SIZE / 8, 104, defenceSprite.getWidth() * 2, defenceSprite.getHeight() * 2);
        Graphics.image(screen, vitalitySprite, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 112 + Tiles.TILE_SIZE / 2, vitalitySprite.getWidth() * 2, vitalitySprite.getHeight() * 2);
        Graphics.image(screen, wisdomSprite, Util.GUI_WIDTH * 3 / 5 - Tiles.TILE_SIZE / 8, 112 + Tiles.TILE_SIZE / 2, wisdomSprite.getWidth() * 2, wisdomSprite.getHeight() * 2);
        Graphics.image(screen, speedSprite, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 120 + Tiles.TILE_SIZE, speedSprite.getWidth() * 2, speedSprite.getHeight() * 2);
        screen.restore();

        mouseOverStat();
    }

    private void drawPortal() {
        Portal portal = Main.engine.getClosestPortal();
        if (portal == null) {
            showingPortal = false;
            return;
        }
        showingPortal = true;
        enterPortal.show(canvas);
        screen.setTextAlign(TextAlignment.CENTER);
        screen.fillText("Enter", enterPortal.x, enterPortal.y);
        screen.fillText(portal.name, enterPortal.x, enterPortal.y + Tiles.TILE_SIZE * 2);
    }

    private void renderMiniMap() {

        float vw = Util.GUI_WIDTH - (2 * invBuff); //Main.width of the view
        float vh = vw * 0.8f;

        int minScale = Util.max(Util.ceil(vw/Main.engine.currentLevel.w), Util.ceil(vh/Main.engine.currentLevel.h));
        int scale = Util.max((int)((vw/Main.engine.currentLevel.w) * miniMapZoom), minScale);

        int sx = (int)((Main.engine.player.x * scale) - vw/2); //get the x-cord to start
        int sy = (int)((Main.engine.player.y * scale) - vh/2); //get the y-cord to start

        //when you get close to the edges - stop centering on the player
        if (sx < 0) sx = 0;
        if (sy < 0) sy = 0;
        if (sx > (Main.engine.currentLevel.w * scale) - vw) sx = (int)((Main.engine.currentLevel.w * scale) - vw);
        if (sy > (Main.engine.currentLevel.h * scale) - vh) sy = (int)((Main.engine.currentLevel.h * scale) - vh);

        BufferedImage map = Util.scaleImage(Util.getImage(Main.engine.currentLevel.getMiniMap()), (int)scale);
        BufferedImage over = Util.scaleImage(Util.getImage(Main.engine.currentLevel.getOverlay()), (int)scale);

        screen.setFill(Util.gray(150));
        screen.rect(0, Main.height - vh - invBuff * 2, vw + invBuff * 2, vh + invBuff * 2);
        screen.setFill(Color.BLACK);
        screen.rect(invBuff, Main.height - vh - invBuff, vw, vh);
        Graphics.image(screen, map.getSubimage(sx, sy, (int)vw, (int)vh), invBuff, Main.height - vh - invBuff, vw, vh);
        Graphics.image(screen, over.getSubimage(sx, sy, (int)vw, (int)vh), invBuff, Main.height - vh - invBuff, vw, vh);
    }

    private void renderInv() {

        prevSelection = currSelection;
        currSelection = Main.mousePressed;

        ItemBag itemBag = Main.engine.getClosestBag();
        Item[] items = Main.engine.getClosestBagItems();

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
                    Main.engine.player.inv.swapItemsActive(b1, b2);
                } else if (b2Type == active && b1Type == inv) {
                    Main.engine.player.inv.swapItemsActive(b2, b1);
                } else if (b1Type == inv && b2Type == inv) {
                    Main.engine.player.inv.swapItemsInv(b1, b2);
                } else if (b1Type == inv && b2Type == bag) { //----INV/BAG
                    Item bagItem = itemBag.takeItem(b2);
                    itemBag.addItem(Main.engine.player.inv.addItemInv(bagItem, b1));
                } else if (b1Type == bag && b2Type == inv) {
                    Item bagItem = itemBag.takeItem(b1);
                    itemBag.addItem(Main.engine.player.inv.addItemInv(bagItem, b2));
                } else if (b1Type == active && b2Type == bag) { //-----ACTIVE/BAG
                    Item bagItem = itemBag.takeItem(b2);
                    itemBag.addItem(Main.engine.player.inv.addItemActive(bagItem, b1));
                } else if (b1Type == bag && b2Type == active) {
                    Item bagItem = itemBag.takeItem(b1);
                    itemBag.addItem(Main.engine.player.inv.addItemActive(bagItem, b2));
                } else if (b1Type == active && b2Type == out && !inMenu) { //----ACTIVE/GROUND
                    if (itemBag == null || itemBag.isFull()) {
                        ItemBag newBag = new ItemBag(Main.engine.player.x, Main.engine.player.y, 0);
                        newBag.addItem(Main.engine.player.inv.addItemActive(null, b1));
                        Main.engine.addDrop(newBag);
                    } else {
                        itemBag.addItem(Main.engine.player.inv.addItemActive(null, b1));
                    }
                } else if (b1Type == inv && b2Type == out && !inMenu) { //----INV/GROUND
                    if (itemBag == null || itemBag.isFull()) {
                        ItemBag newBag = new ItemBag(Main.engine.player.x, Main.engine.player.y, 0);
                        newBag.addItem(Main.engine.player.inv.addItemInv(null, b1));
                        Main.engine.addDrop(newBag);
                    } else {
                        itemBag.addItem(Main.engine.player.inv.addItemInv(null, b1));
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

        for (int i = 0; i < Main.engine.player.active().length; i++) {
            if (Main.engine.player.active()[i] != null) {
                if (currSelection && b1Type == active && b1 == i) {
                    Graphics.image(screen, Main.engine.player.active()[i].sprite, Input.mouseX() - invSize/2+ itemOffset, Input.mouseY() - invSize/2 + itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                } else {
                    Graphics.image(screen, Main.engine.player.active()[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, invBuff + invY+ itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                }
            }
        }
        int j = 0;
        for (int i = 0; i < Main.engine.player.inv().length; i++) {
            j = (int)(i/Inventory.WIDTH);
            if (Main.engine.player.inv()[i] != null) {
                if (currSelection && b1Type == inv && b1 == i) {
                    Graphics.image(screen, Main.engine.player.inv()[i].sprite, Input.mouseX() - invSize/2 + itemOffset, Input.mouseY() - invSize/2 + itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                } else {
                    Graphics.image(screen, Main.engine.player.inv()[i].sprite, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + itemOffset, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                }
            }
        }

        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    if (currSelection && b1Type == bag && b1 == i) {
                        Graphics.image(screen, items[i].sprite, Input.mouseX() - invSize/2 + itemOffset, Input.mouseY() - invSize/2 + itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                    } else {
                        Graphics.image(screen, items[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, 3 * invBuff + invY + 4 * (invSize + invBuff) + itemOffset, Sprites.SPRITE_SIZE * invScale, Sprites.SPRITE_SIZE * invScale);
                    }
                }
            }
        }
        if (inMenu && itemOver != -1 && mouseOverItem != null) {
            mouseOver(Input.mouseX(), Input.mouseY(), mouseOverItem);
        }
    }

    void drawBack(boolean showBag, Item[] items) {
        menuType = out;
        screen.setFill(Util.gray(51));
        if (showBag) {
            screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, (Inventory.WIDTH + 1) * (invSize + invBuff) + invBuff * 3);
        } else {
            screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, Inventory.WIDTH * (invSize + invBuff) + invBuff * 2);
        }

        itemOver = -1;
        for (int i = 0; i < Main.engine.player.active().length; i++) {
            screen.setFill(Util.gray(150));
            screen.rect(invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize);
            if (Util.pointInBox(Input.mouseX(), Input.mouseY(), invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
                itemOver = i;
                mouseOverItem = Main.engine.player.active()[i];
                menuType = active;
            }
        }
        int j = 0;
        for (int i = 0; i < Main.engine.player.inv().length; i++) {
            j = (int)(i/Inventory.WIDTH);
            screen.setFill(Util.gray(150));
            screen.rect(invBuff + invX + (i%4) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize);
            if (Util.pointInBox(Input.mouseX(), Input.mouseY(), invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) {
                itemOver = i;
                mouseOverItem = Main.engine.player.inv()[i];
                menuType = inv;
            }
        }
        if (showBag) {
            for (int i = 0; i < Main.engine.player.active().length; i++) {
                screen.setFill(Util.gray(150));
                screen.rect(invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize);
                if (Util.pointInBox(Input.mouseX(), Input.mouseY(), invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize)) {
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
        int mouseOverWidth = 3 * Util.GUI_WIDTH/4;
        WrappedText title = WrappedText.wrapText(item.name, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/2);
        WrappedText subtitle = WrappedText.wrapText("Tier " + item.tier + " " + type, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/3);
        WrappedText description = WrappedText.wrapText(desc, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/3);

        drawMouseOverText(x, y, title, subtitle, description);
    }

    private void mouseOverStat() {

        int x = Input.mouseX();
        int y = Input.mouseY();

        String statName = "";
        String type = "";
        String desc = "";

        if (Util.pointInBox(x, y, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 104, Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2)) { // attack sprite hover
            statName = "Attack";
            type = String.valueOf(Main.engine.player.stats.getAttack());
            desc = "Increases Damage dealt by player projectiles";
        } else if (Util.pointInBox(x, y, Util.GUI_WIDTH * 3 / 5 - Tiles.TILE_SIZE / 8, 104, Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2)) { // defence sprite hover
            statName = "Defence";
            type = String.valueOf(Main.engine.player.stats.getDefence());
            desc = "Decreases damage taken from enemies";
        } else if (Util.pointInBox(x, y, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 112 + Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2)) { // vitality hover
            statName = "Vitality";
            type = String.valueOf(Main.engine.player.stats.getVitality());
            desc = "Increases health regeneration rate";
        } else if (Util.pointInBox(x, y, Util.GUI_WIDTH * 3 / 5 - Tiles.TILE_SIZE / 8, 112 + Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2)) { // wisdom hover
            statName = "Wisdom";
            type = String.valueOf(Main.engine.player.stats.getVitality());
            desc = "Increases mana regeneration rate";
        } else if (Util.pointInBox(x, y, Util.GUI_WIDTH / 5 - Tiles.TILE_SIZE / 8, 120 + Tiles.TILE_SIZE, Tiles.TILE_SIZE / 2, Tiles.TILE_SIZE / 2)) { // speed hover
            statName = "Speed";
            type = String.valueOf((int)(Main.engine.player.stats.speed * 100));
            desc = "Increases player speed";
        }

        if (!statName.equals("")) {

            int mouseOverWidth = 3 * Util.GUI_WIDTH/4;
            WrappedText title = WrappedText.wrapText(statName, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/2);
            WrappedText subtitle = WrappedText.wrapText(type, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/3);
            WrappedText description = WrappedText.wrapText(desc, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/3);

            drawMouseOverText(x, y, title, subtitle, description);
        }
    }

    private void drawMouseOverText(float x, float y, WrappedText title, WrappedText subtitle, WrappedText description) {
        int mouseOverWidth = 3 * Util.GUI_WIDTH/4;
        int mouseOverHeight = title.textHeight + subtitle.textHeight + description.textHeight + (buff * 5);

        if (x + mouseOverWidth > canvas.getWidth()) x = (float)canvas.getWidth() - mouseOverWidth;
        if (y + mouseOverHeight > canvas.getHeight()) y = (float)canvas.getHeight() - mouseOverHeight;

        screen.setTextAlign(TextAlignment.LEFT);

        screen.setFill(Util.gray(100));
        screen.rect(x, y, mouseOverWidth, mouseOverHeight);
        screen.setStroke(Util.gray(130));
        screen.rect(x + buff, y + buff, mouseOverWidth - buff * 2, mouseOverHeight - buff * 2);
        Graphics.line(screen, x + buff, y + title.textHeight + buff/2, x + mouseOverWidth - buff, y + title.textHeight + buff/2);
        Graphics.line(screen, x + buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2, x + mouseOverWidth - buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2);

        screen.setFill(Util.gray(210));
        screen.setFont(Util.font("bitcell", title.textSize));
        screen.fillText(title.string, x + buff * 2, y + buff);

        screen.setFont(Util.font("bitcell",  subtitle.textSize));
        screen.fillText(subtitle.string, x + buff * 2, y + title.textHeight + (buff * 2));

        screen.setFill(Util.gray(255));
        screen.setFont(Util.font("bitcell",  description.textSize));
        screen.fillText( description.string, x + buff * 2, (y + mouseOverHeight) - description.textHeight - buff);
    }

    private void showStatusEffects() {
        int i = 0;
        String mouseOverEffect = "";
        for (String effect : Main.engine.player.stats.statusEffects.keySet()) {
            i++;
            Graphics.image(screen, Sprites.playerStatusSprites.get(effect), (int)canvas.getWidth() - i * Tiles.TILE_SIZE, (int)canvas.getHeight() - Tiles.TILE_SIZE, Tiles.TILE_SIZE, Tiles.TILE_SIZE);
            if (Util.pointInBox(Input.mouseX(), Input.mouseY(), (int)canvas.getWidth() - i * Tiles.TILE_SIZE, (int)canvas.getHeight() - Tiles.TILE_SIZE, Tiles.TILE_SIZE, Tiles.TILE_SIZE)) {
                mouseOverEffect = effect;
            }
        }

        if (!mouseOverEffect.equals("")) {
            int mouseOverWidth = 3 * Util.GUI_WIDTH/4;
            WrappedText title = WrappedText.wrapText(mouseOverEffect, mouseOverWidth - buff * 4, Tiles.TILE_SIZE/2);
            WrappedText subtitle = WrappedText.wrapText(Util.roundTo(Main.engine.player.stats.statusEffects.get(mouseOverEffect), 10) + "s remaining", mouseOverWidth - buff * 4, Tiles.TILE_SIZE/2);
            WrappedText description = WrappedText.wrapText("", mouseOverWidth - buff * 4, 0);
            drawMouseOverText(Input.mouseX(), Input.mouseY(), title, subtitle, description);
        }
    }

    private void drawQuest() {
        float x = (Main.width - Util.GUI_WIDTH)/2 + Util.GUI_WIDTH;
        float y = Main.height/2;
        float r = Util.min(x, y) - Tiles.TILE_SIZE/2;
        BufferedImage sprite = null;
        for (Enemy boss : Main.engine.currentLevel.bosses) {
            float bx = ((StandardEnemy)boss).x;
            float by = ((StandardEnemy)boss).y;
            if (Main.engine.currentLevel.visited((int)bx, (int)by) && Util.dist(bx, by, Main.engine.player.x, Main.engine.player.y) < Util.min(x, y)/Tiles.TILE_SIZE) continue;
            float ang = (float)Math.atan2(by - Main.engine.player.y, bx - Main.engine.player.x);
            float dx = x + (float)Math.cos(ang) * r;
            float dy = y + (float)Math.sin(ang) * r;
            screen.save();
            screen.translate(dx, dy);
            screen.rotate(ang);
            Graphics.image(screen, Sprites.guiSprites.get("QUEST"), -Tiles.TILE_SIZE/4, -Tiles.TILE_SIZE/4, Tiles.TILE_SIZE/2, Tiles.TILE_SIZE/2);
            screen.restore();
            if (Util.dist(Input.mouseX(), Input.mouseY(), dx, dy) < Tiles.TILE_SIZE/2) {
                sprite = ((StandardEnemy)boss).sprite;
            }
        }
        if (sprite != null) {
            drawMouseOverSprite(Input.mouseX(), Input.mouseY(), sprite);
        }
    }

    public void keyPressed(char key) {
        Util.println(key);
        if (KeyCode.getKeyCode(key + "") == KeyCode.ENTER && playerName.length() > 0) {
            if (checkFileAlreadyExists(playerName)) {
                screen.fillText( "That name already exists", Main.width/2, Main.height/2 - Tiles.TILE_SIZE);
            } else {
                Main.loadedPlayerName = playerName;
                Main.setState(State.PLAYING);
                Settings.characterNaming = false;
            }
        } else if (Character.isLetter(key) && playerName.length() < 10) {
            playerName = playerName + key;
        } else if (KeyCode.getKeyCode(key + "") == KeyCode.BACK_SPACE) {
            playerName = playerName.substring(0, playerName.length() - 1);
        }
    }

    private Boolean checkFileAlreadyExists(String fileName) {

        java.io.File folder = new java.io.File("./out/saves/");
        String[] listOfFiles = folder.list();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private void drawMouseOverSprite(float x, float y, BufferedImage sprite) {
        int mouseOverSize = Tiles.TILE_SIZE + buff * 4;

        if (x + mouseOverSize > canvas.getWidth()) x = (float)canvas.getWidth() - mouseOverSize;
        if (y + mouseOverSize > canvas.getHeight()) y = (float)canvas.getHeight() - mouseOverSize;

        screen.setFill(Util.gray(100));
        screen.fillRect(x, y, mouseOverSize, mouseOverSize);
        screen.setStroke(Util.gray(130));
        screen.strokeRect(x + buff, y + buff, mouseOverSize - buff * 2, mouseOverSize - buff * 2);
        Graphics.image(screen, sprite, x + buff * 2, y + buff * 2, Tiles.TILE_SIZE, Tiles.TILE_SIZE);
    }
}
