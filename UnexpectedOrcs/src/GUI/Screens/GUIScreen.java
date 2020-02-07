package GUI.Screens;

import processing.core.PGraphics;

import static Utility.Constants.*;

public class GUIScreen {

    public static void show(PGraphics screen) {}

    public static void handleMouseReleased() {}

    public static void refresh() {}

    protected static void clearScreen(PGraphics screen) {
        screen.background(0, 0);
    }

    protected static void background(PGraphics screen) {
        screen.image(game.title, 0, 0, game.width, game.height);
    }

    public static void handleKeyInput(char key) {}

}
