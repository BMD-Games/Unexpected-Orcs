package Tiles;

public class WallTile extends Tile {

    public WallTile(String sprite, float speedMod, short bitmask) {
        super(true, sprite, speedMod);
        bitmask(bitmask);
    }

    public WallTile(String sprite, float speedMod) {
        super(true, sprite, speedMod);
    }

    public WallTile(String sprite) {
        super(true, sprite, 1f);
    }

}
