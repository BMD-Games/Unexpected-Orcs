package GUI.Screens;

import GUI.Button;
import processing.core.PGraphics;

import static Utility.Constants.TILE_SIZE;
import static Utility.Constants.game;

public class CreditsScreen extends GUIScreen {

    private static Button back = new Button(game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "BACK");

    public static void show(PGraphics screen) {
        //Draws the main menu
        screen.beginDraw();
        //background
        background(screen);
        //buttons
        back.show(screen);

        screen.fill(0, 60);
        screen.noStroke();
        screen.rect(0, screen.height/4, screen.width, TILE_SIZE * 4.5f);

        screen.fill(255);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.textSize(TILE_SIZE);
        screen.text("CREDITS", screen.width/2, screen.height/3);
        screen.textSize(TILE_SIZE/2);
        screen.text("Matthew Jones", screen.width/2, screen.height/2 - TILE_SIZE);
        screen.text("Daniel Jones", screen.width/2, screen.height/2);
        screen.text("Barney Whiteman", screen.width/2, screen.height/2 + TILE_SIZE);


        screen.endDraw();
        //draw to screen
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.revertState();
        }
    }

    public static void refresh() {
        back = new Button(game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "BACK");
    }
}
