package Items.Abilities;

import Entities.Projectile;
import Items.Ability;
import Utility.StatusEffect;
import processing.core.PVector;
import Sprites.Sprites;

import java.util.ArrayList;

import static Stats.StatusEffectType.ALL;
import static Stats.StatusEffectType.SLOWED;
import static Utility.Constants.engine;
import static Utility.Constants.game;

public class Knockback extends Ability {

    public Knockback() {
        super("SPELLBOMB", "Knock Back", "Knock Back");
        this.cooldown = 0.2f;
        this.manaCost = 3;
    }

    @Override
    public boolean ability() {
        if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            ArrayList<StatusEffect> statusEffects = new ArrayList<>();
            statusEffects.add(new StatusEffect(SLOWED, ALL, 1));
            for (int i = 0; i < 8; i++) {
                engine.playerProjectiles.add(new Projectile(engine.player.x, engine.player.y, PVector.fromAngle(game.PI * i / 4),
                        5, 3, 1, Sprites.projectileSprites.get("FIREBALL"), statusEffects, 200));
            }
            // soundFiles.get("FLAME").play();
            return true;
        }
        return false;
    }

}
