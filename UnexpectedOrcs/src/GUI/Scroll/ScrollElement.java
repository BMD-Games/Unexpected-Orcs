package GUI.Scroll;

import GUI.GUI;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class ScrollElement {

    public String title, text;
    int x, y, w, h;
    int buffer = 10;

    public ScrollElement(String title, String text, int h) {
        this.title = title;
        this.text = text;
        this.h = h;
    }

    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {

        this.x = xpos;
        this.y = ypos;
        this.w = w;

        screen.fill(180);
        if (selected) {
            screen.fill(200);
        }
        screen.noStroke();
        screen.rect(xpos, ypos, w, h);
        screen.fill(255);
        screen.textAlign(game.LEFT, game.TOP);
        screen.textSize(40);
        screen.text(title, xpos + buffer, ypos + buffer);
        screen.textSize(20);
        screen.text(text, xpos + buffer, ypos + TILE_SIZE / 2 + buffer);
        if (selected) {
            screen.strokeWeight(4);
            screen.stroke(200);
            screen.noFill();
            screen.rect(x, y, w, h);
        }
        screen.strokeWeight(1);
    }
}