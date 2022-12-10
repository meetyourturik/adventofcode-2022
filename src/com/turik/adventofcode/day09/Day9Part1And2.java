package com.turik.adventofcode.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day9Part1And2 {

    private static void makeMove(Position[] chain, String way, Set<Position> positions) {
        moveHead(chain[0] ,way);
        for (int j = 1; j < chain.length; j++) {
            chain[j] = moveLink(chain[j-1], chain[j]);
        }
        positions.add(chain[chain.length - 1]);
    }

    private static void moveHead(Position position, String way) {
        switch (way) {
            case "L":
                position.decX();
                break;
            case "R":
                position.incX();
                break;
            case "U":
                position.incY();
                break;
            case "D":
                position.decY();
                break;
            default:
                throw new RuntimeException("unknown way: " + way);
        }
    }

    private static Position moveLink(Position previous, Position current) {
        int directionX = 0;
        int directionY = 0;
        if (Math.abs(previous.getX() - current.getX()) > 1 || Math.abs(previous.getY() - current.getY()) > 1) {
            directionY = Integer.signum(previous.getY() - current.getY());
            directionX = Integer.signum(previous.getX() - current.getX());
        }
        return new Position(current.getX() + directionX, current.getY() + directionY);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day09.txt"));
        String line;

        Set<Position> positions1 = new HashSet<>();
        Set<Position> positions2 = new HashSet<>();

        Position[] chain1 = new Position[2];
        Arrays.fill(chain1, new Position(0, 0));

        Position[] chain2 = new Position[10];
        Arrays.fill(chain2, new Position(0, 0));

        while ((line = reader.readLine()) != null) {
            String way = line.split(" ")[0];
            int count = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < count; i++) {
                makeMove(chain1, way, positions1);
                makeMove(chain2, way, positions2);
            }
        }

        System.out.printf("part 1 answer: %d\n", positions1.size());
        System.out.printf("part 2 answer: %d", positions2.size());
    }
}
