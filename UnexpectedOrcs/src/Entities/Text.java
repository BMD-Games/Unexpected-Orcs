package Entities;

import processing.core.PGraphics;
import processing.core.PVector;

import static Utility.Colour.*;
import static Utility.Constants.*;

public class Text {

    float x, y, life, time = 0;
    String message;
    int c;

    public Text(String message, float xp, float yp, float lifeTime, int c) {
        this.message = message;
        this.life = lifeTime;
        x = xp;
        y = yp;
        this.c = c;
    }

    public boolean update(double delta) {
        time += delta;
        return time < life;
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.textFont(bitcell);
        float a = 255 - (255 * time / life);
        screen.textSize(TILE_SIZE/2);
        outlineText(screen, message, x * TILE_SIZE - renderOffset.x, (y - (time/life)/2) * TILE_SIZE - renderOffset.y, colour(red(c), green(c), blue(c), a), colour(255, a));
    }

    public void outlineText(PGraphics screen, String s, float x, float y, int text, int outline) {
        screen.fill(outline);
        for (int i = -1; i < 2; i++) {
            screen.text(s, x + i, y);
            screen.text(s, x, y + i);
        }
        screen.fill(text);
        screen.text(s, x, y);
    }

}
