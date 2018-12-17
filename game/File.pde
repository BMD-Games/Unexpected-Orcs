public void saveStats(String filename){
  // set up file reader
  PrintWriter printer;
  try {
    File file = new File(filename);
    printer =  new PrintWriter(filename);
    printer.println(engine.player.stats.attack);
    printer.println(engine.player.stats.defence);
    printer.println(engine.player.stats.vitality);
    printer.println(engine.player.stats.wisdom);
    printer.println(engine.player.stats.getHealthMax());
    printer.println(engine.player.stats.getManaMax());
    
    printer.flush();
    printer.close();
  } catch (IOException ioe) {
    System.out.println(ioe);
  }
  
  
}

public void readStats(String filename){
  
  
}
