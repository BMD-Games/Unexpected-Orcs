package com.bmd.game.GUI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bmd.game.Sprites.Sprites;
import com.bmd.game.Util.Util;

public class HUDElement {public float x, y, w, h;
    public String spriteName;

    HUDElement(float x, float y, String spriteName) {
        this.x = x;
        this.y = y;
        this.w = Sprites.guiSprites.get(spriteName).getWidth() * Util.SCALE;
        this.h = Sprites.guiSprites.get(spriteName).getHeight() * Util.SCALE;
        this.spriteName = spriteName;
    }

    /*public void show(PGraphics screen) {
        Sprite sprite = Sprites.guiSprites.get(spriteName);
        screen.image(sprite, x, y, sprite.getWidth() * Util.SCALE, sprite.getHeight() * Util.SCALE);
    }*/
}
