package Enemies.Elementals;

import Enemies.Enemy;
import Entities.Drops.StatOrb;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class IceElemental extends Elemental implements Enemy {

    public IceElemental(float x, float y, int tier) {
        super(x, y, tier);
        statusEffect = "SLOWED";
        sprite = charSprites.get("ICE_ELEMENTAL");
        sprites[0] = charSprites.get("ICE_ELEMENTAL");
        sprites[1] = charSprites.get("ICE_ELEMENTAL_2");
        sprites[2] = charSprites.get("ICE_ELEMENTAL_3");
        sprites[3] = charSprites.get("ICE_ELEMENTAL_4");
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
    }

}
