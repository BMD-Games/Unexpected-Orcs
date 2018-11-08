class Engine {
  /**
  This class is used to update and track all the game objects (eg player, monsters, levels)
  **/
  double lastUpdate = 0;
  
  void update() {
    //updates all game objects
    double delta = millis() - lastUpdate;
    println(delta);

    
    lastUpdate = millis();
  }
  
  void show() {
    background(255);
    //level.show();
    //player.show();
    //enemies.show();
  }
  
  void handleMouseReleased() {
    //handle mouse released
  }
  
}
