package Utility;

import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;

import static Utility.Constants.*;
import static Utility.Colour.*;
import static processing.core.PApplet.*;


public class Util {
    public static float distance(float x1, float y1, float x2, float y2) {
        float xdiff = pow(x2 - x1, 2);
        float ydiff = pow(y2 - y1, 2);
        return (sqrt(xdiff + ydiff));
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
        float num = ((float)floor(number * rounder)) / rounder;
        return num;
    }

    public static int randomColour(int tier) {
        float maxTier = 5;
        return hsb(game.random(360), 1, ((tier + 1)/maxTier));
    }

    public static PImage applyColourToImage(PImage img, int c) {
        PImage temp = game.createImage(img.width, img.height, ARGB);
        temp.loadPixels();
        img.loadPixels();

        for(int i = 0; i < img.pixels.length; i++) {
            temp.pixels[i] = colour(red(c), green(c), blue(c), alpha(img.pixels[i]));
        }
        //temp.updatePixels();
        return temp;
    }

    public static PImage scaleImage(PImage img, int scale) {
        PImage scaled = game.createImage(img.width * scale, img.height * scale, ARGB);
        img.loadPixels();
        scaled.loadPixels();

        for(int i = 0; i < img.width; i ++) {
            for(int j = 0; j < img.height; j ++) {
                int index = i + j * img.width;
                for(int x = 0; x < scale; x ++) {
                    for(int y = 0; y < scale; y ++) {
                        int index2 = (i * scale + x) + (j * scale + y) * (img.width * scale);
                        scaled.pixels[index2] = img.pixels[index];
                    }
                }
            }
        }

        scaled.updatePixels();
        return scaled;
    }

    public static PImage getCombinedSprite(PImage baseImage, PImage tintImage, int colour) {
        PGraphics temp = game.createGraphics(baseImage.width, baseImage.height);
        temp.beginDraw();
        temp.image(baseImage, 0, 0);
        temp.image(applyColourToImage(tintImage, colour), 0, 0);
        temp.endDraw();
        return temp.get();
    }

    public static PImage getCombinedSprite(PImage baseImage, PImage secondImage) {
        PGraphics temp = game.createGraphics(baseImage.width, baseImage.height);
        temp.beginDraw();
        temp.image(baseImage, 0, 0);
        temp.image(secondImage, 0, 0);
        temp.endDraw();
        return temp.get();
    }

    public static String debuffToVerb(String debuff) {
        switch(debuff) {
            case "SLOWED":
                return "slowing";
            case "DAZED":
                return "dazing";
            case "WEAK":
                return "weakening";
            case "ARMOUR_BREAK":
                return "armour piercing";
            case "CURSED":
                return "cursing";
            case "SICK":
                return "illness";
        }
        return "No such debuff";
    }

    public static String debuffToPresentVerb(String debuff) {
        switch(debuff) {
            case "SLOWED":
                return "slows";
            case "DAZED":
                return "dazes";
            case "WEAK":
                return "weakens";
            case "ARMOUR_BREAK":
                return "breaks the armour of";
            case "CURSED":
                return "curses";
            case "SICK":
                return "poisons";
        }
        return "No such debuff";
    }

    public static String linkWords(String[] words) {
        if(words.length == 1) {
            return words[0];
        } else {
            String linked = words[0];
            for(int i = 1; i < words.length - 1; ++i) {
                linked += ", " + words[i];
            }
            linked += " and " + words[words.length - 1];
            return linked;
        }
    }

    public static String capFirstLetter(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    public static void deleteFile(File file) {
        //cannot delete non-empty folders -> need to use recursion
        if(file.isDirectory()) {
            //directory is empty, then delete it
            if(file.list().length==0) {
                file.delete();
            } else {
                while (file.list().length != 0) {
                    File fileDelete = new File(file, file.list()[0]);
                    deleteFile(fileDelete);
                }
                file.delete();

            }

        } else {
            file.delete();
        }
    }
}
