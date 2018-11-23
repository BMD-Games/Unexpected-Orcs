interface Enemy {
  
  public int tier = 0;
  public int x = 0;
  public int y = 0;
  
  /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY);
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset);
  
  /* This mob takes damage */
  public void damage(int amount);
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY);
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box);
  
}
