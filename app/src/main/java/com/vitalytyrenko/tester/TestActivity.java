package com.vitalytyrenko.tester;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    private Context context = this;
    private Button checkButton;
    private Button nextButton;
    private LinearLayout container;    //consist elements (buttons, checkboxes, etc)
    private TextView caption;

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
    private Result result = new Result();           //saving score and question number
    private ArrayList<Integer> checkedItems;         //saving ids of checked
    //          elements (buttons, checkboxes, etc)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //initialization objects:
        container = findViewById(R.id.container);
        caption = findViewById(R.id.caption);
        checkButton = findViewById(R.id.check_button);
        nextButton = findViewById(R.id.next_button);
        nextButton.setVisibility(View.GONE);

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


    private void updateUI() {
        /*txt.setTitle(getResources().getString(R.string.question_)
                .concat(Integer.toString(questionNumber + 1))
                .concat(getResources().getString(R.string._of_)
                        .concat(Integer.toString(sharedTest.getNumberOfQuestions()))));*/
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
                break;
            }
            default: {

            }
        }
    }


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

                //добавляем пометку правильно/неправильно
                if (sharedTest.getQuestion(questionNumber).getCorrectAnswer().contains(i)) {
                    button.setTag("correct");
                } else {
                    button.setTag("incorrect");
                }

                return button;
            }
            case Question.TYPE_CHECKBOX: {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setLayoutParams(layoutParams);
                checkBox.setText(sharedTest.getQuestion(questionNumber).getAnswerText(i));
                //берем текст елемента по индексу
                checkBox.setOnClickListener(tmpElementsClickListener);
                //добавляем обработчик событий

                //добавляем пометку правильно/неправильно
                if (sharedTest.getQuestion(questionNumber).getCorrectAnswer().contains(i)) {
                    checkBox.setTag("correct");
                } else {
                    checkBox.setTag("incorrect");
                }

                return checkBox;
            }
            case Question.TYPE_INPUT: {
                break;
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
                break;
            }
            default: {

            }

        }
    }


    public void onCheckButtonClicked(View v) {
        //проверки, проверки, проверки...
        if (checkedItems != null && container.getChildCount() != 0) {
            if (!checkedItems.isEmpty()) {
                checkButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);

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

                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.green_100));

                                //если правильный чекбокс неотмечен
                                if (!((CheckBox) container.getChildAt(checkedItems.get(i)))
                                        .isChecked())
                                    //то вопрос не засчитывается
                                    isCorrect = false;

                            } else {
                                container.getChildAt(checkedItems.get(i)).setBackgroundColor(
                                        getResources().getColor(R.color.red_100));

                                //если неправильный чекбокс отмечен
                                if (((CheckBox) container.getChildAt(checkedItems.get(i)))
                                        .isChecked())
                                    //то вопрос не засчитывается
                                    isCorrect = false;
                            }

                            i++;
                        }

                        if (isCorrect)
                            result.addScore();

                        break;
                    }
                    case Question.TYPE_INPUT: {
                        break;
                    }
                    default: {

                    }
                }
            } else {
                toast(getResources().getString(R.string.no_checked_items));
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
            Intent intent = new Intent(context, ResultActivity.class);
            startActivity(intent);
            finish();
        }
    }


    //exit from application
    public void onExitButtonClicked(View v) {
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
