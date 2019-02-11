package GUI.Screens;

import File.GameFile;
import GUI.Button;
import processing.core.PGraphics;

import static Utility.Constants.*;
import static Utility.Constants.game;

public class PausedScreen extends GUIScreen {

    private static Button play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
    private static Button load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 1, "LOAD");
    private static Button options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
    private static Button menu = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "MENU");

    public static void show(PGraphics screen) {
        //Draws the paused overlay
        PlayScreen.show(screen);
        screen.beginDraw();
        //clearScreen();
        screen.fill(0, 100);
        screen.rect(-TILE_SIZE, -TILE_SIZE, game.width + TILE_SIZE, game.height + TILE_SIZE);
        menu.show(screen);
        options.show(screen);
        play.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(play.pressed()) {
            game.setState("PLAYING");
            engine.updateMillis();
        } else if(options.pressed()) {
            game.setState("OPTIONS");
        } else if (load.pressed()) {
            game.setState("LOAD");
        } else if (menu.pressed()) {
            game.setState("MENU");
            GameFile.saveGame();
            engine.initiateDrops();
        }
    }

    public static void refresh() {
        play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
        load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 1, "LOAD");
        options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
        menu = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "MENU");
    }

}
