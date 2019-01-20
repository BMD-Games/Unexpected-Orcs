package GUI.Screens;

import GUI.Button;
import GUI.Slider.Slider;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class TestScreen extends GUIScreen {

    private static Slider sliderV = new Slider(width/3, height/2, TILE_SIZE * 4, true);

    private static Slider sliderH = new Slider(width/3 * 2, height/2, TILE_SIZE * 4, false);

    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");

    public static void show(PGraphics screen) {
        screen.beginDraw();
        clearScreen(screen);
        background(screen);

        sliderV.update();
        sliderV.show(screen);

        sliderH.update();
        sliderH.show(screen);

        back.show(screen);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.revertState();
        }
    }
}
