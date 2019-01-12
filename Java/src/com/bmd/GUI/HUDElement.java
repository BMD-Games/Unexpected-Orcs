package com.bmd.GUI;

import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import com.bmd.App.Graphics;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;

public class HUDElement {

    public float x, y, w, h;
    public String spriteName;

    HUDElement(float x, float y, String spriteName) {
        this.x = x;
        this.y = y;
        this.w = (float)Sprites.guiSprites.get(spriteName).getWidth();
        this.h = (float)Sprites.guiSprites.get(spriteName).getHeight();
        this.spriteName = spriteName;
    }

    public void show(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BufferedImage sprite = Sprites.guiSprites.get(spriteName);
        Graphics.image(gc, sprite, x, y, w, h);
    }
}