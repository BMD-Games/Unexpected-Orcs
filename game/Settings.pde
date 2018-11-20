public int UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY, ABILITY_KEY;
public JSONObject settings, controls;


public final int up = 0, down = 1, left = 2, right = 3, ability = 4;

public void loadSettings() {
  settings = loadJSONObject("/assets/settings/settings.json");
  loadControls();
}

public String getKeyFromCode(int code) {
  
  if(code == UP) code = 8593;
  if(code == DOWN) code = 8595;
  if(code == RIGHT) code = 8594;
  if(code == LEFT) code = 8592;
  
  String keyChar = Character.toString((char)code);
  if(keyChar.equals(" ")) keyChar = "SPACE";
  
  return keyChar;
}

public String[] getKeys() {
  String[] keys = new String[5];
  keys[up] = getKeyFromCode(UP_KEY);
  keys[down] = getKeyFromCode(DOWN_KEY);
  keys[left] = getKeyFromCode(LEFT_KEY);
  keys[right] = getKeyFromCode(RIGHT_KEY);
  keys[ability] = getKeyFromCode(ABILITY_KEY);
  return keys;
}

public void loadControls() {
  controls = settings.getJSONObject("controls");
  UP_KEY = controls.getInt("UP");
  DOWN_KEY = controls.getInt("DOWN");
  LEFT_KEY = controls.getInt("LEFT");
  RIGHT_KEY = controls.getInt("RIGHT");
  ABILITY_KEY = controls.getInt("ABILITY");
  
  printArray(getKeys());
}

public void remapKey(int action, int code) {
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
    RIGHT_KEY = code;
    controls.setInt("RIGHT", code);
  }
  saveSettings();
}

public void saveSettings() {
  settings.setJSONObject("controls", controls);
  saveJSONObject(settings, "assets/settings/settings.json");
}