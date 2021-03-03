package com.kis.calculus;
import android.widget.TextView;

import com.kis.calculus.operations.Addition;
import com.kis.calculus.operations.Divison;
import com.kis.calculus.operations.Multiplication;
import com.kis.calculus.operations.Operation;
import com.kis.calculus.operations.Subtraction;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;

public class CalculusState {
    private Operation mCurrentOperation;
    private String mFirstOperand;
    private String mSecondOperand;
    private String mResult;
    private boolean mWorkWithResult;
    private int mCurrentElement;
    private HashMap<String, Operation> mAvailableOperaions;
    private TextView mDisplay;
    private DecimalFormat resultFormat = new DecimalFormat("#.##############################");;

    public CalculusState(TextView display) {
        mFirstOperand = "";
        mSecondOperand = "";
        mDisplay = display;
        refreshDisplay();
    }

    private void refreshDisplay() {
        mDisplay.setText(
                        (mFirstOperand +
                        (mCurrentOperation != null ? mCurrentOperation.toString() : "") +
                        (mCurrentOperation == null ? "" : mSecondOperand) +
                        (mResult != null ? "=" + mResult : "")));
    }

    public void execute() {
        if (mCurrentOperation == null || mSecondOperand.isEmpty()) {
            return;
        }

        Double aOperand;
        Double bOperand;
        try {
            aOperand = Double.parseDouble(mFirstOperand);
        } catch (NumberFormatException e) {
            aOperand = 0d;
        }
        try {
            bOperand = Double.parseDouble(mSecondOperand);
        } catch (NumberFormatException e) {
            bOperand = 0d;
        }

        if (mCurrentOperation.validateOperation(aOperand, bOperand) == false) {
            mResult = mCurrentOperation.getResultForNonValidatedOperation();
        } else {
            Double result = mCurrentOperation.execute(aOperand, bOperand);
            mResult = resultFormat.format(result);
        }
        refreshDisplay();
        resetVariables();
    }

    private void resetVariables() {
        mWorkWithResult = true;
        mSecondOperand = "";
        mCurrentElement = 0;
        mCurrentOperation = null;
        try {
            Double resultDouble = Double.parseDouble(mResult);
        } catch (NumberFormatException e) {
            mFirstOperand = "";
            mWorkWithResult = false;
            mResult = null;
            return;
        }
        mFirstOperand = mResult;
        mResult = null;
        mWorkWithResult = true;
    }
    
    public void determineOperation(String operation) {
        if (mAvailableOperaions == null) {
            mAvailableOperaions = new HashMap<String, Operation>() {{
                put("÷", new Divison());
                put("+", new Addition());
                put("×", new Multiplication());
                put("-", new Subtraction());
            }};
        }
        if (mWorkWithResult) {
            mWorkWithResult = false;
        }
        if (mFirstOperand.isEmpty()) {
            return;
        }
        mCurrentOperation = mAvailableOperaions.get(operation);
        mCurrentElement = 1;
        refreshDisplay();
    }

    public void appendDigit(String digit) {
        if (mCurrentElement == 0) {

            if (mWorkWithResult) {
                mFirstOperand = "";
                mWorkWithResult = false;
            }

            if (mFirstOperand.equals("")) {
                if (digit.equals(".")){
                    mFirstOperand = mFirstOperand.concat(digit);
                } else {
                    mFirstOperand = digit;
                }
            } else {
                mFirstOperand = mFirstOperand.concat(digit);
            }
        }
        else if (mCurrentElement == 1) {
            if (mSecondOperand.equals("")) {
                if (digit.equals(".")){
                    mSecondOperand = mSecondOperand.concat(digit);
                } else {
                    mSecondOperand = digit;
                }
            } else {
                mSecondOperand = mSecondOperand.concat(digit);
            }
        }
        refreshDisplay();
    }

    public void changePosNeg() {
        if (mCurrentElement == 0) {
            if (mWorkWithResult) {
                mWorkWithResult = false;
            }
            if (mFirstOperand.equals("")) {
                return;
            } else if (mFirstOperand.contains("-")) {
                mFirstOperand = mFirstOperand.replace("-", "");
            } else {
                mFirstOperand = "-".concat(mFirstOperand);
            }
        } else  if (mCurrentElement == 1) {
            if (mSecondOperand.equals("")) {
                return;
            } else if (mSecondOperand.contains("-")) {
                mSecondOperand = mSecondOperand.replace("-", "");
            } else {
                mSecondOperand = "-".concat(mSecondOperand);
            }
        }
        refreshDisplay();
    }
    
    public void clearStatemenet() {
        mFirstOperand = "";
        mSecondOperand = "";
        mResult = null;
        mCurrentOperation = null;
        mCurrentElement = 0;
        mWorkWithResult = false;
        refreshDisplay();
    }

    public void clearLastDigit() {
        if (mCurrentElement == 0) {
            if (mFirstOperand.equals("")) {
                return;
            } else if (mFirstOperand.length() == 1) {
                mFirstOperand = "";
            } else {
                mFirstOperand = mFirstOperand.substring(0, mFirstOperand.length() - 1);
            }
        } else if (mCurrentElement == 1) {
            if (mSecondOperand.equals("")){
                mCurrentElement = 0;
                mCurrentOperation = null;
            } else if (mSecondOperand.length() == 1) {
                mSecondOperand = "";
            }
            else {
                mSecondOperand = mSecondOperand.substring(0, mSecondOperand.length()-1);
            }
        }

        refreshDisplay();
    }

    public void clearCurrentOperand() {
        if (mCurrentElement == 0) {
            mFirstOperand = "";
        } else if (mCurrentElement == 1) {
            mSecondOperand = "";
        }

        refreshDisplay();
    }
}

