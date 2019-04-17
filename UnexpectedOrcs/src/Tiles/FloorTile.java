package Tiles;

public class FloorTile extends Tile{

    public FloorTile(String sprite, float speedMod, short bitmask) {
        super(false, sprite, speedMod);
        bitmask(bitmask);
    }

    public FloorTile(String sprite, float speedMod) {
        super(false, sprite, speedMod);
    }

    public FloorTile(String sprite) {
        super(false, sprite, 1f);
    }


}
