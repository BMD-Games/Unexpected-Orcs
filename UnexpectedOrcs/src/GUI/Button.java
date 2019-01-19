package GUI;

import Utility.Util;

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
}