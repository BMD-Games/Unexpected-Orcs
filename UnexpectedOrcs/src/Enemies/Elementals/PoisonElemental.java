package Enemies.Elementals;

import Enemies.Enemy;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Stats.StatType;
import Stats.StatusEffectType;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class PoisonElemental extends Elemental {

    public PoisonElemental(float x, float y, int tier) {
        super(x, y, tier);
        statusEffect = StatusEffectType.ARMOURBREAK;
        setSprite(charSprites.get("POISON_ELEMENTAL"));
        sprites[0] = charSprites.get("POISON_ELEMENTAL");
        sprites[1] = charSprites.get("POISON_ELEMENTAL_2");
        sprites[2] = charSprites.get("POISON_ELEMENTAL_3");
        sprites[3] = charSprites.get("POISON_ELEMENTAL_4");
        animatedSprite = new AnimatedSprite(new PImage[] {charSprites.get("POISON_ELEMENTAL"), charSprites.get("POISON_ELEMENTAL_2"), charSprites.get("POISON_ELEMENTAL_3"), charSprites.get("POISON_ELEMENTAL_4")}, 0.2f);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, StatType.DEFENCE));
    }

}