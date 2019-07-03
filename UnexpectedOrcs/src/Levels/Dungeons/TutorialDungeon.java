package Levels.Dungeons;

import Enemies.Chomps.BigChomp;
import Enemies.Chomps.BossChomp;
import Enemies.Chomps.Chomp;
import Enemies.Elementals.FireElemental;
import Enemies.Elementals.IceElemental;
import Enemies.Elementals.MagicElemental;
import Enemies.Elementals.PoisonElemental;
import Enemies.Plants.Daisy;
import Enemies.Plants.Rose;
import Enemies.StandardEnemy;
import Entities.Drops.Portals.BloodPortal;
import Entities.Drops.Portals.CavePortal;
import Levels.Generator;
import Levels.Level;
import Sprites.TileSet;
import Tiles.FloorTile;
import Tiles.Tile;
import processing.core.PVector;

import static Tiles.Tiles.STONE_BRICK;
import static Tiles.Tiles.WOOD;
import static Utility.Constants.*;

public class TutorialDungeon  extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    public TutorialDungeon() {
        super(15, 50, "DungeonTutorial", TileSet.cellarTileSet());

        this.setTiles(Generator.finishingPass(new Tile[][]{
                {STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, WOOD, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK},
                {STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK, STONE_BRICK}
        }, TileSet.cellarTileSet()));

        start = new PVector(3,7);
        generateEnemies();
    }

    void generateEnemies() {
        engine.addDrop(new CavePortal(9, 7));

        addEnemy(new Chomp(9, 7, 1));
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = game.random(w);
            enemy.y = game.random(h);
        }
    }
}