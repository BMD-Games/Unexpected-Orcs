package GUI;

import processing.core.PGraphics;

import static Utility.Constants.*;

public class DisplayBar {

    public float x, y, w, h;
    private float percentFull;
    int current, total;
    private int c;
    private HUDElement element;

    public DisplayBar(float x, float y, int c) {
        this.x = x;
        this.y = y;
        this.h = TILE_SIZE / 2;
        this.w = TILE_SIZE * 3 - 8;
        this.c = c;
        percentFull = 1.0f;

        element = new HUDElement(x - TILE_SIZE/16, y, "BAR");
    }

    public void show(PGraphics screen) {
        screen.fill(c);
        screen.textSize(TILE_SIZE/2);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.noStroke();
        screen.rect(x, y, w * percentFull, h);
        screen.fill(255);
        screen.text(current + "/" + total, x + w/2, y + h/2 - 5);

        element.show(screen);
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
        element.x = x - TILE_SIZE/16;
        element.y = y;
    }

    public void updateBar(float current, float total) {
        float tmp = game.constrain(current, 0, total);
        this.current = (int)current;
        this.total = (int)total;
        percentFull = tmp / total;
    }
}