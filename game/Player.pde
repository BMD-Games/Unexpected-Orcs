class Player {
  
  public float x, y;
  private float speed = 4, ang;
  private int health = 100, size = TILE_SIZE/2;
  
  boolean slowed = false;
  
  Player(float x, float y) {
    this.x = x; //in tile space, not screen space;
    this.y = y;
  }
  
  public void move(double delta, int[] neighbours) {
    int mod = 1;
    if(slowed) mod = 2;
    x += (keys[3] - keys[1]) * (delta * speed/mod);
    y += (keys[2] - keys[0]) * (delta * speed/mod);
  }
  
  public void update(double delta, int[] neighbours) {
    ang = atan2(mouseY - height/2, mouseX - width/2);
    move(delta, neighbours);
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
  
}
