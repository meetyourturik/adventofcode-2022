package com.turik.adventofcode.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;

public class Day11Part2 {

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

        List<Monkey2> monkeys = new ArrayList<>();
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
                items.add((long) Integer.parseInt(s.trim()));
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
            Monkey2 monkey = new Monkey2(items, operation, test, monkeyIfTrue, monkeyIfFalse);
            monkeys.add(monkey);
        }


        for (int i = 1; i <= 10000; i++) {
            for (Monkey2 monkey: monkeys) {
                List<Long> items = monkey.getItems();
                for (int j = 0; j < items.size(); j++) {
                    long newItem = monkey.inspect(j);
                    newItem %= magicNumber;
                    int newMonkey = monkey.monkeyBusiness(newItem);
                    monkey.throwTo(monkeys.get(newMonkey), newItem);
                }
                monkey.removeAll();
            }
        }

        long[] counts = new long[monkeys.size()];
        for (int i = 0; i < monkeys.size(); i++) {
            counts[i] = monkeys.get(i).getInspectedCount();
        }

        System.out.println(Arrays.toString(counts));
        Arrays.sort(counts);

        System.out.println(counts[counts.length - 2] * counts[counts.length - 1]);
    }
}
