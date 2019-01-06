package com.bmd.Entities;

import com.bmd.Tiles.Tiles;
import com.bmd.Util.PVector;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    public void show(Canvas canvas, PVector renderOffset) {
        GraphicsContext screen = canvas.getGraphicsContext2D();

        screen.setFont(Util.font("bitcell", Tiles.TILE_SIZE/2));
        float a = 255 - (255 * time / life);
        screen.setFill(Color.color(c.getRed(), c.getGreen(), c.getBlue(), a));
        screen.setStroke(Util.color(255, 255, 255, a));
        screen.strokeText(message, x * Tiles.TILE_SIZE - renderOffset.x, (y - (time/life)/2) * Tiles.TILE_SIZE - renderOffset.y);
        screen.fillText(message, x * Tiles.TILE_SIZE - renderOffset.x, (y - (time/life)/2) * Tiles.TILE_SIZE - renderOffset.y);
    }
}