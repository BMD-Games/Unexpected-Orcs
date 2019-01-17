package Items.Abilities;

import Entities.Projectile;
import Items.Ability;
import Sprites.Sprites;
import processing.core.PVector;

import static Utility.Constants.*;

public class FireBomb extends Ability {

    public FireBomb() {
        super("FIREBOMB", "Fire Bomb", "Fire Bomb");
        this.cooldown = 0.5f;
        this.manaCost = 30;
    }

    @Override
    public boolean ability() {
        if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            for (int i = 0; i < 8; i++) {
                engine.playerProjectiles.add(new Projectile(engine.player.x, engine.player.y, PVector.fromAngle(game.PI * i / 4),
                        5, 3, 100, Sprites.projectileSprites.get("FIREBALL")));
            }
            // soundFiles.get("FLAME").play();
            return true;
        }
        return false;
    }
}