package com.turik.adventofcode.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22Part2 {
    static List<String> lines = new ArrayList<>();
    static char open = '.';
    // position
    /**
     *     3
     *     ^
     * 2 <-|-> 0
     *     v
     *     1
     */
    static int direction = 0;
    static int row;
    static int col;
    //
    static int TILE_SIDE = 49;
    static Map<Integer, Corner> corners = new HashMap<>();
    static {
        corners.put(1, new Corner(50, 0));
        corners.put(2, new Corner(100, 0));
        corners.put(3, new Corner(50, 50));
        corners.put(4, new Corner(0, 100));
        corners.put(5, new Corner(50, 100));
        corners.put(6, new Corner(0, 150));
    }

    static void turn(char dir) {
        int where = (dir == 'R') ? 1 : -1;
        direction = (4 + direction + where) % 4;
    }

    static void move(int num) {
        for (int i = 0; i < num; i++) {
            switch (direction) {
                case 0:
                    right();
                    break;
                case 1:
                    down();
                    break;
                case 2:
                    left();
                    break;
                case 3:
                    up();
                    break;
                default:
                    throw new RuntimeException("unknown direction " + direction);
            }
        }
    }

    static int getBlockByCoords(int ccol, int crow) {
        for (Map.Entry<Integer, Corner> entry : corners.entrySet()) {
            Corner corner = entry.getValue();
            if (ccol >= corner.col() && ccol <= (corner.col() + TILE_SIDE) && crow >= corner.row() && crow <= (corner.row() + TILE_SIDE)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    static int getCurrentBlock() {
        return getBlockByCoords(col, row);
    }

    static void cont(int newBlock) {
        Corner curCorner = corners.get(getCurrentBlock());
        Corner newCorner = corners.get(newBlock);

        int dcol = col - curCorner.col();
        int drow = row - curCorner.row();

        int ncol = newCorner.col() + dcol;
        int nrow = newCorner.row() + (TILE_SIDE - drow);
        if (lines.get(nrow).charAt(ncol) == open) {
            col = ncol;
            row = nrow;
        }
    }

    static void flip(int newBlock) {
        Corner curCorner = corners.get(getCurrentBlock());
        Corner newCorner = corners.get(newBlock);

        int dcol = col - curCorner.col();
        int drow = row - curCorner.row();

        int ncol = newCorner.col() + dcol;
        int nrow = newCorner.row() + (TILE_SIDE - drow);
        if (lines.get(nrow).charAt(ncol) == open) {
            turn('R');
            turn('R');
            col = ncol;
            row = nrow;
        }
    }

    static void turn(int newBlock, char dir) {
        Corner curCorner = corners.get(getCurrentBlock());
        Corner newCorner = corners.get(newBlock);

        int dcol = col - curCorner.col();
        int drow = row - curCorner.row();

        int ncol = newCorner.col() + drow;
        int nrow = newCorner.row() + dcol;
        if (lines.get(nrow).charAt(ncol) == open) {
            turn(dir);
            col = ncol;
            row = nrow;
        }
    }

    static void right() {
        int newBlock = getBlockByCoords(col + 1, row);
        if (newBlock != -1 && lines.get(row).charAt(col + 1) == open) {
            col++;
        } else if (newBlock == -1) {
            int currentBlock = getCurrentBlock();
            switch (currentBlock) {
                case 2: flip(5); break;
                case 3: turn(2, 'L'); break;
                case 5: flip(2); break;
                case 6: turn(5, 'L'); break;
                default:
                    throw new RuntimeException("idk right " + currentBlock + " " + col + " " + row);
            }
        }
    }

    static void down() {
        int newBlock = getBlockByCoords(col, row + 1);
        if (newBlock != -1 && lines.get(row + 1).charAt(col) == open) {
            row++;
        } else if (newBlock == -1) {
            int currentBlock = getCurrentBlock();
            switch (currentBlock) {
                case 2: turn(3, 'R'); break;
                case 5: turn(6, 'R'); break;
                case 6: cont(2); break;
                default:
                    throw new RuntimeException("idk down " + currentBlock + " " + col + " " + row);
            }
        }
    }

    static void left() {
        int newBlock = getBlockByCoords(col - 1, row);
        if (newBlock != -1 && lines.get(row).charAt(col - 1) == open) {
            col--;
        } else if (newBlock == -1) {
            int currentBlock = getCurrentBlock();
            switch (currentBlock) {
                case 1: flip(4); break;
                case 3: turn(4, 'L'); break;
                case 4: flip(1); break;
                case 6: turn(1, 'L'); break;
                default:
                    throw new RuntimeException("idk left " + currentBlock + " " + col + " " + row);
            }
        }
    }

    static void up() {
        int newBlock = getBlockByCoords(col, row - 1);
        if (newBlock != -1 && lines.get(row - 1).charAt(col) == open) {
            row--;
        } else if (newBlock == -1) {
            int currentBlock = getCurrentBlock();
            switch (currentBlock) {
                case 1: turn(6, 'R'); break;
                case 2: cont(6); break;
                case 4: turn(3, 'R'); break;
                default:
                    throw new RuntimeException("idk up " + currentBlock + " " + col + " " + row);
            }
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
