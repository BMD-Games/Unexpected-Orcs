package com.bmd.Items.Abilities;

import com.bmd.App.Main;
import com.bmd.Engine.Engine;
import com.bmd.Entities.Projectile;
import com.bmd.Input.Input;
import com.bmd.Items.Ability;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.PVector;
import com.bmd.Util.Util;

public class SpellBomb  extends Ability {

    public SpellBomb() {
        super("FIREBOMB", "Spell Bomb", "Spell Bomb");
        this.cooldown = 0.5f;
        this.manaCost = 30;
    }

    @Override
    public boolean ability() {
        float x = Engine.screenToTileCoordX(Input.mouseX());
        float y = Engine.screenToTileCoordY(Input.mouseY());
        if(Main.engine.currentLevel.visited((int)x, (int)y) && Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            for (int i = 0; i < 8; i++) {
                Main.engine.playerProjectiles.add(new Projectile(x, y, PVector.fromAngle((float)Math.PI * i / 4),
                        5, 3, 100, Sprites.projectileSprites.get("FIREBALL")));
            }
            return true;
            // soundFiles.get("FLAME").play();
        }
        return false;
    }
}