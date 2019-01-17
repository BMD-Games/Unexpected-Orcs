package Items.Abilities;

import Items.Ability;
import static Utility.Constants.*;

public class Telescope extends Ability {

    public Telescope() {
        super("TELESCOPE", "Telescope of seeing", "Have a look");
        this.cooldown = 0.5f;
        this.manaCost = 50;
    }

    @Override
    public boolean ability() {
        if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            engine.currentLevel.newSmoothUncover((int)engine.player.x, (int)engine.player.y, 30);
            return true;
        }
        return false;
    }
}
