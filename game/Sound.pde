import processing.sound.*;
SoundFile file;

void setupSound() {
  
  
  SoundFile file = new SoundFile(this, "/assets/music/song.mp3");
  file.play();
  
  
}
