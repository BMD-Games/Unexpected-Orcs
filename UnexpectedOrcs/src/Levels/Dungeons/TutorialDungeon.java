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
import Levels.Level;
import Sprites.TileSet;
import Tiles.FloorTile;
import Tiles.Tile;
import processing.core.PVector;

import static Tiles.Tiles.FLOOR_TILE;
import static Utility.Constants.*;

public class TutorialDungeon  extends Level {

    private int roomAttempts = 300, minRoomSize = 3, maxRoomSize = 15;
    private float straightChance = 0.9f, loopChance = 0.05f;

    public TutorialDungeon() {
        super(15, 50, "DungeonTutorial", TileSet.cellarTileSet());

        this.setTiles(new Tile[][]{
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE},
                {FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE, FLOOR_TILE}
        });

        /*this.setTiles(new int[][]{
                {-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-29,-33,-33,-33,-33,-33,-29,-29,-29,-29,-29,-29,-29,-29,-29,-33,-33,-33,-33,-29,-27,-27,-27,-27,-27,-27,-27,-27,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-28,-29,-29,-29,-21,2,2,2,2,2,2,2,2,2,-28,-29,-29,-29,-21,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-30,-31,-31,-31,-23,2,2,2,2,2,2,2,2,2,-30,-31,-31,-31,-23,2,4,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-25,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,2,-32,-33,-33,-33,-25,2,2,2,2,2,2,2,2,-33},
                {-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,-31,
                        -31,-31,-31,-31,-31,-31,-31,-33,-33,-33,-33,-33,-33,-33,-33,-33,-33,-33,-33,-33,-27,-33,-33,-33,-33,-31,-27,-27,-27,-27,-27,-27,-27,-27,-33}
        });*/
        start = new PVector(3,45);
        generateEnemies();
    }

    void generateEnemies() {
        engine.addDrop(new CavePortal(12, 45));

        addEnemy(new Chomp(6, 45, 1));
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = game.random(w);
            enemy.y = game.random(h);
        }
    }
}