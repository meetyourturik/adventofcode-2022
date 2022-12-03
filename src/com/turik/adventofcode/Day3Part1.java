package com.turik.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day3.txt"));

        String line;
        int res = 0;
        while ((line = reader.readLine()) != null) {
            int[] occs = new int[53];
            String b1 = line.substring(0, line.length() / 2);
            String b2 = line.substring(line.length() / 2);

            for (char c : b1.toCharArray()) {
                int index = (c < 91) ? c - 38 : c - 96;
                occs[index]++;
            }

            for (char c: b2.toCharArray()) {
                int index = (c < 91) ? c - 38 : c - 96;
                if (occs[index] > 0) {
                    System.out.printf("%s %s %s\n", b1, b2, c);
                    res += index;
                    break;
                }
            }
        }

        System.out.println(res);
    }
}
