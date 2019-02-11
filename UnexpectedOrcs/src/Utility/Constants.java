package Utility;

import App.Game;
import Engine.Engine;
import Entities.Player;
import GUI.GUI;
import Items.ItemFactory;
import processing.core.PFont;
import processing.core.PVector;
import processing.opengl.PShader;

import java.util.HashMap;

import static Utility.Colour.colour;

public class Constants {

    public static Game game;

    public final static PVector[] resolutions = new PVector[] {
        new PVector(848, 480),
        new PVector(1280, 720),
        new PVector(1920, 1080),
        new PVector(2716, 1524),
        new PVector(3840, 2160)
    };

    public final static String[] resolutionNames = new String[] {
            "WVGA (848x480)",
            "720p (1280x720)",
            "1080p (1920x1080)",
            "2.7k (2716x1524)",
            "4k (3840x2160)"
    };


    public final static int SPRITE_SIZE = 16;
    public final static int DESIRED_NUM_TILE_V = 10;

    public static int SCALE = (720/DESIRED_NUM_TILE_V)/SPRITE_SIZE;
    public static int TILE_SIZE = SPRITE_SIZE * SCALE;
    public static int GUI_WIDTH = TILE_SIZE * 4;

    //keeps track of buttons being pressed
    public static int[] keys = {0, 0, 0, 0, 0, 0};

    public static float miniMapZoom = 1;
    public static float zoomMax = 5;
    public static float zoomMin = 1;
    public static boolean inMenu = false;

    public static int edgeSize = 1;

    public static PFont bitcell;

    public static PShader outlineShader;

    //movements
    public final static int up = 0, down = 1, left = 2, right = 3, ability = 4, interact = 5, topLeft = 4, topRight = 5, bottomLeft = 6, bottomRight = 7;

    public static String loadMessage = "Litty";
    public static float loadPercentage = 0.0f;
    public static String loadedPlayerName = "";
    public static Player[] loadedPlayers = new Player[0];

    public static boolean showConfirmation = false;

    public static boolean guestMode = false;

    public static Engine engine;
    public static GUI gui;
    public static ItemFactory itemFactory = new ItemFactory();

    public static HashMap<String, Boolean> STATUSES = new HashMap<String, Boolean>();
    public static HashMap<String, Integer> statColours = new HashMap<String, Integer>();

    //Return values for pop-up windows
    public static int OPEN = 0, CONFIRM = 1, CANCEL = 2;

    //List of available stats, used to confirm genuine status effects
    String[] stats = {"HEALTH", "MANA", "SPEED", "WISDOM", "DEFENCE", "ATTACK"};

    public static void loadStats() {
        STATUSES.put("SICK", true);
        STATUSES.put("HEALING", true);
        STATUSES.put("WEAK", true);
        STATUSES.put("DAMAGING", true);
        STATUSES.put("CURSED", true);
        STATUSES.put("SMART", true);
        STATUSES.put("ARMOURBREAK", true);
        STATUSES.put("ARMOURED", true);
        STATUSES.put("DAZED", true);
        STATUSES.put("BEZERK", true);
        STATUSES.put("SLOWED", true);
        STATUSES.put("SWIFT", true);

        statColours.put("HEALTH", colour(230, 100, 100));
        statColours.put("MANA", colour(153, 217, 234));
        statColours.put("SPEED", colour(100, 230, 100));
        statColours.put("WISDOM", colour(140, 50, 230));
        statColours.put("DEFENCE", colour(0, 15, 230));
        statColours.put("ATTACK", colour(230, 150, 0));
        statColours.put("VITALITY", colour(255, 105, 180));
    }

    public static void setGame(Game g) {
        game = g;
    }

    public static void setEngine(Engine e) {
        engine = e;
    }

    public static void setGUI(GUI g) {
        gui = g;
    }

    public static boolean mouseReleased = false, mouseClicked = false;
    public static int mouseDownCount = 0, mouseUpCount = 0, mouseCountThreshold = 10;

}
