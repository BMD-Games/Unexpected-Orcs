import java.util.Map;
HashMap<Integer, PImage> tileSprites;

String path = "/out/Room_";

int SPRITE_SIZE = 16;
int MAX_SCALE = 8;
int SCALE = 4;
int TILE_SIZE = SPRITE_SIZE * SCALE;

int GUI_WIDTH = SPRITE_SIZE * MAX_SCALE + 20;

final int MIN_TILE = -65, MAX_TILE = 14;
int tile = WALL;

int w = 10;
int h = 10;

int[][] tiles = new int[w][h];

void setup() {
  size(1080, 720);
  noSmooth();
  background(0);
  loadAssets();
}

void draw() {
  TILE_SIZE = SPRITE_SIZE * SCALE;
  background(0);
  image(tileSprites.get(tile), 10, 10, SPRITE_SIZE * MAX_SCALE, SPRITE_SIZE * MAX_SCALE);
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      image(tileSprites.get(tiles[i][j]), GUI_WIDTH + i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
  }
  noFill();
  stroke(255);
  rect((int)getTile().x * TILE_SIZE + GUI_WIDTH, (int)getTile().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
}

void mouseReleased() {
  PVector pos = getTile();
  try { tiles[(int)pos.x][(int)pos.y] = tile; } catch(Exception e) {}
}

void mouseDragged() {
  PVector pos = getTile();
  try { tiles[(int)pos.x][(int)pos.y] = tile; } catch(Exception e) {}
}

void mouseWheel(MouseEvent event) {
  int e = event.getCount();
  tile = constrain(tile + e, MIN_TILE, MAX_TILE);
  if (tile == -1 && e < 0) tile = -2;
  if (tile == -1 && e > 0) tile = 0;
  println(tile);
}

void keyPressed() {
  if (keyCode == UP) SCALE = constrain(SCALE + 1, 1, MAX_SCALE);
  if (keyCode == DOWN) SCALE = constrain(SCALE - 1, 1, MAX_SCALE);
  if (key == ' ') saveToFile();
}

PVector getTile() {
  return new PVector((mouseX-GUI_WIDTH)/TILE_SIZE, mouseY/TILE_SIZE);
}

void saveToFile() {
  PrintWriter file = createWriter(path + year() + "_" + month() + "_" + day() + "_" + hour() + "_" + minute() + "_" + second() + ".txt");
  for (int j = 0; j < h; j ++) {
    file.print("[");
    for (int i = 0; i < w; i ++) {
      file.print(tiles[i][j]);
      if (i < w -1) file.print(',');
    }
    file.println("]");
  }
  file.flush();
  file.close();
}
