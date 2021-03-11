package com.kis.calculus.operations;

import androidx.annotation.NonNull;

public class Addition implements Operation {
    @Override
    public Double execute(Double a, Double b) {
        return a + b;
    }

    @NonNull
    @Override
    public String toString() {
        return "+";
    }
}
