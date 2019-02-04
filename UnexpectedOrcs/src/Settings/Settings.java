package Settings;

import GUI.Scroll.KeyRemapElement;
import GUI.Scroll.ScrollElement;
import processing.data.JSONObject;

import static Utility.Constants.*;

public class Settings {
    
    public static int UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY, ABILITY_KEY, INTERACT_KEY;
    private static JSONObject settings, controls;

    public static boolean remapNextKey = false;
    public static boolean characterNaming = false;
    public static int remapAction = -1;

    public static void loadSettings() {
        settings = game.loadJSONObject("/assets/settings/settings.json");
        loadControls();
    }

    public static ScrollElement[] getElements() {
        ScrollElement[] elements = new ScrollElement[6];
        elements[up] = new KeyRemapElement("UP", up);
        elements[down] = new KeyRemapElement("DOWN", down);
        elements[left] = new KeyRemapElement("LEFT", left);
        elements[right] = new KeyRemapElement("RIGHT", right);
        elements[ability] = new KeyRemapElement("ABILITY", ability);
        elements[interact] = new KeyRemapElement("INTERACT", interact);
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
        UP_KEY = controls.getInt("UP");
        DOWN_KEY = controls.getInt("DOWN");
        LEFT_KEY = controls.getInt("LEFT");
        RIGHT_KEY = controls.getInt("RIGHT");
        ABILITY_KEY = controls.getInt("ABILITY");
        INTERACT_KEY = controls.getInt("INTERACT");
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

    private static void saveSettings() {
        settings.setJSONObject("controls", controls);
        game.saveJSONObject(settings, "assets/settings/settings.json");
    }
    
}
