package Levels;

import Utility.Util;
import processing.core.PVector;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Room {

    public int x, y, w, h;
    public int[][] tiles;

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

    public boolean overlapsX(Room room) {
        return ((x >= room.x && x <= room.x + room.w) || (x + w >= room.x && x + w <= room.x + room.w));
    }

    public boolean overlapsY(Room room) {
        return ((y >= room.y && y <= room.y + room.h) || (y + h >= room.y && y + h <= room.y + room.h));
    }

    public boolean inRoom(int px, int py) {
        return (tiles[px-x][py-y] > WALL) && Util.pointInBox(px, py, x, y, w, h);
    }

    public PVector midPoint() {
        return new PVector((int)x + w/2, (int)y + h/2);
    }

    public int distance(Room room) {
        //returns the distance between the rooms (but uses separate x and y);
        return (int)(game.abs(room.x - x) + game.abs(room.y - y));
    }

    public boolean outOfBounds(int w, int h) {
        return (this.x < edgeSize || this.x + this.w > w - edgeSize || this.y < edgeSize || this.y + this.h > h - edgeSize);
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
        w = tiles.length;
        h = tiles[0].length;
    }

    public void getClosestPoints(Room room) {

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
        room.setTiles(new int[][]{
                {2, 2, 2, 4, 2, 2, 2, 5, 2},
                {2, 5, 6, 6, 6, 6, 6, 2, 2},
                {2, 6, 6, 10, 10, 10, 6, 6, 2},
                {2, 6, 10, 10, 13, 10, 10, 6, 2},
                {4, 6, 10, 13, 11, 13, 10, 6, 2},
                {2, 6, 10, 10, 13, 10, 10, 6, 2},
                {2, 6, 6, 10, 10, 10, 6, 6, 2},
                {5, 2, 6, 6, 6, 6, 6, 2, 5},
                {2, 2, 2, 2, 2, 4, 2, 2, 2}
        });
        return room;
    }

    public static Room testRoom() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2},
                {2, 2, 8, 2, 2},
                {2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2}
        });
        return room;
    }

    public static Room testRoom2() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2},
                {2, 2, 9, 2, 2},
                {2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2}
        });
        return room;
    }

    public static Room testBoss() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8},
                {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8},
                {8, 6, 6, 2, 2, 2, 2, 2, 6, 6, 8},
                {8, 6, 6, 2, 2, 2, 2, 2, 6, 6, 8},
                {8, 6, 6, 2, 2, 3, 2, 2, 6, 6, 8},
                {8, 6, 6, 2, 2, 2, 2, 2, 6, 6, 8},
                {8, 6, 6, 2, 2, 2, 2, 2, 6, 6, 8},
                {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8},
                {8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8},
                {9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9}
        });
        return room;
    }

    public static Room circle5() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0}
        });
        return room;
    }
    public static Room circle7() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {0, 0, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 0, 0}
        });
        return room;
    }
    public static Room circle11() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}
        });
        return room;
    }

    public static Room circleBoss() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}
        });
        return room;
    }
    public static Room diamondSpawn() {
        Room room = new Room();
        room.setTiles(new int[][]{
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 7, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0}
        });
        return room;
    }
}