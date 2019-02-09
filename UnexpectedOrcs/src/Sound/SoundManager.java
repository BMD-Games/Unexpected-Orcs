package Sound;

import File.GameFile;
import Utility.Util;
import processing.core.PApplet;
import processing.sound.SoundFile;

import java.io.File;
import java.util.HashMap;

import static Utility.Constants.game;

public class SoundManager {

    public static HashMap<String, SoundFile> soundFiles;
    public static HashMap<String, SoundFile> musicFiles;

    public static String soundPath = "./assets/sound/sfx";
    public static String musicPath = "./assets/sound/music";

    public static PApplet app;

    SoundFile file;

    public static void setPApplet(PApplet papp) {
        app = papp;
    }

    public static void loadSounds(PApplet app) {
        setPApplet(app);

        soundFiles = new HashMap<String, SoundFile>();
        musicFiles = new HashMap<String, SoundFile>();

        addAllFilesToMap(soundFiles, soundPath);
        addAllFilesToMap(musicFiles, musicPath);

    }

    public static void playSound(String sound) {
        try {
            soundFiles.get(sound).play();
        } catch (Exception e) {}
    }

    public static void playMusic(String music) {
        try {
            musicFiles.get(music).play();
        } catch (Exception e) {}
    }


    public static void addAllFilesToMap(HashMap<String, SoundFile> map, String path) {
        addAllFilesToMap(map, path, path);
    }

    public static void addAllFilesToMap(HashMap<String, SoundFile> map, String path, String originalPath) {
        File file = new File(path);

        if(file.isDirectory()) {
            //in folder, need to check all entries
            if(file.list().length != 0){
                for (String f : file.list()) {
                    addAllFilesToMap(map, path + "/" + f, originalPath);
                }
            }

        } else {
            //add file to map
            String name = path.substring(originalPath.length() + 1).replace('/', '_');
            name = Util.stripExstension(name).toUpperCase();
            map.put(name, new SoundFile(app, path));
        }
    }

}