final static int TILE_SIZE = 32;

public String STATE;
public String PREV_STATE;

public Engine engine;
public GUI gui;

boolean show = true;

void setup() {
  size (1080, 720);
  
  textAlign(CENTER, CENTER);
  textSize(TILE_SIZE);
  
  setState("PLAYING");
  
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

void keyPressed() {
  
}

void keyReleased() {
  if(key == ' ') show = !show;
}

public void setState(String state) {
  PREV_STATE = STATE;
  STATE = state;
}

public void revertState() {
  STATE = PREV_STATE;
}

public boolean pointInBox(float px, float py, float bx, float by, float w, float h) {
  //returns whether a point (px, py) is inside a box (bx, by, w, h);
  return((px > bx) && (px < bx + w) && (py > by) && (py < by + h));
}
