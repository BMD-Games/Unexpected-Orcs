package Levels;

import Utility.Util;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

import static Tiles.Tiles.FLOOR;
import static Utility.Constants.game;

public class Corridor {

    int r1;
    int r2;

    ArrayList<PVector> path;

    int minX, maxX, minY, maxY;

    Corridor(int r1, int r2, ArrayList<PVector> path) {
        this.r1 = r1;
        this.r2 = r2;

        this.path = path;
    }

    public void offset(int x, int y) {
        for(PVector p : path) {
            p.add(x, y);
        }
    }

    public static Corridor generateCorridor(ArrayList<Room> rooms, int r1, int r2) {
        //Find a path between the two rooms that does not intersect any other rooms
        PVector start = rooms.get(r1).midPoint();
        PVector stop = rooms.get(r2).midPoint();

        ArrayList<PVector> queue = new ArrayList<>();
        ArrayList<PVector> visited = new ArrayList<>();

        HashMap<PVector, PVector> parents = new HashMap<>();

        HashMap<PVector, Integer> gScore = new HashMap<>();
        HashMap<PVector, Integer> fScore = new HashMap<>();

        gScore.put(start, 0);
        fScore.put(start, costEstimate(start, stop));

        parents.put(start, null);

        queue.add(start);

        //game.println("Start: " + start + " Goal: " + stop);

        while(queue.size() > 0) {
            PVector current = queue.remove(getClosest(queue, fScore));

            if(current.equals(stop)) break;

            visited.add(current);

            PVector[] neighbours = getNeighbours(current);

            int score = gScore.get(current) + 1;

            for(PVector n : neighbours) {
                if(validTile(rooms, (int)n.x, (int)n.y, r1, r2) && !visited.contains(n)) {
                    if(!queue.contains(n)) queue.add(n);

                    if(gScore.getOrDefault(n, (int)Float.POSITIVE_INFINITY) > score) {
                        parents.put(n, current);
                        gScore.put(n, score);
                        fScore.put(n, score + costEstimate(n, stop));
                    }
                }
            }

        }

        return reconstructPath(parents, stop, r1, r2);
    }

    private static Corridor reconstructPath(HashMap<PVector, PVector> parents, PVector current, int r1, int r2) {

        float minX = Float.POSITIVE_INFINITY, maxX  = Float.NEGATIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;

        ArrayList<PVector> path = new ArrayList<>();

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

        if(rooms.get(r1).contains(x, y) || rooms.get(r2).contains(x, y)) return true;

        for(int i = 0; i < rooms.size(); i ++) {
            if(i == r1 || i == r2) continue;

            if(rooms.get(i).contains(x + 1, y)) return false;
            if(rooms.get(i).contains(x - 1, y)) return false;

            if(rooms.get(i).contains(x, y + 1)) return false;
            if(rooms.get(i).contains(x, y - 1)) return false;

            if(rooms.get(i).contains(x + 1, y + 1)) return false;
            if(rooms.get(i).contains(x + 1, y - 1)) return false;

            if(rooms.get(i).contains(x - 1, y + 1)) return false;
            if(rooms.get(i).contains(x - 1, y - 1)) return false;
        }

        return true;
    }

    private static int costEstimate(PVector pos, PVector goal) {
        return (int)(game.abs(pos.x - goal.x) + game.abs(pos.y - goal.y));
    }

    private static int getClosest(ArrayList<PVector> queue, HashMap<PVector, Integer> fScore) {
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

    private static PVector[] getNeighbours(PVector current) {
        PVector[] n = new PVector[4];

        n[0] = new PVector(current.x + 1, current.y);
        n[1] = new PVector(current.x, current.y + 1);
        n[2] = new PVector(current.x - 1, current.y);
        n[3] = new PVector(current.x, current.y - 1);

        return n;
    }

}
