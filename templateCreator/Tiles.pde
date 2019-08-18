HashMap<String, PImage> tileSprites;
HashMap<String, Boolean> tileSolid;
    
public void loadTileJSON(String path) {

    tileSprites = new HashMap<String, PImage>();
    tileSolid = new HashMap<String, Boolean>();
    
    JSONObject data = loadJSONObject(path);
    if(data == null) return;

    JSONArray tiles = data.getJSONArray("tiles");

    PImage tilesheet = loadImage("../UnexpectedOrcs" + data.getString("spritesheet"));

    int spriteScale = data.getInt("spritescale");

    loadTileData(tiles, spriteScale, tilesheet);
    tileNames = tileSprites.keySet().toArray(new String[tileSprites.keySet().size()]);
    sortTileNames();
}

private void loadTileData(JSONArray tiles, int spriteScale, PImage tilesheet) {
    //Creates the tiles based on the data in the JSON file and loads the required sprites
    for(int i = 0; i < tiles.size(); i ++) {
        JSONObject tile = tiles.getJSONObject(i);

        String name = tile.getString("name");
        try {
            int spriteX = tile.getInt("spriteX");
            int spriteY = tile.getInt("spriteY");

            int spriteW = tile.getInt("spriteW", 1);
            int spriteH = tile.getInt("spriteH", 1);
            
            boolean solid = tile.getBoolean("solid", true);
            
            tileSprites.put(name, getSprite(tilesheet, spriteX, spriteY, spriteW, spriteH, spriteScale));
            tileSolid.put(name, solid);
            
        } catch(Exception e) {
            System.out.println(String.format("Error loading tile data for tile: '%s'", name));
        }
    }
}

void sortTileNames() {
  ArrayList<String> names = new ArrayList<String>();
  for(String name : tileNames) {
    if(tileSolid.get(name)) names.add(name);
  }
  for(String name : tileNames) {
    if(!tileSolid.get(name)) names.add(name);
  }
  tileNames = names.toArray(tileNames);
}
