package com.bmd.game.Util;

import com.badlogic.gdx.Gdx;
import com.bmd.game.Sprites.Sprites;
import com.bmd.game.Tiles.Tiles;

public class Util {

    public final static int GUI_WIDTH = 240;
    public final static int SCALE = Tiles.TILE_SIZE/ Sprites.SPRITE_SIZE;
    public final static int width = Gdx.graphics.getWidth();
    public final static int height = Gdx.graphics.getHeight();

    public static float exp(float pow) {
        return (float)Math.pow(Math.E, pow);
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        float xdiff = (float)Math.pow(x2 - x1, 2);
        float ydiff = (float)Math.pow(y2 - y1, 2);
        return (float)Math.sqrt(xdiff + ydiff);
    }

    public static int sign(float value) {
        if(value == 0) {
            return 0;
        } else {
            return (value > 0 ? 1 : -1);
        }
    }

    //Returns whether a point (px, py) is inside a box (bx, by, w, h);
    public  static boolean pointInBox(float px, float py, float bx, float by, float w, float h) {
        return((px > bx) && (px < bx + w) && (py > by) && (py < by + h));
    }

    public static boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        // calculate the direction of the lines
        float uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
        float uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) return true;
        return false;
    }

    public static boolean linePoint(float x1, float y1, float x2, float y2, float px, float py) {

        // get distance from the point to the two ends of the line
        float d1 = dist(px, py, x1, y1);
        float d2 = dist(px, py, x2, y2);

        // get the length of the line
        float lineLen = dist(x1, y1, x2, y2);

        // since floats are so minutely accurate, add
        // a little buffer zone that will give collision
        float buffer = 0.1f;    // higher # = less accurate

        // if the two distances are equal to the line's
        // length, the point is on the line!
        // note we use the buffer here to give a range,
        // rather than one #
        if (d1+d2 >= lineLen-buffer && d1+d2 <= lineLen+buffer) {
            return true;
        }
        return false;
    }

    public static float roundTo(float number, int rounder) {
        float num = ((float)Math.floor(number * rounder)) / rounder;
        return num;
    }
}
