package com.turik.adventofcode.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Day5Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day5.txt"));
        String line;

        Deque<String> cratesStrings = new ArrayDeque<>();

        while (!(line = reader.readLine()).isEmpty()) {
            cratesStrings.push(line);
        }

        String numbers = cratesStrings.pop();
        int count = numbers.charAt(numbers.length() - 1) - '0'; // number of stacks

        Deque[] crates = new Deque[count];

        while (!cratesStrings.isEmpty()) {
            String cratesString = cratesStrings.pop();
            for (int i = 0; i < count; i++) {
                if (crates[i] == null) {
                    crates[i] = new ArrayDeque<Character>();
                }
                char supply = cratesString.charAt(1 + 4 * i);
                if (supply != ' ') {
                    crates[i].push(supply);
                }
            }
        }

        while ((line = reader.readLine()) != null) {
            Move move = new Move(line);
            Deque<Character> tmp = new ArrayDeque<>();
            for (int i = 0; i < move.count; i++) {
                tmp.push((Character) crates[move.from].pop());
            }
            while (!tmp.isEmpty()) {
                crates[move.to].push(tmp.pop());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Deque crate : crates) {
            sb.append(crate.peek());
        }
        System.out.println(sb);
    }
}
