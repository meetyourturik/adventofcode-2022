package com.turik.adventofcode.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day12Part1And2 {

    static List<String> strings = new ArrayList<>();
    static int maxX;
    static int maxY;
    static int[] start = new int[2];
    static int[] end = new int[2];
    static int[][] distances;

    private static void resetDistances() {
        for (int[] row: distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
    }

    static int getHeight(int x, int y) {
        int height;
        if (x == start[0] && y == start[1]) {
            height = 0;
        } else if (x == end[0] && y == end[1]) {
            height = 'z' - 'a';
        } else {
            height = strings.get(y).charAt(x) - 'a';
        }
        return height;
    }

    public static void processNode(int[] coords, int distance) {
        int x = coords[0];
        int y = coords[1];

        if (distance < distances[y][x]) {
            distances[y][x] = distance;
        } else {
            return;
        }

        if (x == end[0] && y == end[1]) {
            return;
        }

        int[] xchanges = {0, 1, 0, -1};
        int[] ychanges = {1, 0, -1, 0};

        int height = getHeight(x, y);

        for (int i = 0; i < 4; i++) {
            int childx = x + xchanges[i];
            int childy = y + ychanges[i];
            boolean inField = childx >= 0 && childx < maxX && childy >= 0 && childy < maxY;
            if (inField ) {
                boolean isOptimalWay = distances[childy][childx] > distance;
                int childHeight = getHeight(childx, childy);
                if (childHeight - height <= 1 && isOptimalWay) {
                    processNode(new int[]{childx, childy}, distance + 1);
                }
            }

        }
    }

    public static void main(String[] args) throws IOException {
        String startStr = "S";
        String endStr = "E";
        List<int[]> lowestPoints = new ArrayList<>();
        List<Integer> trails = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day12.txt"));
        String line;

        int y = 0;
        while ((line = reader.readLine()) != null) {
            if (line.contains(startStr)) {
                int x = line.indexOf(startStr);
                start = new int[]{x, y};
            }
            if (line.contains(endStr)) {
                int x = line.indexOf(endStr);
                end = new int[]{x, y};
            }
            if (line.contains("a")) {
                int prevIdx = line.indexOf('a');
                lowestPoints.add(new int[]{prevIdx, y});
                int idx;
                while ((idx = line.indexOf('a', prevIdx + 1)) != -1) {
                    lowestPoints.add(new int[]{idx, y});
                    prevIdx = idx;
                }
            }
            strings.add(line);
            y++;
        }

        maxY = strings.size();
        maxX = strings.get(0).length();
        distances = new int[maxY][maxX];

        resetDistances();
        // part 1
        processNode(new int[]{start[0], start[1]}, 0);
        System.out.printf("part 1 answer: %d\n", distances[end[1]][end[0]]);
        // part 2
        trails.add(distances[end[1]][end[0]]);
        for (int[] low: lowestPoints) {
            resetDistances();
            processNode(new int[]{low[0], low[1]}, 0);
            trails.add(distances[end[1]][end[0]]);
        }

        System.out.printf("part 2 answer: %d", trails.stream().min(Comparator.naturalOrder()).get());
    }
}
