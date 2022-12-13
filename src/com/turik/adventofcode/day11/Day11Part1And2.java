package com.turik.adventofcode.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;

public class Day11Part1And2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day11.txt"));
        String line;

        LongBinaryOperator plus = (i1, i2) -> i1 + i2;
        LongBinaryOperator minus = (i1, i2) -> i1 - i2;
        LongBinaryOperator mult = (i1, i2) -> i1 * i2;
        LongBinaryOperator div = (i1, i2) -> i1 / i2;
        Map<String, LongBinaryOperator> operations = new HashMap<>();
        operations.put("+", plus);
        operations.put("-", minus);
        operations.put("*", mult);
        operations.put("/", div);

        List<Monkey> monkeys1 = new ArrayList<>();
        List<Monkey> monkeys2 = new ArrayList<>();

        int magicNumber = 1;

        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            String itemsString = reader.readLine();
            itemsString = itemsString.trim().replace("Starting items: ", "");
            String[] itemsStr = itemsString.trim().split(",");
            String[] operationsStr = reader.readLine().trim().split(" ");
            String[] testStr = reader.readLine().trim().split(" ");
            String[] ifTrueStr = reader.readLine().trim().split(" ");
            String[] ifFalseStr = reader.readLine().trim().split(" ");
            reader.readLine(); // empty line

            List<Long> items = new ArrayList<>();

            for (String s : itemsStr) {
                items.add(Long.parseLong(s.trim()));
            }

            final String first = operationsStr[3];
            final String op = operationsStr[4];
            final String second = operationsStr[5];

            LongFunction<Long> operation = (item) -> {
                LongBinaryOperator operator = operations.get(op);
                if (first.equals("old") && second.equals("old")) {
                    return operator.applyAsLong(item, item);
                } else if (first.equals("old")) {
                    long i2 = Long.parseLong(second);
                    return operator.applyAsLong(item, i2);
                } else if (second.equals("old")) {
                    long i1 = Long.parseLong(first);
                    return operator.applyAsLong(i1, item);
                } else { // for some reason both are integers
                    long i1 = Long.parseLong(first);
                    long i2 = Long.parseLong(second);
                    return operator.applyAsLong(i1, i2);
                }
            };

            int test = Integer.parseInt(testStr[3]);
            magicNumber *= test;
            int monkeyIfTrue = Integer.parseInt(ifTrueStr[5]);
            int monkeyIfFalse = Integer.parseInt(ifFalseStr[5]);
            Monkey monkey1 = new Monkey(items, operation, test, monkeyIfTrue, monkeyIfFalse);
            monkeys1.add(monkey1);
            Monkey monkey2 = new Monkey(new ArrayList<>(items), operation, test, monkeyIfTrue, monkeyIfFalse);
            monkeys2.add(monkey2);
        }

        // part 1
        for (int i = 1; i <= 20; i++) {
            for (Monkey monkey: monkeys1) {
                List<Long> items = monkey.getItems();
                for (int j = 0; j < items.size(); j++) {
                    long newItem = monkey.inspect(j);
                    newItem /= 3; // relief
                    int newMonkey = monkey.monkeyBusiness(newItem);
                    monkey.throwTo(monkeys1.get(newMonkey), newItem);
                }
                monkey.removeAll();
            }
        }

        // part 2
        for (int i = 1; i <= 10_000; i++) {
            for (Monkey monkey: monkeys2) {
                List<Long> items = monkey.getItems();
                for (int j = 0; j < items.size(); j++) {
                    long newItem = monkey.inspect(j);
                    newItem %= magicNumber; // relief
                    int newMonkey = monkey.monkeyBusiness(newItem);
                    monkey.throwTo(monkeys2.get(newMonkey), newItem);
                }
                monkey.removeAll();
            }
        }

        long[] counts1 = new long[monkeys1.size()];
        long[] counts2 = new long[monkeys2.size()];

        for (int i = 0; i < monkeys1.size(); i++) {
            counts1[i] = monkeys1.get(i).getInspectedCount();
            counts2[i] = monkeys2.get(i).getInspectedCount();
        }

        Arrays.sort(counts1);
        Arrays.sort(counts2);

        System.out.printf("part 1 answer: %d\n", counts1[counts1.length - 2] * counts1[counts1.length - 1]);
        System.out.printf("part 2 answer: %d", counts2[counts2.length - 2] * counts2[counts2.length - 1]);
    }
}
