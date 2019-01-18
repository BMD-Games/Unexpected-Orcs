package GUI;

import processing.core.PGraphics;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class HUDElement {

    public float x, y, w, h;
    public String spriteName;

    HUDElement(float x, float y, String spriteName) {
        this.x = x;
        this.y = y;
        this.w = guiSprites.get(spriteName).width * SCALE;
        this.h = guiSprites.get(spriteName).height * SCALE;
        this.spriteName = spriteName;
    }

    HUDElement(float x, float y, float w, float h, String spriteName) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.spriteName = spriteName;
    }

    public void show(PGraphics screen) {
        PImage sprite = guiSprites.get(spriteName);
        screen.image(sprite, x, y, w, h);
    }
}