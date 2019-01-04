package com.bmd.Levels.Dungeons;

import com.bmd.Enemies.Chomps.BigChomp;
import com.bmd.Enemies.Chomps.BossChomp;
import com.bmd.Enemies.Chomps.Chomp;
import com.bmd.Enemies.Elementals.FireElemental;
import com.bmd.Enemies.Elementals.IceElemental;
import com.bmd.Enemies.Elementals.MagicElemental;
import com.bmd.Enemies.Elementals.PoisonElemental;
import com.bmd.Enemies.Plants.Daisy;
import com.bmd.Enemies.Plants.Rose;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Levels.Generator;
import com.bmd.Levels.Level;
import com.bmd.Tiles.TileSet;
import com.bmd.Util.Util;

class GrassDungeon extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    GrassDungeon() {
        super(60, 45, "DungeonGrass", TileSet.grassTileset());

        this.setTiles(Generator.finishingPass(Generator.generateWindyDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance), tileset));
        //this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
        generateStart();
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 12; i ++) {
            enemy = new FireElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new IceElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new MagicElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new PoisonElemental(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 40; i ++) {
            enemy = new Chomp(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 10; ++i) {
            enemy = new BigChomp(Util.random(w), Util.random(h), 2);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new BossChomp(Util.random(w), Util.random(h), 3);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
        for (int i = 0; i < 30; i ++) {
            enemy = new Rose(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 30; ++i) {
            enemy = new Daisy(Util.random(w), Util.random(h), 2);
            validSpawn(enemy);
            addEnemy(enemy);
        }
    }

    public void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = Util.random(w);
            enemy.y = Util.random(h);
        }
    }
}