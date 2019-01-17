package Entities.Drops;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import static Utility.Constants.*;

public class Drop {

    public float x, y, radius, lifeTime, fadeTime = 1.5f;
    public PImage sprite;
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
        if(lifeTime <= fadeTime) alpha = (int)game.map(lifeTime, fadeTime, 0, 255, 0);
        return (lifeTime > 0) && alive;
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.tint(255, 255, 255, alpha);
        screen.image(sprite, x * TILE_SIZE - renderOffset.x - (sprite.width * SCALE/2), y * TILE_SIZE - renderOffset.y - (sprite.height * SCALE/2), sprite.width * SCALE, sprite.height * SCALE);
        screen.tint(255);
    }

    public boolean inRange(float xPos, float yPos) {
        return(game.dist(xPos, yPos, x, y) < radius);
    }

    public float getDist(float xPos, float yPos) {
        return game.dist(xPos, yPos, x, y);
    }

}