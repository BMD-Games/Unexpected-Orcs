package Levels.Dungeons;

import Enemies.CreepyCrawlies.Antlion;
import Enemies.CreepyCrawlies.Scorpion;
import Enemies.Plants.Cactus;
import Enemies.StandardEnemy;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;

import static Utility.Constants.*;

public class DesertDungeon extends Level {

    float chance = 0.4f; //chance the a cell will be a wall
    int iterations = 5;
    /***
     Creates a dungeon and appends the tiles to the Level it's been given.

     level     - The level being generated for
     maxRooms  - Number of rooms the dungeon will have
     spread    - Angle variation of the rooms from existing rooms
     minRadius - minimum distance between rooms
     maxRadius - maximum distance between rooms
     spawnRoom - Preset for the spawn room
     bossRoom  - Preset for the boss romm
     rooms     - Presets for all other rooms
     ***/

    public DesertDungeon() {
        super(160, 90, "Desert", TileSet.desertTileSet());
        Generator.generateCave(this, w, h, iterations, chance);
        generateStart();
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 30; ++i) {
            enemy = new Cactus(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Antlion(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Scorpion(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
    }
}