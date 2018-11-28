public interface Enemy {
  
  public int tier = 0;
  public float x = 0;
  public float y = 0;
  
  /* Enemies need to update on tics */
  public boolean update(double delta, float playerX, float playerY);
  
  /* Displays enemy to screen */
  public void show(PGraphics screen, PVector renderOffset);
  
  /* This mob takes damage */
  public void damage(int amount);
  
  /* Drop what on death */
  public void onDeath();
  
  /* Checks collision with point */
  public boolean pointCollides(float pointX, float pointY);
  
  /* Checks collision with area  */
  public boolean AABBCollides(AABB box);
  
  /* Checks if mob collides with any walls */
  public boolean validPosition(Level level, float xPos, float yPos);
  
}
