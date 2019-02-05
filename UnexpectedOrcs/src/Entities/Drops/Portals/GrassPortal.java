package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.GrassDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.dropSprites;

public class GrassPortal extends Portal {

    public GrassPortal(float x, float y) {
        super(x, y, "Grass World");
        this.sprites = new AnimatedSprite(new PImage[]{dropSprites.get("PORTAL_GRASS"), dropSprites.get("PORTAL_GRASS2"), dropSprites.get("PORTAL_GRASS3")}, 0.5f);
        this.activeSprite = dropSprites.get("PORTAL_GRASS_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new GrassDungeon();
    }
}