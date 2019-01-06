package com.bmd.GUI;

import com.bmd.Input.Input;
import com.bmd.Util.Util;

public class Button extends HUDElement {

    Button(float x, float y, String spriteName) {
        super(x, y, spriteName);
    }

    public boolean pressed() {
        return Util.pointInBox(Input.mouseX(), Input.mouseY(), x, y, w, h);
    }
}