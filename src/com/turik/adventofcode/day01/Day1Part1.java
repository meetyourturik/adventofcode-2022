package com.turik.adventofcode.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day01.txt"));
        String line;

        int current = 0;
        int max = 0;

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                max = Math.max(max, current);
                current = 0;
            } else {
                current += Integer.parseInt(line);
            }
        }

        System.out.println(max);
    }
}
