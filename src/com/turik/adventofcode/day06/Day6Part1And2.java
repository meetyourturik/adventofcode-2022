package com.turik.adventofcode.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day6Part1And2 {

    private static int solveForMessageLength(String input, int messageLength) {
        Map<Character, Integer> chars = new HashMap<>();

        for (int i = 0; i < messageLength; i++) {
            char c = input.charAt(i);
            int value = chars.getOrDefault(c, 0) + 1;
            chars.put(c, value);
        }

        if (chars.keySet().size() == messageLength) {
            return messageLength;
        }

        for (int i = messageLength; i < input.length(); i++) {
            char c = input.charAt(i - messageLength);
            int value = chars.get(c);

            if (value > 1) {
                chars.put(c, value - 1);
            } else {
                chars.remove(c);
            }

            int newValue = chars.getOrDefault(input.charAt(i), 0) + 1;
            chars.put(input.charAt(i), newValue);

            if (chars.keySet().size() == messageLength) {
                return i + 1;
            }
        }

        throw new RuntimeException("answer should exist");
    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day06.txt"));
        String line = reader.readLine();

        System.out.printf("part 1 answer: %d\n", solveForMessageLength(line, 4));
        System.out.printf("part 2 answer: %d", solveForMessageLength(line, 14));
    }
}
