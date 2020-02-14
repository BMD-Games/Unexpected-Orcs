package Levels.Dungeons;

import Enemies.Elementals.*;
import Enemies.StandardEnemy;
import Levels.Generator;
import Levels.Level;
import Levels.Room;
import Sprites.TileSet;

import static Utility.Constants.*;

public class CellarDungeon  extends Level {

    /***
     Creates a dungeon and appends the tiles to the Level it's been given.

     level     - The level being generated for
     maxRooms  - Number of rooms the dungeon will have
     spread    - Angle variation of the rooms from existing rooms
     minRadius - minimum distance between rooms
     maxRadius - maximum distance between rooms
     spawnRoom - Preset for the spawn room
     bossRoom  - Preset for the boss room
     rooms     - Presets for all other rooms
     ***/

    public CellarDungeon() {
        super(60, 45, "Cellar", TileSet.cellarTileSet());
        Generator.generateConnectedDungeon(this, 20, game.PI/4, 5, 10, Room.testSpawn(), Room.testBoss(), new Room[]{Room.testRoom1(), Room.testRoom2()});
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 20; i ++) {
            enemy = new FireElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new IceElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new MagicElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new PoisonElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new FireElemental(game.random(w), game.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new IceElemental(game.random(w), game.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new MagicElemental(game.random(w), game.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new PoisonElemental(game.random(w), game.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new KingElemental(game.random(w), game.random(h), 1);
        validBossSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
    }



}