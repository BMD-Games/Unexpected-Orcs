package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.Cave;
import Levels.Dungeons.CircleDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.*;

public class CavePortal extends Portal {

    public CavePortal(float x, float y) {
        super(x, y, "Cave");
        this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites, 0.8f, "PORTAL_CAVE", "PORTAL_CAVE2", "PORTAL_CAVE", "PORTAL_CAVE3");
        this.activeSprite = Sprites.Sprites.dropSprites.get("PORTAL_CAVE_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new Cave();
    }
}