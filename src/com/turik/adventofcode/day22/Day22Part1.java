package com.turik.adventofcode.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day22Part1 {
    static List<String> lines = new ArrayList<>();
    /**
     *     3
     *     ^
     * 2 <-|-> 0
     *     v
     *     1
     */
    static int direction = 0;
    static char empty = ' ';
    static char open = '.';
    static char solid = '#';
    // position
    static int row;
    static int col;
    // debug
    static Map<Integer, Character> directions = Map.of(
            0, '>',
            1, 'v',
            2, '<',
            3, '^'
    );
    static boolean isDebug = false;

    static void turn(char dir) {
        int where = (dir == 'R') ? 1 : -1;
        direction = (4 + direction + where) % 4;
    }

    static void draw() {
        if (isDebug) {
            String newStr = lines.get(row).substring(0, col);
            newStr += directions.get(direction);
            newStr += lines.get(row).substring(col + 1);
            lines.set(row, newStr);
        }
    }

    static void move(int num) {
        switch (direction) {
            case 0:
                moveRight(num);
                break;
            case 1:
                moveDown(num);
                break;
            case 2:
                moveLeft(num);
                break;
            case 3:
                moveUp(num);
                break;
            default:
                throw new RuntimeException("unknown direction " + direction);
        }
    }

    static void moveRight(int num) {
        for (int i = 0; i < num; i++) {
            right();
            draw();
        }
    }

    static void right() {
        String currow = lines.get(row);
        int firstNonEmpty = -1;
        for (int i = 0; i < col; i++) {
            if (currow.charAt(i) != empty) {
                firstNonEmpty = i;
                break;
            }
        }

        int newcol = (col+1) % currow.length();
        if (currow.charAt(newcol) == solid) {
            return;
        } else if (currow.charAt(newcol) == empty) {
            if (currow.charAt(firstNonEmpty) == open) {
                col = firstNonEmpty;
            }
        } else {
            col = newcol;
        }
    }

    static void moveDown(int num) {
        for (int i = 0; i < num; i++) {
            down();
            draw();
        }
    }

    static void down() {
        int lastRow = row;

        while (lastRow >= 0 && lines.get(lastRow).charAt(col) != empty) {
            lastRow--;
        }
        lastRow++;

        int newrowN = (row + 1) % lines.size();
        String newrow = lines.get(newrowN);

        if (newrow.charAt(col) == solid) {
            // do nothing
        } else if (newrow.charAt(col) == empty) {
            if (lines.get(lastRow).charAt(col) == open) {
                row = lastRow;
            }
        } else {
            row = newrowN;
        }
    }

    static void moveLeft(int num) {
        for (int i = 0; i < num; i++) {
            left();
            draw();
        }
    }

    static void left() {
        String currow = lines.get(row);

        int lastNonEmpty = -1;
        for (int i = currow.length() - 1; i > col; i--) {
            if (currow.charAt(i) != empty) {
                lastNonEmpty = i;
                break;
            }
        }

        int newcol = (currow.length() + col - 1) % currow.length();
        if (currow.charAt(newcol) == solid) {
            return;
        } else if (currow.charAt(newcol) == empty) {
            if (currow.charAt(lastNonEmpty) == open) {
                col = lastNonEmpty;
            }
        } else {
            col = newcol;
        }
    }

    static void moveUp(int num) {
        for (int i = 0; i < num; i++) {
            up();
            draw();
        }
    }

    static void up() {
        int lastRow = row;

        while (lastRow < lines.size() && lines.get(lastRow).charAt(col) != empty) {
            lastRow++;
        }
        lastRow--;

        int newrowN = (lines.size() + row - 1) % lines.size();
        String newrow = lines.get(newrowN);

        if (newrow.charAt(col) == solid) {
            return;
        } else if (newrow.charAt(col) == empty) {
            if (lines.get(lastRow).charAt(col) == open) {
                row = lastRow;
            }
        } else {
            row = newrowN;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day22.txt"));
        String line;
        int maxL = -1;

        while (!(line = reader.readLine()).isEmpty()) {
            lines.add(line);
            maxL = Math.max(maxL, line.length());
        }

        String path = reader.readLine();

        // making all lines same length
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).length() < maxL) {
                lines.set(i, lines.get(i) + String.valueOf(empty).repeat(maxL - lines.get(i).length()));
            }
        }

        // determine starting pos
        row = 0;
        col = lines.get(row).indexOf(open);

        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < path.length() && Character.isDigit(path.charAt(i))) {
                    sb.append(path.charAt(i));
                    i++;
                }
                i--;
                move(Integer.parseInt(sb.toString()));
            } else {
                turn(c);
            }
        }

        System.out.println(1000 * (row + 1) + 4 * (col + 1) + direction);
    }
}
