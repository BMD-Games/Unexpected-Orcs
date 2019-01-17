package Settings;

import processing.data.JSONObject;

import static Utility.Constants.*;

public class Settings {
    
    public static int UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY, ABILITY_KEY;
    public static JSONObject settings, controls;

    public static boolean remapNextKey = false;
    public static boolean characterNaming = false;
    public static int remapAction = -1;

    public static void loadSettings() {
        settings = game.loadJSONObject("/assets/settings/settings.json");
        loadControls();
    }

    public static String getKeyFromCode(int code) {

        if(code == game.UP) code = 8593;
        if(code == game.DOWN) code = 8595;
        if(code == game.RIGHT) code = 8594;
        if(code == game.LEFT) code = 8592;
        if(code == 32) code = '_'; //Space key

        String keyChar = Character.toString((char)code);

        return keyChar;
    }

    public static String[] getKeys() {
        String[] keys = new String[5];
        keys[up] = getKeyFromCode(UP_KEY);
        keys[down] = getKeyFromCode(DOWN_KEY);
        keys[left] = getKeyFromCode(LEFT_KEY);
        keys[right] = getKeyFromCode(RIGHT_KEY);
        keys[ability] = getKeyFromCode(ABILITY_KEY);
        return keys;
    }

    public static String getKeyString(int action) {
        //lmao so lazy
        return getKeys()[action];
    }

    public static void loadControls() {
        controls = settings.getJSONObject("controls");
        UP_KEY = controls.getInt("UP");
        DOWN_KEY = controls.getInt("DOWN");
        LEFT_KEY = controls.getInt("LEFT");
        RIGHT_KEY = controls.getInt("RIGHT");
        ABILITY_KEY = controls.getInt("ABILITY");
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
        }
        saveSettings();
    }

    public static void saveSettings() {
        settings.setJSONObject("controls", controls);
        game.saveJSONObject(settings, "assets/settings/settings.json");
    }
    
}
