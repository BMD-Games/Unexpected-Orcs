package Items.Abilities;

import Entities.Projectile;
import Items.Ability;
import processing.core.PVector;
import Sprites.Sprites;
import Engine.Engine;

import static Utility.Constants.*;

public class SpellBomb extends Ability {

    public SpellBomb() {
        super("SPELLBOMB", "Spell Bomb", "Spell Bomb");
        this.cooldown = 0.5f;
        this.manaCost = 30;
    }

    @Override
    public boolean ability() {
        float x = Engine.screenToTileCoordX(game.mouseX);
        float y = Engine.screenToTileCoordY(game.mouseY);
        if(engine.currentLevel.visited((int)x, (int)y) && engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            for (int i = 0; i < 8; i++) {
                engine.playerProjectiles.add(new Projectile(x, y, PVector.fromAngle(game.PI * i / 4),
                        5, 3, 100, Sprites.projectileSprites.get("FIREBALL")));
            }
            return true;
            // soundFiles.get("FLAME").play();
        }
        return false;
    }
}