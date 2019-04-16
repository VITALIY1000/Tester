package com.vitalytyrenko.tester;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        Button button1 = new Button(context);
        button1.setText("Choice 1");
        Button button2 = new Button(context);
        button2.setText("Choice 2");
        Button button3 = new Button(context);
        button3.setText("Choice 3");
        layout.addView(button1);
        layout.addView(button2);
        layout.addView(button3);

        return layout;
    }
}
