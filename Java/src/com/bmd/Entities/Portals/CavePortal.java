package com.bmd.Entities.Portals;

import com.bmd.Entities.Portal;
import com.bmd.Levels.Dungeons.Cave;
import com.bmd.Levels.Level;
import com.bmd.Sprites.Sprites;

public class CavePortal extends Portal {

    public CavePortal(float x, float y) {
        super(x, y, "Cave");
        this.sprite = Sprites.dropSprites.get("PORTAL_CAVE");
    }

    @Override
    public Level getLevel() {
        return new Cave();
    }
}