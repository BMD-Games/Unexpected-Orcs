package GUI.PopUp;

import GUI.Button;
import processing.core.PGraphics;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class PopUpWindow {

    public String title, message;
    protected float x, y, w, h;

    Button close;

    public PopUpWindow(String title, String message, float x, float y, float w, float h) {
        this.title = title;
        this.message = message;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        close = new Button(x + w - SPRITE_SIZE * 5/2, y + SPRITE_SIZE/2, SPRITE_SIZE * 2, SPRITE_SIZE * 2, "DELETE");
    }

    public int show(PGraphics screen) {
        screen.beginDraw();
        screen.fill(50, 100);
        screen.rect(0, 0, width, height);
        screen.fill(150);
        screen.stroke(100);
        screen.rect(x, y, w, h);

        screen.line(x, y + TILE_SIZE, x + w, y + TILE_SIZE);

        screen.fill(200);

        screen.textAlign(game.LEFT, game.TOP);
        screen.textSize(TILE_SIZE);
        screen.text(title, x + gui.buff, y + gui.buff);

        screen.textSize(TILE_SIZE/2);
        screen.text(message, x + gui.buff, y + TILE_SIZE + gui.buff, w - gui.buff * 2, h - TILE_SIZE - gui.buff * 2);

        close.show(screen);

        if(close.pressed() && mouseReleased) {
            return CANCEL;
        }

        return OPEN;
    }

}
