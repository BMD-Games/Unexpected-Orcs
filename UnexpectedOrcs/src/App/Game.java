package App;

import Engine.Engine;
import File.GameFile;
import GUI.GUI;
import GUI.Screens.LoadScreen;
import GUI.Screens.LoadingScreen;
import GUI.Screens.NewGameScreen;
import GUI.Screens.OptionsScreen;
import Settings.Settings;
import Sound.SoundManager;
import Sprites.Sprites;
import Utility.Constants;
import Utility.Util;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;

import java.util.Objects;

import static Levels.Generator.getBitMaskValue;
import static Settings.Settings.*;
import static Utility.Constants.*;

public class Game extends PApplet{

    // HashMap<String, SoundFile> soundFiles;


    public String STATE;
    public String PREV_STATE;

    public PImage title;

    public static PGraphics debugScreen;
    public static boolean drawDebug = false;

    public static void main(String[] args) {
        String[] appletArgs = new String[] { "Game" };
        Game game = new Game();
        PApplet.runSketch(appletArgs, game);
    }

    public void settings() {
        size(1280, 720, P2D);
        pixelDensity(displayDensity());
        noSmooth();
        PJOGL.setIcon("/assets/sprites/icon.png");
    }

    public void setup() {
        frameRate(60);

        surface.setTitle("Unexpected Orcs");

        setState("LOADING");
        thread("load");

        bitcell = createFont("./assets/fonts/bitcell.ttf", TILE_SIZE);

        outlineShader = loadShader("/assets/shaders/outlineFrag.glsl");//, "/assets/shaders/outlineVert.glsl");
        outlineShader.set("scale", SCALE);

        textFont(bitcell);
        textAlign(CENTER, CENTER);
        textSize(TILE_SIZE);
        title = loadImage("/assets/sprites/title.png");

        debugScreen = createGraphics(width, height);
    }

    public void draw() {
        updateMouse();

        if(STATE.equals( "PLAYING")) {
            engine.update();
            engine.show();
            if(drawDebug) {
                image(debugScreen, 0, 0);
                if(frameCount % 30 == 0) debugScreen.save("out/level/debug.jpg");
                debugScreen.beginDraw();
                debugScreen.clear();
                debugScreen.endDraw();
            }
        } else if (STATE.equals( "PAUSED")) {
            engine.show();
        }

        if(STATE.equals( "LOADING")) {
            LoadingScreen.show(g);
        } else {
            gui.show();
        }

    }

    public void mouseReleased() {
       if(gui != null) gui.handleMouseReleased();
    }

    public void mouseWheel(MouseEvent e) {
        if(STATE.equals("PLAYING")){
            miniMapZoom -= e.getCount();
            miniMapZoom = constrain(miniMapZoom, zoomMin, zoomMax);
        } else if(STATE.equals( "LOAD")) {
            LoadScreen.loadScroll.changeScrollPosition(e.getCount());
        } else if(STATE.equals( "OPTIONS")) {
            OptionsScreen.settingsScroll.changeScrollPosition(e.getCount());
        }
    }

    public void keyPressed() {
        if (remapNextKey) remapKey(remapAction, keyCode);
        if (keyCode == UP_KEY) keys[up] = 1;
        if (keyCode == LEFT_KEY) keys[left] = 1;
        if (keyCode == DOWN_KEY) keys[down] = 1;
        if (keyCode == RIGHT_KEY) keys[right] = 1;
        if (keyCode == ABILITY_KEY) keys[ability] = 1;
        if (keyCode == INTERACT_KEY) keys[interact] = 1;
        if(characterNaming) NewGameScreen.keyPressed(key);
        if(key == '`') {
            drawDebug = !drawDebug;
            String state = "disabled";
            if(drawDebug) state = "enabled";
            engine.addText("Debug " + state, engine.player.x - 1, engine.player.y, 1f, 255);
        }
    }

    public void keyReleased() {
        if (keyCode == UP_KEY) keys[up] = 0;
        if (keyCode == LEFT_KEY) keys[left] = 0;
        if (keyCode == DOWN_KEY) keys[down] = 0;
        if (keyCode == RIGHT_KEY) keys[right] = 0;
        if (keyCode == ABILITY_KEY) keys[ability] = 0;
        if (keyCode == INTERACT_KEY) keys[interact] = 0;

        if(STATE.equals("PLAYING")) {
            if(keyCode == HOT_SWAP_0) {
                engine.player.inv.hotSwap(0);
            } else if(keyCode == HOT_SWAP_1) {
                engine.player.inv.hotSwap(1);
            } else if(keyCode == HOT_SWAP_2) {
                engine.player.inv.hotSwap(2);
            } else if(keyCode == HOT_SWAP_3) {
                engine.player.inv.hotSwap(3);
            }
        }

    }

    public void dispose() {
        //runs when the "x" button is pressed
        quitGame();
    }

    public void quitGame() {
        GameFile.saveGame();
        exit();

        //give the game 1/2 a second to close
        delay(500);
        //force the game to close if it doesn't
        Runtime.getRuntime().halt(0);
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
        loadPercentage = 0;
        loadMessage = "Setting up variables";
        Constants.setGame(this);

        loadPercentage = 1/7f;
        loadMessage = "Loading Settings";
        Settings.loadSettings();

        loadPercentage = 2/7f;
        loadMessage = "Loading Assets";
        Sprites.loadAssets(this);


        loadPercentage = 3/7f;
        loadMessage = "Generating level";
        Constants.setEngine(new Engine());


        loadPercentage = 4/7f;
        loadMessage = "Making the GUI beautiful";
        Constants.setGUI(new GUI());

        loadPercentage = 5/7f;
        loadMessage = "Loading Sounds";
        SoundManager.loadSounds(this);
        SoundManager.playMusic("TEST_MUSIC");

        loadPercentage = 6/7f;
        loadMessage = "Loading Stats";
        loadStats();

        loadPercentage = 1;
        loadMessage = "DONE!";
        setState("MENU");
    }

    private void updateMouse() {
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
        loadPercentage = 0;
        loadMessage = "Generating Level";
        engine.currentLevel = engine.getClosestPortal().getLevel();

        loadPercentage = 1/4f;
        loadMessage = "Sweeping the floors";
        engine.initiateDrops();

        loadPercentage = 2/4f;
        loadMessage = "Putting you in the right spot";
        engine.player.x = engine.currentLevel.start.x;
        engine.player.y = engine.currentLevel.start.y;

        loadPercentage = 3/4f;
        loadMessage = "Saving the Game";
        GameFile.saveGame();

        loadPercentage = 1;
        loadMessage = "DONE!";
        setState("PLAYING");
    }

    public void resize(int w, int h) {
        surface.setSize(w, h);
    }
}
