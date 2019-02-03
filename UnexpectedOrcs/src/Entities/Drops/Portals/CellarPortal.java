package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.CellarDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.*;

public class CellarPortal extends Portal {

    public CellarPortal(float x, float y) {
        super(x, y, "Cellar");
        this.sprites = new AnimatedSprite(new PImage[]{dropSprites.get("PORTAL_CELLAR"), dropSprites.get("PORTAL_CELLAR2")}, 0.5f);
    }

    @Override
    public Level getLevel() {
        return new CellarDungeon();
    }
}