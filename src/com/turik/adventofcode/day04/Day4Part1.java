package com.turik.adventofcode.day04;

import com.turik.adventofcode.Utils;

import java.io.IOException;
import java.util.function.ToIntFunction;

public class Day4Part1 {

    public static void main(String[] args) throws IOException {
        ToIntFunction<String> function = (line) -> {
            String[] choices = line.split(",");

            String[] elf1boundaries = choices[0].split("-");
            String[] elf2boundaries = choices[1].split("-");

            int elf1left = Integer.parseInt(elf1boundaries[0]);
            int elf1right = Integer.parseInt(elf1boundaries[1]);

            int elf2left = Integer.parseInt(elf2boundaries[0]);
            int elf2right = Integer.parseInt(elf2boundaries[1]);

            if ((elf2left >= elf1left && elf2right <= elf1right) || (elf1left >= elf2left && elf1right <= elf2right)) {
                return 1;
            }

            return 0;
        };

        int result = Utils.processFile("./inputs/day04.txt", function);
        System.out.println(result);
    }
}
