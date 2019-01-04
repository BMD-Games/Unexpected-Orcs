package com.bmd.Levels.Dungeons;

import com.bmd.Enemies.CreepyCrawlies.Antlion;
import com.bmd.Enemies.CreepyCrawlies.Scorpion;
import com.bmd.Enemies.Plants.Cactus;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Levels.Generator;
import com.bmd.Levels.Level;
import com.bmd.Tiles.TileSet;
import com.bmd.Util.Util;

class DesertDungeon extends Level {

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

    DesertDungeon() {
        super(120, 90, "Desert", TileSet.desertTileSet());
        this.setTiles(Generator.finishingPass(Generator.generateCave(w, h, 5, 0.4f), tileset));
        generateStart();
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 30; ++i) {
            enemy = new Cactus(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Antlion(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Scorpion(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
    }
}