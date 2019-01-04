package com.bmd.Items.Abilities;

import com.bmd.App.Main;
import com.bmd.Items.Ability;

public class SwiftBoots extends Ability {

    public SwiftBoots() {
        super("BOOTS", "Boots of Swiftness", "Speed Buff");
        this.cooldown = 4;
        this.manaCost = 30;
    }

    @Override
    public boolean ability(){
        if (Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            Main.engine.player.stats.addStatusEffect("SWIFT", 3);
            // soundFiles.get("FLYBY").play();
            return true;
        }
        return false;
    }

}