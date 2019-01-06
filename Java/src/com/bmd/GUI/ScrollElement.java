package com.bmd.GUI;

import com.bmd.Tiles.Tiles;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ScrollElement {

    String title, text;
    int x, y, w, h;
    int buffer = 10;

    public ScrollElement(String title, String text, int h) {
        this.title = title;
        this.text = text;
        this.h = h;
    }

    public void show(Canvas canvas, int xpos, int ypos, int w, boolean selected) {
        GraphicsContext screen = canvas.getGraphicsContext2D();

        this.x = xpos;
        this.y = ypos;
        this.w = w;

        screen.setFill(Util.gray(150));
        if (selected) {
            screen.setFill(Util.gray(180));
        }
        screen.fillRect(xpos, ypos, w, h);
        screen.setFill(Color.WHITE);
        screen.setTextAlign(TextAlignment.LEFT);
        screen.setFont(Util.font("bitcell", 40));
        screen.fillText(title, xpos + buffer, ypos + buffer);
        screen.setFont(Util.font("bitcell", 20));
        screen.fillText(text, xpos + buffer, ypos + Tiles.TILE_SIZE / 2 + buffer);
        if (selected) {
            screen.setLineWidth(4);
            screen.setStroke(Util.gray(200));
            screen.rect(x, y, w, h);
        }
        screen.setLineWidth(1);
    }
}