package com.bmd.Items.Abilities;

import com.bmd.App.Main;
import com.bmd.Entities.Projectile;
import com.bmd.Items.Ability;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.PVector;

public class FireBomb extends Ability {

    public FireBomb() {
        super("FIREBOMB", "Fire Bomb", "Fire Bomb");
        this.cooldown = 0.5f;
        this.manaCost = 30;
    }

    @Override
    public boolean ability() {
        if (Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            for (int i = 0; i < 8; i++) {
                Main.engine.playerProjectiles.add(new Projectile(Main.engine.player.x, Main.engine.player.y, PVector.fromAngle((float)Math.PI * i / 4),
                        5, 3, 100, Sprites.projectileSprites.get("FIREBALL")));
            }
            // soundFiles.get("FLAME").play();
            return true;
        }
        return false;
    }
}