package Items;

import Utility.StatusEffect;

import java.util.ArrayList;

public class Scroll extends Item {

    public ArrayList<StatusEffect> statusEffects;
    public String description;

    public Scroll(String sprite, String name) {
        super(sprite, name);
        this.type = "Scroll";
    }
}
