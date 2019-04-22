package com.vitalytyrenko.tester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.vitalytyrenko.tester.Data.Question;
import com.vitalytyrenko.tester.Data.Test;

import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    public final static String JSON_OBJECT = "JSON_OBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Gson gson = new Gson();
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(JSON_OBJECT, gson.toJson(createDemoTest()));
        startActivity(intent);
        finish();
    }

    private Test createDemoTest() {
        ArrayList<String> answerText0 = new ArrayList<>();
        answerText0.add("Choice 1");
        answerText0.add("Choice 2");
        answerText0.add("Choice 3");

        ArrayList<String> answerText1 = new ArrayList<>();
        answerText1.add("Choice 1");
        answerText1.add("Choice 2");
        answerText1.add("Choice 3");
        answerText1.add("Choice 4");

        ArrayList<String> answerText2 = new ArrayList<>();
        answerText2.add("Choice 1");
        answerText2.add("Choice 2");
        answerText2.add("Choice 3");

        String questionText0 = "Choose second answer:";
        String questionText1 = "Choose first and third answer:";
        String questionText2 = "Please enter \"Choice 1\":";

        ArrayList<Integer> ca0 = new ArrayList<>();
        ca0.add(1);

        ArrayList<Integer> ca1 = new ArrayList<>();
        ca1.add(0);
        ca1.add(2);

        ArrayList<Integer> ca2 = new ArrayList<>();

        ArrayList<Question> questions = new ArrayList<>();
        Question question0 = new Question(Question.TYPE_BUTTON, questionText0, answerText0, ca0);
        questions.add(question0);

        Question question1 = new Question(Question.TYPE_CHECKBOX, questionText1, answerText1, ca1);
        questions.add(question1);

        Question question2 = new Question(Question.TYPE_INPUT, questionText2, answerText2, ca2);
        questions.add(question2);

        String name = "MyFirstTest";

        return new Test(questions, name);
    }
}
