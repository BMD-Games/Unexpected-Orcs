package Tiles;

import static Utility.Constants.game;

public class WallTile extends Tile {

    public WallTile(String sprite, float speedMod, short bitmask, String groupID) {
        super(true, sprite, speedMod, groupID);
        bitmask(bitmask);
    }

    public WallTile(String sprite, float speedMod, String groupID) {
        super(true, sprite, speedMod, groupID);
    }

    public WallTile(String sprite, String groupID) {
        super(true, sprite, 1f, groupID);
    }

}
