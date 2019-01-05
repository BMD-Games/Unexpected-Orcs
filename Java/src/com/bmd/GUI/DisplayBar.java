package com.bmd.GUI;

import com.bmd.Entities.Portals.CavePortal;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class DisplayBar {

    private float x, y, w, h, percentFull;
    int current, total;
    private Color c;
    private HUDElement element;

    DisplayBar(float x, float y, Color c) {
        this.x = x;
        this.y = y;
        this.h = Tiles.TILE_SIZE / 2;
        this.w = Tiles.TILE_SIZE * 3 - 8;
        this.c = c;
        percentFull = 1.0f;

        element = new HUDElement(x - Tiles.TILE_SIZE/16, y, "BAR");
    }

    public void show(Canvas canvas) {
        GraphicsContext screen = canvas.getGraphicsContext2D();
        screen.setFill(c);
        screen.setFont(Util.font("bitcell", Tiles.TILE_SIZE/2));
        screen.setTextAlign(TextAlignment.CENTER);
        screen.fillRect(x, y, w * percentFull, h);
        screen.setFill(Color.gray(255));
        screen.fillText(current + "/" + total, x + w/2, y + h/2 - 5);
        element.show(canvas);
    }

    public void updateBar(float current, float total) {
        this.current = (int)current;
        this.total = (int)total;
        percentFull = current / total;
    }
}