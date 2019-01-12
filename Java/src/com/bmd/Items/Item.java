package com.bmd.Items;

import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item implements Serializable {

    public String type, name;
    public transient BufferedImage sprite;

    public int tier = 0;

    Item(String spriteName, String name) {
        sprite = Sprites.itemSprites.get(spriteName);
        this.name = name;
    }

    Item(BufferedImage sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    private void writeObject(ObjectOutputStream out) {

        try {
            out.defaultWriteObject();
            ImageIO.write(sprite, "png", out);
        } catch (IOException e) {
            Util.println(e);
        }

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        sprite = ImageIO.read(in);
    }

}
