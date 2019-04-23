package com.vitalytyrenko.tester.Data;

public class Result {
    private int score = 0;
    private int maxScore = 0;

    public Result(int maxScore) {
        this.maxScore = maxScore;
    }

    public void addScore() {
        this.score += 1;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
