package Tiles;

import Sprites.Bitmask;
import Sprites.Sprites;

import static Utility.Constants.game;

public class Tile {
    public boolean solid;

    public boolean visited = false;

    public String sprite;
    private short bitmask;

    public float speedMod;

    public Tile(Tile tile) {
        this.solid = tile.solid;
        this.sprite = tile.sprite;
        this.speedMod = tile.speedMod;
    }

    public Tile(boolean solid, String sprite, float speedMod, short bitmask) {
        new Tile(solid, sprite, speedMod);
        bitmask(bitmask);
    }

    public Tile(boolean solid, String sprite, float speedMod) {
        this.solid = solid;
        this.sprite = sprite;
        this.speedMod = speedMod;
    }

    public Tile(boolean solid, String sprite) {
        new Tile(solid, sprite, 1f);
    }

    public void bitmask(short bitmask) {
        this.bitmask = bitmask;
        this.sprite = Bitmask.bitmask(sprite, bitmask);
    }

}
