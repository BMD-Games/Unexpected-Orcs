package com.bmd.Items;

import com.bmd.Sprites.Sprites;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {

    public String type, name;
    public BufferedImage sprite;

    public int tier = 0;

    Item(String spriteName, String name) {
        sprite = Sprites.itemSprites.get(spriteName);
        this.name = name;
    }

    Item(BufferedImage sprite, String name) {
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
