package GUI;

import Utility.Util;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class Button extends HUDElement {

    public Button(float x, float y, String spriteName) {
        super(x, y, spriteName);
    }

    public Button(float x, float y, float w, float h, String spriteName) {
        super(x, y, w, h, spriteName);
    }

    public boolean pressed() {
        return Util.pointInBox(game.mouseX, game.mouseY, x, y, w, h);
    }

    public void show(PGraphics screen) {
        super.show(screen);
        if(pressed()) {
            screen.fill(100, 50);
            screen.noStroke();
            screen.rect(x, y, w, h);
        }
    }
}