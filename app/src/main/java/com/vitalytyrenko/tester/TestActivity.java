package com.vitalytyrenko.tester;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vitalytyrenko.tester.Data.Result;
import com.vitalytyrenko.tester.Data.Test;
import com.vitalytyrenko.tester.Data.Question;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    public static final String RESULT_OBJECT = "RESULT_OBJECT";

    private Context context = this;
    private Button checkButton;
    private Button nextButton;
    private Button finishButton;
    private LinearLayout container;    //consist elements (buttons, checkboxes, etc)
    private TextView caption;
    private Toolbar toolbar;

    private View.OnClickListener tmpElementsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tmpViewClicked(v);
        }
    };                                               //OnClickListener for generation elements
    //        (buttons, checkboxes, etc)

    private int questionNumber = 0;
    private Test sharedTest;                         //consist data of any questions
    private int mode;                                //mode of current question
    private Result result = new Result();            //saving score and question number
    private ArrayList<Integer> checkedItems;         //saving ids of checked
    //          elements (buttons, checkboxes, etc)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //initialization objects:
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        container = findViewById(R.id.container);
        caption = findViewById(R.id.caption);
        checkButton = findViewById(R.id.check_button);
        nextButton = findViewById(R.id.next_button);
        nextButton.setVisibility(View.GONE);
        finishButton = findViewById(R.id.finish_button);
        finishButton.setVisibility(View.GONE);

        //Get test object:
        Gson gson = new Gson();
        sharedTest = gson.fromJson(getIntent().getStringExtra(ChoiceActivity.JSON_OBJECT),
                Test.class);

        //start test at first question
        startTest();
    }


    private void startTest() {
        Question question = sharedTest.getQuestion(questionNumber);    //достаем 1 вопрос
        mode = question.getQuestionMode();                             //получаем тип вопроса
        updateUI();                           //обновляем пользовательский интерфейс

    }


    private void nextQuestion() {
        updateData();

        Question question = sharedTest.getQuestion(questionNumber);    //достаем 1 вопрос
        mode = question.getQuestionMode();                             //получаем тип вопроса
        updateUI();                           //обновляем пользовательский интерфейс

    }


    //Expandable
    private void updateUI() {
        getSupportActionBar().setTitle(getResources().getString(R.string.question_)
                .concat(Integer.toString(questionNumber + 1))
                .concat(getResources().getString(R.string._of_))
                .concat(Integer.toString(sharedTest.getNumberOfQuestions())));
        //добавляем текст на toolbar

        caption.setText(sharedTest.getQuestion(questionNumber).getQuestionText());

        switch (mode) {
            case Question.TYPE_BUTTON: {
                int i = 0;

                //создаем кнопки и добавляем в контейнер на экране
                while (i < sharedTest.getQuestion(questionNumber).getNumberOfAnswers()) {
                    container.addView(createView(i));
                    i++;
                }

                break;
            }
            case Question.TYPE_CHECKBOX: {
                int i = 0;

                //создаем кнопки и добавляем в контейнер на экране
                while (i < sharedTest.getQuestion(questionNumber).getNumberOfAnswers()) {
                    container.addView(createView(i));
                    i++;
                }

                break;
            }
            case Question.TYPE_INPUT: {
                container.addView(createView(0));
                break;
            }
            default: {

            }
        }
    }


    //Expandable
    private View createView(int i) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        switch (mode) {
            case Question.TYPE_BUTTON: {
                Button button = new Button(context);
                button.setLayoutParams(layoutParams);
                button.setText(sharedTest.getQuestion(questionNumber).getAnswerText(i));
                //берем текст елемента по индексу
                button.setOnClickListener(tmpElementsClickListener);
                //добавляем обработчик событий


                return button;
            }
            case Question.TYPE_CHECKBOX: {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setLayoutParams(layoutParams);
                checkBox.setText(sharedTest.getQuestion(questionNumber).getAnswerText(i));
                //берем текст елемента по индексу
                checkBox.setOnClickListener(tmpElementsClickListener);
                //добавляем обработчик событий


                return checkBox;
            }
            case Question.TYPE_INPUT: {
                EditText editText = new EditText(context);
                editText.setLayoutParams(layoutParams);
                editText.setOnClickListener(tmpElementsClickListener);

                return editText;
            }
        }
        return null;
    }


    private void updateData() {
        container.removeAllViews();
        questionNumber++;
        mode = sharedTest.getQuestion(questionNumber).getQuestionMode();
        checkedItems = null;
    }


    //Expandable
    public void tmpViewClicked(View v) {
        switch (mode) {
            case Question.TYPE_BUTTON: {
                int i = 0;

                //выделение кнопок при нажатии
                while (i < sharedTest.getQuestion(questionNumber).getNumberOfAnswers()) {
                    if (container.getChildAt(i) == v) {
                        setColorFilter(v, getResources().getColor(R.color.dark_btn_bgcolor));

                        checkedItems = new ArrayList<>();    //добавляем индекс нажатого view в
                        checkedItems.add(i);                 //                 checkedItems
                    } else {
                        setColorFilter(container.getChildAt(i),
                                getResources().getColor(R.color.default_btn_bgcolor));
                    }
                    i++;
                }

                break;
            }
            case Question.TYPE_CHECKBOX: {
                CheckBox c = (CheckBox) v;
                checkedItems = new ArrayList<>();
                int i = 0;

                while (i < sharedTest.getQuestion(questionNumber).getNumberOfAnswers()) {
                    if (c.isChecked()) {
                        checkedItems.add(i);
                    }

                    i++;
                }

                break;
            }
            case Question.TYPE_INPUT: {
                //ничего не надо делать
                break;
            }
            default: {

            }

        }
    }


    //Expandable
    public void onCheckButtonClicked(View v) {
        //проверки, проверки, проверки...
        if ((checkedItems != null && container.getChildCount() != 0 && !checkedItems.isEmpty())
                || mode == Question.TYPE_INPUT) {

            checkButton.setVisibility(View.GONE);

            if (questionNumber + 1 < sharedTest.getNumberOfQuestions()) {
                nextButton.setVisibility(View.VISIBLE);
            } else {
                finishButton.setVisibility(View.VISIBLE);
            }

            switch (mode) {
                case Question.TYPE_BUTTON: {
                    //если нажатая кнопка является правильным ответом
                    if (sharedTest.getQuestion(questionNumber).getCorrectAnswer()
                            .contains(checkedItems.get(0))) {

                        setColorFilter(container.getChildAt(checkedItems.get(0)),
                                getResources().getColor(R.color.green));
                        result.addScore();
                        toast(getResources().getString(R.string.correct));
                    } else {
                        setColorFilter(container.getChildAt(checkedItems.get(0)),
                                getResources().getColor(R.color.red));
                        toast(getResources().getString(R.string.incorrect));
                    }
                    break;
                }
                case Question.TYPE_CHECKBOX: {
                    int i = 0;
                    boolean isCorrect = true;

                    //проходим по каждому отмеченному чекбоксу
                    while (i < checkedItems.size()) {
                        if (sharedTest.getQuestion(questionNumber).getCorrectAnswer()
                                .contains(checkedItems.get(i))) {

                            //если правильный чекбокс отмечен
                            if (((CheckBox) container.getChildAt(checkedItems.get(i)))
                                    .isChecked()) {

                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.green_100));
                            } else {
                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.red_100));

                                isCorrect = false;
                            }

                        } else {


                            //если неправильный чекбокс неотмечен
                            if (!((CheckBox) container.getChildAt(checkedItems.get(i)))
                                    .isChecked()) {

                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.green_100));
                            } else {
                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.red_100));

                                isCorrect = false;
                            }

                        }

                        i++;
                    }

                    if (isCorrect)
                        result.addScore();

                    break;
                }
                case Question.TYPE_INPUT: {
                    String text = ((EditText) container.getChildAt(0))
                            .getText().toString();
                    int i = 0;
                    boolean correct = false;

                    while (i < sharedTest.getQuestion(questionNumber).getNumberOfAnswers()) {
                        if (text.equals(sharedTest.getQuestion(questionNumber)
                                .getAnswerText(i))) {
                            correct = true;
                            break;
                        }
                        i++;
                    }

                    if (correct) {
                        toast(getResources().getString(R.string.correct));
                        result.addScore();
                    } else {
                        toast(getResources().getString(R.string.incorrect));
                    }

                    break;
                }
                default: {

                }
            }
            checkedItems = null;
        } else {
            toast(getResources().getString(R.string.no_checked_items));
        }
    }


    public void onNextButtonClicked(View v) {
        nextButton.setVisibility(View.GONE);
        checkButton.setVisibility(View.VISIBLE);

        if (questionNumber + 1 < sharedTest.getNumberOfQuestions()) {
            nextQuestion();
        } else {
            //переходим на экран результатов
            Gson gson = new Gson();
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra(RESULT_OBJECT, gson.toJson(result));
            startActivity(intent);
            finish();
        }
    }


    //exit from application
    public void onExitButtonClicked(View v) {
        //то вызываем AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.you_sure)
                .setCancelable(true)
                .setNegativeButton(getResources().getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                .setPositiveButton(getResources().getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onFinishButtonClicked(View v) {
        //переходим на экран результатов
        Gson gson = new Gson();
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(RESULT_OBJECT, gson.toJson(result));
        startActivity(intent);
        finish();
    }


    //set a color filter
    public void setColorFilter(View view, int color) {
        view.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }


    //show a simple toast message
    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
