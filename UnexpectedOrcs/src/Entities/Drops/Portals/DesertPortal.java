package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.DesertDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;

public class DesertPortal extends Portal {

    public DesertPortal(float x, float y) {
        super(x, y, "Desert");
        this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites, 0.5f, "PORTAL_DESERT", "PORTAL_DESERT2", "PORTAL_DESERT3");
        this.activeSprite = Sprites.Sprites.dropSprites.get("PORTAL_DESERT_ACTIVE");
    }

    @Override
    public Level getLevel() {
        super.getLevel();
        return new DesertDungeon();
    }
}