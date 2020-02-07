package Tiles;

import Sprites.Sprites;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.HashMap;

import static Utility.Constants.SPRITE_SIZE;
import static Utility.Constants.game;

public class Tiles {


    public static HashMap<String, Tile> Tiles;

    //----ZONES-----
    public final static int NO_SPAWN = 0;
    public final static int BOSS     = 1;
    public final static int GENERAL  = 2;

    //----Tile groups for outlining----
    public final static String testWallGroup = "testWallGroup";
    public final static String testFloorGroup = "testFloorGroup";

    public final static String woodGroup = "woodGroup";
    public final static String stoneGroup = "stoneGroup";
    public final static String grassGroup = "grassGroup";
    public final static String sandGroup = "sandGroup";
    public final static String bloodGroup = "bloodGroup";
    public final static String cobbleGroup = "cobbleGroup";
    public final static String stoneTileGroup = "stoneTileGroup";

    public final static String bloodSpongeGroup = "bloodSpongeGroup";
    public final static String sandstoneGroup = "sandstoneGroup";
    public final static String hedgeGroup = "hedgeGroup";
    public final static String rockGroup = "rockGroup";
    public final static String stoneBrickGroup = "stoneBrickGroup";


    //----Generic Tile VALUES----- Used for map generation
    public final static int WALL   = 0;
    public final static int FLOOR  = 1;


    public static void loadTileJSON(String path) {
        Tiles = new HashMap<String, Tile>();

        JSONObject data = game.loadJSONObject(path);
        if(data == null) return;

        JSONArray tiles = data.getJSONArray("tiles");
        JSONArray sprites = data.getJSONArray("sprites");

        PImage tilesheet = game.loadImage(data.getString("spritesheet"));

        int spriteScale = data.getInt("spritescale");

        loadTileData(tiles, spriteScale, tilesheet);
        loadSpriteData(sprites, spriteScale, tilesheet);
        loadBitMaskTextures(tilesheet);
    }

    private static void loadTileData(JSONArray tiles, int spriteScale, PImage tilesheet) {
        //Creates the tiles based on the data in the JSON file and loads the required sprites
        for(int i = 0; i < tiles.size(); i ++) {
            JSONObject tile = tiles.getJSONObject(i);

            String name = tile.getString("name");
            try {
                String group = tile.getString("group");

                int spriteX = tile.getInt("spriteX");
                int spriteY = tile.getInt("spriteY");

                int spriteW = tile.getInt("spriteW", 1);
                int spriteH = tile.getInt("spriteH", 1);

                float speedMod = tile.getFloat("speedMod", 1);

                boolean solid = tile.getBoolean("solid", true);

                if(solid) {
                    Tiles.put(name, new WallTile(name, speedMod, group));
                } else {
                    Tiles.put(name, new FloorTile(name, speedMod, group));
                }

                Sprites.tileSprites.put(name, Sprites.getSprite(tilesheet, spriteX, spriteY, spriteW, spriteH, spriteScale));

                try {
                    int bottomX = tile.getInt("bottomX");
                    int bottomY = tile.getInt("bottomY");
                    //load bottom texture
                    Sprites.tileSprites.put(name + "_BOTTOM", Sprites.getSprite(tilesheet, bottomX, bottomY, spriteW, spriteH, spriteScale));
                } catch(Exception e) {}
            } catch(Exception e) {
                System.out.println(String.format("Error loading tile data for tile: '%s'", name));
            }
        }
    }

    private static void loadSpriteData(JSONArray sprites, int spriteScale, PImage tilesheet) {
        //Loads the "extra" sprites needed (ie sprites for bitmasking)
        for(int i = 0; i < sprites.size(); i ++) {
            JSONObject sprite = sprites.getJSONObject(i);

            String name = sprite.getString("name");
            try {
                String group = sprite.getString("group");

                int spriteX = sprite.getInt("spriteX");
                int spriteY = sprite.getInt("spriteY");

                int spriteW = sprite.getInt("spriteW", 1);
                int spriteH = sprite.getInt("spriteH", 1);

                Sprites.tileSprites.put(name, Sprites.getSprite(tilesheet, spriteX, spriteY, spriteW, spriteH, spriteScale));

            } catch(Exception e) {
                System.out.println(String.format("Error loading tile data for sprite: '%s'", name));
            }
        }
    }

    private static void loadBitMaskTextures(PImage tilesheet) {
        //---Image Masks---
        Sprites.mask.put(255,Sprites.getSprite(tilesheet, 0, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(0,Sprites.getSprite(tilesheet, 1, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(2,Sprites.getSprite(tilesheet, 2, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(22,Sprites.getSprite(tilesheet, 3, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(214,Sprites.getSprite(tilesheet, 4, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(66,Sprites.getSprite(tilesheet, 5, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(18,Sprites.getSprite(tilesheet, 6, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(210,Sprites.getSprite(tilesheet, 7, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(86,Sprites.getSprite(tilesheet, 8, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(82,Sprites.getSprite(tilesheet, 9, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(91,Sprites.getSprite(tilesheet, 10, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(90,Sprites.getSprite(tilesheet, 11, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(219,Sprites.getSprite(tilesheet, 12, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(123,Sprites.getSprite(tilesheet, 13, 25, 1, 1, SPRITE_SIZE));
        Sprites.mask.put(251,Sprites.getSprite(tilesheet, 14, 25, 1, 1, SPRITE_SIZE));
    }

    public static Tile[][] stringToTileArray(String[][] stringTiles) {
        Tile[][] tiles = new Tile[stringTiles.length][stringTiles[0].length];

        for(int i = 0; i < stringTiles.length; i ++) {
            for(int j = 0; j < stringTiles[0].length; j ++) {
                tiles[i][j] = new Tile(stringTiles[i][j]);
            }
        }

        return tiles;
    }

}
