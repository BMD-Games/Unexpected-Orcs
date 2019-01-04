package com.bmd.Levels.Dungeons;

import com.bmd.Enemies.CreepyCrawlies.Basilisk;
import com.bmd.Enemies.CreepyCrawlies.Bat;
import com.bmd.Enemies.CreepyCrawlies.Crawler;
import com.bmd.Enemies.CreepyCrawlies.Spider;
import com.bmd.Enemies.Goblins.*;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Levels.Generator;
import com.bmd.Levels.Level;
import com.bmd.Tiles.TileSet;
import com.bmd.Util.Util;

class Cave extends Level {
    float chance = 0.4f; //chance the a cell will be a wall
    int iterations = 5;

    Cave() {
        super(120, 90, "Cave", TileSet.caveTileset());

        this.setTiles(Generator.finishingPass(Generator.generateCave(w, h, iterations, chance), tileset));
        generateStart();
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinArcher(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinMage(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinSpearman(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinWarrior(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Spider(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Crawler(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Bat(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new GoblinBoxer(Util.random(w), Util.random(h), 1);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);

        enemy = new Basilisk(Util.random(w), Util.random(h), 1);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
    }

    public void validSpawn(StandardEnemy enemy) {
        while(!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = Util.random(w);
            enemy.y = Util.random(h);
        }
    }
}