class Level {
  
  private int[][] tiles;
  private int w, h;
  private String name;
  public ArrayList<Enemy> enemies  = new ArrayList<Enemy>();
  
  Level(int w, int h) {
    this.w = w;
    this.h = h;
  }
  
  public void setTiles(int[][] tiles) {
    this.tiles = tiles;
  }
  
  public int[][] getTiles() {
    return tiles;
  }
  
  public void setTile(int t, int i, int j) {
    tiles[i][j] = t;
  }
  
  public int getTile(int i, int j) {
    return tiles[i][j];
  }
  
  public int getWidth() {
    return w;
  }
  
  public int getHeight() {
    return h;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  } 
}
