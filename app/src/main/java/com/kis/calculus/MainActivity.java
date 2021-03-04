package com.kis.calculus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mResultView;
    private CalculusState mCalculusState;
    private HashSet<String> mOperandParts;
    private HashSet<String> mOperations;
    private static final String CALCULUS_STATE_NAMESPACE = "CALCULUS_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultView = findViewById(R.id.calcus_state);
        mCalculusState = new CalculusState(mResultView);
        initializeKeyHashes();
        setListenerForButtons();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CALCULUS_STATE_NAMESPACE, mCalculusState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mCalculusState = savedInstanceState.getParcelable(CALCULUS_STATE_NAMESPACE);
        mCalculusState.setDisplay(mResultView);
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void initializeKeyHashes() {
        mOperandParts = new HashSet<String>() {{
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

        mOperations = new HashSet<String>() {{
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
        if (mOperandParts.contains(pressedValue)) {
            mCalculusState.appendDigit(pressedValue);
        } else if (pressedValue.equals(getResources().getString(R.string.negative_positive_symbol))) {
            mCalculusState.changePosNeg();
        } else if (mOperations.contains(pressedValue)) {
            mCalculusState.determineOperation(pressedValue);
        } else if (pressedValue.equals(getResources().getString(R.string.equal_symbol))) {
            mCalculusState.execute();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_expression))) {
            mCalculusState.clearStatemenet();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_digit))) {
            mCalculusState.clearLastDigit();
        } else if (pressedValue.equals(getResources().getString(R.string.clear_number))) {
            mCalculusState.clearCurrentOperand();
        } else {
            throw new UnsupportedOperationException();
        }

    }


}