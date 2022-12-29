package com.turik.adventofcode.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongBinaryOperator;
import java.util.regex.Pattern;

public class Day21Part2 {

    static Pattern numberPattern = Pattern.compile("\\d+");

    static LongBinaryOperator plus = (i1, i2) -> (i1 + i2);
    static LongBinaryOperator minus = (i1, i2) -> (i1 - i2);
    static LongBinaryOperator mult = (i1, i2) -> (i1 * i2);
    static LongBinaryOperator div = (i1, i2) -> (i1 / i2);


    public static void main(String[] args) throws IOException {

        Map<String, Long> numbers = new HashMap<>();
        Map<String, Operation> operations = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day21.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split(": ");
            String name = split[0];

            if (name.equals("humn")) {
                continue;
            }

            if (numberPattern.matcher(split[1]).matches()) {
                numbers.put(name, Long.parseLong(split[1]));
            } else {
                String[] split2 = split[1].split(" ");
                Operation operation = new Operation(split2[0], split2[2], split2[1]);
                operations.put(name, operation);
            }
        }

        while (!operations.isEmpty()) {
            String toremove = null;
            for (Map.Entry<String, Operation> entry : operations.entrySet()) {
                String name = entry.getKey();
                Operation operation = entry.getValue();
                if (numbers.containsKey(operation.monkey1) && numbers.containsKey(operation.monkey2)) {
                    long value = operation.operator.applyAsLong(numbers.get(operation.monkey1), numbers.get(operation.monkey2));
                    toremove = name;
                    numbers.put(name, value);
                    break;
                }
            }
            if (toremove != null) {
                operations.remove(toremove);
            }
        }

        System.out.println(numbers.get("root"));
    }
}
