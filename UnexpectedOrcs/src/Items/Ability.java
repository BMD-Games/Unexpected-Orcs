package Items;

import static Utility.Constants.*;
import static Utility.Colour.*;

public class Ability extends Item {

    public int manaCost;
    public float cooldown;
    String useText;

    public Ability(String sprite, String name, String useText) {
        super(sprite, name);
        this.type = "Ability";
        this.useText = useText;
    }

    public void makeText() {

        if (manaCost > engine.player.stats.getMana() && engine.player.textTimer >= 0.5){
            String cooldownText = "Low Mana";
            engine.addText(cooldownText, engine.player.x, engine.player.y, 0.5f, colour(0, 0, 200));
            engine.player.textTimer = 0;
        }

        if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
            engine.player.cooldownTimer = cooldown;
            engine.player.stats.mana -= manaCost;
            engine.addText(useText, engine.player.x, engine.player.y, 0.5f, colour(0, 0, 200));
            engine.player.textTimer = 0;
        } else {
            if (engine.player.textTimer >= 0.5 && manaCost < engine.player.stats.getMana()) {
                String cooldownText = "";
                cooldownText = String.format("%.3gs%n", engine.player.cooldownTimer);
                engine.addText(cooldownText, engine.player.x, engine.player.y, 0.5f, colour(0,0,200));
                engine.player.textTimer = 0;
            }
        }
    }

    public boolean ability() { return false; }

}