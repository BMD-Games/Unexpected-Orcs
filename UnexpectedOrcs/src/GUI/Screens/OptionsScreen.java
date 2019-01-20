package GUI.Screens;

import GUI.Button;
import GUI.Scroll.ScrollElement;
import GUI.Scroll.ScrollWindow;
import Settings.Settings;
import processing.core.PGraphics;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class OptionsScreen extends GUIScreen {


    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");

    public static ScrollWindow settingsScroll = new ScrollWindow(width/8, height/4, width/4 * 3, height/2, Settings.getElements());

    public static void show(PGraphics screen) {
        //Draws the options menu
        screen.beginDraw();
        screen.textAlign(game.CENTER, game.CENTER);
        screen.textSize(TILE_SIZE/2);

        clearScreen(screen);
        game.image(game.title, 0, 0, width, height);

        settingsScroll.update();
        settingsScroll.show(screen);

        back.show(screen);
        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.revertState();
        }
        settingsScroll.handleMouse();
    }
}