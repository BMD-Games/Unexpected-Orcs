package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.BloodDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.dropSprites;

public class BloodPortal extends Portal {

    public BloodPortal(float x, float y) {
        super(x, y, "Cave");
        this.sprites = new AnimatedSprite(new PImage[]{dropSprites.get("PORTAL_BLOOD"), dropSprites.get("PORTAL_BLOOD2"), dropSprites.get("PORTAL_BLOOD"), dropSprites.get("PORTAL_BLOOD3")}, 0.2f);
        this.activeSprite = dropSprites.get("PORTAL_BLOOD_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new BloodDungeon();
    }
}