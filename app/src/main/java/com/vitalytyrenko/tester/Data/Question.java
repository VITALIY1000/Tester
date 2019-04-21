package com.vitalytyrenko.tester.Data;

import java.util.ArrayList;

public class Question {
    public static final int TYPE_BUTTON = 0;
    public static final int TYPE_CHECKBOX = 1;
    public static final int TYPE_INPUT = 2;

    private int questionMode;
    private String questionText;
    private ArrayList<String> answerText;
    private ArrayList<Integer> correctAnswer;

    public Question(int questionMode, String questionText, ArrayList<String> answerText,
                    ArrayList<Integer> correctAnswer) {
        this.questionMode = questionMode;
        this.questionText = questionText;
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionMode() {
        return questionMode;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerText(int i) {
        return answerText.get(i);
    }

    public int getNumberOfAnswers() {
        return answerText.size();
    }

    public ArrayList<Integer> getCorrectAnswer() {
        return correctAnswer;
    }
}
