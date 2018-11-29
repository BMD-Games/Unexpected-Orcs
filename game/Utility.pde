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
