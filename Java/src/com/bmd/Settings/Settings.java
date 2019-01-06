package com.bmd.Settings;

import com.bmd.App.Main;
import com.bmd.Input.Input;
import com.bmd.Util.Util;
import javafx.scene.input.KeyCode;

import java.io.*;

public class Settings {
    //public static JSONObject settings, controls;

    public static boolean remapNextKey = false;
    public static boolean characterNaming = false;
    public static int remapAction = -1;

    public static KeyMap controls = new KeyMap();

    public static void loadSettings() {
        KeyMap controlsTemp = new KeyMap();
        try {
            FileInputStream controlSaveFile = new FileInputStream("./out/settings/controls.txt");
            ObjectInputStream controlStream = new ObjectInputStream(controlSaveFile);
            controlsTemp = (KeyMap)controlStream.readObject();
            controlStream.close();
            controlSaveFile.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        controls = controlsTemp;
    }

    public static void saveSettings() {
        try {
            FileOutputStream controlSaveFile = new FileOutputStream("./out/settings/controls.txt");
            ObjectOutputStream controlStream = new ObjectOutputStream(controlSaveFile);
            controlStream.writeObject(controls);
            controlStream.close();
            controlSaveFile.close();
        }
        catch(Exception e) {
            if(e instanceof FileNotFoundException) {
                File file = new File("./out/settings/controls.txt");
                file.getParentFile().mkdirs();
            }
            System.out.println(e);
        }
    }

    public static String getKeyFromCode(int code) {

        if(code == Input.UP) code = 8593;
        if(code == Input.DOWN) code = 8595;
        if(code == Input.RIGHT) code = 8594;
        if(code == Input.LEFT) code = 8592;
        if(code == Input.SPACE) code = '_';

        String keyChar = Character.toString((char)code);

        return keyChar;
    }

    public static String[] getKeys() {
        String[] keys = new String[5];
        keys[Util.up] = controls.UP_KEY.getName();
        keys[Util.down] = controls.DOWN_KEY.getName();
        keys[Util.left] = controls.LEFT_KEY.getName();
        keys[Util.right] = controls.RIGHT_KEY.getName();
        keys[Util.ability] = controls.ABILITY_KEY.getName();
        return keys;
    }

    public static String getKeyString(int action) {
        //lmao so lazy
        return getKeys()[action];
    }

    public static void remapKey(int action, KeyCode code) {
        remapNextKey = false;
        if(action == Util.up) {
            controls.UP_KEY = code;;
        } else if(action == Util.down) {
            controls.DOWN_KEY = code;
        } else if(action == Util.left) {
            controls.LEFT_KEY = code;
        } else if(action == Util.right) {
            controls.RIGHT_KEY = code;
        } else if(action == Util.ability) {
            controls.ABILITY_KEY = code;
        }
        saveSettings();
    }
}
