package com.vitalytyrenko.tester;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vitalytyrenko.tester.Data.Result;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTV;
    private Result sharedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Gson gson = new Gson();
        sharedResult = gson.fromJson(getIntent().getStringExtra(TestActivity.RESULT_OBJECT),
                Result.class);
        resultTV = findViewById(R.id.result_text_view);

        String tmp_text = getResources().getString(R.string.you_scored_)
                .concat(Integer.toString(sharedResult.getScore()))
                .concat(getResources().getString(R.string._out_of_))
                .concat(Integer.toString(sharedResult.getMaxScore()))
                .concat(getResources().getString(R.string._points));
        resultTV.setText(tmp_text);

    }

    public void buttonToMainMenuClicked(View v) {
        finish();
    }
}
