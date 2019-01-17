package Utility.Collision;

import Levels.Level;
import Utility.Util;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Circle {

    //Calculates if coordinates mean chomp is not in a wall
    //Needs level because it's used in setup
    public static boolean validPosition(Level level, float x, float y, float radius) {
        return validCentre(level, x, y) &&
                validLeft(level, x, y, radius) &&
                validRight(level, x, y, radius) &&
                validTop(level, x, y, radius) &&
                validBottom(level, x, y, radius);
    }

    protected static boolean validCentre(Level level, float x, float y) {
        return level.getTile((int)x, (int)y) > WALL;
    }

    protected static boolean validLeft(Level level, float x, float y, float radius) {
        return level.getTile((int)(x - radius), (int)y) > WALL;
    }

    protected static boolean validRight(Level level, float x, float y, float radius) {
        return level.getTile((int)(x + radius), (int)y) > WALL;
    }

    protected static boolean validTop(Level level, float x, float y, float radius) {
        return level.getTile((int)x, (int)(y - radius)) > WALL;
    }

    protected static boolean validBottom(Level level, float x, float y, float radius) {
        return level.getTile((int)x, (int)(y + radius)) > WALL;
    }

    protected static boolean validTopLeft(Level level, float x, float y, float radius) {
        return !((pointCollides(x, y, game.floor(x), game.floor(y), radius)) && (level.getTile(game.floor(x) - 1, game.floor(y) - 1) <= WALL));
    }

    protected static boolean validTopRight(Level level, float x, float y, float radius) {
        return !((pointCollides(x, y, (int)x + 1, (int)y, radius)) && (level.getTile((int)x + 1, (int)y - 1) <= WALL));
    }

    protected static boolean validBottomLeft(Level level, float x, float y, float radius) {
        return !((pointCollides(x, y, (int)x, (int)y + 1, radius)) && (level.getTile((int)x - 1, (int)y + 1) <= WALL));
    }

    protected static boolean validBottomRight(Level level, float x, float y, float radius) {
        return !((pointCollides(x, y, (int)x + 1, (int)y + 1, radius)) && (level.getTile((int)x + 1, (int)y + 1) <= WALL));
    }

    public static float[] adjust(Level level, float x, float y, float radius, float moveX, float moveY) {
        x += moveX;
        y += moveY;
        int xDir = Util.sign(moveX);
        int yDir = Util.sign(moveY);

        if(!Circle.validCentre(level, x, y)) {
            x -= moveX;
            y -= moveY;
        }
        if(xDir == 1) {
            if(!Circle.validRight(level, x, y, radius)) {
                x = game.ceil(x) - radius;
            } else {
                if(!Circle.validTopRight(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.floor(y), x - game.ceil(x));
                    x = game.ceil(x) + game.cos(attackAngle) * radius;
                    y = game.floor(y) + game.sin(attackAngle) * radius;
                }
                if(!Circle.validBottomRight(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.ceil(y), x - game.ceil(x));
                    x = game.ceil(x) + game.cos(attackAngle) * radius;
                    y = game.ceil(y) + game.sin(attackAngle) * radius;
                }
            }
        } else if(xDir == -1) {
            if (!Circle.validLeft(level, x, y, radius)) {
                x = game.floor(x) + radius;
            } else {
                if(!Circle.validTopLeft(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.floor(y), x - game.floor(x));
                    x = game.floor(x) + game.cos(attackAngle) * radius;
                    y = game.floor(y) + game.sin(attackAngle) * radius;
                }
                if(!Circle.validBottomLeft(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.ceil(y), x - game.floor(x));
                    x = game.floor(x) + game.cos(attackAngle) * radius;
                    y = game.ceil(y) + game.sin(attackAngle) * radius;
                }
            }
        }
        if(yDir == 1) {
            if (!Circle.validBottom(level, x, y, radius)) {
                y = game.ceil(y) - radius;
            } else {
                if(!Circle.validBottomLeft(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.ceil(y), x - game.floor(x));
                    x = game.floor(x) + game.cos(attackAngle) * radius;
                    y = game.ceil(y) + game.sin(attackAngle) * radius;
                }
                if(!Circle.validBottomRight(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.ceil(y), x - game.ceil(x));
                    x = game.ceil(x) + game.cos(attackAngle) * radius;
                    y = game.ceil(y) + game.sin(attackAngle) * radius;
                }
            }
        } else if(yDir == -1) {
            if(!Circle.validTop(level, x, y, radius)) {
                y = game.floor(y) + radius;
            } else {
                if(!Circle.validTopLeft(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.floor(y), x - game.floor(x));
                    x = game.floor(x) + game.cos(attackAngle) * radius;
                    y = game.floor(y) + game.sin(attackAngle) * radius;
                }
                if(!Circle.validTopRight(level, x, y, radius)) {
                    float attackAngle = game.atan2(y - game.floor(y), x - game.ceil(x));
                    x = game.ceil(x) + game.cos(attackAngle) * radius;
                    y = game.floor(y) + game.sin(attackAngle) * radius;
                }
            }
        }
        return new float[] {x, y};
    }

    /* Checks collision with point */
    public static boolean pointCollides(float x, float y, float pointX, float pointY, float radius) {
        return (Util.distance(x, y, pointX, pointY) < radius);
    }

    public static boolean lineCollides(float lx1, float ly1, float lx2, float ly2, float cx, float cy, float r) {
        // if end points are inside the circle
        if(Circle.pointCollides(lx1, ly1, cx, cy, r)) return true;
        if(Circle.pointCollides(lx2, ly2, cx, cy, r)) return true;

        //line length
        float len = game.dist(lx1, ly1, lx2, ly2);
        //dot product of line and circle
        float dot = (((cx - lx1) * (lx2 - lx1)) + ((cy - ly1) * (ly2 - ly1)))/game.sq(len);
        //closest point on line
        float closestX = lx1 + (dot * (lx2 - lx1));
        float closestY = ly1 + (dot * (ly2 - ly1));
        //check if point is actually on line segment
        if(!Util.linePoint(lx1, ly1, lx2, ly2, closestX, closestY)) return false;

        float distance = game.dist(closestX, closestY, cx, cy);

        return distance < r;
    }
}