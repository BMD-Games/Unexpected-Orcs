package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.DesertDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.dropSprites;

public class DesertPortal extends Portal {

    public DesertPortal(float x, float y) {
        super(x, y, "Grass World");
        this.sprites = new AnimatedSprite(new PImage[]{dropSprites.get("PORTAL_DESERT"), dropSprites.get("PORTAL_DESERT2"), dropSprites.get("PORTAL_DESERT3")}, 0.5f);
    }

    @Override
    public Level getLevel() {
        return new DesertDungeon();
    }
}