package com.mattieapps.roommates.model;

import com.mattieapps.roommates.model.state.CalculatorType;

/**
 * Created by andrewmattie on 5/25/15.
 */
public class Calculator {

    public static double calculate(double val1, double val2, CalculatorType type) {
        switch (type) {
            case ADD:
                return val1 + val2;
            case SUBTRACT:
                return val1 - val2;
            case MULTIPLY:
                return val1 * val2;
            case DIVIDE:
                return val1 / val2;
            default:
                return 0;
        }
    }

}
