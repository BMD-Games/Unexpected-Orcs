package com.bmd.Levels;

import com.bmd.App.Graphics;
import com.bmd.App.Main;
import com.bmd.Enemies.CreepyCrawlies.Bat;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Sprites.Sprites;
import com.bmd.Tiles.TileSet;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.PVector;
import com.bmd.Util.PVectorZComparator;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Level {

    protected int[][] tiles;
    protected ArrayList<PVector> bossZones;
    protected ArrayList<PVector> generalZones;

    protected boolean[][] visited;
    protected boolean[][] visitedCalcLocations;
    protected int visitRadius = 8;

    private final int CHUNK_SIZE = 7;
    private int CHUNK_W, CHUNK_H;
    public ArrayList<Enemy>[] enemies;

    public int w, h;
    public PVector start;
    protected String name;
    public TileSet tileset  = new TileSet();
    protected int xTileOffset, yTileOffset, renderW, renderH, buffer = 2, tileBuffer = Main.width/ Tiles.TILE_SIZE/2;

    public ArrayList<Enemy> bosses = new ArrayList<Enemy>();

    private HashMap<PVector, Boolean> smoothBeenVisited = new HashMap<PVector, Boolean>();
    private PriorityQueue<PVector> smoothQueue = new PriorityQueue<PVector>();

    private Canvas background, miniMap, miniMapOverlay;

    public Level(int w, int h, String name, TileSet tileset) {
        this.w = w;
        this.h = h;

        this.name = name;
        this.tileset = tileset;

        initialiseChunks();

        visited = new boolean[w][h];
        visitedCalcLocations = new boolean[w][h];

        renderW = Main.width/Tiles.TILE_SIZE + 2 * buffer;
        renderH = Main.height/Tiles.TILE_SIZE + 2 * buffer;

        background = new Canvas(Main.width - Util.GUI_WIDTH, Main.height);
        miniMapOverlay = new Canvas(w, h);
        miniMap = new Canvas(w, h);
    }

    public Canvas generateImage() {
        Canvas canvas = new Canvas(Sprites.SPRITE_SIZE * w, Sprites.SPRITE_SIZE * h);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Graphics.background(canvas, Util.gray(0, 0));
        for (int i = 0; i < w; i ++) {
            for (int j = 0; j < h; j ++) {
                int tile = tileset.walls[15];
                try {
                    tile = tiles[i][j];
                }
                catch(Exception e) {
                }
                Graphics.image(gc, Sprites.tileSprites.get(tile), i * Sprites.SPRITE_SIZE, j * Sprites.SPRITE_SIZE, Sprites.SPRITE_SIZE, Sprites.SPRITE_SIZE);
            }
        }
        return canvas;
    }

    public void update(Canvas canvas, float x, float y) {
        GraphicsContext screen = canvas.getGraphicsContext2D();
        xTileOffset = (int)(x - (canvas.getWidth()/2)/Tiles.TILE_SIZE);
        yTileOffset = (int)(y - (canvas.getHeight()/2)/Tiles.TILE_SIZE);
        updateVisited((int)x, (int)y, visitRadius, false);
        updateVisitedSmooth();
        updateMapEntities((int)x, (int)y);
        updateBosses();
    }

    public void show(Canvas canvas, PVector renderOffset) {
        //generate an image based off the tile map;

        GraphicsContext screen = canvas.getGraphicsContext2D();
        GraphicsContext bg = background.getGraphicsContext2D();

        Graphics.background(background, Color.BLACK);
        for (int x = 0; x < renderW; x ++) {
            for (int y = 0; y < renderH; y ++) {
                int i = (x + xTileOffset) - buffer;
                int j = (y + yTileOffset) - buffer;
                int tile = tileset.walls[15];
                boolean visit = false;
                try {
                    visit = visited[i][j];
                }
                catch(Exception e) {
                }
                if (visit) {
                    try {
                        tile = tiles[i][j];
                    }
                    catch(Exception e) {
                    }
                    BufferedImage sprite = Sprites.tileSprites.get(tile);
                    Graphics.image(bg, sprite, i * Tiles.TILE_SIZE - renderOffset.x, j * Tiles.TILE_SIZE - renderOffset.y, (sprite.getWidth() * Util.SCALE), (sprite.getHeight() * Util.SCALE));
                }
            }
        }
        Graphics.image(screen, Util.getImage(background), 0, 0);
    }

    public boolean isEdge(int[][] tiles, int i, int j) {
        return (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[0].length);
    }

    public int[] getNeighbours(int i, int j) {
        int[] n = new int[8];
        try {
            n[Util.up] = tiles[i][j-1];
        }
        catch(Exception e) {
        } //up
        try {
            n[Util.down] = tiles[i][j+1];
        }
        catch(Exception e) {
        } //down
        try {
            n[Util.left] = tiles[i-1][j];
        }
        catch(Exception e) {
        } //left
        try {
            n[Util.right] = tiles[i+1][j];
        }
        catch(Exception e) {
        } //right
        try {
            n[Util.topLeft] = tiles[i-1][j-1];
        }
        catch(Exception e) {
        } // up left
        try {
            n[Util.topRight] = tiles[i+1][j-1];
        }
        catch(Exception e) {
        } // up right
        try {
            n[Util.bottomLeft] = tiles[i-1][j+1];
        }
        catch(Exception e) {
        } // down left
        try {
            n[Util.bottomRight] = tiles[i+1][j+1];
        }
        catch(Exception e) {
        } // down right

        return n;
    }

    public void generateStart() {
        while (start == null) {
            int i = (int)Math.floor(Util.random(Generator.edgeSize, w - Generator.edgeSize));
            int j = (int)Math.floor(Util.random(Generator.edgeSize, h - Generator.edgeSize));
            if (tiles[i][j] > Tiles.WALL) {
                tiles[i][j] = tileset.spawn;
                start = new PVector(i, j);
            }
        }
    }

    protected void updateBosses() {
        for(int i = bosses.size() - 1; i >= 0; i --) {
            if(((StandardEnemy)bosses.get(i)).stats.health < 0) {
                bosses.remove(i);
            }
        }
    }

    protected void updateMapEntities(int playerX, int playerY) {
//        miniMapOverlay.beginDraw();
//        miniMapOverlay.background(0, 0);
//        miniMapOverlay.stroke(0, 0, 255);
//        miniMapOverlay.point(playerX, playerY);
//        //can add monsters here too;
//        miniMapOverlay.stroke(255, 0, 0);
//        for(Enemy boss : bosses) {
//            miniMapOverlay.point(((StandardEnemy)boss).x, ((StandardEnemy)boss).y);
//        }
//
//    /*for(int i : getChunks((int)engine.player.x, (int)engine.player.y)) {
//      for(Enemy enemy : enemies[i]) {
//        miniMapOverlay.point(((StandardEnemy)enemy).x, ((StandardEnemy)enemy).y);
//      }
//    }*/
//
//        miniMapOverlay.endDraw();
    }

    public void newSmoothUncover(int i, int j, int radius) {
        smoothQueue = new PriorityQueue<PVector>(new PVectorZComparator());
        smoothBeenVisited = new HashMap<PVector, Boolean>();
        smoothQueue.add(new PVector(i, j, radius));
    }

    protected void updateVisitedSmooth() {
        if(smoothQueue.size() <= 0) return;
        int level = (int)smoothQueue.peek().z;
        while(smoothQueue.size() > 0 && (int)smoothQueue.peek().z == level) {
            PVector tile = smoothQueue.remove();
            visitTileSmooth((int)tile.x, (int)tile.y, (int)tile.z);
        }
    }

    protected void visitTileSmooth(int i, int j, int level) {
        if (!smoothBeenVisited.getOrDefault(new PVector(i, j), false)) {
            visitTileFull(i, j);
            smoothBeenVisited.put(new PVector(i, j), true);

            if (level != 0) {
                int[] nb = getNeighbours(i, j);
                if (nb[Util.up] > Tiles.WALL) smoothQueue.add(new PVector(i, j - 1, level - 1));
                if (nb[Util.down] > Tiles.WALL) smoothQueue.add(new PVector(i, j + 1, level - 1));
                if (nb[Util.left] > Tiles.WALL) smoothQueue.add(new PVector(i - 1, j, level - 1));
                if (nb[Util.right] > Tiles.WALL) smoothQueue.add(new PVector(i + 1, j, level - 1));
            }
        }
        smoothQueue.remove(0);
    }

    protected void updateVisited(int x0, int y0, int radius, boolean force) {

        boolean calcLocation = true; //default to true so if out-of-bounds we will still skip the calcs
        try {
            calcLocation = visitedCalcLocations[x0][y0];
        }
        catch(Exception e) {
        };
        if (!calcLocation || force) {
            HashMap<PVector, Boolean> beenVisited = new HashMap<PVector, Boolean>();
            ArrayList<PVector> queue = new ArrayList<PVector>();
            queue.add(new PVector(x0, y0, radius));
            visitTile(x0, y0, radius, beenVisited, queue); //Flood fill
            visitedCalcLocations[x0][y0] = true;
        }
    }

    ///*FLOOD FILL
    protected void visitTile(int i, int j, int level, HashMap<PVector, Boolean> beenVisited, ArrayList<PVector> queue) {

        if (!beenVisited.getOrDefault(new PVector(i, j), false)) {
            visitTileFull(i, j);
            beenVisited.put(new PVector(i, j), true);

            if (level != 0) {
                int[] nb = getNeighbours(i, j);
                if (nb[Util.up] > Tiles.WALL) queue.add(new PVector(i, j - 1, level - 1));
                if (nb[Util.down] > Tiles.WALL) queue.add(new PVector(i, j + 1, level - 1));
                if (nb[Util.left] > Tiles.WALL) queue.add(new PVector(i - 1, j, level - 1));
                if (nb[Util.right] > Tiles.WALL) queue.add(new PVector(i + 1, j, level - 1));
            }
        }
        queue.remove(0);
        if (queue.size() > 0) {
            visitTile((int)queue.get(0).x, (int)queue.get(0).y, (int)queue.get(0).z, beenVisited, queue);
        }
    }//*/

    protected void visitTileFull(int x, int y) {
        for (int i = -1; i <= 1; i ++) {
            for (int j = -1; j <= 1; j ++) {
                visitTile(x + i, y + j);
            }
        }
    }

    protected void visitTile(int i, int j) {
        try {
            if (!visited[i][j]) drawVisitedTile(i, j);
            visited[i][j] = true;
        }
        catch (Exception e) {
        }
    }

    protected void drawVisitedTile(int i, int j) {
        GraphicsContext gc = miniMap.getGraphicsContext2D();

        int tile = Tiles.WALL;
        try {
            tile = tiles[i][j];
        }
        catch(Exception e) {
        }
        gc.setStroke(Util.getFXColor(Sprites.tileSprites.get(tile).getRGB(1, 1))); //set the colour to a pixel from the tile

        gc.fillRect(i, j, 1, 1);
    }

    public boolean canSee(int x1, int y1, int x2, int y2) {
        int dist = (int)Math.max(Util.fastAbs(x2 - x1), Util.fastAbs(y2 - y1));
        for (int i = 0; i < dist; i ++) {
            int tX = (int)Util.map(i, 0, dist, x1, x2);
            int tY = (int)Util.map(i, 0, dist, y1, y2);
            int tile = Tiles.WALL;
            try {
                tile = tiles[tX][tY];
            }
            catch(Exception e) {
            }
            if (tile <= Tiles.WALL) return false;
        }
        return true;
    }

    public void visitTilesInLine(int x1, int y1, int x2, int y2) {
        int dist = (int)Math.max(Util.fastAbs(x2 - x1), Util.fastAbs(y2 - y1));
        for (int i = 0; i < dist; i ++) {
            int tX = (int)Util.map(i, 0, dist, x1, x2);
            int tY = (int)Util.map(i, 0, dist, y1, y2);

            int tile = Tiles.WALL;
            try {
                tile = tiles[tX][tY];
            }
            catch(Exception e) {
            }

            boolean visit = false;
            try {
                visit = visited[tX][tY];
            }
            catch(Exception e) {
            }

            if (!visit) {
                if (tile <= Tiles.WALL) visitTile(tX, tY);
                else visitTileFull(tX, tY);
            }
            if (tile <= Tiles.WALL) {
                break;
            }
        }
    }

    private void initialiseChunks() {
        CHUNK_W = (int)Math.ceil(w/(float)CHUNK_SIZE);
        CHUNK_H = (int)Math.ceil(h/(float)CHUNK_SIZE);
        enemies = new ArrayList[CHUNK_W * CHUNK_H];
        for(int i = 0; i < enemies.length; i ++) {
            enemies[i] = new ArrayList<Enemy>();
        }
    }

    public void addEnemy(StandardEnemy enemy) {
        if(enemies[getChunk((int)enemy.x, (int)enemy.y)] == null) enemies[getChunk((int)enemy.x, (int)enemy.y)] = new ArrayList<Enemy>();
        enemies[getChunk((int)enemy.x, (int)enemy.y)].add(enemy);
    }

    public ArrayList<Integer> getChunks(int x, int y) {
        ArrayList<Integer> chunks = new ArrayList<Integer>();
        for(int i = x - CHUNK_SIZE; i <= x + CHUNK_SIZE; i += CHUNK_SIZE) {
            for(int j = y - CHUNK_SIZE; j <= y + CHUNK_SIZE; j += CHUNK_SIZE) {
                int c = getChunk(i, j);
                if(c >= 0 && c < enemies.length) {
                    chunks.add(getChunk(i, j));
                }
            }
        }
        return chunks;
    }

    public int getChunk(int x, int y) {
        return (x/CHUNK_SIZE) + (y/CHUNK_SIZE) * CHUNK_W;
    }

    public PVector getChunkPos(int x, int y) {
        return new PVector(x/CHUNK_SIZE, y/CHUNK_SIZE);
    }


    public void setTiles(int[][] tiles) { //Tiles with tileset
        this.tiles = tiles;
        resizeLevel();
        saveLevel();
    }

    private void resizeLevel() {
        this.w = tiles.length;
        this.h = tiles[0].length;

        initialiseChunks();
        visited = new boolean[w][h];
        visitedCalcLocations = new boolean[w][h];

        background = new Canvas(Main.width - Util.GUI_WIDTH, Main.height);
        miniMapOverlay = new Canvas(w, h);
        miniMap = new Canvas(w, h);
        Graphics.background(miniMap, Color.BLACK);
    }

    public void setZones(ArrayList<PVector> bossZones, ArrayList<PVector> generalZones) { //sets the zones
        this.bossZones = bossZones;
        this.generalZones = generalZones;
    }


    public int[][] getTiles() {
        return tiles;
    }

    public void setTile(int t, int i, int j) {
        tiles[i][j] = t;
    }

    public int getTile(int i, int j) {
        int tile = Tiles.WALL;
        try { tile = tiles[i][j]; } catch(Exception e) {}
        return tile;
    }

    public void setStart(PVector start) {
        this.start = start;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Canvas getMiniMap() {
        return miniMap;
    }

    public Canvas getOverlay() {
        return miniMapOverlay;
    }

    public void saveLevel() {
        //generateImage().save("/out/image" + name + ".png");
        /*PrintWriter file = null;
        try {
            file = new PrintWriter("/out/" + name + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < h; j ++) {
            for (int i = 0; i < w; i ++) {
                file.print(tiles[i][j]);
                if (i < w -1) file.print(',');
            }
            file.println();
        }
        file.flush();
        file.close();
        */
    }

    protected void validSpawnRooms(StandardEnemy enemy) {
        do {
            PVector coords = generalZones.get((int)Util.random(generalZones.size()));
            enemy.x = coords.x + Util.random(1);
            enemy.y = coords.y + Util.random(1);
        } while (!enemy.validPosition(this, enemy.x, enemy.y));
    }

    protected void validBossSpawn(StandardEnemy enemy) {
        do {
            PVector coords = bossZones.get((int)Util.random(bossZones.size()));
            enemy.x = coords.x + Util.random(1);
            enemy.y = coords.y + Util.random(1);
        } while (!enemy.validPosition(this, enemy.x, enemy.y));
    }

    protected void validSpawn(StandardEnemy enemy) {
        while (!enemy.validPosition(this, enemy.x, enemy.y)) {
            enemy.x = Util.random(w);
            enemy.y = Util.random(h);
        }
    }

    protected void addEnemies(Class className, int number, int tier) {
        StandardEnemy enemy;
        for(int i = 0; i < 30; ++i) {
            enemy = new Bat(Util.random(w), Util.random(h), 1);
            validSpawn(enemy);
            addEnemy(enemy);
        }
    }

  /*protected void addBoss(StandardEnemy, int tier) {
    //StandardEnemy enemy = className.getConstructors()[0].newInstance(Util.random(w), Util.random(h), tier);
    if (this instanceof RoomLevel) {
      validBossSpawn(enemy);
    } else {
      validSpawn(enemy);
    }
    addEnemy(enemy);
    bosses.add(enemy);
  }*/

    public boolean visited(int x, int y) {
        try {
            return visited[x][y];
        } catch(Exception e) {
            return false;
        }
    }
}