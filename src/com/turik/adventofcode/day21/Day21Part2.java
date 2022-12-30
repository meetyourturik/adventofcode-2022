package com.turik.adventofcode.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day21Part2 {

    static Pattern numberPattern = Pattern.compile("\\d+");

    public static void main(String[] args) throws IOException {

        Map<String, Long> numbersOrig = new HashMap<>();
        Map<String, Operation> operationsOrig = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day21.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split(": ");
            String name = split[0];

            if (name.equals("humn")) {
                continue;
            }

            if (numberPattern.matcher(split[1]).matches()) {
                numbersOrig.put(name, Long.parseLong(split[1]));
            } else {
                String[] split2 = split[1].split(" ");
                Operation operation = new Operation(split2[0], split2[2], split2[1]);
                operationsOrig.put(name, operation);
            }
        }

        /**
         * again, found number from which to start more or less manually :shrug:
         */

        for (long i = 3_759_566_892_000L; i < Long.MAX_VALUE; i++) {

            Map<String, Long> numbers = new HashMap<>(numbersOrig);
            Map<String, Operation> operations = new HashMap<>(operationsOrig);
            numbers.put("humn", i);

            String r1 = operations.get("root").monkey1;
            String r2 = operations.get("root").monkey2;

            while (!(numbers.containsKey(r1) && numbers.containsKey(r2))) {
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

            // System.out.println(i + ": " + numbers.get(r1) + " " + numbers.get(r2));

            if (Objects.equals(numbers.get(r1), numbers.get(r2))) {
                System.out.println("found: " + i);
                break;
            }
        }
    }
}
