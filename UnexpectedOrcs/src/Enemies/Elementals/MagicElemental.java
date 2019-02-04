package Enemies.Elementals;

import Enemies.Enemy;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class MagicElemental extends Elemental implements Enemy {

    public MagicElemental(float x, float y, int tier) {
        super(x, y, tier);
        statusEffect = "CURSED";
        sprite = charSprites.get("MAGIC_ELEMENTAL");
        sprites[0] = charSprites.get("MAGIC_ELEMENTAL");
        sprites[1] = charSprites.get("MAGIC_ELEMENTAL_2");
        sprites[2] = charSprites.get("MAGIC_ELEMENTAL_3");
        sprites[3] = charSprites.get("MAGIC_ELEMENTAL_4");
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("MAGIC_ELEMENTAL"), charSprites.get("MAGIC_ELEMENTAL_2"), charSprites.get("MAGIC_ELEMENTAL_3"), charSprites.get("MAGIC_ELEMENTAL_4")}, 0.2f);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "WISDOM"));
    }

}