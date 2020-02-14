package Utility;

import processing.core.PVector;

public class Vec2 {

    public int x, y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public PVector asPVector() {
        return new PVector(x, y);
    }

    @Override
    public int hashCode() {
        return (x * 73856093) ^ (y * 83492791);
    }

    @Override
    public boolean equals(Object obj) {
        boolean instance = obj instanceof Vec2;
        return instance && ((Vec2)(obj)).x == this.x && ((Vec2)(obj)).y == this.y;
    }

}
