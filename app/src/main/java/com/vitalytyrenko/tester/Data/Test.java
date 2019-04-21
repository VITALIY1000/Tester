package com.vitalytyrenko.tester.Data;

import java.util.ArrayList;

public class Test {
    private ArrayList<Question> arrayOfQuestions;
    private String name;

    public Test(ArrayList<Question> arrayOfQuestions, String name) {
        this.arrayOfQuestions = arrayOfQuestions;
        this.name = name;
    }

    public int getNumberOfQuestions() {
        return arrayOfQuestions.size();
    }

    public String getName() {
        return name;
    }

    public Question getQuestion(int i) {
        return arrayOfQuestions.get(i);
    }
}
