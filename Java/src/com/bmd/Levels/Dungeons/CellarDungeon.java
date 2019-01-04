package com.bmd.Levels.Dungeons;

import com.bmd.Enemies.Elementals.*;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Levels.Generator;
import com.bmd.Levels.Level;
import com.bmd.Levels.Room;
import com.bmd.Levels.RoomLevel;
import com.bmd.Tiles.TileSet;
import com.bmd.Util.Util;

class CellarDungeon extends Level implements RoomLevel {

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

    CellarDungeon() {
        super(60, 45, "Cellar", TileSet.cellarTileSet());
        Generator.generateConnectedDungeon(this, 20, (float)Math.PI/4f, 5, 10, Room.testSpawn(), Room.testBoss(), new Room[]{Room.testRoom()});
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 20; i ++) {
            enemy = new FireElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new IceElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new MagicElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 20; i ++) {
            enemy = new PoisonElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new FireElemental(Util.random(w), Util.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new IceElemental(Util.random(w), Util.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new MagicElemental(Util.random(w), Util.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 3; i ++) {
            enemy = new PoisonElemental(Util.random(w), Util.random(h), 1);
            validBossSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new KingElemental(Util.random(w), Util.random(h), 1);
        validBossSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
    }



}