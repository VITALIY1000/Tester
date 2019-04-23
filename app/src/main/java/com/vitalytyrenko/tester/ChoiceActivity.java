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
        ArrayList<Question> questions = new ArrayList<>();


        ArrayList<String> answerText0 = new ArrayList<>();
        answerText0.add("\"Асканія-Нова\"");
        answerText0.add("\"Карпатський\"");
        answerText0.add("\"Медобори\"");
        String questionText0 = "Який заповідник є найбільшим в Україні?";
        ArrayList<Integer> ca0 = new ArrayList<>();
        ca0.add(1);
        Question question0 = new Question(Question.TYPE_BUTTON, questionText0, answerText0, ca0);
        questions.add(question0);

        ArrayList<String> answerText1 = new ArrayList<>();
        answerText1.add("Настання дня після ночі");
        answerText1.add("Перетворення води у лід при 0 градусах за Цельсієм");
        answerText1.add("Випадіння аверсу при підкиданні монетки");
        answerText1.add("Після 25 травня - 26 травня");
        answerText1.add("Випадіння 3 очок на гральному кубику");
        String questionText1 = "Оберіть події, що є випадковими:";
        ArrayList<Integer> ca1 = new ArrayList<>();
        ca1.add(2);
        ca1.add(4);
        Question question1 = new Question(Question.TYPE_CHECKBOX, questionText1, answerText1, ca1);
        questions.add(question1);

        ArrayList<String> answerText2 = new ArrayList<>();
        answerText2.add("Інформатика");
        answerText2.add("інформатика");
        String questionText2 = "Як називається теоретична та прикладна дисципліна," +
                " що вивчає структуру і загальні властивості інформації," +
                " а також методи і засоби її створення, перетворення, зберігання," +
                " передачі та використання в різних галузях людської діяльності?";
        ArrayList<Integer> ca2 = new ArrayList<>();
        Question question2 = new Question(Question.TYPE_INPUT, questionText2, answerText2, ca2);
        questions.add(question2);



        String name = "Демо-тест";
        return new Test(questions, name);
    }
}
