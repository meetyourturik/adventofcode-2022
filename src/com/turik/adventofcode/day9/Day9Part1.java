package com.turik.adventofcode.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day9Part1 {

    private static Position head;
    private static Position tail;

    private static void moveHead(String way) {
        switch (way) {
            case "L":
                head.decX();
                break;
            case "R":
                head.incX();
                break;
            case "U":
                head.incY();
                break;
            case "D":
                head.decY();
                break;
            default:
                throw new RuntimeException("unknown way");
        }
    }

    private static Position moveTail() {
        int directionX = 0;
        int directionY = 0;
        if (Math.abs(head.getX() - tail.getX()) <= 1 && Math.abs(head.getY() - tail.getY()) <= 1) {
            // no need to move
        } else {
            directionY = Integer.signum(head.getY() - tail.getY());
            directionX = Integer.signum(head.getX() - tail.getX());
        }
        return new Position(tail.getX() + directionX, tail.getY() + directionY);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day9.txt"));
        String line;

        Set<Position> positions = new HashSet<>();

        Position start = new Position(0, 0);
        head = new Position(0, 0);
        tail = start;

        while ((line = reader.readLine()) != null) {
            String way = line.split(" ")[0];
            int count = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < count; i++) {
                moveHead(way);
                tail = moveTail();
                positions.add(tail);
            }
        }

        System.out.println(positions.size());
    }
}
