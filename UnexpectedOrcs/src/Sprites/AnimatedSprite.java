package Sprites;

import processing.core.PImage;

import java.util.HashMap;

public class AnimatedSprite {

    public PImage[] sprites;
    private float frameLength, frameCounter = 0;
    public int currentSprite = 0;

    public AnimatedSprite(PImage[] sprites, float frameLength) {
        this.sprites = sprites;
        this.frameLength = frameLength;
    }

    public AnimatedSprite(HashMap<String, PImage> map, float frameLength, String... names) {
        sprites = new PImage[names.length];
        for(int i = 0; i < sprites.length; i ++) {
            sprites[i] = map.get(names[i]);
        }
        this.frameLength = frameLength;
    }

    public AnimatedSprite(PImage sprite) {
        this.sprites = new PImage[1];
        this.sprites[0] = sprite;

        this.frameLength = 1;
    }

    public AnimatedSprite(PImage... sprites) {
        this(1, sprites);
    }

    public AnimatedSprite(float frameLength, PImage... sprites) {
        this.sprites = sprites;

        this.frameLength = frameLength;
    }

    public PImage getCurrentSprite() {
        return sprites[currentSprite];
    }

    public void update(double delta) {
        frameCounter += delta;
        if(frameCounter > frameLength) {
            frameCounter -= frameLength;
            currentSprite ++;
            if(currentSprite >= sprites.length) {
                currentSprite -= sprites.length;
            }
        }
    }

    public void setCurrentSprite(int sprite) {
        this.currentSprite = sprite % sprites.length;
    }

    public void reset() {
        frameCounter = 0;
        currentSprite = 0;
    }
}
