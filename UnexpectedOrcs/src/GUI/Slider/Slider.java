package GUI.Slider;

import Utility.Util;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class Slider {

    private float x, y, w, h, r;

    private boolean vertical;

    public float percent = 1;

    public Slider(float x, float y, float len, boolean vertical) {
        this.x = x;
        this.y = y;

        this.w = len;
        this.h = gui.buff * 2;
        if(vertical) {
            this.w = gui.buff * 2;
            this.h = len;
        }

        this.r = SPRITE_SIZE;

        this.vertical = vertical;
    }

    public void update() {
        if(game.mousePressed && Util.pointInBox(game.mouseX, game.mouseY, x - r, y - r, w + r * 2, h + r * 2)) {
            if(vertical) {
                percent = game.constrain(((y + h) - game.mouseY)/h, 0, 1);
            } else {
                percent = game.constrain((game.mouseX - x)/w, 0, 1);
            }
            game.println(percent);
        }
    }

    public void show(PGraphics screen) {
        screen.fill(50);
        screen.noStroke();

        screen.rect(x, y, w, h);

        screen.fill(200);

        float tmpX = w/2;
        float tmpY = h/2;

        if(vertical) {
            tmpY = h * percent;
        } else {
            tmpX = w * percent;
        }

        screen.ellipse(x + tmpX, y + h - tmpY, r * 2, r * 2);
    }

}
