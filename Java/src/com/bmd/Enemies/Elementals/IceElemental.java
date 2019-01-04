package com.bmd.Enemies.Elementals;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Entities.StatOrb;
import com.bmd.Sprites.Sprites;

public class IceElemental extends Elemental implements Enemy {

    public IceElemental(float x, float y, int tier) {
        super(x, y, tier);
        statusEffect = "SLOWED";
        sprite = Sprites.charSprites.get("ICE_ELEMENTAL");
        sprites[0] = Sprites.charSprites.get("ICE_ELEMENTAL");
        sprites[1] = Sprites.charSprites.get("ICE_ELEMENTAL_2");
        sprites[2] = Sprites.charSprites.get("ICE_ELEMENTAL_3");
        sprites[3] = Sprites.charSprites.get("ICE_ELEMENTAL_4");
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "MANA"));
    }

}