import java.util.Map;

int SPRITE_SIZE = 16;
int MAX_SCALE = 8;
float SCALE = 4;
int TILE_SIZE = (int)(SPRITE_SIZE * SCALE);

int GUI_WIDTH = SPRITE_SIZE * MAX_SCALE + 20;

int w = 15;
int h = 20;

float xoff = 0, yoff = 0;

String[][] tiles = new String[w][h];
String[] tileNames;

int tile = 0;

PGraphics pg;

void setup() {
  size(1080, 720, FX2D);
  noSmooth();
  background(200);
  
  loadTileJSON("../UnexpectedOrcs/assets/data/tiles.json");
  initMenu();
  
  pg = createGraphics(width, height);
  pg.noSmooth();
  
  clearTiles();
  
}

void draw() {
  TILE_SIZE = (int)(SPRITE_SIZE * SCALE);
  pg.beginDraw();
  pg.background(100);
  pg.noFill();
  pg.stroke(150);
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      try {
        if(tiles[i][j] != null && !tileOffScreen(i, j)) pg.image(tileSprites.get(tiles[i][j]), (i * TILE_SIZE) + xoff, (j * TILE_SIZE) + yoff, TILE_SIZE, TILE_SIZE);
        else pg.rect((i * TILE_SIZE) + xoff, (j * TILE_SIZE) + yoff, TILE_SIZE, TILE_SIZE);
      } catch(Exception e) {
        println(tiles[i][j]);
      }
    }
  }
  pg.noFill();
  pg.stroke(255);
  pg.rect(((int)getTile().x * TILE_SIZE) + xoff, ((int)getTile().y * TILE_SIZE) + yoff, TILE_SIZE, TILE_SIZE);
  pg.fill(150);
  pg.noStroke();
  pg.rect(width - GUI_WIDTH, 0, GUI_WIDTH, height);
  drawMenu();
  pg.endDraw();
  
  image(pg, 0, 0, width, height);
}

void mouseReleased() {
  if(mouseButton == RIGHT) return;
  if(mouseInBox(width - GUI_WIDTH, 0, GUI_WIDTH, height)) {
    checkMenu();
    return;
  }
  PVector pos = getTile();
  try { 
    tiles[(int)pos.x][(int)pos.y] = tileNames[tile];
  } 
  catch(Exception e) {
  }
}

void mouseDragged() {
  if(mouseInBox(width - GUI_WIDTH, 0, GUI_WIDTH, height)) return;
  
  if(mouseButton == RIGHT) {
    yoff += (mouseY - pmouseY);
    xoff += (mouseX - pmouseX);
    return;
  }
  
  PVector pos = getTile();
  try { 
    tiles[(int)pos.x][(int)pos.y] = tileNames[tile];
  } 
  catch(Exception e) {
  }
}

void mouseWheel(MouseEvent event) {
  int e = event.getCount();  
  SCALE = constrain(SCALE - ((e * SCALE)/10f), 0.1, MAX_SCALE);
}

void keyPressed() {
  
  if (keyCode == UP) yoff += 1;
  if (keyCode == DOWN) yoff -= 1;
  if (keyCode == LEFT) xoff += 1;
  if (keyCode == RIGHT) xoff -= 1;
  if (key == '=') SCALE = constrain(SCALE + 1, 1, MAX_SCALE);
  if (key == '-') SCALE = constrain(SCALE - 1, 1, MAX_SCALE);
  if (key == 'S') saveToFile();
  if (key == 'L') loadFromFile();
  if (key == 'C') clearTiles();
  if (key == ' ') { xoff = yoff = 0; SCALE = 4; }
}

PVector getTile() {
  return new PVector((mouseX - xoff)/TILE_SIZE, (mouseY - yoff)/TILE_SIZE);
}

PVector getScreen(int x, int y) {
  return new PVector((x * TILE_SIZE)  + xoff, (y * TILE_SIZE) + yoff);
}

boolean pointInBox(float x, float y, float bx, float by, float bw, float bh) {
  return (x >= bx && x <= bx + bw && y >= by && y <= by + bh);
}

boolean mouseInBox(float x, float y, float w, float h) {
  return pointInBox(mouseX, mouseY, x, y, w, h);
}

PImage getSprite(PImage image, int x, int y, int w, int h, int spriteSize) {
  return image.get(x * spriteSize, y * spriteSize, w * spriteSize, h * spriteSize);
}

boolean tileOffScreen(int i, int j) {
  return (i * TILE_SIZE) + xoff < -TILE_SIZE || (i * TILE_SIZE) + xoff > width - GUI_WIDTH || (j * TILE_SIZE) + yoff < - TILE_SIZE || (j * TILE_SIZE) + yoff > height;
}

void clearTiles() {
  tiles = new String[w][h];
  for(int i = 0; i < w; i ++) {
    for(int j = 0; j < h; j ++) {
      tiles[i][j] = "WALL";
    }
  }
}
