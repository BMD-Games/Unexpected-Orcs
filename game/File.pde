import java.io.*;

public void readStats(PrintWriter printer, HashMap<Integer, Integer> killsMap) {

  for (Integer tier : killsMap.keySet()) {
    printer.print(tier + "," + killsMap.get(tier) + ";");
  }
  printer.println();
}


public void saveGame() {
  if (loadedPlayerName != null && loadedPlayerName != "") {
    saveStats(loadedPlayerName);
    //saveInventory(loadedPlayerName);
  }
}

public void saveInventory(String savename) {
  try {
    FileOutputStream invSaveFile = new FileOutputStream(sketchPath() + "/saves/" + savename + "/inventory.txt");
    ObjectOutputStream inv = new ObjectOutputStream(invSaveFile);
    inv.writeObject(engine.player.inv);
    inv.close();
    invSaveFile.close();
  } 
  catch(Exception e) { 
    println(e);
  }
}

public Inventory loadInventory(String savename) {
  Inventory inv = new Inventory();

  try {
    FileInputStream invSaveFile = new FileInputStream(sketchPath() + "/saves/" + savename + "/inventory.txt");
    ObjectInputStream in = new ObjectInputStream(invSaveFile);
    println("yeet");
    inv =  (Inventory) in.readObject();
    println("peen");
    in.close();
    invSaveFile.close();
  } 
  catch(Exception e) { 
    println("load: " + e);
  }
  println(inv);
  return inv;
}

public void saveStats(String savename) {
  // set up file reader
  PrintWriter printer;

  // attack
  printer =  createWriter("/saves/" + savename + "/" + savename + ".txt");

  readStats(printer, engine.player.stats.attackKills);
  readStats(printer, engine.player.stats.defenceKills);
  readStats(printer, engine.player.stats.vitalityKills);
  readStats(printer, engine.player.stats.wisdomKills);
  readStats(printer, engine.player.stats.healthKills);
  readStats(printer, engine.player.stats.manaKills);
  readStats(printer, engine.player.stats.speedKills);

  printer.flush();
  printer.close();
}

public Player readStats(String savename) throws IOException {

  Player playerToReturn = new Player(engine.currentLevel.start.x + 0.5, engine.currentLevel.start.y + 0.5);
  BufferedReader reader = null;
  try { // read the file
    println("yeet");
    reader = new BufferedReader(new FileReader(sketchPath() + "/saves/" + savename + "/" + savename + ".txt"));
    println("double yeet");
  } 
  catch (IOException ioException) {
    println("wank", ioException);
  }

  if (reader == null) {
    return playerToReturn;
  }

  playerToReturn.stats.attackKills = makeHashmap(reader);
  playerToReturn.stats.defenceKills = makeHashmap(reader);
  playerToReturn.stats.vitalityKills = makeHashmap(reader);
  playerToReturn.stats.wisdomKills = makeHashmap(reader);
  playerToReturn.stats.healthKills = makeHashmap(reader);
  playerToReturn.stats.manaKills = makeHashmap(reader);
  playerToReturn.stats.speedKills = makeHashmap(reader);
  playerToReturn.stats.calcAllStats(); 

  //playerToReturn.inv = loadInventory(savename);

  return playerToReturn;
}

public ScrollElement[] loadSaves() {

  java.io.File folder = new java.io.File(sketchPath() + "/saves/");
  String[] listOfFiles = folder.list(new FilenameFilter() {
    @Override
      public boolean accept(File current, String name) {
      return new File(current, name).isDirectory();
    }
  }
  );
  ScrollElement[] scrollElements = new ScrollElement[listOfFiles.length];
  loadedPlayers = new Player[listOfFiles.length];

  for (int i = 0; i < listOfFiles.length; i++) {
    try {
      println(listOfFiles[i]);
      loadedPlayers[i] = readStats(listOfFiles[i]);
      scrollElements[i] = new ScrollElement(listOfFiles[i], loadedPlayers[i].stats.toString(), 200);
    } 
    catch (Exception ioe) {
      println("twat", ioe);
    }
  }

  return scrollElements;
}

public HashMap<Integer, Integer> makeHashmap(BufferedReader reader) {

  HashMap <Integer, Integer> statsMap = new HashMap<Integer, Integer>();
  String[] splitLine = new String[0];
  try {
    String line = reader.readLine();
    if (line != null && line.indexOf(";") != -1) {
      splitLine = line.split(";");
    }
  } 
  catch (IOException ioe) {
  }

  for (String pair : splitLine) {
    int i = pair.indexOf(',');
    if(i == -1) continue;
    Integer tier = Integer.parseInt(pair.substring(0, i));
    Integer killCount = Integer.parseInt(pair.substring(i + 1));
    statsMap.put(tier, killCount);
  }
  return statsMap;
}
