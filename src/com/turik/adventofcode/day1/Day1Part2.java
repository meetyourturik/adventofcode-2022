package com.turik.adventofcode.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day1Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day01.txt"));
        String line;

        int current = 0;
        Queue<Integer> calories = new PriorityQueue<>(3, Comparator.naturalOrder());

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                // maintaining size of queue equal to 3
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
        System.out.println(calories.poll() + calories.poll() + calories.poll());
    }
}
