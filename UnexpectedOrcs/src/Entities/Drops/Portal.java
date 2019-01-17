package Entities.Drops;

import Levels.Level;

import static Sprites.Sprites.*;

public class Portal extends Drop {

    public String name;

    public Portal(float x, float y, String name) {
        super(x, y, 0.5f, 60);
        this.name = name;
    }

    public Level getLevel() { //Override this with the level specific to a given portal
        return null;
    }

    @Override
    public boolean update(double delta, float px, float py) {
        lifeTime += delta;
        return super.update(delta, px, py);
    }

}