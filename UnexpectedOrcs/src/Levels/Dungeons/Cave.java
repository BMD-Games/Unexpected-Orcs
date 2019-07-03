package Levels.Dungeons;

import Enemies.Bloods.MudBlood;
import Enemies.CreepyCrawlies.Basilisk;
import Enemies.CreepyCrawlies.Bat;
import Enemies.CreepyCrawlies.Crawler;
import Enemies.CreepyCrawlies.Spider;
import Enemies.Goblins.*;
import Enemies.StandardEnemy;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;
import Utility.Util;

import static Utility.Constants.*;

public class Cave extends Level {
    float chance = 0.4f; //chance the a cell will be a wall
    int iterations = 10;

    public Cave() {
        super(160, 90, "Cave", TileSet.caveTileset());

        Generator.generateCave(this, w, h, iterations, chance);
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinArcher(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinMage(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinSpearman(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new GoblinWarrior(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Spider(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Crawler(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; ++i) {
            enemy = new Bat(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new GoblinBoxer(game.random(w), game.random(h), 1);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);

        enemy = new Basilisk(game.random(w), game.random(h), 1);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);

        enemy = new MudBlood(game.random(w), game.random(h), 1);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
    }

}
