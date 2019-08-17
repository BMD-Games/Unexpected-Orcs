package Levels;

import Tiles.Tile;
import Tiles.WallTile;
import Utility.Util;
import processing.core.PVector;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Room {

    public int x, y, w, h;
    public String[][] tiles;

    Room() {
    }

    Room(Room room) { //create a room from another room
        this.x = room.x;
        this.y = room.y;
        this.w = room.w;
        this.h = room.h;
        this.tiles = room.tiles;
    }

    public boolean collides(Room room) {
        int x = this.x - 1;//factor in a wall buffer
        int y = this.y - 1;
        int w = this.w + 2;
        int h = this.h + 2;
        return (x + w >= room.x  && // r1 right edge past r2 left
                x <= room.x + room.w &&   // r1 left edge past r2 right
                y + h >= room.y      &&   // r1 top edge past r2 bottom
                y <= room.y + room.h);    // r1 bottom edge past r2 top
    }

    public boolean contains(int x, int y) {
        return Util.pointInBox(x, y, this.x, this.y, this.w, this.h);
    }

    public boolean overlapsX(Room room) {
        return ((x >= room.x && x <= room.x + room.w) || (x + w >= room.x && x + w <= room.x + room.w));
    }

    public boolean overlapsY(Room room) {
        return ((y >= room.y && y <= room.y + room.h) || (y + h >= room.y && y + h <= room.y + room.h));
    }

    public boolean inRoom(int px, int py) {
        return (!Tiles.get(tiles[px-x][py-y]).solid) && Util.pointInBox(px, py, x, y, w, h);
    }

    public PVector midPoint() {
        return new PVector((int)(x + w/2), (int)(y + h/2));
    }

    public int distanceSquare(Room room) {
        //returns the distance between the rooms (but uses separate x and y);
        return (int)(game.abs(room.x - x) + game.abs(room.y - y));
    }

    public int distance(Room room) {

        int[] edges = new int[4];

        edges[0] = game.abs(room.x - (x + w));
        edges[1] = game.abs((room.x + room.w) - x);
        edges[2] = game.abs(room.y - (y + h));
        edges[3] = game.abs((room.y + room.h) - y);

        return game.min(edges);
    }

    public boolean outOfBounds(int w, int h) {
        return (this.x < edgeSize || this.x + this.w > w - edgeSize || this.y < edgeSize || this.y + this.h > h - edgeSize);
    }

    public void setTiles(String[][] newTiles) {
        w = newTiles.length;
        h = newTiles[0].length;
        this.tiles = new String[w][h];
        for(int i = 0; i < w; i ++) {
            for(int j = 0; j < h; j ++) {
                this.tiles[i][j] = newTiles[i][j];
            }
        }
    }

    public static Room randomRoom(int minSize, int maxSize, int w, int h) {
        Room room = new Room();
        room.w = game.floor(game.random(minSize, maxSize)); //room width
        room.h = game.floor(game.random(minSize, maxSize)); //room height
        room.x = game.floor(game.random(edgeSize, w - room.w - edgeSize)); //room x pos - avoid edges
        room.y = game.floor(game.random(edgeSize, h - room.h - edgeSize)); //toom y pos - avoid edges

        return new Room();
    }


    public static Room randomlyPlaceRoom(Room room, int w, int h) {
        room.x = (int)game.random(edgeSize, w - room.w - edgeSize);
        room.y = (int)game.random(edgeSize, h - room.h - edgeSize);
        return room;
    }

    public static Room testSpawn() {
        Room room = new Room();

        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }

    public static Room testRoom() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }

    public static Room testRoom2() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }

    public static Room testBoss() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }

    public static Room circle5() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }
    public static Room circle7() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }
    public static Room circle11() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }

    public static Room circleBoss() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }
    public static Room diamondSpawn() {
        Room room = new Room();
        room.setTiles(new String[][]{
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "WOOD", "WOOD", "WOOD", "WOOD", "STONE_BRICK"},
                {"STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK", "STONE_BRICK"}
        });
        return room;
    }
}