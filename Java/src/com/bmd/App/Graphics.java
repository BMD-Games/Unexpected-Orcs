package com.bmd.App;

import com.bmd.Util.Util;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Graphics {

    public static void image(GraphicsContext gc, BufferedImage img, float x, float y, float w, float h) {
        /*ImageView view = new ImageView(SwingFXUtils.toFXImage(img, null));
        view.setFitWidth(w);
        view.setFitHeight(h);
        view.setSmooth(false);
        WritableImage newImg = new WritableImage((int)w, (int)h);
        newImg = view.snapshot(null, newImg);*/
        Image newImg = SwingFXUtils.toFXImage(Util.scaleImage(img, w, h), null);
        gc.drawImage(newImg, x, y, w, h);
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

    public static void line(GraphicsContext gc, float x, float y, float x2, float y2) {
        gc.save();
        gc.moveTo(x, y);
        gc.lineTo(x2, y2);
        gc.restore();
    }
}
