package Sprites;

import processing.core.PImage;

public class AnimatedSprite {

    private PImage[] sprites;
    private float frameLength, frameCounter = 0;
    private int currentSprite = 0;

    public AnimatedSprite(PImage[] sprites, float frameLength) {
        this.sprites = sprites;
        this.frameLength = frameLength;
    }

    public AnimatedSprite(PImage sprite) {
        this.sprites = new PImage[1];
        this.sprites[0] = sprite;

        this.frameLength = 1;
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
}
