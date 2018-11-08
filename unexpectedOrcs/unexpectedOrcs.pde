final static int TILE_SIZE = 32;

String STATE;

Engine engine;
GUI gui;

void setup() {
  size (1080, 720);
  
  textAlign(CENTER, CENTER);
  textSize(TILE_SIZE);
  
  setState("MENU");
  
  gui = new GUI();
  engine = new Engine();
}

void draw() {
  switch(STATE) {
    case "MENU":
      gui.drawMenu();
      break;
    case "OPTIONS":
      gui.drawOptions();
      break;
    case "PLAYING":
      engine.update();
      engine.show();
      gui.drawPause();
      break;
    case "PAUSED":
      gui.drawPaused();
      break;
  }
}

void mouseReleased() {
  if(STATE == "PLAYING") engine.handleMouseReleased();
  gui.handleMouseReleased();
}

void setState(String state) {
  STATE = state;
}

boolean pointInBox(float px, float py, float bx, float by, float w, float h) {
  //returns whether a point (px, py) is inside a box (bx, by, w, h);
  return((px > bx) && (px < bx + w) && (py > by) && (py < by + h));
}
