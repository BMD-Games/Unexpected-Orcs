class Player {
  
  public float x, y, w = 0.5, h = 0.5;
  private float speed = 2, speedMod = 1, ang;
  private int health = 100, size = TILE_SIZE/2;
  
  private AABB bound;

  boolean slowed = false;
  
  Player(float x, float y) {
    this.x = x; //in tile space, not screen space;
    this.y = y;
    bound = new AABB(x, y, w, h);
  }
  
  public void move(double delta, int[] neighbours) {
    x += getDX(delta, neighbours); 
    y += getDY(delta, neighbours);
  }
  
  private float getDX(double delta, int[] neighbours) {
    float dx = (float)((keys[3] - keys[1]) * (delta * speed * speedMod));
    int xpos = (int)x;
    int ypos = (int)y;
    if(dx < 0 && neighbours[3] <= WALL) {
      AABB tile = new AABB(xpos - 1, ypos, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    } else if(dx > 0 && neighbours[1] <= WALL) {
      AABB tile = new AABB(xpos + 1, ypos, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    }    
    return dx;
  }

  private float getDY(double delta, int[] neighbours) {
    float dy = (float)((keys[2] - keys[0]) * (delta * speed * speedMod));
    int xpos = (int)x;
    int ypos = (int)y;
    if(dy < 0 && neighbours[0] <= WALL) {
      AABB tile = new AABB(xpos, ypos - 1, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    } else if(dy > 0 && neighbours[2] <= WALL) {
      AABB tile = new AABB(xpos, ypos + 1, 1, 1);
      if(bound.collidesWith(tile)) return 0;
    }  
    return dy;
  }

  public void update(double delta, int[] neighbours) {
    ang = atan2(mouseY - height/2, mouseX - width/2);
    move(delta, neighbours);
    updateBound();
  }
  
  public void show() {
    pushMatrix();
    translate(width/2, height/2);
    rotate(ang);
    fill(255, 0, 0);
    rect(-size/2, -size/2, size, size);
    stroke(0);
    line(0, 0, size, 0);
    popMatrix();
  }
  
  private void updateBound() {
    bound.x = x - bound.w/2;
    bound.y = y - bound.h/2; 
  }
  
}
