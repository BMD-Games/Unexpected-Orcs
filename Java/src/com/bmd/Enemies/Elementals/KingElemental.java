package com.bmd.Enemies.Elementals;

import com.bmd.App.Main;
import com.bmd.Enemies.Enemy;
import com.bmd.Entities.Portals.CavePortal;
import com.bmd.Entities.StatOrb;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;

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
        sprite = Sprites.charSprites.get("KING_ELEMENTAL");
        sprites[0] = Util.getCombinedSprite(sprite, Sprites.charSprites.get("ELEMENTAL_BODYGUARDS_1"));
        sprites[1] = Util.getCombinedSprite(sprite, Sprites.charSprites.get("ELEMENTAL_BODYGUARDS_2"));
        sprites[2] = Util.getCombinedSprite(sprite, Sprites.charSprites.get("ELEMENTAL_BODYGUARDS_3"));
        sprites[3] = Util.getCombinedSprite(sprite, Sprites.charSprites.get("ELEMENTAL_BODYGUARDS_4"));
    }

    public boolean update(double delta) {
        summonWait += delta;
        if(summonWait > (3 - 0.1 * tier) && Util.dist(x, y, Main.engine.player.x, Main.engine.player.y) < range) {
            summon();
            summonWait = 0;
        }
        return super.update(delta);
    }

    public void onDeath() {
        Main.engine.addDrop(new StatOrb(x, y, tier, "MANA"));
        Main.engine.addDrop(new CavePortal(x, y));
    }

    private void summon() {
        Elemental elemental = null;
        switch((int)Util.random(4)) {
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
        Main.engine.currentLevel.addEnemy(elemental);
    }

}