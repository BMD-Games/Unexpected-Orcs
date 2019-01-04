package com.bmd.GUI;

import com.bmd.Util.Util;

public class Button extends HUDElement {

    Button(float x, float y, String spriteName) {
        super(x, y, spriteName);
    }

    public boolean pressed() {
        return Util.pointInBox(Util.mouseX(), Util.mouseY(), x, y, w, h);
    }
}