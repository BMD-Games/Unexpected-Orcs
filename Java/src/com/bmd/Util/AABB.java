package com.bmd.Util;

public class AABB {
    //Axis-Aligned Bounding-Box
    //Used for collision detection
    //Should use Tile co-ordinates and scale

    public float x, y, w, h;

    public AABB(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collidesWith(AABB box) {
        return (this.x +  this.w >= box.x && this.x <= box.x + box.w &&
                this.y +  this.h >= box.y && this.y <= box.y + box.h);
    }

    public boolean collidesWith(float pointX, float pointY) {
        return Util.pointInBox(pointX, pointY, this.x, this.y, this.w, this.h);
    }
}