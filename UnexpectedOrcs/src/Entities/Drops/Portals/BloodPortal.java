package Entities.Drops.Portals;

import Entities.Drops.Portal;
import Levels.Dungeons.BloodDungeon;
import Levels.Level;
import Sprites.AnimatedSprite;

public class BloodPortal extends Portal {

    public BloodPortal(float x, float y) {
        super(x, y, "Cave");
        this.sprites = new AnimatedSprite(Sprites.Sprites.dropSprites, 0.2f, "PORTAL_BLOOD", "PORTAL_BLOOD2", "PORTAL_BLOOD", "PORTAL_BLOOD3");
        this.activeSprite = Sprites.Sprites.dropSprites.get("PORTAL_BLOOD_ACTIVE");
    }

    @Override
    public Level getLevel() {
        return new BloodDungeon();
    }
}