package Levels.Dungeons;

import Enemies.Chomps.Chomp;
import Enemies.StandardEnemy;
import Entities.Drops.Chest;
import Entities.Drops.Portals.DesertPortal;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;
import Tiles.Tile;
import Tiles.Tiles;
import processing.core.PVector;

import static Utility.Constants.engine;
import static Utility.Constants.game;

public class TutorialDungeon  extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    public TutorialDungeon() {
        super(15, 50, "DungeonTutorial", TileSet.cellarTileSet());

        this.setTiles(Generator.finishingPass(new String[][] {
                { "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                { "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        }, TileSet.cellarTileSet()));

        start = new PVector(3,7);
        generateEnemies();

        engine.addDrop(new Chest(6, 7));
    }

    void generateEnemies() {
        engine.addDrop(new DesertPortal(9, 7));

        addEnemy(new Chomp(9, 7, 1));
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = game.random(w);
            enemy.y = game.random(h);
        }
    }
}