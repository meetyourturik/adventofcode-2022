package com.turik.adventofcode.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day13Part1And2 {

    static List<List<Object>> packets2 = new LinkedList<>();
    static char open = '[';
    static char close = ']';

    static void addPacket(List<Object> packet) {
        int i = 0;
        while (i < packets2.size() && compareObjects(packet, packets2.get(i)) == -1) {
            i++;
        }
        packets2.add(i, packet);
    }

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

    // 1 means right order, -1 - wrong, 0 - continue search
    static int compareObjects(Object left, Object right) { // Object is either int or List<Integer>
        if (left instanceof Integer && right instanceof Integer) {
            if ((int) left == (int) right) {
                return 0;
            }
            return Integer.signum((int) right - (int) left);
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

            return Integer.signum(rightList.size() - leftList.size());
        } else {
            throw  new RuntimeException("should've processed all cases above");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day13.txt"));

        List<Pair<List<Object>>> packets = new ArrayList<>();

        while (true) {
            String s1 = reader.readLine();
            if (s1 == null) {
                break;
            }
            String s2 = reader.readLine();
            reader.readLine(); // empty line

            List<Object> l1 = getPacketFromString(s1.substring(s1.indexOf(open) + 1, s1.lastIndexOf(close)));
            List<Object> l2 = getPacketFromString(s2.substring(s2.indexOf(open) + 1, s2.lastIndexOf(close)));

            // part 1
            Pair<List<Object>> pair = new Pair<>(l1, l2);
            packets.add(pair);

            // part 2
            addPacket(l1);
            addPacket(l2);
        }
        // part 1
        int res1 = 0;

        // part 2
        List<Object> d1 = getPacketFromString("[2]");
        List<Object> d2 = getPacketFromString("[6]");
        addPacket(d1);
        addPacket(d2);
        int res2 = 1;

        for (int i = 0; i < packets.size(); i++) {
            int comparison = compareObjects(packets.get(i).left(), packets.get(i).right());
            if (comparison == 1) {
                res1 += i+1;
            }
        }

        for (int i = 0; i < packets2.size(); i++) {
            List<Object> packet = packets2.get(i);
            if (packet.equals(d1)) {
                res2 *= (i+1);
            } else if (packet.equals(d2)) {
                res2 *= (i+1);
                break;
            }
        }

        System.out.printf("part 1 answer: %d\n", res1);
        System.out.printf("part 2 answer: %d", res2);
    }
}
