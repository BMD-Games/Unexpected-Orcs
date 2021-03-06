package GUI.Screens;

import App.GameState;
import File.GameFile;
import GUI.Button;
import Utility.Util;
import processing.core.PGraphics;

import static Utility.Constants.TILE_SIZE;
import static Utility.Constants.game;

public class MenuScreen extends GUIScreen {

    private static Button play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
    private static Button load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 0.5f, "LOAD");
    private static Button options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
    private static Button exit = new Button(game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "EXIT");
    private static Button credits = new Button(0, game.height - TILE_SIZE, "GS");

    public static void show(PGraphics screen) {
        //Draws the main menu
        screen.beginDraw();
        //background
        background(screen);
        //buttons
        play.show(screen);
        load.show(screen);
        options.show(screen);
        exit.show(screen);
        credits.show(screen);

        screen.endDraw();
        //draw to screen
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(play.pressed()) {
            game.setState(GameState.NEWGAME);
        } else if(options.pressed()) {
            game.setState(GameState.OPTIONS);
        } else if (load.pressed()) {
            game.setState(GameState.LOAD);
            LoadScreen.loadScroll.setScrollElements(GameFile.loadAllSaves());
        } else if (exit.pressed()) {
            game.quitGame();
        } else if(credits.pressed()) {
            game.setState(GameState.CREDITS);
        } else if(Util.pointInBox(game.mouseX, game.mouseY, 0, 0, TILE_SIZE, TILE_SIZE)) {
            game.setState(GameState.TEST);
        }
    }

    public static void refresh() {
        play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
        load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 0.5f, "LOAD");
        options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
        exit = new Button(game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "EXIT");
        credits = new Button(0, game.height - TILE_SIZE, "GS");
    }
}
