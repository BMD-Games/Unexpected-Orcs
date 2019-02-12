package Settings;

import GUI.Scroll.*;
import Sound.SoundManager;
import Utility.Util;
import javafx.util.Pair;
import processing.data.JSONObject;

import static Utility.Constants.*;

public class Settings {
    
    public static int UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY, ABILITY_KEY, INTERACT_KEY, HOT_SWAP_0, HOT_SWAP_1, HOT_SWAP_2, HOT_SWAP_3;
    public static float MASTER_VOLUME, MUSIC_VOLUME, SOUND_VOLUME;
    public static int RESOLUTION_INDEX;
    public static boolean BLOOD;

    private static int hot0 = 6, hot1 = 7, hot2 = 8, hot3 = 9;

    private static JSONObject settings, controls, sound, video, graphics;


    public static boolean remapNextKey = false;
    public static boolean characterNaming = false;
    public static int remapAction = -1;

    public static void loadSettings() {
        settings = game.loadJSONObject("/assets/settings/settings.json");
        loadControls();
        loadSoundSettings();
        loadVideoSettings();
        loadGraphicsSettings();
    }

    public static Pair[] getElements() {
        Pair<String, ScrollElement[]>[] elements = new Pair[4];


        ScrollElement[] controls = new ScrollElement[10];
        controls[up] = new KeyRemapElement("UP", up);
        controls[down] = new KeyRemapElement("DOWN", down);
        controls[left] = new KeyRemapElement("LEFT", left);
        controls[right] = new KeyRemapElement("RIGHT", right);
        controls[ability] = new KeyRemapElement("ABILITY", ability);
        controls[interact] = new KeyRemapElement("INTERACT", interact);

        controls[hot0] = new KeyRemapElement("HOT SWAP (SLOT1)", hot0);
        controls[hot1] = new KeyRemapElement("HOT SWAP (SLOT2)", hot1);
        controls[hot2] = new KeyRemapElement("HOT SWAP (SLOT3)", hot2);
        controls[hot3] = new KeyRemapElement("HOT SWAP (SLOT4)", hot3);

        elements[0] = new Pair<>("Controls", controls);


        ScrollElement[] sound = new ScrollElement[3];
        sound[0] = new SliderElement("MASTER VOLUME", sliderValue -> { Settings.setMasterVolume(sliderValue); }, MASTER_VOLUME);
        sound[1] = new SliderElement("MUSIC VOLUME", sliderValue -> { Settings.setMusicVolume(sliderValue); }, MUSIC_VOLUME);
        sound[2] = new SliderElement("SOUND VOLUME", sliderValue -> { Settings.setSoundVolume(sliderValue); }, SOUND_VOLUME);

        elements[1] = new Pair<>("Sound", sound);


        ScrollElement[] video = new ScrollElement[1];
        Integer[] params = new Integer[resolutions.length];
        for(int i = 0; i < params.length; i ++) params[i] = i;
        video[0] = new SelectElement("RESOLUTION", resolutionNames, params, obj -> { Settings.changeResolution((int)obj); }, RESOLUTION_INDEX);
        //video[1] = new ToggleElement("FULLSCREEN", value -> { Settings.toggleFullscreen(); } );

        elements[2] = new Pair<>("Video", video);


        ScrollElement[] graphics = new ScrollElement[1];
        graphics[0] = new ToggleElement("BLOOD FX", BLOOD, value -> { toggleBlood(); });

        elements[3] = new Pair<>("Graphics", graphics);


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
        String[] keys = new String[10];
        keys[up] = getKeyFromCode(UP_KEY);
        keys[down] = getKeyFromCode(DOWN_KEY);
        keys[left] = getKeyFromCode(LEFT_KEY);
        keys[right] = getKeyFromCode(RIGHT_KEY);
        keys[ability] = getKeyFromCode(ABILITY_KEY);
        keys[interact] = getKeyFromCode(INTERACT_KEY);
        keys[hot0] = getKeyFromCode(HOT_SWAP_0);
        keys[hot1] = getKeyFromCode(HOT_SWAP_1);
        keys[hot2] = getKeyFromCode(HOT_SWAP_2);
        keys[hot3] = getKeyFromCode(HOT_SWAP_3);
        return keys;
    }

    public static String getKeyString(int action) {
        //lmao so lazy
        return getKeys()[action];
    }

    private static void loadControls() {
        controls = settings.getJSONObject("controls");
        if(controls == null) video = new JSONObject();

        UP_KEY = controls.getInt("UP", 87);
        DOWN_KEY = controls.getInt("DOWN", 83);
        LEFT_KEY = controls.getInt("LEFT", 65);
        RIGHT_KEY = controls.getInt("RIGHT", 68);
        ABILITY_KEY = controls.getInt("ABILITY", 32);
        INTERACT_KEY = controls.getInt("INTERACT", 16);

        HOT_SWAP_0 = controls.getInt("HOT_SWAP_0", 49);
        HOT_SWAP_1 = controls.getInt("HOT_SWAP_1", 50);
        HOT_SWAP_2 = controls.getInt("HOT_SWAP_2", 51);
        HOT_SWAP_3 = controls.getInt("HOT_SWAP_3", 52);
    }

    private static void loadSoundSettings() {
        sound = settings.getJSONObject("sound");
        if(sound == null) video = new JSONObject();

        MASTER_VOLUME = sound.getFloat("MASTER", 1);
        MUSIC_VOLUME = sound.getFloat("MUSIC", 1);
        SOUND_VOLUME = sound.getFloat("SOUND", 1);
    }

    private static void loadVideoSettings() {
        video = settings.getJSONObject("video");
        if(video == null) video = new JSONObject();

        RESOLUTION_INDEX = video.getInt("RESOLUTION_INDEX", 0);

        if(game.width != resolutions[RESOLUTION_INDEX].x || game.height != resolutions[RESOLUTION_INDEX].y) changeResolutionInitial(RESOLUTION_INDEX);
    }

    private static void loadGraphicsSettings() {
        graphics = settings.getJSONObject("graphics");
        if(graphics == null) graphics = new JSONObject();
        BLOOD = graphics.getBoolean("BLOOD", false);
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
        } else if(action == hot0) {
            HOT_SWAP_0 = code;
            controls.setInt("HOT_SWAP_0", code);
        } else if(action == hot1) {
            HOT_SWAP_1 = code;
            controls.setInt("HOT_SWAP_1", code);
        } else if(action == hot2) {
            HOT_SWAP_2 = code;
            controls.setInt("HOT_SWAP_2", code);
        } else if(action == hot3) {
            HOT_SWAP_3 = code;
            controls.setInt("HOT_SWAP_3", code);
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

    public static void toggleBlood() {
        BLOOD = !BLOOD;
        graphics.setBoolean("BLOOD", BLOOD);
        saveSettings();
    }

    public static void changeResolution(int index) {
        RESOLUTION_INDEX = index;
        video.setInt("RESOLUTION_INDEX", RESOLUTION_INDEX);
        saveSettings();
        Util.resize(RESOLUTION_INDEX);
    }

    public static void changeResolutionInitial(int index) {
        Util.resizeInitial(index);
    }

    public static void saveSettings() {
        settings.setJSONObject("controls", controls);
        settings.setJSONObject("sound", sound);
        settings.setJSONObject("video", video);
        settings.setJSONObject("graphics", graphics);
        game.saveJSONObject(settings, "assets/settings/settings.json");
    }
    
}
