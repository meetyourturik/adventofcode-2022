package com.turik.adventofcode.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day17Part2Help {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day17help.txt"));
        String line;

        long cycleSum = 0;
        long tailSum = 0;
        int i = 0;

        while ((line = reader.readLine()) != null) {
            long num = Long.parseLong(line);
            cycleSum += num;
            if (i < 205) {
                tailSum += num;
            }
            i++;
        }

        System.out.println(cycleSum);
        System.out.println(tailSum);
    }
}
