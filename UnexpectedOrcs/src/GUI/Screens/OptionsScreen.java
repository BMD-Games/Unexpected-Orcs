package GUI.Screens;

import GUI.Button;
import GUI.Scroll.ScrollElement;
import GUI.Scroll.ScrollWindow;
import GUI.Scroll.TabbedScroll;
import Settings.Settings;
import processing.core.PGraphics;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class OptionsScreen extends GUIScreen {


    private static Button back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 4, "BACK");

    public static TabbedScroll settingsScroll = new TabbedScroll(game.width/8, game.height/4 + TILE_SIZE/2, game.width/4 * 3, game.height/2 + TILE_SIZE/2, Settings.getElements());

    public static void show(PGraphics screen) {
        screen.beginDraw();
        screen.textAlign(game.CENTER, game.CENTER);
        screen.textSize(TILE_SIZE/2);

        clearScreen(screen);
        game.image(game.title, 0, 0, game.width, game.height);

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

    public static void refresh() {
        back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 4, "BACK");
        int tab = settingsScroll.currentTab;
        settingsScroll = new TabbedScroll(game.width/8, game.height/4 + TILE_SIZE/2, game.width/4 * 3, game.height/2 + TILE_SIZE/2, Settings.getElements());
        settingsScroll.currentTab = tab;
    }
}