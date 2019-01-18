package GUI;

import Enemies.Enemy;
import Enemies.StandardEnemy;
import Entities.Drops.Portal;
import File.GameFile;
import GUI.Scroll.ScrollWindow;
import Items.Inventory;
import Utility.Util;
import processing.core.PGraphics;
import processing.core.PImage;

import static Settings.Settings.*;
import static Sprites.Sprites.*;
import static Utility.Colour.colour;
import static Utility.Constants.*;

public class GUI {
    /**
     This class is used for drawing and handeling all UI related screens and elements
     **/

    private Button play, back, options, menu, exit, pause, newGame, load, save, play2, deleteSave, quick;
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
    public final int invX = (GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH+ itemOffset)))/2, invY = 7 * TILE_SIZE/2;
    private boolean showingPortal = false;


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
        quick = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "QUICK");
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
        //renderInv();
        engine.player.inv.show(screen, invX, invY);
        engine.player.inv.drawCooldown(screen, invX, invY);

        engine.player.stats.show(screen, GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2);

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
        quick.show(screen);
        back.show(screen);
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
            loadScroll.scrollElements = GameFile.loadSaves();
            game.setState("LOAD");
        } else if (game.STATE == "PAUSED" && menu.pressed()) {
            GameFile.saveGame();
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
                guestMode = false;
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
                guestMode = false;
                GameFile.saveGame();
                game.setState("PLAYING");
            }
        } else if (game.STATE == "NEWGAME" && quick.pressed()) {
            guestMode = true;
            characterNaming = false;
            loadedPlayerName = "GUEST";
            game.setState("PLAYING");
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