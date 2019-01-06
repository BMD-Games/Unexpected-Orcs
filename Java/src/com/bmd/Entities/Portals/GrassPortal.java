package com.bmd.Entities.Portals;

import com.bmd.Entities.Portal;
import com.bmd.Levels.Dungeons.GrassDungeon;
import com.bmd.Levels.Level;
import com.bmd.Sprites.Sprites;

public class GrassPortal extends Portal {

    public GrassPortal(float x, float y) {
        super(x, y, "Grass World");
        this.sprite = Sprites.dropSprites.get("PORTAL_GRASS");
    }

    @Override
    public Level getLevel() {
        return new GrassDungeon();
    }
}