package GUI.Screens;

import GUI.Button;
import processing.core.PGraphics;

import static Utility.Constants.*;
import static Utility.Constants.game;

public class DeadScreen extends GUIScreen {

    private static Button menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, "MENU");

    public static void show(PGraphics screen) {
        screen.beginDraw();
        background(screen);

        screen.fill(200, 0, 0);
        screen.text("Nibba u dead", width/2, height/2);
        menu.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(menu.pressed()) {
            game.setState("MENU");
        }
    }
}