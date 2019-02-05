package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.Cave;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.*;

public class CavePortal extends Portal {

    public CavePortal(float x, float y) {
        super(x, y, "Cave");
        this.sprites = new AnimatedSprite(new PImage[]{dropSprites.get("PORTAL_CAVE"), dropSprites.get("PORTAL_CAVE2"),dropSprites.get("PORTAL_CAVE"), dropSprites.get("PORTAL_CAVE3")}, 0.8f);
        this.activeSprite = dropSprites.get("PORTAL_CAVE_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new Cave();
    }
}