package com.bmd.Items;

import com.bmd.App.Main;
import com.bmd.Util.Util;


public class Ability extends Item {

    public int manaCost;
    public float cooldown;
    public String useText;

    public Ability(String sprite, String name, String useText) {
        super(sprite, name);
        this.type = "Ability";
        this.useText = useText;
    }

    public void makeText() {

        if (manaCost > Main.engine.player.stats.getMana() && Main.engine.player.textTimer >= 0.5){
            String cooldownText = "Low Mana";
            Main.engine.addText(cooldownText, Main.engine.player.x, Main.engine.player.y, 0.5f, Util.color(0, 0, 200));
            Main.engine.player.textTimer = 0;
        }

        if (Main.engine.player.cooldownTimer <= 0 && manaCost <= Main.engine.player.stats.getMana()){
            Main.engine.player.cooldownTimer = cooldown;
            Main.engine.player.stats.mana -= manaCost;
            Main.engine.addText(useText, Main.engine.player.x, Main.engine.player.y, 0.5f, Util.color(0, 0, 200));
            Main.engine.player.textTimer = 0;
        } else {
            if (Main.engine.player.textTimer >= 0.5 && manaCost < Main.engine.player.stats.getMana()) {
                String cooldownText = "";
                cooldownText = String.format("%.3gs%n", Main.engine.player.cooldownTimer);
                Main.engine.addText(cooldownText, Main.engine.player.x, Main.engine.player.y, 0.5f, Util.color(0,0,200));
                Main.engine.player.textTimer = 0;
            }
        }
    }

    public boolean ability() { return false; }

}