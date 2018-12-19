import java.io.*;

public void saveStats(String filename){
  // set up file reader
  PrintWriter printer;

  println("saving");
  printer =  createWriter("/saves/" + filename + ".txt");
  for(Integer tier: engine.player.stats.attackKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.attackKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.defenceKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.defenceKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.vitalityKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.vitalityKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.wisdomKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.wisdomKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.healthKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.healthKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.manaKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.manaKills.get(tier) + ";");
    printer.println();
  }
  for(Integer tier: engine.player.stats.speedKills.keySet()) {
    printer.print(tier + "," + engine.player.stats.speedKills.get(tier) + ";");
    printer.println();
  }
  
  printer.flush();
  printer.close();
  
  
}

public void readStats(String filename){
  
  
  BufferedReader reader = null;
  try { // read the file
      reader = new BufferedReader(new FileReader(filename));
  } catch (IOException ioException) {
  
  }
}
