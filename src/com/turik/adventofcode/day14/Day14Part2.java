package com.turik.adventofcode.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day14Part2 {

    static Map<Integer, Set<Integer>> cave = new HashMap<>();
    static int lowestWall = 0;

    static void addPoint(int x, int y) {
        Set<Integer> vert = cave.computeIfAbsent(x, key -> new TreeSet<>(Comparator.naturalOrder()));
        vert.add(y);
    }

    static int getLocalPeak(int x, int y) {
        for (int i : cave.get(x)) {
            if (i > y) {
                return i;
            }
        }
        return -1;
    }

    static boolean dropSand() {
        int x = 500;
        int y = 0;

        while (true) {
            Set<Integer> vert = cave.get(x);
            if (vert == null) {
                return false;
            }
            int peak = getLocalPeak(x, y);
            if (peak == -1) { // falling to the bottom
                addPoint(x, lowestWall);
                addPoint(x, lowestWall - 1);
                return true;
            }
            // check left
            Set<Integer> leftVert = cave.get(x - 1);
            if (leftVert == null) { // there's abyss on the left
                addPoint(x-1 , lowestWall);
                addPoint(x-1 , lowestWall - 1);
                return true;
            }
            if (!leftVert.contains(peak)) { // if true, then we can't fall on the left
                x -= 1;
                y = peak;
                continue;
            }
            // check right
            Set<Integer> rightVert = cave.get(x + 1);
            if (rightVert == null) { // there's abyss on the right
                addPoint(x+1 , lowestWall);
                addPoint(x+1 , lowestWall - 1);
                return true;
            }
            if (!rightVert.contains(peak)) { // if true, then we can't fall on the right
                x += 1;
                y = peak;
                continue;
            }
            // we are at the starting point and it's filled
            if (x == 500 && y == 0 && cave.get(500).contains(0)) {
                return false;
            }
            addPoint(x, peak - 1);
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day14.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] allCoords = line.split(" -> ");

            int prevX = Integer.parseInt(allCoords[0].split(",")[0]);
            int prevY = Integer.parseInt(allCoords[0].split(",")[1]);

            lowestWall = Math.max(lowestWall, prevY);

            for (int i = 1; i < allCoords.length; i++) {
                int x = Integer.parseInt(allCoords[i].split(",")[0]);
                int y = Integer.parseInt(allCoords[i].split(",")[1]);

                lowestWall = Math.max(lowestWall, y);

                if (prevX == x) {
                    for (int j = Math.min(prevY, y); j <= Math.max(prevY, y); j++) {
                        addPoint(x, j);
                    }
                } else if (prevY == y) {
                    for (int j = Math.min(prevX, x); j <= Math.max(prevX, x); j++) {
                        addPoint(j, y);
                    }
                } else {
                    throw new RuntimeException("wall's not supposed to be diagonal!");
                }

                prevX = x;
                prevY = y;
            }
        }

        lowestWall += 2;

        int res = 0;
        
        while (true) {
            if (!dropSand()) {
                break;
            }
            res++;
            if (res == Integer.MAX_VALUE) { // emergency break lol
                break;
            }
        }


        System.out.println(res);
    }
}
