package com.turik.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day3.txt"));

        int res = 0;
        while (true) {

            String elf1 = reader.readLine();
            if (elf1 == null) {
                break;
            }
            String elf2 = reader.readLine();
            String elf3 = reader.readLine();

            int[] occs = new int[53];

            for (char c : elf1.toCharArray()) {
                occs[(c < 91) ? c - 38 : c - 96] |= 0B001;
            }

            for (char c : elf2.toCharArray()) {
                occs[(c < 91) ? c - 38 : c - 96] |= 0B010;
            }

            for (char c : elf3.toCharArray()) {
                occs[(c < 91) ? c - 38 : c - 96] |= 0B100;
            }

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
