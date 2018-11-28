import java.util.Map;

HashMap<Integer, PImage> tileSprites;
HashMap<String, PImage> itemSprites;
HashMap<String, PImage> guiSprites;
HashMap<String, PImage> charSprites;
HashMap<String, PImage> projectileSprites;
HashMap<String, PImage> dropSprites;

final int GUI_WIDTH = 240;

final int TILE_SIZE = 64;
final int SPRITE_SIZE = 16;
final int SCALE = TILE_SIZE/SPRITE_SIZE;

public int[] keys = {0, 0, 0, 0, 0};
public boolean inMenu = false;

public boolean assetsLoaded = false;

public String STATE;
public String PREV_STATE;

public Engine engine;
public GUI gui;

void setup() {
  size(1080, 720, FX2D);
  noSmooth();
  frameRate(60);
  
  thread("loadAssets");
  thread("loadSettings");
  
  textAlign(CENTER, CENTER);
  textSize(TILE_SIZE);
  
  setState("MENU");
  
  gui = new GUI();
  engine = new Engine();
}

void draw() {
  if(!assetsLoaded) return;
  switch(STATE) {
    case "MENU":
      gui.drawMenu();
      break;
    case "OPTIONS":
      gui.drawOptions();
      break;
    case "PLAYING":
      //thread("update");
      engine.update();
      engine.show();
      gui.drawUnpaused(engine.player);
      break;
    case "PAUSED":
      gui.drawUnpaused(engine.player);
      engine.show();
      gui.drawPaused();
      
      break;
    case "DEAD":
      gui.drawDead();
      break;
  }
}

public void update() {
  engine.update();
}

void mouseReleased() {
  gui.handleMouseReleased();
}

void keyPressed() {
  if(remapNextKey) remapKey(remapAction, keyCode);
  if(keyCode == UP_KEY) keys[up] = 1;
  if(keyCode == LEFT_KEY) keys[left] = 1;
  if(keyCode == DOWN_KEY) keys[down] = 1;
  if(keyCode == RIGHT_KEY) keys[right] = 1;
  if(keyCode == ABILITY_KEY) keys[ability] = 1;
}
void keyReleased() {
  if(keyCode == UP_KEY) keys[up] = 0;
  if(keyCode == LEFT_KEY) keys[left] = 0;
  if(keyCode == DOWN_KEY) keys[down] = 0;
  if(keyCode == RIGHT_KEY) keys[right] = 0;
  if(keyCode == ABILITY_KEY) keys[ability] = 0;
}

public void quitGame() {
  //do all savey-stuff here
  //saveGame();
  exit();
}

public void setState(String state) {
  remapNextKey = false;
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

public float fastAbs(float v) {
  if(v < 0) return v * -1;
  return v;
}
