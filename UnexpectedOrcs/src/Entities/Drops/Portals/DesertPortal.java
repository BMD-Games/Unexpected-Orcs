package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.DesertDungeon;
import Levels.Level;

import static Sprites.Sprites.dropSprites;

public class DesertPortal extends Portal {

    public DesertPortal(float x, float y) {
        super(x, y, "Grass World");
        this.sprite = dropSprites.get("PORTAL_GRASS");
    }

    @Override
    public Level getLevel() {
        return new DesertDungeon();
    }
}