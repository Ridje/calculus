package com.kis.calculus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultView;
    private CalculusState calculusState;
    private HashSet<String> operandParts;
    private HashSet<String> operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultView = findViewById(R.id.calcus_state);
        calculusState = new CalculusState(resultView);
        initializeKeyHashes();
        setListenerForButtons();
    }

    private void initializeKeyHashes() {
        operandParts = new HashSet<String>() {{
            add(getResources().getString(R.string._0));
            add(getResources().getString(R.string._1));
            add(getResources().getString(R.string._2));
            add(getResources().getString(R.string._3));
            add(getResources().getString(R.string._4));
            add(getResources().getString(R.string._5));
            add(getResources().getString(R.string._6));
            add(getResources().getString(R.string._7));
            add(getResources().getString(R.string._8));
            add(getResources().getString(R.string._9));
            add(getResources().getString(R.string.dot_symbol));
        }};

        operations = new HashSet<String>() {{
            add(getResources().getString(R.string.divide_symbol));
            add(getResources().getString(R.string.multiply_symbol));
            add(getResources().getString(R.string.plus_symbol));
            add(getResources().getString(R.string.minus_symbol));
        }};
    }

    private void setListenerForButtons() {
        ViewGroup rootView = findViewById(android.R.id.content);
        ArrayList<Button> buttons = buttonsOfViewGroupRecursevly(rootView);
        for (Button key:
             buttons) {
            key.setOnClickListener(this);
        }
    }

    private ArrayList<Button> buttonsOfViewGroupRecursevly(ViewGroup view) {
        ArrayList<Button> buttons = new ArrayList<>();
        processViewGroup(view, buttons);
        return buttons;
    }

    private void processViewGroup(ViewGroup view, ArrayList<Button> buttons) {
        for (int i = 0; i < view.getChildCount(); i++) {
            View element = view.getChildAt(i);
            if (element instanceof Button) {
                buttons.add((Button) element);
            } else if (element instanceof ViewGroup) {
                processViewGroup((ViewGroup) element, buttons);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof Button)) {
            throw new RuntimeException("Listener on click was set up to wrong type view. It should be Button.");
        }
        String pressedValue = ((Button) v).getText().toString();
        if (operandParts.contains(pressedValue)) {
            calculusState.appendDigit(pressedValue);
        } else if (pressedValue.equals(getResources().getString(R.string.negative_positive_symbol))) {
            calculusState.changePosNeg();
        } else if (operations.contains(pressedValue)) {
            calculusState.determineOperation(pressedValue);
        } else if (pressedValue.equals(getResources().getString(R.string.equal_symbol))) {
            calculusState.execute();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_expression))) {
            calculusState.clearStatemenet();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_digit))) {
            calculusState.clearLastDigit();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_number))) {
            calculusState.clearCurrentOperand();
        } else {
            throw new UnsupportedOperationException();
        }

    }


}