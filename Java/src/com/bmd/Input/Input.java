package com.bmd.Input;

import java.awt.event.KeyEvent;

public class Input {

    public static int mouseX, mouseY;

    public static short[] keys = {0, 0, 0, 0, 0};

    public static int mouseX() {
        return mouseX;
    }

    public static int mouseY() {
        return mouseY;
    }

    public static final char BACKSPACE = 8;
    public static final char TAB       = 9;
    public static final char ENTER     = 10;
    public static final char RETURN    = 13;
    public static final char ESC       = 27;
    public static final char SPACE     = 32;
    public static final char DELETE    = 127;

    // i.e. if ((key == CODED) && (keyCode == UP))
    public static final int CODED     = 0xffff;

    // key will be CODED and keyCode will be this value
    public static final int UP        = KeyEvent.VK_UP;
    public static final int DOWN      = KeyEvent.VK_DOWN;
    public static final int LEFT      = KeyEvent.VK_LEFT;
    public static final int RIGHT     = KeyEvent.VK_RIGHT;

    // key will be CODED and keyCode will be this value
    public static final int ALT       = KeyEvent.VK_ALT;
    public static final int CONTROL   = KeyEvent.VK_CONTROL;
    public static final int SHIFT     = KeyEvent.VK_SHIFT;

}
