package GUI.Screens;

import GUI.Button;
import GUI.Slider.Slider;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class TestScreen extends GUIScreen {

    private static Slider sliderV = new Slider(game.width/3, game.height/2, TILE_SIZE * 4, true);

    private static Slider sliderH = new Slider(game.width/3 * 2, game.height/2, TILE_SIZE * 4, false);

    private static Button back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");

    public static void show(PGraphics screen) {
        screen.beginDraw();
        clearScreen(screen);
        background(screen);

        sliderV.update();
        sliderV.show(screen);

        Settings.Settings.setSoundVolume(sliderV.percent);

        sliderH.update();
        sliderH.show(screen);
        Settings.Settings.setMasterVolume(sliderH.percent);

        back.show(screen);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.revertState();
        }
    }

    public static void refresh() {
        sliderV = new Slider(game.width/3, game.height/2, TILE_SIZE * 4, true);

        sliderH = new Slider(game.width/3 * 2, game.height/2, TILE_SIZE * 4, false);

        back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");
    }
}
