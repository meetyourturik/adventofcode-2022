package com.turik.adventofcode.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day10Part2 {
    private static int cycle = 0;
    private static int registerX = 1;

    private static char HASHTAG = '#';
    private static char[][] CRT = new char[6][40];

    private static void cycle() {
        int posX = cycle % 40;
        int posY = cycle / 40;

        if (Math.abs(posX - registerX) <= 1) {
            CRT[posY][posX] = HASHTAG;
        }
        cycle++;
    }

    public static void main(String[] args) throws IOException {
        for (char[] row : CRT) {
            Arrays.fill(row, '.');
        }

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day10.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String command = line.split(" ")[0];
            if (command.equals("noop")) {
                cycle();
            } else { // addx
                int value = Integer.parseInt(line.split(" ")[1]);
                cycle();
                cycle();
                registerX += value;
            }
        }

        for (char[] row : CRT) {
            System.out.println(new String(row));
        }
    }
}
