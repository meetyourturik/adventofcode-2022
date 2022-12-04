package com.turik.adventofcode.day2;

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
