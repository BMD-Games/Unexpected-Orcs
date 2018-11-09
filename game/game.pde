final static int TILE_SIZE = 32;

public String STATE;
public String PREV_STATE;

public Engine engine;
public GUI gui;

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

void keyPressed() {
  
}

void keyReleased() {
  
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

public PGraphics crossHatch(PGraphics graphic) {
  int size = 2, len = 100, num = 9;
  graphic.beginDraw();
  graphic.noStroke();
  for(int i = 0; i < width/num; i ++) {
    for(int j = 0; j < height/num; j++) {
      graphic.pushMatrix();
      graphic.translate(random(-len, len) + i * num, random(-len, len) + j * num);
      graphic.rotate(random(PI));
      for(int k = 0; k < num; k ++) {
        graphic.fill(255);
        if(k % 2 == 0) graphic.fill(50);
        graphic.rect(k * size, 0, size, len);
      }
      graphic.popMatrix();
    }
  }
  graphic.endDraw();
  return graphic;
}
