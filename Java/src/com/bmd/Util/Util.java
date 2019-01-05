package com.bmd.Util;

import com.bmd.App.Main;
import com.bmd.Sprites.Sprites;
import com.bmd.Tiles.Tiles;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Util {



    public final static int GUI_WIDTH = 240;
    public final static int SCALE = Tiles.TILE_SIZE/ Sprites.SPRITE_SIZE;

    public final static int up = 0, down = 1, left = 2, right = 3, ability = 4, topLeft = 4, topRight = 5, bottomLeft = 6, bottomRight = 7;

    public static float exp(float pow) {
        return (float)Math.pow(Math.E, pow);
    }

    public static float constrain(float value, float min, float max) {
        if(value < min) return min;
        if(value > max) return max;
        return value;
    }

    public static float sq(float n) {
        return n * n;
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

    public static Font font(String name, int size) {
        return Font.loadFont(Main.class.getResourceAsStream("../assets/fonts/" + name + ".ttf"), size);
    }

    public static float mouseX() {
        return MouseInfo.getPointerInfo().getLocation().x;
    }

    public static float mouseY() {
        return MouseInfo.getPointerInfo().getLocation().y;
    }

    public static float random(float n) {
        return (float)Math.random() * n;
    }

    public static float random(float min, float max) {
        return (float)((Math.random() * (max - min)) + min);
    }

    public static float randomGaussian() {
        return (float)(new Random()).nextGaussian();
    }

    public static float fastAbs(float v) {
        if (v < 0) return v * -1;
        return v;
    }

    public static float map(float v, float min1, float max1, float min2, float max2) {
        float percent = (v-min1)/(max1-min1);
        return (percent * (max2-min2)) + min2;
    }

    public static Color randomColour(int tier) {
        float maxTier = 5;
        return Color.getHSBColor(random(255), 255, ((tier + 1)/maxTier) * 255);
    }

    public static BufferedImage copyImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage applyColourToImage(BufferedImage img, Color color) {
        BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < img.getWidth(); i ++) {
            for(int j = 0; j < img.getHeight(); j ++) {
                Color c = new Color(img.getRGB(i, j), true);
                Color pix = new Color(color.getRed(), color.getGreen(), color.getBlue(), c.getAlpha());
                temp.setRGB(i, j, pix.getRGB());
            }
        }
        return temp;
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

    public static BufferedImage getCombinedSprite(BufferedImage baseImage, BufferedImage tintImage, Color colour) {
        BufferedImage combined = copyImage(baseImage);
        Graphics2D temp = combined.createGraphics();
        temp.drawImage(applyColourToImage(tintImage, colour), null,0, 0);
        return combined;
    }

    public static BufferedImage getCombinedSprite(BufferedImage baseImage, BufferedImage secondImage) {
        BufferedImage combined = copyImage(baseImage);
        Graphics2D temp = combined.createGraphics();
        temp.drawImage(secondImage, null,0, 0);
        return combined;
    }

    public static BufferedImage loadImage(String path) {
        return SwingFXUtils.fromFXImage(new Image(path), null);
    }
}
