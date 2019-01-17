package Items;

import Utility.Pair;

import java.util.ArrayList;

public class Scroll extends Item {

    public ArrayList<Pair> statusEffects;
    public String description;

    public Scroll(String sprite, String name) {
        super(sprite, name);
        this.type = "Scroll";
    }
}
