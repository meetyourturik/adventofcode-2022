package com.turik.adventofcode.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;

public class Day11Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day11.txt"));
        String line;

        IntBinaryOperator plus = (i1, i2) -> i1 + i2;
        IntBinaryOperator minus = (i1, i2) -> i1 - i2;
        IntBinaryOperator mult = (i1, i2) -> i1 * i2;
        IntBinaryOperator div = (i1, i2) -> i1 / i2;
        Map<String, IntBinaryOperator> operations = new HashMap<>();
        operations.put("+", plus);
        operations.put("-", minus);
        operations.put("*", mult);
        operations.put("/", div);

        List<Monkey1> monkeys = new ArrayList<>();

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

            List<Integer> items = new ArrayList<>();

            for (String s : itemsStr) {
                items.add(Integer.parseInt(s.trim()));
            }

            final String first = operationsStr[3];
            final String op = operationsStr[4];
            final String second = operationsStr[5];

            IntFunction<Integer> operation = (item) -> {
                IntBinaryOperator operator = operations.get(op);
                if (first.equals("old") && second.equals("old")) {
                    return operator.applyAsInt(item, item);
                } else if (first.equals("old")) {
                    int i2 = Integer.parseInt(second);
                    return operator.applyAsInt(item, i2);
                } else if (second.equals("old")) {
                    int i1 = Integer.parseInt(first);
                    return operator.applyAsInt(i1, item);
                } else { // for some reason both are integers
                    int i1 = Integer.parseInt(first);
                    int i2 = Integer.parseInt(second);
                    return operator.applyAsInt(i1, i2);
                }
            };

            int test = Integer.parseInt(testStr[3]);
            int monkeyIfTrue = Integer.parseInt(ifTrueStr[5]);
            int monkeyIfFalse = Integer.parseInt(ifFalseStr[5]);
            Monkey1 monkey = new Monkey1(items, operation, test, monkeyIfTrue, monkeyIfFalse);
            monkeys.add(monkey);
        }

        for (int i = 1; i <= 20; i++) {
            for (Monkey1 monkey: monkeys) {
                List<Integer> items = monkey.getItems();
                for (int j = 0; j < items.size(); j++) {
                    int newItem = monkey.inspect(j);
                    int newMonkey = monkey.monkeyBusiness(newItem);
                    monkey.throwTo(monkeys.get(newMonkey), newItem);
                }
                monkey.removeAll();
            }
        }
        int[] counts = new int[monkeys.size()];

        for (int i = 0; i < monkeys.size(); i++) {
            counts[i] = monkeys.get(i).getInspectedCount();
        }

        Arrays.sort(counts);

        System.out.println((long) counts[counts.length - 2] * counts[counts.length - 1]);
    }
}
