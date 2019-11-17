package Levels.Dungeons;

import Enemies.Chomps.Chomp;
import Enemies.MoneyBag;
import Enemies.StandardEnemy;
import Entities.Drops.Chest;
import Entities.Drops.Portals.CavePortal;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;
import processing.core.PVector;

import static Utility.Constants.engine;
import static Utility.Constants.game;

public class TutorialDungeon  extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    public TutorialDungeon() {
        super(15, 50, "DungeonTutorial", TileSet.cellarTileSet());

        this.setTiles(Generator.finishingPass(new String[][] {
                {"STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND_CACTUS","SAND","SAND","SAND_TILE","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","SAND_TILE","SAND_TILE","GRASS","GRASS","SAND_TILE","GRASS","MUSHROOM_GRASS","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND","SAND","SAND_ROCK","SAND_TILE","STONE_BRICK","BROKEN_WOOD","WOOD","WOOD","STONE_BRICK","SAND_TILE","SAND_TILE","GRASS","GRASS_TUFT","SAND_TILE","GRASS","GRASS","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND","SAND","SAND","SAND_TILE","STONE_BRICK","WOOD","LONG_WOOD","WOOD","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND","SAND","SAND_CACTUS","SAND_TILE","STONE_BRICK","WOOD","BROKEN_WOOD","WOOD","STONE_BRICK","SAND_TILE","SAND_TILE","GRASS_TUFT","GRASS","SAND_TILE","GRASS","GRASS","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND_ROCK","SAND","SAND","SAND_TILE","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","SAND_TILE","SAND_TILE","GRASS_LEAF","GRASS","SAND_TILE","GRASS","GRASS_TUFT","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","SAND_TILE","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","HEDGE","STONE_BRICK"},
                {"STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK","STONE_BRICK"}
        }, TileSet.cellarTileSet()));

        start = new PVector(7.5f,15.5f);
        generateEnemies();

        engine.addDrop(new Chest(7.5f, 10.5f));
    }

    void generateEnemies() {
        engine.addDrop(new CavePortal(7.5f, 8.5f));

        addEnemy(new Chomp(7.5f, 9.5f, 1));


        addEnemy(new MoneyBag(5.5f, 12.5f));
        addEnemy(new MoneyBag(6.5f, 12.5f));
        addEnemy(new MoneyBag(7.5f, 12.5f));
        addEnemy(new MoneyBag(8.5f, 12.5f));
        addEnemy(new MoneyBag(9.5f, 12.5f));
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = game.random(w);
            enemy.y = game.random(h);
        }
    }
}