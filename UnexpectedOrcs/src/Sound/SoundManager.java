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

    public static PApplet app;

    SoundFile file;

    public static void setPApplet(PApplet papp) {
        app = papp;
    }

    public static void loadSounds(PApplet app) {
        setPApplet(app);

        String soundPath = "/assets/sound/sfx/";
        String musicPath = "/assets/sound/music/";

        soundFiles = new HashMap<String, SoundFile>();
        musicFiles = new HashMap<String, SoundFile>();

        String[] soundNames = GameFile.allFilesInDirectory(soundPath);
        String[] musicNames = GameFile.allFilesInDirectory(musicPath);

        for(String name : soundNames) {
            soundFiles.put(Util.stripExstension(name).toUpperCase(), new SoundFile(app, app.sketchPath() + soundPath + name));
        }

        for(String name : musicNames) {
            musicFiles.put(Util.stripExstension(name).toUpperCase(), new SoundFile(app, app.sketchPath() + musicPath + name));
        }

    }

    public static void playSound(String sound) {
        soundFiles.get(sound).play();
    }

    public static void playMusic(String music) {
        musicFiles.get(music).play();
    }
}