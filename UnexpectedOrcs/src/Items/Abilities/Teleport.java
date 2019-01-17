package Items.Abilities;

import Items.Ability;

import Engine.Engine;

import static Utility.Constants.*;

public class Teleport extends Ability {

    public Teleport() {
        super("TELESCOPE", "Teleport", "Teleport");
        this.cooldown = 0.5f;
        this.manaCost = 5;
    }

    @Override
    public boolean ability() {
        float x = Engine.screenToTileCoordX(game.mouseX);
        float y = Engine.screenToTileCoordY(game.mouseY);
        if(engine.currentLevel.visited((int)x, (int)y) && engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            engine.player.x = x;
            engine.player.y = y;
            return true;
            // soundFiles.get("FLAME").play();
        }
        return false;
    }
}