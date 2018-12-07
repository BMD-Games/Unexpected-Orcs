public static class Util {
  
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
  
  public static boolean lineRect(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {
    // check if the line has hit any of the rectangle's sides
    // uses the Line/Line function below
    if(Util.pointInBox(lerp(x1, x2, 0.5), lerp(y1, y2, 0.5), rx, ry, rw, rh)) return true;
    boolean left =   lineLine(x1,y1,x2,y2, rx,ry,rx, ry+rh);
    boolean right =  lineLine(x1,y1,x2,y2, rx+rw,ry, rx+rw,ry+rh);
    boolean top =    lineLine(x1,y1,x2,y2, rx,ry, rx+rw,ry);
    boolean bottom = lineLine(x1,y1,x2,y2, rx,ry+rh, rx+rw,ry+rh);
  
    // if ANY of the above are true, the line
    // has hit the rectangle
    if (left || right || top || bottom) {
      return true;
    }
    return false;
  }
  
  public static boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
    // calculate the direction of the lines
    float uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
    float uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
  
    // if uA and uB are between 0-1, lines are colliding
    if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) return true;
    return false;
  }
  
  public static float roundTo(float number, int rounder) {
    float num = ((float)floor(number * rounder)) / rounder;
    return num;
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
        return Util.pointInBox(pointX, pointY, this.x, this.y, this.w, this.h);
    }
    
    public boolean collidesWith(float lineX1, float lineY1, float lineX2, float lineY2) {
      return Util.lineRect(lineX1, lineY1, lineX2, lineY2, this.x, this.y, this.w, this.h);
    }
    
    void show() {
      int s = 4;
      rect(x * TILE_SIZE/s, y * TILE_SIZE/s, w * TILE_SIZE/s, h * TILE_SIZE/s);
    }
    
}
