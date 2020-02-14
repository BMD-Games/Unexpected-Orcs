package Levels;

import Tiles.Tile;
import Tiles.WallTile;
import Utility.Util;
import Utility.Vec2;
import processing.core.PVector;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Room {

    public int x, y, w, h;
    public String[][] tiles;

    public Vec2[] doors = new Vec2[] {new Vec2(3, -1) }; //list of positions for doors and an offset from the (x, y) location of the room eg (0, 0) would put a door in the top left of room

    Room() {
    }

    Room(Room room) { //create a room from another room
        this.x = room.x;
        this.y = room.y;
        this.w = room.w;
        this.h = room.h;
        this.tiles = room.tiles;
        this.doors = room.doors;
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

    public boolean containsCorridorPoint(int x, int y) {
        return Util.pointInBox(x, y, this.x - 1, this.y - 1, this.w + 2, this.h + 2);
    }

    public Vec2 midPoint() {
        return new Vec2((int)(x + w/2), (int)(y + h/2));
    }

    public Vec2 closestDoor(Room room) {
        return closestDoor(room.midPoint());
    }

    public Vec2 closestDoor(Vec2 door) {
        int minDist = 10000;
        int index = 0;
        for(int i = 0; i < doors.length; i ++) {
            int dx = game.abs((x + door.x )- doors[i].x);
            int dy = game.abs((y + door.y) - doors[i].y);
            if(dx + dy < minDist) { //use manhattan distance
                minDist = dx + dy;
                index = i;
            }
        }

        return new Vec2(doors[index].x + x, doors[index].y + y);
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

    public boolean inDoorway(Vec2 pos) {
        Vec2 offSet = new Vec2(pos.x - x, pos.y - y);
        boolean inDoorway = false;
        for(Vec2 door : doors) {
            if(door.equals(offSet)) {
                inDoorway = true;
                break;
            }
        }
        return inDoorway;
    }

    public boolean inDoorway(int x, int y) {

        return inDoorway(new Vec2(x, y));
    }

    public static Room randomRoom(int minSize, int maxSize, int w, int h) {
        Room room = new Room();
        room.w = game.floor(game.random(minSize, maxSize)); //room width
        room.h = game.floor(game.random(minSize, maxSize)); //room height
        room.x = game.floor(game.random(edgeSize, w - room.w - edgeSize)); //room x pos - avoid edges
        room.y = game.floor(game.random(edgeSize, h - room.h - edgeSize)); //toom y pos - avoid edges

        return new Room();
    }

    public static Room testSpawn() {
        Room room = new Room();

        room.setTiles(new String[][] {
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","STONE_TILE","STONE_TILE","SAND_TILE","STONE_TILE","STONE_TILE","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"}
        });
        return room;
    }

    public static Room testRoom1() {
        Room room = new Room();
        room.setTiles(new String[][] {
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","GRASS","GRASS","GRASS","FLOOR"},
                {"FLOOR","GRASS","GRASS","GRASS","FLOOR"},
                {"FLOOR","GRASS","GRASS","GRASS","FLOOR"},
                {"FLOOR","GRASS","GRASS","GRASS","FLOOR"},
                {"FLOOR","GRASS","GRASS","GRASS","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"}
        });
        return room;
    }

    public static Room testRoom2() {
        Room room = new Room();
        room.setTiles(new String[][] {
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","WALL","WALL","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","WALL","WALL","FLOOR"},
                {"FLOOR","WALL","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","WALL","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"},
                {"FLOOR","WALL","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","WALL","FLOOR"},
                {"FLOOR","WALL","WALL","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","WALL","WALL","FLOOR"},
                {"FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR"}
        });
        return room;
    }

    public static Room testBoss() {
        Room room = new Room();
        room.setTiles(new String[][] {
                {"STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE"},
                {"STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","WALL","WALL","WALL","FLOOR","STONE_TILE","FLOOR","WALL","WALL","WALL","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","FLOOR","STONE_TILE","STONE_TILE","FLOOR","WALL","WALL","WALL","FLOOR","STONE_TILE","STONE_TILE","FLOOR","FLOOR","STONE_TILE"},
                {"STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","FLOOR","WALL","WALL","WALL","FLOOR","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE"},
                {"STONE_TILE","FLOOR","FLOOR","STONE_TILE","STONE_TILE","FLOOR","WALL","WALL","WALL","FLOOR","STONE_TILE","STONE_TILE","FLOOR","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","WALL","WALL","WALL","WALL","FLOOR","STONE_TILE","FLOOR","WALL","WALL","WALL","WALL","FLOOR","STONE_TILE"},
                {"STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","FLOOR","STONE_TILE"},
                {"STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE","STONE_TILE"}
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