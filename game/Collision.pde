class AABB {
//Axis-Aligned Bounding-Box
//Used for collision detection
//Should use Tile co-ordinates and scale

    public float x, y, w, h;

    AABB(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collidesWith(AABB box) {
        return (this.x +  this.w >= box.x && this.x <= box.x + box.w &&
                this.y +  this.h >= box.y && this.y <= box.y + box.h);    
    }

    public boolean collidesWith(Point point) {
        return pointInBox(point.x, point.y, this.x, this.y, this.w, this.h);
    }
    
    void show() {
      int s = 4;
      rect(x * TILE_SIZE/s, y * TILE_SIZE/s, w * TILE_SIZE/s, h * TILE_SIZE/s);
    }
    
}

class Point {
//Point
//Used for collision detection (projectiles)
//Should use Tile co-ordinates and scale

    private float x, y;

    Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean collidesWith(AABB box) {
        return pointInBox(this.x, this.y, box.x, box.y, box.w, box.h);
    }
}
