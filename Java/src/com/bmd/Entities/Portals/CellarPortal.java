package com.bmd.Entities.Portals;

import com.bmd.Entities.Portal;
import com.bmd.Levels.Dungeons.CellarDungeon;
import com.bmd.Levels.Level;
import com.bmd.Sprites.Sprites;

public class CellarPortal extends Portal {

    public CellarPortal(float x, float y) {
        super(x, y, "Cellar");
        this.sprite = Sprites.dropSprites.get("PORTAL_CELLAR");
    }

    @Override
    public Level getLevel() {
        return new CellarDungeon();
    }
}