package com.turik.adventofcode.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3Part2 {

    private static void processBackpack(String backpack, int[] occs, int offset) {
        for (char c : backpack.toCharArray()) {
            occs[(c < 91) ? c - 38 : c - 96] |= offset;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day03.txt"));

        int res = 0;

        while (true) {
            String elf1 = reader.readLine();
            if (elf1 == null) {
                break;
            }
            String elf2 = reader.readLine();
            String elf3 = reader.readLine();

            int[] occs = new int[53];
            processBackpack(elf1, occs, 0B001);
            processBackpack(elf2, occs, 0B010);
            processBackpack(elf3, occs, 0B100);

            for (int i = 0; i < occs.length; i++) {
                if (occs[i] == 0B111) {
                    res += i;
                    break;
                }
            }

        }

        System.out.println(res);
    }
}
