import java.io.*;

public void readStats(PrintWriter printer, HashMap<Integer, Integer> killsMap) {
  
  for(Integer tier: killsMap.keySet()) {
    printer.print(tier + "," + killsMap.get(tier) + ";");
  }
  printer.println();
}


public void saveStats(String filename){
  // set up file reader
  PrintWriter printer;

  println("saving");
  // attack
  printer =  createWriter("/saves/" + filename + ".txt");
  
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

public Player readStats(String filename) throws IOException{
  
  Player playerToReturn = new Player(engine.currentLevel.start.x + 0.5, engine.currentLevel.start.y + 0.5);
  BufferedReader reader = null;
  try { // read the file
      reader = new BufferedReader(new FileReader(filename));
  } catch (IOException ioException) {
  
  }
  
  if(reader == null) {
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
  
  return playerToReturn;
  
}

public HashMap<Integer, Integer> makeHashmap(BufferedReader reader) {
  
  HashMap <Integer, Integer> statsMap = new HashMap<Integer, Integer>();
  String[] splitLine = new String[0];
  try {
    String line = reader.readLine();
    if(line != null) {
      splitLine = line.split(";");
    }
  } catch (IOException ioe) {}
  
  for(String pair : splitLine) {
    int i = pair.indexOf(',');
    Integer tier = Integer.parseInt(pair.substring(0,i));
    Integer killCount = Integer.parseInt(pair.substring(i + 1));
    statsMap.put(tier, killCount);
    
  }
  return statsMap;
}
