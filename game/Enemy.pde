interface Enemy {
  
  public int tier = 0;
  public int x = 0;
  public int y = 0;
  
  /* Enemies need to update on tics */
  void update(Player player);
  
  /* Displays enemy to screen */
  void show(PGraphics screen, PVector renderOffset);
  
  /* This mob takes damage */
  void damage(int amount);
  
}
