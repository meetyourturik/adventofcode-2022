package com.turik.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day2 {

    enum RockPaperScissors {
        ROCK(1, 2, 3),
        PAPER(2, 3, 1),
        SCISSORS(3, 1, 2);

        public final int score; // how many points gives this choice
        public final int winningScore; // how many points gives winning AGAINST this choice
        public final int loosingScore; // how many points gives loosing AGAINST this choice

        RockPaperScissors(int score, int winningScore, int loosingScore) {
            this.score = score;
            this.winningScore = winningScore;
            this.loosingScore = loosingScore;
        }
    }

    // used for part one of the problem
    static class RPSComparator implements Comparator<RockPaperScissors> {

        @Override
        public int compare(RockPaperScissors o1, RockPaperScissors o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == RockPaperScissors.ROCK) {
                return o2 == RockPaperScissors.SCISSORS ? 1 : -1;
            } else if (o1 == RockPaperScissors.PAPER) {
                return o2 == RockPaperScissors.ROCK ? 1 : -1;
            } else { // SCISSORS
                return o2 == RockPaperScissors.PAPER ? 1 : -1;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        RPSComparator comparator = new RPSComparator();

        Map<String, RockPaperScissors> choiceMap = new HashMap<>();
        choiceMap.put("A", RockPaperScissors.ROCK);
        choiceMap.put("B", RockPaperScissors.PAPER);
        choiceMap.put("C", RockPaperScissors.SCISSORS);
        // for part 1
        choiceMap.put("X", RockPaperScissors.ROCK);
        choiceMap.put("Y", RockPaperScissors.PAPER);
        choiceMap.put("Z", RockPaperScissors.SCISSORS);

        int result1 = 0;
        int result2 = 0;

        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day2.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] choices = line.split(" ");
            RockPaperScissors opponent = choiceMap.get(choices[0]);
            String strategy = choices[1];
            RockPaperScissors you = choiceMap.get(strategy); // again, part 1

            int comparingRes = comparator.compare(you, opponent);
            result1 += you.score;

            if (comparingRes > 0) { // win
                result1 += 6;
            } else if (comparingRes == 0) { // draw
                result1 += 3;
            } else { // lose
                result1 += 0;
            }

            if (strategy.equals("Z")) { // win
                result2 += 6;
                result2 += opponent.winningScore;
            } else if (strategy.equals("Y")) { // draw
                result2 += 3;
                result2 += opponent.score;
            } else { // lose
                result2 += opponent.loosingScore;
            }
        }

        System.out.println(result1);
        System.out.println(result2);
    }
}
