package Settings;

import GUI.Screens.MenuScreen;
import GUI.Scroll.KeyRemapElement;
import GUI.Scroll.ScrollElement;
import GUI.Scroll.SliderElement;
import Sound.SoundManager;
import javafx.util.Pair;
import processing.data.JSONObject;

import static Utility.Constants.*;

public class Settings {
    
    public static int UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY, ABILITY_KEY, INTERACT_KEY;
    private static JSONObject settings, controls, sound;

    public static float MASTER_VOLUME, MUSIC_VOLUME, SOUND_VOLUME;

    public static boolean remapNextKey = false;
    public static boolean characterNaming = false;
    public static int remapAction = -1;

    public static void loadSettings() {
        settings = game.loadJSONObject("/assets/settings/settings.json");
        loadControls();
        loadSoundSettings();
    }

    public static Pair[] getElements() {
        Pair<String, ScrollElement[]>[] elements = new Pair[2];


        ScrollElement[] controls = new ScrollElement[6];
        controls[up] = new KeyRemapElement("UP", up);
        controls[down] = new KeyRemapElement("DOWN", down);
        controls[left] = new KeyRemapElement("LEFT", left);
        controls[right] = new KeyRemapElement("RIGHT", right);
        controls[ability] = new KeyRemapElement("ABILITY", ability);
        controls[interact] = new KeyRemapElement("INTERACT", interact);

        elements[0] = new Pair<>("Controls", controls);


        ScrollElement[] sound = new ScrollElement[3];
        sound[0] = new SliderElement("MASTER VOLUME", sliderValue -> { Settings.setMasterVolume(sliderValue); }, MASTER_VOLUME);
        sound[1] = new SliderElement("MUSIC VOLUME", sliderValue -> { Settings.setMusicVolume(sliderValue); }, MUSIC_VOLUME);
        sound[2] = new SliderElement("SOUND VOLUME", sliderValue -> { Settings.setSoundVolume(sliderValue); }, SOUND_VOLUME);

        elements[1] = new Pair<>("Sound", sound);


        return elements;
    }

    private static String getKeyFromCode(int code) {

        if(code == game.UP) code = '^';
        if(code == game.DOWN) code = 'V';
        if(code == game.RIGHT) code = '>';
        if(code == game.LEFT) code = '<';
        if(code == 32) code = '_'; //Space key

        return Character.toString((char)code);
    }

    private static String[] getKeys() {
        String[] keys = new String[6];
        keys[up] = getKeyFromCode(UP_KEY);
        keys[down] = getKeyFromCode(DOWN_KEY);
        keys[left] = getKeyFromCode(LEFT_KEY);
        keys[right] = getKeyFromCode(RIGHT_KEY);
        keys[ability] = getKeyFromCode(ABILITY_KEY);
        keys[interact] = getKeyFromCode(INTERACT_KEY);
        return keys;
    }

    public static String getKeyString(int action) {
        //lmao so lazy
        return getKeys()[action];
    }

    private static void loadControls() {
        controls = settings.getJSONObject("controls");
        UP_KEY = controls.getInt("UP", 87);
        DOWN_KEY = controls.getInt("DOWN", 83);
        LEFT_KEY = controls.getInt("LEFT", 65);
        RIGHT_KEY = controls.getInt("RIGHT", 68);
        ABILITY_KEY = controls.getInt("ABILITY", 32);
        INTERACT_KEY = controls.getInt("INTERACT", 16);
    }

    private static void loadSoundSettings() {
        sound = settings.getJSONObject("sound");
        MASTER_VOLUME = sound.getFloat("MASTER", 1);
        MUSIC_VOLUME = sound.getFloat("MUSIC", 1);
        SOUND_VOLUME = sound.getFloat("SOUND", 1);
    }

    public static void remapKey(int action, int code) {
        remapNextKey = false;
        if(action == up) {
            UP_KEY = code;
            controls.setInt("UP", code);
        } else if(action == down) {
            DOWN_KEY = code;
            controls.setInt("DOWN", code);
        } else if(action == left) {
            LEFT_KEY = code;
            controls.setInt("LEFT", code);
        } else if(action == right) {
            RIGHT_KEY = code;
            controls.setInt("RIGHT", code);
        } else if(action == ability) {
            ABILITY_KEY = code;
            controls.setInt("ABILITY", code);
        } else if(action == interact) {
            INTERACT_KEY = code;
            controls.setInt("INTERACT", code);
        }
        saveSettings();
    }

    public static void setMasterVolume(float vol) {
        MASTER_VOLUME = game.constrain(vol, 0, 1);
        sound.setFloat("MASTER", MASTER_VOLUME);
        SoundManager.setMasterVolume(MASTER_VOLUME);
        saveSettings();
    }

    public static void setMusicVolume(float vol) {
        MUSIC_VOLUME = game.constrain(vol, 0, 1);
        sound.setFloat("MUSIC", MUSIC_VOLUME);
        SoundManager.setMusicVolume(vol);
        saveSettings();
    }

    public static void setSoundVolume(float vol) {
        SOUND_VOLUME = game.constrain(vol, 0, 1);
        sound.setFloat("SOUND", SOUND_VOLUME);
        saveSettings();
    }

    public static void saveSettings() {
        settings.setJSONObject("controls", controls);
        settings.setJSONObject("sound", sound);
        game.saveJSONObject(settings, "assets/settings/settings.json");
    }
    
}
