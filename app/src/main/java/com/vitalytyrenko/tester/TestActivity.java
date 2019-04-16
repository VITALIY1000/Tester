package com.vitalytyrenko.tester;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        testFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.run_fragment_container, testFragment);
        fragmentTransaction.commit();*/

    }
}
