package Entities.Drops;

import Sprites.AnimatedSprite;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import static Settings.Settings.BLOOD;
import static Utility.Constants.*;

public class Blood extends Drop {

    private float rot = 0;

    public Blood(float x, float y) {
        super(x, y, 0, 10);
        if(BLOOD) {
            this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites, 0.1f, "BLOOD_0", "BLOOD_1", "BLOOD_2", "BLOOD_3", "BLOOD_4");
        } else {
            this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites, 0.2f, "BLOOD_PG_0", "BLOOD_PG_1", "BLOOD_PG_2", "BLOOD_PG_3", "BLOOD_PG_4");
        }
        this.rot = game.random(game.TAU);
    }

    public boolean update(double delta, float px, float py) {
        if(sprites.currentSprite == sprites.sprites.length -1) {
            sprites.update(-delta);
        }
        return super.update(delta, px, py);
    }

    public void show(PGraphics screen, PVector renderOffset) {
        PImage sprite = sprites.getCurrentSprite();
        screen.tint(255, 255, 255, alpha);
        screen.pushMatrix();
        screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
        screen.rotate(rot);
        screen.image(sprite,- (sprite.width * SCALE/2), - (sprite.height * SCALE/2) , sprite.width * SCALE, sprite.height * SCALE);
        screen.tint(255);
        screen.popMatrix();
    }

}
