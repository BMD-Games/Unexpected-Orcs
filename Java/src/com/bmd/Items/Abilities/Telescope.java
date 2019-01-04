package com.bmd.Items.Abilities;

import com.bmd.App.Main;
import com.bmd.Items.Ability;

public class Telescope extends Ability {

    public Telescope() {
        super("TELESCOPE", "Telescope of seeing", "Have a look");
        this.cooldown = 0.5f;
        this.manaCost = 50;
    }

    @Override
    public boolean ability() {
        if (Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            Main.engine.currentLevel.newSmoothUncover((int)Main.engine.player.x, (int)Main.engine.player.y, 30);
            return true;
        }
        return false;
    }
}