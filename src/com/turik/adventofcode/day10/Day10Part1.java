package com.turik.adventofcode.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10Part1 {

    private static int cycle = 0;
    private static int registerX = 1;
    private static int res = 0;

    private static void cycle() {
        cycle++;
        if (cycle % 40 == 20) {
            res += cycle * registerX;
        }
    }

    public static void main(String[] args) throws IOException {
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

        System.out.println(res);
    }
}
