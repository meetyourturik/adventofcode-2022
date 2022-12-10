package com.turik.adventofcode.day02;

import java.util.Comparator;

class RPSComparator implements Comparator<RockPaperScissors> {

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
