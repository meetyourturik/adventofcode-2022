package com.turik.adventofcode.day02;

import com.turik.adventofcode.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

public class Day2Part1 {

    public static void main(String[] args) throws IOException {
        RPSComparator comparator = new RPSComparator();

        Map<String, RockPaperScissors> choiceMap = new HashMap<>();
        choiceMap.put("A", RockPaperScissors.ROCK);
        choiceMap.put("B", RockPaperScissors.PAPER);
        choiceMap.put("C", RockPaperScissors.SCISSORS);

        choiceMap.put("X", RockPaperScissors.ROCK);
        choiceMap.put("Y", RockPaperScissors.PAPER);
        choiceMap.put("Z", RockPaperScissors.SCISSORS);

        ToIntFunction<String> function = (line) -> {
            String[] choices = line.split(" ");
            RockPaperScissors opponent = choiceMap.get(choices[0]);
            RockPaperScissors you = choiceMap.get(choices[1]);

            int comparingRes = comparator.compare(you, opponent);
            int res = you.score;

            if (comparingRes > 0) { // win
                res += 6;
            } else if (comparingRes == 0) { // draw
                res += 3;
            } else { // lose
                res += 0;
            }
            return res;
        };

        int result = Utils.processFile("./inputs/day02.txt", function);
        System.out.println(result);
    }
}
