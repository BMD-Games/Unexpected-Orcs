package Tiles;

public class FloorTile extends Tile{

    public FloorTile(String sprite, float speedMod, short bitmask, String groupID) {
        super(false, sprite, speedMod, groupID);
        bitmask(bitmask);
    }

    public FloorTile(String sprite, float speedMod, String groupID) {
        super(false, sprite, speedMod, groupID);
    }

    public FloorTile(String sprite, String groupID) {
        super(false, sprite, 1f, groupID);
    }


}
