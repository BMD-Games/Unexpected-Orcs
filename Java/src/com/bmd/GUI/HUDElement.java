package com.bmd.GUI;

import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class HUDElement {

    public float x, y, w, h;
    public String spriteName;

    HUDElement(float x, float y, String spriteName) {
        this.x = x;
        this.y = y;
        this.w = (float)Sprites.guiSprites.get(spriteName).getWidth() * Util.SCALE;
        this.h = (float)Sprites.guiSprites.get(spriteName).getHeight() * Util.SCALE;
        this.spriteName = spriteName;
    }

    public void show(PGraphics screen) {
        BufferedImage sprite = Sprites.guiSprites.get(spriteName);
        screen.image(sprite, x, y, sprite.getWidth() * Util.SCALE, sprite.getHeight() * Util.SCALE);
    }
}