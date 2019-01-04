package com.bmd.Entities;

import com.bmd.Tiles.Tiles;
import com.bmd.Util.PVector;
import com.bmd.Util.Util;

import java.awt.image.BufferedImage;

public class Drop {

    public float x, y, radius, lifeTime, fadeTime = 1.5f;
    public BufferedImage sprite;
    public boolean alive = true;

    private int alpha = 255;

    public Drop(float x, float y, float radius, float lifeTime) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.lifeTime = lifeTime;
    }

    public boolean update(double delta, float px, float py) {
        lifeTime -= delta;
        if(lifeTime <= fadeTime) alpha = (int)Util.map(lifeTime, fadeTime, 0, 255, 0);
        return (lifeTime > 0) && alive;
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.tint(255, 255, 255, alpha);
        screen.image(sprite, x * Tiles.TILE_SIZE - renderOffset.x - (sprite.width * Util.SCALE/2), y * Tiles.TILE_SIZE - renderOffset.y - (sprite.height * Util.SCALE/2),
                sprite.width * Util.SCALE, sprite.height * Util.SCALE);
        screen.tint(255);
    }

    public boolean inRange(float xPos, float yPos) {
        return(Util.dist(xPos, yPos, x, y) < radius);
    }

    public float getDist(float xPos, float yPos) {
        return Util.dist(xPos, yPos, x, y);
    }

}