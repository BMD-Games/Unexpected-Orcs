import java.io.*;

public void saveStats(String filename){
  // set up file reader
  PrintWriter printer;

  printer =  createWriter("/saves/" + filename + ".txt");
  System.out.println(engine.player.stats.attack);
  printer.println(engine.player.stats.attack);
  printer.println(engine.player.stats.defence);
  printer.println(engine.player.stats.vitality);
  printer.println(engine.player.stats.wisdom);
  printer.println(engine.player.stats.getHealthMax());
  printer.println(engine.player.stats.getManaMax());
  
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
