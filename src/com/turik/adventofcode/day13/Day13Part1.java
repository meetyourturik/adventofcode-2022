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
        // idk some implementation
        // example line: [1,[2,[3,[4,[5,6,7]]]],8,9]
        List<Object> packet = new ArrayList<>();

        if (!line.contains(String.valueOf(open))) { // we assume that all brackets are properly paired
            for (String number : line.split(",")) {
                if (!number.isEmpty())
                    packet.add(Integer.parseInt(number));
            }
            return packet;
        } else {
            int openPos = line.indexOf(open);
            int prevClose = 0;

            while (openPos != -1) {
                int closePos = getCorrespondingClosedBracket(line, openPos);

                if (openPos > 0) {
                    String numbersPart = line.substring(0, openPos - 1);
                    packet.addAll(getPacketFromString(numbersPart));
                }

                if (openPos - prevClose > 2) { // there're some numbers between arrays
                    String numbersPart = line.substring(prevClose + 1, openPos - 1);
                    packet.addAll(getPacketFromString(numbersPart));
                }

                String arrayPart = line.substring(line.indexOf(open, openPos) + 1, closePos);
                packet.add(getPacketFromString(arrayPart));

                openPos = line.indexOf(open, closePos);

                if (closePos < line.length() - 1) { // there're some more numbers after our array
                    int tmpEnd = openPos != -1 ? openPos : line.length();
                    String numbersPart = line.substring(closePos + 1, tmpEnd);
                    packet.addAll(getPacketFromString(numbersPart));
                }

                prevClose = closePos;
            }

            return packet;
        }
    }

    static int comparePair(Pair<List<Object>> pair) {
        List<Object> left = pair.left();
        List<Object> right = pair.right();
        // again some impl that probably is recursive and returns 1 if left < right, -1 otherwise
        return 1;
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


            System.out.println(pair.left() + " " + pair.right() + "\n");

            packets.add(pair);
        }

        int res = 0;

        for (int i = 0; i < packets.size(); i++) {
            int comparison = comparePair(packets.get(i));
            if (comparison == 1) {
                res += i+1;
            }
        }

        System.out.println(res);
    }
}
