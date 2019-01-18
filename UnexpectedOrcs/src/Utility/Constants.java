package Utility;

import Engine.Engine;
import Entities.Player;
import GUI.GUI;
import Items.ItemFactory;
import App.Game;
import processing.core.PFont;

import java.util.HashMap;

import static Utility.Colour.*;

public class Constants {

    public static Game game;

    public final static int width = 1080;
    public final static int height = 720;

    public final static int GUI_WIDTH = 240;

    public final static int TILE_SIZE = 64;
    public final static int SPRITE_SIZE = 16;
    public final static int SCALE = TILE_SIZE/SPRITE_SIZE;

    public static int[] keys = {0, 0, 0, 0, 0};
    public static float miniMapZoom = 1;
    public static float zoomMax = 5;
    public static float zoomMin = 1;
    public static boolean inMenu = false;

    public static int edgeSize = 1;

    public static PFont bitcell;

    public final static int up = 0, down = 1, left = 2, right = 3, ability = 4, topLeft = 4, topRight = 5, bottomLeft = 6, bottomRight = 7;

    public static String loadMessage = "Litty";
    public static String loadedPlayerName = "";
    public static Player[] loadedPlayers = new Player[0];

    public static boolean guestMode = false;

    public static Engine engine;
    public static GUI gui;
    public static ItemFactory itemFactory = new ItemFactory();

    public static HashMap<String, Boolean> STATUSES = new HashMap<String, Boolean>();
    public static HashMap<String, Integer> statColours = new HashMap<String, Integer>();

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

    String[] stats = {"HEALTH", "MANA", "SPEED", "WISDOM", "DEFENCE", "ATTACK"};

    public static void setGame(Game g) {
        game = g;
    }

    public static void setEngine(Engine e) {
        engine = e;
    }

    public static void setGUI(GUI g) {
        gui = g;
    }



}
