package Tiles;

import Sprites.Bitmask;

import static Utility.Constants.game;

public class Tile {
    public boolean solid;

    public boolean visited = false;

    public String sprite;
    public short bitmask;

    public float speedMod;

    public String groupID;

    public Tile(Tile tile) {
        this.solid = tile.solid;
        this.sprite = tile.sprite;
        this.bitmask = tile.bitmask;
        this.speedMod = tile.speedMod;
        this.groupID = tile.groupID;
    }

    public Tile(boolean solid, String sprite, float speedMod, short bitmask, String groupID) {
        new Tile(solid, sprite, speedMod, groupID);
        bitmask(bitmask);
    }

    public Tile(boolean solid, String sprite, float speedMod, String groupID) {
        this.solid = solid;
        this.sprite = sprite;
        this.speedMod = speedMod;
        this.groupID = groupID;
    }

    public Tile(boolean solid, String sprite, String groupID) {
        new Tile(solid, sprite, 1f, groupID);
    }

    public Tile(String tilename) {
        this.solid = Tiles.Tiles.get(tilename).solid;
        this.sprite = Tiles.Tiles.get(tilename).sprite;
        this.bitmask = Tiles.Tiles.get(tilename).bitmask;
        this.speedMod = Tiles.Tiles.get(tilename).speedMod;
        this.groupID = Tiles.Tiles.get(tilename).groupID;
    }

    public void bitmask(short bitmask) {
        this.bitmask = bitmask;
        this.sprite = Bitmask.bitmask(this, bitmask);
    }

}
