package Levels.Dungeons;

import Enemies.Chomps.BigChomp;
import Enemies.Chomps.BossChomp;
import Enemies.Chomps.Chomp;
import Enemies.CreepyCrawlies.Slugite;
import Enemies.Elementals.FireElemental;
import Enemies.Elementals.IceElemental;
import Enemies.Elementals.MagicElemental;
import Enemies.Elementals.PoisonElemental;
import Enemies.Plants.Daisy;
import Enemies.Plants.Rose;
import Enemies.StandardEnemy;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;

import static Utility.Constants.*;

public class GrassDungeon  extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    public GrassDungeon() {
        super(60, 45, "DungeonGrass", TileSet.grassTileset());

        Generator.generateWindyDungeon(this, w, h, roomAttempts, minRoomSize, maxRoomSize, straightChance, loopChance);
        //this.setTiles(generateStraightDungeon(w, h, roomAttempts, minRoomSize, maxRoomSize));
        generateEnemies();
    }

    void generateEnemies() {
        //Add enemies to level
        StandardEnemy enemy;
        for(int i = 0; i < 12; i ++) {
            enemy = new FireElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 30; i ++) {
            enemy = new Slugite(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new IceElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new MagicElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for(int i = 0; i < 12; i ++) {
            enemy = new PoisonElemental(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 40; i ++) {
            enemy = new Chomp(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 10; ++i) {
            enemy = new BigChomp(game.random(w), game.random(h), 2);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        enemy = new BossChomp(game.random(w), game.random(h), 3);
        validSpawn(enemy);
        addEnemy(enemy);
        bosses.add(enemy);
        for (int i = 0; i < 30; i ++) {
            enemy = new Rose(game.random(w), game.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
        for (int i = 0; i < 30; ++i) {
            enemy = new Daisy(game.random(w), game.random(h), 2);
            validSpawn(enemy);
            addEnemy(enemy);
        }
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = game.random(w);
            enemy.y = game.random(h);
        }
    }
}