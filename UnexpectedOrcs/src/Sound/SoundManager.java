package Sound;

import File.GameFile;
import Settings.Settings;
import Utility.Util;
import processing.core.PApplet;
import processing.sound.Sound;
import processing.sound.SoundFile;

import java.io.File;
import java.util.HashMap;

import static Utility.Constants.game;

public class SoundManager {

    private static HashMap<String, SoundFile> soundFiles;
    private static HashMap<String, SoundFile> musicFiles;

    private static String soundPath = "./assets/sound/sfx";
    private static String musicPath = "./assets/sound/music";

    private static String currentMusic = "";

    private static PApplet app;

    private static Sound sound;

    SoundFile file;

    private static void setPApplet(PApplet papp) {
        app = papp;
    }

    public static void loadSounds(PApplet app) {
        setPApplet(app);

        soundFiles = new HashMap<String, SoundFile>();
        musicFiles = new HashMap<String, SoundFile>();

        addAllFilesToMap(soundFiles, soundPath);
        addAllFilesToMap(musicFiles, musicPath);
         sound = new Sound(app);

    }

    public static void playSound(String sound) {
        try {
            soundFiles.get(sound).amp(Settings.SOUND_VOLUME);
            soundFiles.get(sound).play();
        } catch (Exception e) {}
    }

    public static void playMusic(String music) {
        try {
            musicFiles.get(currentMusic).stop();
        } catch (Exception e) {}
        try {
            currentMusic = music;
            musicFiles.get(music).amp(Settings.MUSIC_VOLUME);
            musicFiles.get(music).loop();
        } catch (Exception e) {
            game.println("Music \"" + music + "\" doesn't exist. The only existing files are:");
            for(String key : musicFiles.keySet()) {
                game.println(key);
            }
        }
    }

    public static void setMasterVolume(float vol) {
        sound.volume(vol);
    }

    public static void setMusicVolume(float vol) {
        try {
            musicFiles.get(currentMusic).amp(vol);
        } catch(Exception e) {}
    }

    private static void addAllFilesToMap(HashMap<String, SoundFile> map, String path) {
        addAllFilesToMap(map, path, path);
    }

    private static void addAllFilesToMap(HashMap<String, SoundFile> map, String path, String originalPath) {
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