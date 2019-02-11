package GUI.Screens;

import GUI.Bars.LoadBar;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class LoadingScreen extends GUIScreen {

    private static LoadBar loadingBar = new LoadBar(game.width/2 - TILE_SIZE * 3/2, game.height/2 - TILE_SIZE * 3);

    public static void show(PGraphics screen) {
        loadingBar.updatePercentage(loadPercentage);
        screen.textSize(TILE_SIZE);
        screen.background(0);
        loadingBar.show(screen);
        screen.fill(255);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text("Loading", game.width/2, game.height/2);
        screen.text(loadMessage, game.width/2, game.height/2 + TILE_SIZE);
    }

    public static void refresh() {
        loadingBar = new LoadBar(game.width/2 - TILE_SIZE * 3/2, game.height/2 - TILE_SIZE * 3);
    }

}
