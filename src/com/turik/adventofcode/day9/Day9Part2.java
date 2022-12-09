package com.turik.adventofcode.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day9Part2 {

    private static Position[] chain;

    private static void moveHead(String way) {
        switch (way) {
            case "L":
                chain[0].decX();
                break;
            case "R":
                chain[0].incX();
                break;
            case "U":
                chain[0].incY();
                break;
            case "D":
                chain[0].decY();
                break;
            default:
                throw new RuntimeException("unknown way");
        }
    }

    private static Position moveLink(Position previous, Position current) {
        int directionX = 0;
        int directionY = 0;
        if (Math.abs(previous.getX() - current.getX()) <= 1 && Math.abs(previous.getY() - current.getY()) <= 1) {
            // no need to move
        } else {
            directionY = Integer.signum(previous.getY() - current.getY());
            directionX = Integer.signum(previous.getX() - current.getX());
        }
        return new Position(current.getX() + directionX, current.getY() + directionY);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day9.txt"));
        String line;

        Set<Position> positions = new HashSet<>();

        chain = new Position[10]; // actually, works with anu arbitrary length
        Arrays.fill(chain, new Position(0, 0));

        while ((line = reader.readLine()) != null) {
            String way = line.split(" ")[0];
            int count = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < count; i++) {
                moveHead(way);
                for (int j = 1; j < chain.length; j++) {
                    chain[j] = moveLink(chain[j-1], chain[j]);
                }
                positions.add(chain[chain.length - 1]);
            }
        }

        System.out.println(positions.size());
    }
}
