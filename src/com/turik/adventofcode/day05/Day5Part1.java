package com.turik.adventofcode.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Day5Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day05.txt"));
        String line;

        Deque<String> cratesStrings = new ArrayDeque<>();

        while (!(line = reader.readLine()).isEmpty()) {
            cratesStrings.push(line);
        }

        String numbers = cratesStrings.pop();
        int count = numbers.charAt(numbers.length() - 1) - '0'; // number of stacks

        Deque<Character>[] crates = new Deque[count];

        while (!cratesStrings.isEmpty()) {
            String cratesString = cratesStrings.pop();
            for (int i = 0; i < count; i++) {
                if (crates[i] == null) {
                    crates[i] = new ArrayDeque<>();
                }
                char supply = cratesString.charAt(1 + 4 * i);
                if (supply != ' ') {
                    crates[i].push(supply);
                }
            }
        }

        while ((line = reader.readLine()) != null) {
            Move move = new Move(line);
            for (int i = 0; i < move.count; i++) {
                crates[move.to].push(crates[move.from].pop());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Deque<Character> crate : crates) {
            sb.append(crate.peek());
        }
        System.out.println(sb);
    }
}
