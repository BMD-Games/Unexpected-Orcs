package com.bmd.Entities;

import com.bmd.Tiles.Tiles;
import com.bmd.Util.PVector;

import java.awt.*;
import java.util.Vector;

public class Text {

    float x, y, life, time = 0;
    String message;
    Color c;

    public Text(String message, float xp, float yp, float lifeTime, Color c) {
        this.message = message;
        this.life = lifeTime;
        x = xp;
        y = yp;
        this.c = c;
    }

    public boolean update(double delta) {
        time += delta;
        return time < life;
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.textFont(bitcell);
        float a = 255 - (255 * time / life);
        screen.textSize(Tiles.TILE_SIZE/2);
        outlineText(screen, message, x * Tiles.TILE_SIZE - renderOffset.x, (y - (time/life)/2) * Tiles.TILE_SIZE - renderOffset.y,
                new Color(c.getRed(), c.getGreen(), c.getBlue(), a), new Color(255, 255, 255, a));
    }

    public void outlineText(PGraphics screen, String s, float x, float y, Color text, Color outline) {
        screen.fill(outline);
        for (int i = -1; i < 2; i++) {
            screen.text(s, x + i, y);
            screen.text(s, x, y + i);
        }
        screen.fill(text);
        screen.text(s, x, y);
    }
}