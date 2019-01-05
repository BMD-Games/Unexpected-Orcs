package com.bmd.App;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Graphics {

    public static void image(GraphicsContext gc, BufferedImage img, float x, float y, float w, float h) {
        gc.drawImage(SwingFXUtils.toFXImage(img, null), x, y, w, h);
    }

    public static void image(GraphicsContext gc, BufferedImage img, float x, float y) {
        gc.drawImage(SwingFXUtils.toFXImage(img, null), x, y);
    }

    public static void background(Canvas canvas, Color c) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color temp = (Color) gc.getFill();
        gc.setFill(c);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(temp);
    }
}
