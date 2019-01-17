package Items.Abilities;

import Items.Ability;
import static Utility.Constants.*;

public class SwiftBoots  extends Ability {

    public SwiftBoots() {
        super("BOOTS", "Boots of Swiftness", "Speed Buff");
        this.cooldown = 4;
        this.manaCost = 30;
    }

    @Override
    public boolean ability(){
        if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            engine.player.stats.addStatusEffect("SWIFT", 3);
            // soundFiles.get("FLYBY").play();
            return true;
        }
        return false;
    }

}