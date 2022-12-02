package com.turik.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day1.txt"));
        String line;

        int current = 0;
        Queue<Integer> calories = new PriorityQueue<>(Comparator.naturalOrder());

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                if (calories.isEmpty() || calories.size() < 3) {
                    calories.offer(current);
                } else if (current > calories.element()) {
                    calories.poll();
                    calories.offer(current);
                }
                current = 0;
            } else {
                current += Integer.parseInt(line);
            }
        }
        System.out.println(calories);
        System.out.println(calories.poll());
        System.out.println(calories.poll());
        System.out.println(calories.poll());
    }
}
