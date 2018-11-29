import processing.sound.*;
SoundFile file;

public void loadSounds() {
  
  soundFiles = new HashMap<String, SoundFile>();
  
  soundFiles.put("WHOOSH", new SoundFile(this, "/assets/music/whoosh.wav"));
  soundFiles.put("FLYBY", new SoundFile(this, "/assets/music/flyby.wav"));
  soundFiles.put("FLAME", new SoundFile(this, "/assets/music/flame.wav"));
  
}
