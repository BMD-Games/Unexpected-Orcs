package Entities.Drops;

import Levels.Level;
import Sound.SoundManager;
import Utility.Util;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class Portal extends Drop {

    public String name;

    public Portal(float x, float y, String name) {
        super(x, y, 0.5f, 60);
        this.name = name;
    }

    public Level getLevel() { //Override this with the level specific to a given portal
        SoundManager.playSound("PORTAL_USE");
        return null;
    }

    @Override
    public boolean update(double delta, float px, float py) {
        lifeTime += delta;
        return super.update(delta, px, py);
    }

    @Override
    public void show(PGraphics screen, PVector renderOffset) {
        PImage sprite = sprites.getCurrentSprite();
        if(activeSprite != null && inRange(engine.player.x, engine.player.y)) {
            sprite = activeSprite;
        }
        if(alpha != 255) {
            sprite = Util.alphaImage(sprite, alpha);
        }
        screen.image(sprite, x * TILE_SIZE - renderOffset.x - (sprite.width * SCALE/2), y * TILE_SIZE - renderOffset.y - (sprite.height * SCALE/2), sprite.width * SCALE, sprite.height * SCALE);
    }

}