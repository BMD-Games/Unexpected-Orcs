package com.bmd.game.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bmd.game.Sprites.Sprites;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item implements Serializable {

    public String type, name;
    public Sprite sprite;

    public int tier = 0;

    Item(String spriteName, String name) {
        sprite = Sprites.itemSprites.get(spriteName);
        this.name = name;
    }

    Item(Sprite sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    /*private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        sprite.loadPixels();
        out.writeObject(sprite.pixels);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        sprite.loadPixels();
        sprite.pixels = (int[])in.readObject();
        sprite.updatePixels();
    }*/

}
