package GUI;

import Utility.Util;

import static Utility.Constants.game;

public class Button extends HUDElement {

    public Button(float x, float y, String spriteName) {
        super(x, y, spriteName);
    }

    public boolean pressed() {
        return Util.pointInBox(game.mouseX, game.mouseY, x, y, w, h);
    }
}