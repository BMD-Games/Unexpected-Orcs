package GUI.Scroll;

import GUI.Button;
import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Constants.*;

public class ToggleElement extends ScrollElement{

    private boolean value;
    private Button toggle = new Button(0, 0, "BLANK_1x1");
    private ToggleCallback callback;

    public ToggleElement(String title, boolean initialValue, ToggleCallback callback) {
        super(title, "", TILE_SIZE * 2);

        this.value = initialValue;
        this.callback = callback;
    }

    public ToggleElement(String title, ToggleCallback callback) {
        super(title, "", TILE_SIZE * 2);

        this.value = false;
        this.callback = callback;
    }

    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);

        toggle.x = xpos + w/2 - TILE_SIZE/2;
        toggle.y = ypos + TILE_SIZE/2;
        toggle.show(screen);

        if(value) {
            screen.image(guiSprites.get("TICK"), toggle.x, toggle.y, TILE_SIZE, TILE_SIZE);
        }

    }

    public void handleMouse() {
        if(toggle.pressed()) {
            value = !value;
            callback.toggle(value);
        }
    }
}
