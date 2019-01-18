package App;

import Engine.Engine;
import File.GameFile;
import GUI.GUI;
import Settings.Settings;
import Sprites.Sprites;
import Utility.Constants;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class Game extends PApplet{

    // HashMap<String, SoundFile> soundFiles;


    public static String STATE;
    public static String PREV_STATE;

    public static PGraphics debugScreen;
    public static boolean drawDebug = false;

    public static void main(String[] args) {
        String[] appletArgs = new String[] { "Game" };
        Game game = new Game();
        PApplet.runSketch(appletArgs, game);
    }

    public void settings() {
        size(1080, 720, FX2D);
        noSmooth();
    }

    public void setup() {
        frameRate(60);

        surface.setTitle("Unexpected Orcs");
        surface.setIcon(loadImage("/assets/sprites/icon.png"));


        Constants.setGame(this);

        setState("LOADING");
        thread("load");
        // thread("loadSounds");

        bitcell = createFont("./assets/fonts/bitcell.ttf", TILE_SIZE);
        textFont(bitcell);
        textAlign(CENTER, CENTER);
        textSize(TILE_SIZE);

        debugScreen = createGraphics(width, height);
    }

    public void draw() {
        updateMouse();
        switch(STATE) {
            case "LOADING":
                drawLoading();
                break;
            case "MENU":
                gui.drawMenu();
                break;
            case "OPTIONS":
                gui.drawOptions();
                break;
            case "PLAYING":
                //thread("update");
                engine.update();
                engine.show();
                gui.drawPlay();
                if(drawDebug) {
                    image(debugScreen, 0, 0);
                    debugScreen.beginDraw();
                    debugScreen.clear();
                    debugScreen.endDraw();
                }
                //background(255);
                //engine.player.stats.show(this.getGraphics(), 0, 0);

                break;
            case "PAUSED":
                engine.show();
                gui.drawPaused();
                break;
            case "DEAD":
                gui.drawDead();
                break;
            case "SAVE":
                gui.drawSave();
                break;
            case "NEWGAME":
                gui.drawNewGame();
                break;
            case "LOAD":
                gui.drawLoad();
                break;
        }
    }

    public void mouseReleased() {
       gui.handleMouseReleased();
    }

    public void mouseWheel(MouseEvent e) {
        if(STATE == "PLAYING"){
            miniMapZoom -= e.getCount();
            miniMapZoom = constrain(miniMapZoom, zoomMin, zoomMax);
        } else if(STATE == "LOAD") {
            gui.loadScroll.changeScrollPosition(e.getCount());
        }
    }


    public void keyPressed() {
        if (remapNextKey) remapKey(remapAction, keyCode);
        if (keyCode == UP_KEY) keys[up] = 1;
        if (keyCode == LEFT_KEY) keys[left] = 1;
        if (keyCode == DOWN_KEY) keys[down] = 1;
        if (keyCode == RIGHT_KEY) keys[right] = 1;
        if (keyCode == ABILITY_KEY) keys[ability] = 1;
        if(characterNaming) gui.keyPressed(key);
    }

    public void keyReleased() {
        if (keyCode == UP_KEY) keys[up] = 0;
        if (keyCode == LEFT_KEY) keys[left] = 0;
        if (keyCode == DOWN_KEY) keys[down] = 0;
        if (keyCode == RIGHT_KEY) keys[right] = 0;
        if (keyCode == ABILITY_KEY) keys[ability] = 0;
    }

    public void dispose() {
        //runs when the "x" button is pressed
        quitGame();
    }

    public void quitGame() {
        GameFile.saveGame();
        exit();
    }

    public void setState(String state) {
        remapNextKey = false;
        PREV_STATE = STATE;
        STATE = state;
    }

    public void revertState() {
        STATE = PREV_STATE;
    }

    public void load() {
        Sprites.loadAssets(this);

        loadStats();
        Settings.loadSettings();

        Constants.setEngine(new Engine());
        Constants.setGUI(new GUI());

        setState("MENU");
    }

    public void drawLoading() {
        clear();
        fill(0);
        rect(0, 0, width, height);
        fill(255);
        textAlign(game.CENTER, game.CENTER);
        text("Loading", width/2, height/2);
        text(loadMessage, width/2, height/2 + TILE_SIZE);
    }

    public void updateMouse() {
        if(mousePressed) {
            mouseDownCount ++;
            mouseUpCount = 0;
        } else {
            mouseUpCount ++;
            mouseDownCount = 0;
        }

        mouseReleased = mouseUpCount < mouseCountThreshold && !mousePressed;
        mouseClicked = mouseDownCount < mouseCountThreshold && mousePressed;
    }

    public void loadClosestPortal() {
        engine.currentLevel = engine.getClosestPortal().getLevel();
        engine.clearDrops();
        engine.player.x = engine.currentLevel.start.x;
        engine.player.y = engine.currentLevel.start.y;
        GameFile.saveStats(loadedPlayerName);
        setState("PLAYING");
    }
}
