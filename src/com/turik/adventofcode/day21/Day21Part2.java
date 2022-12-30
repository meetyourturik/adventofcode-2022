package com.turik.adventofcode.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day21Part2 {

    static Pattern numberPattern = Pattern.compile("\\d+");

    public static void main(String[] args) throws IOException {

        Map<String, BigInteger> numbersOrig = new HashMap<>();
        Map<String, BigIntOperation> operationsOrig = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day21.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split(": ");
            String name = split[0];

            if (name.equals("humn")) {
                continue;
            }

            if (numberPattern.matcher(split[1]).matches()) {
                numbersOrig.put(name, new BigInteger(split[1]));
            } else {
                String[] split2 = split[1].split(" ");
                BigIntOperation operation = new BigIntOperation(split2[0], split2[2], split2[1]);
                operationsOrig.put(name, operation);
            }
        }

        // calculating what's possible to calculate

        while (true) {
            String toremove = null;
            for (Map.Entry<String, BigIntOperation> entry : operationsOrig.entrySet()) {
                String name = entry.getKey();
                BigIntOperation operation = entry.getValue();
                if (numbersOrig.containsKey(operation.monkey1) && numbersOrig.containsKey(operation.monkey2)) {
                    BigInteger value = operation.operator.apply(numbersOrig.get(operation.monkey1), numbersOrig.get(operation.monkey2));
                    toremove = name;
                    numbersOrig.put(name, value);
                    break;
                }
            }
            if (toremove != null) {
                operationsOrig.remove(toremove);
            } else {
                break;
            }
        }

        BigInteger start = BigInteger.ZERO;
        BigInteger end = new BigInteger(String.valueOf(Long.MAX_VALUE));

        while (!start.equals(end)) {

            BigInteger mid = (start.add(end)).divide(BigInteger.TWO);

            Map<String, BigInteger> numbers = new HashMap<>(numbersOrig);
            Map<String, BigIntOperation> operations = new HashMap<>(operationsOrig);
            numbers.put("humn", mid);

            String r1 = operations.get("root").monkey1;
            String r2 = operations.get("root").monkey2;

            while (!(numbers.containsKey(r1) && numbers.containsKey(r2))) {
                String toremove = null;
                for (Map.Entry<String, BigIntOperation> entry : operations.entrySet()) {
                    String name = entry.getKey();
                    BigIntOperation operation = entry.getValue();
                    if (numbers.containsKey(operation.monkey1) && numbers.containsKey(operation.monkey2)) {
                        BigInteger value = operation.operator.apply(numbers.get(operation.monkey1), numbers.get(operation.monkey2));
                        toremove = name;
                        numbers.put(name, value);
                        break;
                    }
                }
                if (toremove != null) {
                    operations.remove(toremove);
                }
            }

            BigInteger n1 = numbers.get(r1);
            BigInteger n2 = numbers.get(r2);

            // idk how it works, for test input works opposite approach (when n1 > n2 end = mid etc)
            if (n1.equals(n2)) {
                System.out.println("found: " + mid);
                return;
            } else if (n1.compareTo(n2) > 0) {
                start = mid;
            } else {
                end = mid;
            }
        }
    }
}
