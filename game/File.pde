import java.io.*;

public void saveStats(String filename){
  // set up file reader
  PrintWriter printer;

  println("saving");
  // attack
  printer =  createWriter("/saves/" + filename + ".txt");
  for(Integer tier: engine.player.stats.attackKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.attackKills.get(tier) + ";");
    printer.println();
  }
  
  // defence
  for(Integer tier: engine.player.stats.defenceKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.defenceKills.get(tier) + ";");
    printer.println();
  }
  
  // vitality
  for(Integer tier: engine.player.stats.vitalityKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.vitalityKills.get(tier) + ";");
    printer.println();
  }
  
  // wisdom
  for(Integer tier: engine.player.stats.wisdomKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.wisdomKills.get(tier) + ";");
    printer.println();
  }
  
  // health
  for(Integer tier: engine.player.stats.healthKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.healthKills.get(tier) + ";");
    printer.println();
  }
  
  // mana
  for(Integer tier: engine.player.stats.manaKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.manaKills.get(tier) + ";");
    printer.println();
  }
  
  // speed
  for(Integer tier: engine.player.stats.speedKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.speedKills.get(tier) + ";");
    printer.println();
  }
  
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
  
  // attack
  String line = reader.readLine();
  String[] splitLine = line.split(";");
  println("here");
  playerToReturn.stats.attackKills = makeHashmap(splitLine);
  playerToReturn.stats.calcAllStats();
  // defence
  
  
  
  return playerToReturn;  
}

public HashMap<Integer, Integer> makeHashmap(String[] splitLine) {
  
  HashMap <Integer, Integer> statsMap = new HashMap<Integer, Integer>();
  
  for(String pair : splitLine) {
    int i = pair.indexOf(',');
    Integer tier = Integer.parseInt(pair.substring(0,i));
    Integer killCount = Integer.parseInt(pair.substring(i + 1));
    println(tier);
    println(killCount);
    statsMap.put(tier, killCount);
    
  }
  println(statsMap);
  return statsMap;
}
