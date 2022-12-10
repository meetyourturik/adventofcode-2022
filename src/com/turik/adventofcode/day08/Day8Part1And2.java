package com.turik.adventofcode.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8Part1And2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day08.txt"));
        String line;

        List<String> rows = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            rows.add(line);
        }

        int n = rows.size();
        int m = rows.get(0).length();

        int[][] heights = new int[n][m];
        boolean[][] visibility = new boolean[n][m]; // part 1
        int[][] scenic = new int[n][m]; // part 2

        for (int i = 0; i < n; i++) {
            Arrays.fill(scenic[i], 1);
            for (int j = 0; j < m; j++) {
                heights[i][j] = rows.get(i).charAt(j) - '0';
            }
        }

        // -------------------
        // left to right and back
        for (int i = 1; i < n-1; i++) {
            int rowMaxL = heights[i][0];
            int rowMaxR = heights[i][m-1];

            for (int j = 1; j < m-1; j++) {
                int currentHeight = heights[i][j];
                // L -> R part 1
                if (currentHeight > rowMaxL) {
                    rowMaxL = currentHeight;
                    visibility[i][j] = true;
                }
                // part 2
                int vl = 1;
                while (vl < j && currentHeight > heights[i][j - vl]) {
                    vl++;
                }
                scenic[i][j] *= vl;

                currentHeight = heights[i][m - 1 - j];
                // R -> L part 1
                if (currentHeight > rowMaxR) {
                    rowMaxR = currentHeight;
                    visibility[i][m - 1 - j] = true;
                }
                // part 2
                int vr = 1;
                while (vr < j && currentHeight > heights[i][m - 1 - j + vr]) {
                    vr++;
                }
                scenic[i][m - 1 - j] *= vr;
            }
        }

        // top to bottom and back
        for (int j = 1; j < m-1; j++) {
            int rowMaxT = heights[0][j];
            int rowMaxB = heights[n-1][j];

            for (int i = 1; i < n-1; i++) {
                int currentHeight = heights[i][j];
                // T -> B part 1
                if (currentHeight > rowMaxT) {
                    rowMaxT = currentHeight;
                    visibility[i][j] = true;
                }
                // part 2
                int vt = 1;
                while (vt < i && currentHeight > heights[i - vt][j]) {
                    vt++;
                }
                scenic[i][j] *= vt;

                currentHeight = heights[n - 1 - i][j];
                // B -> T part 1
                if (currentHeight> rowMaxB) {
                    rowMaxB = currentHeight;
                    visibility[n - 1 - i][j] = true;
                }
                // part 2
                int vb = 1;
                while (vb < i && currentHeight > heights[n - 1 - i + vb][j]) {
                    vb++;
                }
                scenic[n - 1 - i][j] *= vb;
            }
        }
        // -------------------

        int res = 0;

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (visibility[i][j]) {
                    res++;
                }
            }
        }

        int max = 0;

        for (int[] row : scenic) {
            for (int view : row) {
                max = Math.max(max, view);
            }
        }

        int borderTrees = (m + n) * 2 - 4;
        System.out.printf("part 1 answer: %d\n", res + borderTrees);
        System.out.printf("part 2 answer: %d", max);
    }
}
