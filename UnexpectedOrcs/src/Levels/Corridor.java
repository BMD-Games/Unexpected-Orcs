package Levels;

import Utility.Util;
import Utility.Vec2;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

import static Tiles.Tiles.FLOOR;
import static Utility.Constants.game;

public class Corridor {

    int r1;
    int r2;

    ArrayList<Vec2> path;

    int minX, maxX, minY, maxY;

    Corridor(int r1, int r2, ArrayList<Vec2> path) {
        this.r1 = r1;
        this.r2 = r2;

        this.path = path;
    }

    public void offset(int x, int y) {
        for(Vec2 p : path) {
            p.add(x, y);
        }
    }

    public static Corridor generateCorridor(ArrayList<Room> rooms, int r1, int r2) {
        //Find a path between the two rooms that does not intersect any other rooms
        Vec2 start = rooms.get(r1).closestDoor(rooms.get(r2));
        Vec2 stop = rooms.get(r2).closestDoor(start);

        ArrayList<Vec2> queue = new ArrayList<>();
        ArrayList<Vec2> visited = new ArrayList<>();

        HashMap<Vec2, Vec2> parents = new HashMap<>();

        HashMap<Vec2, Integer> gScore = new HashMap<>();
        HashMap<Vec2, Integer> fScore = new HashMap<>();

        gScore.put(start, 0);
        fScore.put(start, costEstimate(start, stop));

        parents.put(start, null);

        queue.add(start);

        //game.println("Start: " + start + " Goal: " + stop);

        while(queue.size() > 0) {
            Vec2 current = queue.remove(getClosest(queue, fScore));

            if(current.equals(stop)) break;

            visited.add(current);

            Vec2[] neighbours = getNeighbours(current);

            int score = gScore.get(current) + 1;

            for(Vec2 n : neighbours) {
                if(validTile(rooms, n.x, n.y, r1, r2) && !visited.contains(n)) {
                    if(!queue.contains(n)) queue.add(n);

                    if(gScore.getOrDefault(n, (int)Float.POSITIVE_INFINITY) > score) {
                        parents.put(n, current);
                        gScore.put(n, score);
                        fScore.put(n, score + costEstimate(n, stop));
                    }
                }
            }

        }

        return reconstructPath(parents, stop, rooms, r1, r2);
    }

    private static Corridor reconstructPath(HashMap<Vec2, Vec2> parents, Vec2 current, ArrayList<Room> rooms, int r1, int r2) {

        float minX = Float.POSITIVE_INFINITY, maxX  = Float.NEGATIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;

        ArrayList<Vec2> path = new ArrayList<>();

        while(current != null) {
            path.add(current);
            if(current.x < minX) minX = current.x;
            if(current.x > maxX) maxX = current.x;
            if(current.y < minY) minY = current.y;
            if(current.y > maxY) maxY = current.y;

            current = parents.get(current);
        }

        Corridor cor = new Corridor(r1, r2, path);

        cor.minX = (int)minX;
        cor.maxX = (int)maxX;
        cor.minY = (int)minY;
        cor.maxY = (int)maxY;


        return cor;
    }

    private static boolean validTile(ArrayList<Room> rooms, int x, int y, int r1, int r2) {

        Room room1 = rooms.get(r1);
        Room room2 = rooms.get(r2);

        //allow if it's in a
        if(room1.inDoorway(x, y) || room2.inDoorway(x, y)) return true;

        //if(room1.contains(x, y) || room2.contains(x, y)) return false;

        for(int i = 0; i < rooms.size(); i ++) {
            //if(i == r1 || i == r2) continue;

            if(rooms.get(i).containsCorridorPoint(x, y)) return false;
        }

        return true;
    }

    private static int costEstimate(Vec2 pos, Vec2 goal) {
        return game.abs(pos.x - goal.x) + game.abs(pos.y - goal.y);
    }

    private static int getClosest(ArrayList<Vec2> queue, HashMap<Vec2, Integer> fScore) {
        int inf = (int)Float.POSITIVE_INFINITY;
        int min = inf;
        int index = 0;

        for(int i = 0; i < queue.size(); i ++) {
            if(fScore.getOrDefault(queue.get(i), inf) < min) {
                min = fScore.get(queue.get(i));
                index = i;
            }
        }
        return index;
    }

    private static Vec2[] getNeighbours(Vec2 current) {
        Vec2[] n = new Vec2[4];

        n[0] = new Vec2(current.x + 1, current.y);
        n[1] = new Vec2(current.x, current.y + 1);
        n[2] = new Vec2(current.x - 1, current.y);
        n[3] = new Vec2(current.x, current.y - 1);

        return n;
    }

}
