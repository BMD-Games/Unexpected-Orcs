package GUI.Scroll;

import GUI.Button;
import Sprites.TileSet;
import processing.core.PGraphics;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class KeyRemapElement extends ScrollElement {

    private Button keyRemap = new Button(0, 0, "BLANK_1x1");
    private int action;

    public KeyRemapElement(String title, int action) {
        super(title, "Click to remap this key", TILE_SIZE * 2);
        this.action = action;
    }

    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);

        keyRemap.x = xpos + w/2 - TILE_SIZE/2;
        keyRemap.y = ypos + TILE_SIZE/2;
        keyRemap.show(screen);

        screen.textSize(TILE_SIZE);
        screen.fill(0, 112, 188);
        screen.textAlign(game.CENTER, game.CENTER);
        if (!remapNextKey || remapAction != action) screen.text(getKeyString(action), keyRemap.x + keyRemap.w/2, keyRemap.y + keyRemap.h/3);


    }

    public void handleMouse() {
        remapNextKey = true;
        remapAction = action;
    }
}
