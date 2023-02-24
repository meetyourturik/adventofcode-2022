package com.turik.adventofcode.day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day24Part2 {

    static final int MOVES_LIMIT = 500;

    static final int EMPTY = 0;
    static final int RIGHT = 0b0001;
    static final int DOWN  = 0b0010;
    static final int LEFT  = 0b0100;
    static final int UP    = 0b1000;
    static final int WALL  = 0b10000;

    static int height;
    static int width;

    static int START_X;
    static int START_Y;
    static int END_X;
    static int END_Y;

    static Map<String, List<String>> visited = new HashMap<>();

    static int getIntFromChar(char c) {
        switch (c) {
            case '.':
                return EMPTY;
            case '>':
                return RIGHT;
            case 'v':
                return DOWN;
            case '<':
                return LEFT;
            case '^':
                return UP;
            case '#':
                return WALL;
            default:
                throw new IllegalArgumentException("unknown char: " + c);
        }
    }

    static char getCharFromInt(int i) {
        switch (i) {
            case EMPTY:
                return '.';
            case RIGHT:
                return '>';
            case DOWN:
                return 'v';
            case LEFT:
                return '<';
            case UP:
                return '^';
            case WALL:
                return '#';
            default:
                return 'x';
        }
    }

    // keeping this one for debug
    static void printField(int[][] field) {
        for (int[] row : field) {
            StringBuilder sb = new StringBuilder();
            for (int n : row) {
                sb.append(getCharFromInt(n));
            }
            System.out.println(sb);
        }
    }

    static String hashcode(int[][] field) {
        int res = 0;
        for (int[] row : field) {
            res = 31 * res + Arrays.hashCode(row);
        }
        return String.valueOf(res);
    }

    static int[][] getNextField(int[][] field) {
        int[][] nextField = new int[height][width];

        for (int i = 0; i < width; i++) {
            nextField[0][i] = WALL;
            nextField[height - 1][i] = WALL;
        }

        for (int i = 0; i < height; i++) {
            nextField[i][0] = WALL;
            nextField[i][width - 1] = WALL;
        }

        nextField[START_Y][START_X] = EMPTY;
        nextField[END_Y][END_X] = EMPTY;

        for (int i = 1; i < height - 1; i++) { // we don't care about 1st and last rows
            for (int j = 1; j < width - 1; j++) { // again, 1st and last column don't matter
                if ((field[i][j] & RIGHT) != 0 ) {
                    int nj = (j + 1 == width - 1) ? 1 : j + 1;
                    nextField[i][nj] |= RIGHT;
                }
                if ((field[i][j] & LEFT) != 0 ) {
                    int nj = (j - 1 == 0) ? width - 2 : j - 1;
                    nextField[i][nj] |= LEFT;
                }
                if ((field[i][j] & UP) != 0 ) {
                    int ni = (i - 1 == 0) ? height - 2 : i - 1;
                    nextField[ni][j] |= UP;
                }
                if ((field[i][j] & DOWN) != 0 ) {
                    int ni = (i + 1 == height - 1) ? 1 : i + 1;
                    nextField[ni][j] |= DOWN;
                }
            }
        }
        return nextField;
    }

    static int moveSF(int moves, int[][] field, int curx, int cury) {
        if (curx == END_X && cury == END_Y) {
            return moves;
        }
        if (moves >= MOVES_LIMIT) {
            return MOVES_LIMIT;
        }
        String pos = curx + ":" + cury;
        String fieldHash = hashcode(field);
        List<String> visitedForPos = visited.computeIfAbsent(pos, s -> new ArrayList<>());
        if (visitedForPos.contains(fieldHash)) {
            return MOVES_LIMIT;
        }
        visitedForPos.add(fieldHash);
        moves++;
        int[][] nextField = getNextField(field);

        int res = 99_999_999;

        if (nextField[cury][curx + 1] == EMPTY) {
            res = Math.min(res, moveSF(moves, nextField, curx + 1, cury));
        }
        if (nextField[cury][curx - 1] == EMPTY) {
            res = Math.min(res, moveSF(moves, nextField, curx - 1, cury));
        }
        if (cury < height-1 && nextField[cury + 1][curx] == EMPTY) {
            res = Math.min(res, moveSF(moves, nextField, curx, cury + 1));
        }
        if (cury > 0 && nextField[cury - 1][curx] == EMPTY) {
            res = Math.min(res, moveSF(moves, nextField, curx, cury - 1));
        }
        if (nextField[cury][curx] == EMPTY) {
            res = Math.min(res, moveSF(moves, nextField, curx, cury));
        }

        return res;
    }

    static int moveFS(int moves, int[][] field, int curx, int cury) {
        if (curx == START_X && cury == START_Y) {
            return moves;
        }
        if (moves >= MOVES_LIMIT) {
            return MOVES_LIMIT;
        }
        String pos = curx + ":" + cury;
        String fieldHash = hashcode(field);
        List<String> visitedForPos = visited.computeIfAbsent(pos, s -> new ArrayList<>());
        if (visitedForPos.contains(fieldHash)) {
            return MOVES_LIMIT;
        }
        visitedForPos.add(fieldHash);
        moves++;
        int[][] nextField = getNextField(field);

        int res = 99_999_999;

        if (nextField[cury][curx] == EMPTY) {
            res = Math.min(res, moveFS(moves, nextField, curx, cury));
        }
        if (nextField[cury][curx + 1] == EMPTY) {
            res = Math.min(res, moveFS(moves, nextField, curx + 1, cury));
        }
        if (nextField[cury][curx - 1] == EMPTY) {
            res = Math.min(res, moveFS(moves, nextField, curx - 1, cury));
        }
        if (cury < height-1 && nextField[cury + 1][curx] == EMPTY) {
            res = Math.min(res, moveFS(moves, nextField, curx, cury + 1));
        }
        if (cury > 0 && nextField[cury - 1][curx] == EMPTY) {
            res = Math.min(res, moveFS(moves, nextField, curx, cury - 1));
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day24.txt"));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        width = lines.get(0).length();
        height = lines.size();

        START_X = 1;
        START_Y = 0;
        END_X = width - 2;
        END_Y = height - 1;

        int[][] field = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = getIntFromChar(lines.get(i).charAt(j));
            }
        }
        int res = 0;

        int trip1 = moveSF(0, field, START_X, START_Y);
        res += trip1;

        visited.clear();
        for (int i = 0; i < trip1; i++) {
            field = getNextField(field);
        }
        int trip2 = moveFS(0, field, END_X, END_Y);
        res += trip2;

        visited.clear();
        for (int i = 0; i < trip2; i++) {
            field = getNextField(field);
        }
        int trip3 = moveSF(0, field, START_X, START_Y);
        res += trip3;

        System.out.println(res);
    }
}
