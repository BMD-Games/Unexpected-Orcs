package com.bmd.Items;

public class Armour extends Item {

    public int defence;

    public Armour(String sprite, String name, int defence) {
        super(sprite, name);
        this.type = "Armour";
        this.defence = defence;
    }
}