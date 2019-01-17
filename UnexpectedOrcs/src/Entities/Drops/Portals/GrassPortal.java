package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.GrassDungeon;
import Levels.Level;

import static Sprites.Sprites.dropSprites;

public class GrassPortal extends Portal {

    public GrassPortal(float x, float y) {
        super(x, y, "Grass World");
        this.sprite = dropSprites.get("PORTAL_GRASS");
    }

    @Override
    public Level getLevel() {
        return new GrassDungeon();
    }
}