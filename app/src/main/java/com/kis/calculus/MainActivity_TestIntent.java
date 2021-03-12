package com.kis.calculus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity_TestIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__test_intent);

        Button startCalc = findViewById(R.id.button);
        startCalc.setOnClickListener((view) -> startCalc());
    }

    private void startCalc() {
        Intent startCal = new Intent("com.kis.calculus.START_CALC");
        ActivityInfo ourCalcInfo = startCal.resolveActivityInfo(getPackageManager(), startCal.getFlags());
        if (ourCalcInfo != null) {
            startActivity(startCal);
        }
    }
}