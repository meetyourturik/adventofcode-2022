package com.turik.adventofcode.day13;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day13Part1 {

    static char open = '[';
    static char close = ']';

    static int getCorrespondingClosedBracket(String line, int openPos) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(openPos);
        for (int i = openPos + 1; i < line.length(); i++) {
            if (line.charAt(i) == open) {
                stack.push(i);
            } else if (line.charAt(i) == close) {
                stack.pop();
                if (stack.isEmpty()) {
                    return i;
                }
            }
        }
        throw new RuntimeException("we were supposed to find a corresponding bracket!");
    }

    static List<Object> getPacketFromString(String line) {
        List<Object> packet = new ArrayList<>();

        if (!line.contains(String.valueOf(open))) { // we assume that all brackets are properly paired
            for (String number : line.split(",")) {
                if (!number.isEmpty())
                    packet.add(Integer.parseInt(number));
            }
            return packet;
        } else {
            int openPos = line.indexOf(open);
            int prevClose = -2;

            while (openPos != -1) {
                int closePos = getCorrespondingClosedBracket(line, openPos);

                if (openPos - prevClose > 2) { // there're some numbers between arrays
                    String numbersPart = line.substring(prevClose + 2, openPos - 1);
                    packet.addAll(getPacketFromString(numbersPart));
                }

                String arrayPart = line.substring(line.indexOf(open, openPos) + 1, closePos);
                packet.add(getPacketFromString(arrayPart));

                openPos = line.indexOf(open, closePos);

                prevClose = closePos;
            }

            if (prevClose < line.length() - 1) { // there're some more numbers after our array
                String numbersPart = line.substring(prevClose + 1);
                packet.addAll(getPacketFromString(numbersPart));
            }

            return packet;
        }
    }

    // 1 means right order, -1 - wrong
    static int compareObjects(Object left, Object right) { // Object is either int or List<Integer>
        if (left instanceof Integer && right instanceof Integer) {
            if ((int) left == (int) right) {
                return 0;
            }
            return (int) left < (int) right ? 1 : -1;
        } else if (left instanceof List && right instanceof Integer) {
            List<Object> tmpRight = new ArrayList<>();
            tmpRight.add(right);
            return compareObjects(left, tmpRight);
        } else if (left instanceof Integer && right instanceof List) {
            List<Object> tmpLeft = new ArrayList<>();
            tmpLeft.add(left);
            return compareObjects(tmpLeft, right);
        } else if (left instanceof List && right instanceof List){ // both are lists
            List<Object> leftList = (List<Object>) left;
            List<Object> rightList = (List<Object>) right;

            int minSize = Math.min(leftList.size(), rightList.size());

            for (int i = 0; i < minSize; i++) {
                int compare = compareObjects(leftList.get(i), rightList.get(i));
                if (compare != 0) {
                    return compare;
                }
            }

            if (leftList.size() == rightList.size()) {
                return 1;
            }

            return minSize == leftList.size() ? 1 : -1;
        } else {
            throw  new RuntimeException("should've processed all cases above");
        }
    }

    public static void main(String[] args) throws IOException, ScriptException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day13.txt"));

        List<Pair<List<Object>>> packets = new ArrayList<>();

        while (true) {
            String s1 = reader.readLine();
            if (s1 == null) {
                break;
            }
            String s2 = reader.readLine();
            reader.readLine(); // empty line

            Pair<List<Object>> pair = new Pair<>(
                    getPacketFromString(s1.substring(s1.indexOf(open) + 1, s1.lastIndexOf(close))),
                    getPacketFromString(s2.substring(s2.indexOf(open) + 1, s2.lastIndexOf(close)))
            );

            packets.add(pair);
        }

        int res = 0;

        for (int i = 0; i < packets.size(); i++) {
            int comparison = compareObjects(packets.get(i).left(), packets.get(i).right());
            if (comparison == 1) {
                res += i+1;
            }
        }

        System.out.println(res);
    }
}
