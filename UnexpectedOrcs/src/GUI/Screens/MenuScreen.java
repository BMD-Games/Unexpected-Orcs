package GUI.Screens;

import File.GameFile;
import GUI.Button;
import Utility.Util;
import processing.core.PGraphics;

import static Sprites.Sprites.tileSprites;
import static Tiles.Tiles.BLOOD_EYE;
import static Utility.Constants.*;

public class MenuScreen extends GUIScreen {

    private static Button play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
    private static Button load = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 0.5f, "LOAD");
    private static Button options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "OPTIONS");
    private static Button exit = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "EXIT");

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

        screen.image(tileSprites.get(BLOOD_EYE), 0, 0);

        screen.endDraw();
        //draw to screen
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(play.pressed()) {
            game.setState("NEWGAME");
        } else if(options.pressed()) {
            game.setState("OPTIONS");
        } else if (load.pressed()) {
            game.setState("LOAD");
            LoadScreen.loadScroll.setScrollElements(GameFile.loadAllSaves());
        } else if (exit.pressed()) {
            game.quitGame();
        } else if(Util.pointInBox(game.mouseX, game.mouseY, 0, 0, TILE_SIZE, TILE_SIZE)) {
            game.setState("TEST");
        }
    }
}
