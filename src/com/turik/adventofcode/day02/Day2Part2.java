package com.turik.adventofcode.day02;

import com.turik.adventofcode.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

public class Day2Part2 {

    public static void main(String[] args) throws IOException {
        Map<String, RockPaperScissors> choiceMap = new HashMap<>();
        choiceMap.put("A", RockPaperScissors.ROCK);
        choiceMap.put("B", RockPaperScissors.PAPER);
        choiceMap.put("C", RockPaperScissors.SCISSORS);

        ToIntFunction<String> function = (line) -> {
            String[] choices = line.split(" ");
            RockPaperScissors opponent = choiceMap.get(choices[0]);
            String strategy = choices[1];
            int res = 0;
            if (strategy.equals("Z")) { // win
                res += 6;
                res += opponent.winningScore;
            } else if (strategy.equals("Y")) { // draw
                res += 3;
                res += opponent.score;
            } else { // lose
                res += opponent.loosingScore;
            }
            return res;
        };

        int result = Utils.processFile("./inputs/day02.txt", function);
        System.out.println(result);
    }
}
