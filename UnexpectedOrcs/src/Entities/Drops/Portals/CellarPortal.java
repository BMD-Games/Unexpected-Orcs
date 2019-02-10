package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.CellarDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;

public class CellarPortal extends Portal {

    public CellarPortal(float x, float y) {
        super(x, y, "Cellar");
        this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites,0.25f, "PORTAL_CELLAR", "PORTAL_CELLAR2", "PORTAL_CELLAR", "PORTAL_CELLAR3");
        this.activeSprite = Sprites.Sprites.dropSprites.get("PORTAL_CELLAR_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new CellarDungeon();
    }
}