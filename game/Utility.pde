public static class Utility {
  
  public static float distance(float x1, float y1, float x2, float y2) {
    float xdiff = pow(x2 - x1, 2);
    float ydiff = pow(y2 - y1, 2);
    return (sqrt(xdiff + ydiff));
  }
  
  public static int sign(float value) {
    if(value == 0) {
      return 0;
    } else {
      return (value > 0 ? 1 : -1);
    }
  }
  
  //Returns whether a point (px, py) is inside a box (bx, by, w, h);
  public  static boolean pointInBox(float px, float py, float bx, float by, float w, float h) {
    return((px > bx) && (px < bx + w) && (py > by) && (py < by + h));
  }
  
}

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

    public boolean collidesWith(float pointX, float pointY) {
        return Utility.pointInBox(pointX, pointY, this.x, this.y, this.w, this.h);
    }
    
    void show() {
      int s = 4;
      rect(x * TILE_SIZE/s, y * TILE_SIZE/s, w * TILE_SIZE/s, h * TILE_SIZE/s);
    }
    
}
