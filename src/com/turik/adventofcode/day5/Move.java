package com.turik.adventofcode.day5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Move {
    private static final Pattern pattern = Pattern.compile("^move\\s(?<move>\\d+)\\sfrom\\s(?<from>\\d+)\\sto\\s(?<to>\\d+)$");

    final int from;
    final int to;
    final int count;

    public Move(String line) {
        this(extractNumbersFromLine(line)[0], extractNumbersFromLine(line)[1], extractNumbersFromLine(line)[2]);
    }

    public Move(int from, int to, int count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    private static int[] extractNumbersFromLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int move = Integer.parseInt(matcher.group("move"));
            // counting from zero
            int from = Integer.parseInt(matcher.group("from")) - 1;
            int to = Integer.parseInt(matcher.group("to")) - 1;
            return new int[]{from,to,move};
        } else {
            throw new RuntimeException("move strings should be unified");
        }
    }
}
