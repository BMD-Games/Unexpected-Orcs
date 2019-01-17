package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.CellarDungeon;
import Levels.Level;

import static Sprites.Sprites.*;

public class CellarPortal extends Portal {

    public CellarPortal(float x, float y) {
        super(x, y, "Cellar");
        this.sprite = dropSprites.get("PORTAL_CELLAR");
    }

    @Override
    public Level getLevel() {
        return new CellarDungeon();
    }
}