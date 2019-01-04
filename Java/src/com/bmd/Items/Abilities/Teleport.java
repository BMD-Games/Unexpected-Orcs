package com.bmd.Items.Abilities;

import com.bmd.App.Main;
import com.bmd.Engine.Engine;
import com.bmd.Items.Ability;
import com.bmd.Util.Util;

public class Teleport  extends Ability {

    public Teleport() {
        super("TELESCOPE", "Teleport", "Teleport");
        this.cooldown = 0.5f;
        this.manaCost = 5;
    }

    @Override
    public boolean ability() {
        float x = Engine.screenToTileCoordX(Util.mouseX());
        float y = Engine.screenToTileCoordY(Util.mouseY());
        if(Main.engine.currentLevel.visited((int)x, (int)y) && Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            Main.engine.player.x = x;
            Main.engine.player.y = y;
            return true;
            // soundFiles.get("FLAME").play();
        }
        return false;
    }
}