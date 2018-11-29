import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

HashMap<Integer, PImage> tileSprites;

int SPRITE_SIZE = 16;
int MAX_SCALE = 8;
int SCALE = 4;
int TILE_SIZE = SPRITE_SIZE * SCALE;

int GUI_WIDTH = SPRITE_SIZE * MAX_SCALE + 20;

final int MIN_TILE = -65, MAX_TILE = 14;
int tile = WALL;

int w = 10;
int h = 10;

int xoff = 0, yoff = 0;

int[][] tiles = new int[w][h];

void setup() {
  size(1080, 720);
  noSmooth();
  background(200);
  loadAssets();
}

void draw() {
  TILE_SIZE = SPRITE_SIZE * SCALE;
  background(200);
  for (int i = 0; i < w; i ++) {
    for (int j = 0; j < h; j ++) {
      image(tileSprites.get(tiles[i][j]), GUI_WIDTH + (i + xoff) * TILE_SIZE, (j + yoff) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
  }
  noFill();
  stroke(255);
  rect((int)getTile().x * TILE_SIZE + GUI_WIDTH, (int)getTile().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
  fill(200);
  noStroke();
  rect(0, 0, GUI_WIDTH, height);
  image(tileSprites.get(tile), 10, 10, SPRITE_SIZE * MAX_SCALE, SPRITE_SIZE * MAX_SCALE);
}

void mouseReleased() {
  PVector pos = getTile();
  try { 
    tiles[(int)pos.x][(int)pos.y] = tile;
  } 
  catch(Exception e) {
  }
}

void mouseDragged() {
  PVector pos = getTile();
  try { 
    tiles[(int)pos.x][(int)pos.y] = tile;
  } 
  catch(Exception e) {
  }
}

void mouseWheel(MouseEvent event) {
  int e = event.getCount();
  tile = constrain(tile + e, MIN_TILE, MAX_TILE);
  if (tile == -1 && e < 0) tile = -2;
  if (tile == -1 && e > 0) tile = 0;
}

void keyPressed() {
  if (keyCode == UP) yoff -= 1;
  if (keyCode == DOWN) yoff += 1;
  if (keyCode == LEFT) xoff -= 1;
  if (keyCode == RIGHT) xoff += 1;
  if (key == '=') SCALE = constrain(SCALE + 1, 1, MAX_SCALE);
  if (key == '-') SCALE = constrain(SCALE - 1, 1, MAX_SCALE);
  if (key == 's') saveToFile();
  if (key == 'l') loadFromFile();
  if (key == ' ') { xoff = yoff = 0; SCALE = 4; }
}

PVector getTile() {
  return new PVector((mouseX-GUI_WIDTH)/TILE_SIZE, mouseY/TILE_SIZE);
}

void saveToFile() {
  JFrame frame = new JFrame();
  JFileChooser chooseSave = new JFileChooser(sketchPath() + "/out/");
  int val = chooseSave.showOpenDialog(frame);
  if (val == JFileChooser.APPROVE_OPTION) {
    saveToFile(chooseSave.getSelectedFile().getPath());
  }
}

void saveToFile(String path) {
  PrintWriter file = createWriter(path);
  file.println("{");
  for (int i = 0; i < w; i ++) {
    file.print("{");
    for (int j = 0; j < h; j ++) {
      file.print(tiles[i][j]);
      if (j < h - 1) file.print(',');
    }
    file.print("}");
    if (i < w - 1) file.print(",");
    file.println();
  }
  file.print("}");
  file.flush();
  file.close();
}

void loadFromFile() {
  JFrame frame = new JFrame();
  JFileChooser chooseLoad = new JFileChooser(sketchPath() + "/out/");
  int val = chooseLoad.showOpenDialog(frame);
  if (val == JFileChooser.APPROVE_OPTION) {
    loadFromFile(chooseLoad.getSelectedFile().getPath());
  }
}

void loadFromFile(String path) {
  ArrayList<int[]> loadedTiles = new ArrayList<int[]>();
  BufferedReader reader = createReader(path);

  if (reader == null) { 
    println("Could not find file: " + path); 
    return;
  };
  String line = "";
  try { 
    reader.readLine(); 
    line = reader.readLine();
  } 
  catch(Exception e) { 
    line = null;
  };
  while (line != null) {
    if (line.equals("}")) break;
    line = line.substring(line.indexOf("{") + 1, line.indexOf("}"));
    String[] values = split(line, ',');
    int[] tileRow = new int[values.length];
    for (int i = 0; i < values.length; i ++) {
      try { 
        tileRow[i] = int(values[i]);
      } 
      catch(Exception e) { 
        println("Error in file: " + path); 
        return;
      }
    }
    loadedTiles.add(tileRow);
    try { 
      line = reader.readLine();
    } 
    catch(Exception e) { 
      line = null;
    };
  }

  try {
    w = loadedTiles.size();
    h = loadedTiles.get(0).length;
    tiles = new int[w][h];
    for (int i = 0; i < w; i ++) {
      tiles[i] = loadedTiles.get(i);
    }
  } 
  catch(Exception e) { 
    println("Error in file: " + path); 
    return;
  }
}
