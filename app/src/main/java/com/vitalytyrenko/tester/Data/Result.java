package com.vitalytyrenko.tester.Data;

public class Result {
    private int score = 0;

    public void addScore() {
        this.score += 1;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}
