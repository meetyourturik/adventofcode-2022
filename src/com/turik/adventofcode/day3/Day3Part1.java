package com.turik.adventofcode.day3;

import com.turik.adventofcode.Utils;

import java.io.IOException;
import java.util.function.ToIntFunction;

public class Day3Part1 {

    public static void main(String[] args) throws IOException {
        ToIntFunction<String> function = (line) -> {
            int[] occs = new int[53];
            String b1 = line.substring(0, line.length() / 2);
            String b2 = line.substring(line.length() / 2);

            for (char c : b1.toCharArray()) {
                occs[(c < 91) ? c - 38 : c - 96]++;
            }

            for (char c: b2.toCharArray()) {
                int index = (c < 91) ? c - 38 : c - 96;
                if (occs[index] > 0) {
                    return index;
                }
            }

            throw new RuntimeException("there's supposed to be exactly one shared element");
        };

        int result = Utils.processFile("./inputs/day3.txt", function);
        System.out.println(result);
    }
}
