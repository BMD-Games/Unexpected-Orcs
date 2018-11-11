import java.util.Map;
HashMap<Integer, PImage> tileSprites;
HashMap<Integer, PImage> itemSprites;
HashMap<String, PImage> guiSprites;
HashMap<String, PImage> charSprites;

final static int TILE_SIZE = 64;
final static int SPRITE_SIZE = 16;
final static int SCALE = TILE_SIZE/SPRITE_SIZE;

public int[] keys = {0, 0, 0, 0};

public String STATE;
public String PREV_STATE;

public Engine engine;
public GUI gui;

void setup() {
  size(1080, 720, FX2D);
  noSmooth();
  frameRate(60);
  
  loadAssets();
  
  textAlign(CENTER, CENTER);
  textSize(TILE_SIZE);
  
  setState("MENU");
  cursor(guiSprites.get("CURSOR"));
  
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
      
      engine.show();
      engine.update();
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
  if(key == 'W' || key == 'w') keys[0] = 1;
  if(key == 'A' || key == 'a') keys[1] = 1;
  if(key == 'S' || key == 's') keys[2] = 1;
  if(key == 'D' || key == 'd') keys[3] = 1;
}
void keyReleased() {
  if(key == 'W' || key == 'w') keys[0] = 0; 
  if(key == 'A' || key == 'a') keys[1] = 0;
  if(key == 'S' || key == 's') keys[2] = 0;
  if(key == 'D' || key == 'd') keys[3] = 0;
}

public void quitGame() {
  //do all savey-stuff here
  
  exit();
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
