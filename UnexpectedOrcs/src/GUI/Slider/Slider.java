package GUI.Slider;

import Utility.Util;
import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Constants.*;

public class Slider {

    public float x, y;
    private float w, h, r;

    private boolean vertical;

    public float percent = 1;

    public Slider(float x, float y, float len, boolean vertical) {
        this.x = x;
        this.y = y;

        this.w = len;
        this.h = GUI.GUI.buff * 2;
        if(vertical) {
            this.w = GUI.GUI.buff * 2;
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
        }
    }

    public void show(PGraphics screen) {
        screen.fill(88);
        screen.noStroke();

        screen.rect(x, y, w, h);

        float tmpX = w/2;
        float tmpY = h/2;

        if(vertical) {
            tmpY = h * percent;
        } else {
            tmpX = w * percent;
        }

        screen.image(guiSprites.get("BLANK_1x1"), x + tmpX - TILE_SIZE/4, y + h - tmpY - TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
    }

}
