//Saving and Loading
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;

void saveToFile() {
  FileChooser fileChooser = new FileChooser();
  fileChooser.setTitle("Save Tile Template");
  fileChooser.getExtensionFilters().add(new ExtensionFilter("Save Files", "*.txt"));
  fileChooser.setInitialFileName("room_template" + year() + "_" + month() + "_" + day() + "_" + hour() + "-" + minute() + ".txt");
  fileChooser.setInitialDirectory(new File(sketchPath() + "/out/")); 
  
  File selectedFile = fileChooser.showSaveDialog(new Stage());
  if (selectedFile != null) {
    saveToFile(selectedFile.getPath());
  }
}

void loadFromFile() {
  FileChooser fileChooser = new FileChooser();
  fileChooser.setTitle("Open Template File");
  fileChooser.getExtensionFilters().add(new ExtensionFilter("Save Files", "*.txt"));
  fileChooser.setInitialDirectory(new File(sketchPath() + "/out/"));
  
  File selectedFile = fileChooser.showOpenDialog(new Stage());
  if (selectedFile != null) {
    loadFromFile(selectedFile.getPath());
  }
}

void saveToFile(String path) {
  PrintWriter file = createWriter(path);
  file.println("Room room = new Room();");
  //print tiles
  file.println("room.setTiles(new String[][] {");
  for (int i = 0; i < w; i ++) {
    file.print("{");
    for (int j = 0; j < h; j ++) {
      file.print("\"" + tiles[i][j] + "\"");
      if (j < h - 1) file.print(",");
    }
    file.print("}");
    if (i < w - 1) file.print(",");
    file.println();
  }
  file.println("}");
  file.println(");");
  
  //print doors
  file.println("room.doors = new Vec2[] {");
  for(int  i = 0; i < doors.size(); i ++) {
    file.print("new Vec2(");
    file.print((int)doors.get(i).x + ", " + (int)doors.get(i).y);
    file.println(")" + (i == doors.size() - 1 ? "" : ","));
  }
  file.println("};");
  
  file.flush();
  file.close();
}

void loadFromFile(String path) {
  ArrayList<String[]> loadedTiles = new ArrayList<String[]>();
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
    for(int i = 0; i < values.length; i ++) {
      if(values[i].equals("null")) {
        values[i] = null;
      }
      values[i] = values[i].replace("\"", "");
    }
    loadedTiles.add(values);
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
    tiles = new String[w][h];
    for (int i = 0; i < w; i ++) {
      tiles[i] = loadedTiles.get(i);
    }
  } 
  catch(Exception e) { 
    println("Error in file: " + path); 
    return;
  }
}
