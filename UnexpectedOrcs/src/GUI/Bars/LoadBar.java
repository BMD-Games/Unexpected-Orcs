package GUI.Bars;

import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Colour.colour;
import static Utility.Constants.*;

public class LoadBar extends DisplayBar {
    public LoadBar(float x, float y) {
        super(x, y, TILE_SIZE*3, TILE_SIZE/2, colour(100, 250, 0));
    }

    public void show(PGraphics screen) {
        screen.textSize(TILE_SIZE/2);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.noStroke();

        screen.fill(88);
        screen.rect(x - 4, y - 4, w + 8, h + 8);

        screen.fill(c);
        screen.rect(x, y, w * percentFull, h);

        screen.fill(255);
        screen.text(current + "%", x + w/2, y + h/2 - 5);
    }

    public void updatePercentage(float percent) {
        percentFull = percent;
        this.current = (int)(percent * 100);
    }
}
