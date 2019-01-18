package Items;

import processing.core.PImage;

import Sprites.Sprites;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static Utility.Constants.SPRITE_SIZE;
import static Utility.Constants.game;

public class Item  implements Serializable {

    public String type, name;
    transient public PImage sprite;

    public int tier = 0;

    public Item(String spriteName, String name) {
        sprite = Sprites.itemSprites.get(spriteName);
        this.name = name;
    }

    public Item(PImage sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        sprite.loadPixels();
        out.writeObject(sprite.pixels);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        sprite = game.createImage(SPRITE_SIZE, SPRITE_SIZE, game.ARGB);
        sprite.loadPixels();
        sprite.pixels = (int[])in.readObject();
        sprite.updatePixels();
    }

}