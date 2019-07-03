package Utility.Collision;

import Levels.Level;
import Utility.Util;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Rectangle {

    public static boolean validPosition(Level level, float x, float y, float w, float h) {
        for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
            for(int j = (int)(y - h/2); j <= (int)(y + h/2); ++j) {
                if(level.getTile(i, j).solid) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validTop(Level level, float x, float y, float w, float h) {
        for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
            if(level.getTile(i, (int)(y - h/2)).solid) {
                return false;
            }
        }
        return true;
    }

    public static boolean validBottom(Level level, float x, float y, float w, float h) {
        for(int i = (int)(x - w/2); i <= (int)(x + w/2); ++i) {
            if(level.getTile(i, (int)(y + h/2)).solid) {
                return false;
            }
        }
        return true;
    }

    public static boolean validLeft(Level level, float x, float y, float w, float h) {
        for(int i = (int)(y - h/2); i <= (int)(y + h/2); ++i) {
            if(level.getTile((int)(x - w/2), i).solid) {
                return false;
            }
        }
        return true;
    }

    public static boolean validRight(Level level, float x, float y, float w, float h) {
        for(int i = (int)(y - h/2); i <= (int)(y + h/2); ++i) {
            if(level.getTile((int)(x + w/2), i).solid) {
                return false;
            }
        }
        return true;
    }

    public static float[] adjust(Level level, float x, float y, float w, float h, float moveX, float moveY) {
        x = x + moveX;
        y = y + moveY;
        if(!validPosition(level, x, y, w, h)) {
            int xDir = Util.sign(moveX);
            int yDir = Util.sign(moveY);
            float checkX = xDir == 1 ? game.ceil(x) - w/2 - 0.01f : game.floor(x) + w/2 + 0.01f;
            float checkY = yDir == 1 ? game.ceil(y) - h/2 - 0.01f : game.floor(y) + h/2 + 0.01f;
            boolean xAdjusted = false, yAdjusted = false;
            if((xDir == 1 && !validRight(level, x, checkY, w, h)) || (xDir == -1 && !validLeft(level, x, checkY, w, h))) {
                x = checkX;
                xAdjusted = true;

            }
            if((yDir == 1 && !validBottom(level, checkX, y, w, h)) || (yDir == -1 && !validTop(level, checkX, y, w, h))) {
                y = checkY;
                yAdjusted = true;
            }
            if(!(xAdjusted || yAdjusted)) {
                if((xDir == 1 && !validRight(level, x, y, w, h)) || (xDir == -1 && !validLeft(level, x, y, w, h))) {
                    x = checkX;
                    xAdjusted = true;
                }
                if((yDir == 1 && !validBottom(level, x, y, w, h)) || (yDir == -1 && !validTop(level, checkX, y, w, h))) {
                    y = checkY;
                    yAdjusted = true;
                }
            }
            if(xAdjusted && !yAdjusted) {
                float addY;
                if(xDir == yDir) addY = moveX;
                else addY = -moveX;
                if((yDir == 1 && validBottom(level, x, y + addY, w, h)) ||
                        (yDir == -1 && validTop(level, checkX, y + addY, w, h))) {
                    y += addY;
                    System.out.println("Strafing in Y");
                }
            } else if(!xAdjusted && yAdjusted) {
                float addX;
                if(xDir == yDir) addX = moveX;
                else addX = -moveX;
                if((yDir == 1 && validBottom(level, x, y + addX, w, h)) ||
                        (yDir == -1 && validTop(level, checkX, y + addX, w, h))) {
                    x += addX;
                    System.out.println("Strafing in X");
                }
            }
        }
        return new float[] {x, y};
    }

    public static boolean pointCollides(float xPos, float yPos, float x, float y, float w, float h) {
        return (xPos < x + w/2) && (xPos > x - w/2) && (yPos < y + h/2) && (yPos > y - h/2);
    }

    public static boolean lineCollides(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {
        //check if either end is inside the box
        if (Util.pointInBox(x1, y1, rx, ry, rw, rh) || Util.pointInBox(x2, y2, rx, ry, rw, rh)) return true;

        //check if the line has hit any of the rectangle's sides
        boolean left =   Util.lineLine(x1, y1, x2, y2, rx, ry, rx, ry+rh);
        boolean right =  Util.lineLine(x1, y1, x2, y2, rx+rw, ry, rx+rw, ry+rh);
        boolean top =    Util.lineLine(x1, y1, x2, y2, rx, ry, rx+rw, ry);
        boolean bottom = Util.lineLine(x1, y1, x2, y2, rx, ry+rh, rx+rw, ry+rh);

        //if ANY of the above are true, the line has hit the rectangle
        if(left || right || top || bottom) {
            return true;
        }
        return false;
    }
}