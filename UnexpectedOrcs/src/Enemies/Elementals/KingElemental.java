package Enemies.Elementals;

import Enemies.Enemy;
import Entities.Drops.Portals.CavePortal;
import Entities.Drops.StatOrb;
import Sprites.AnimatedSprite;
import Utility.Util;
import processing.core.PImage;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public class KingElemental extends Elemental implements Enemy {

    private float summonWait;

    public KingElemental(float x, float y, int tier) {
        super(x, y, tier);
        radius = 1;
        stats.health = 30 + 60 * tier;
        stats.attack = 20 + 60 * tier;
        stats.speed = 0.2f * tier;
        stats.defence = 2 + 8 * tier;
        attackWaitTime = 1;
        statusEffect = "DAZED";
        sprite = charSprites.get("KING_ELEMENTAL");
        sprites[0] = Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_1"));
        sprites[1] = Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_2"));
        sprites[2] = Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_3"));
        sprites[3] = Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_4"));
        animatedSprite = new AnimatedSprite(new PImage[] {Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_1")),
                Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_2")),
                Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_3")),
                Util.getCombinedSprite(sprite, charSprites.get("ELEMENTAL_BODYGUARDS_4"))},
                0.21f);
    }

    public boolean update(double delta) {
        summonWait += delta;
        if(summonWait > (3 - 0.1 * tier) && Util.distance(x, y, engine.player.x, engine.player.y) < range) {
            summon();
            summonWait = 0;
        }
        return super.update(delta);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        engine.addDrop(new CavePortal(x, y));
    }

    private void summon() {
        Elemental elemental = null;
        switch((int)game.random(4)) {
            case 0:
                elemental = new FireElemental(x + 0.25f, y - 0.25f, tier);
                break;
            case 1:
                elemental = new IceElemental(x + 0.25f, y + 0.25f, tier);
                break;
            case 2:
                elemental = new MagicElemental(x - 0.25f, y - 0.25f, tier);
                break;
            case 3:
                elemental = new PoisonElemental(x - 0.25f, y, tier);
                break;
        }
        engine.currentLevel.addEnemy(elemental);
    }

}