package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.Cave;
import Levels.Level;

import static Sprites.Sprites.*;

public class CavePortal extends Portal {

    public CavePortal(float x, float y) {
        super(x, y, "Cave");
        this.sprite = dropSprites.get("PORTAL_CAVE");
    }

    @Override
    public Level getLevel() {
        return new Cave();
    }
}