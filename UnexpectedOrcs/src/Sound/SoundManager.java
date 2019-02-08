package Sound;

import File.GameFile;
import Utility.Util;
import processing.core.PApplet;
import processing.sound.SoundFile;

import java.util.HashMap;

import static Utility.Constants.game;

public class SoundManager {

    public static HashMap<String, SoundFile> soundFiles;
    public static HashMap<String, SoundFile> musicFiles;

    SoundFile file;

    public static void loadSounds(PApplet app) {

        String soundPath = "/assets/sound/sfx/";
        String musicPath = "/assets/sound/music/";

        soundFiles = new HashMap<String, SoundFile>();
        musicFiles = new HashMap<String, SoundFile>();

        String[] soundNames = GameFile.allFilesInDirectory(soundPath);
        String[] musicNames = GameFile.allFilesInDirectory(musicPath);

        for(String name : soundNames) {
            game.println(name);
            soundFiles.put(Util.stripExstension(name).toUpperCase(), new SoundFile(app, app.sketchPath() + soundPath + name));
        }

        for(String name : musicNames) {
            game.println(name);
            musicFiles.put(Util.stripExstension(name).toUpperCase(), new SoundFile(app, app.sketchPath() + musicPath + name));
        }

    }
}