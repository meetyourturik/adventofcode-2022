package com.turik.adventofcode.day21;

import java.util.function.LongBinaryOperator;

public class Operation {

    static LongBinaryOperator plus = (i1, i2) -> (i1 + i2);
    static LongBinaryOperator minus = (i1, i2) -> (i1 - i2);
    static LongBinaryOperator mult = (i1, i2) -> (i1 * i2);
    static LongBinaryOperator div = (i1, i2) -> (i1 / i2);

    String monkey1;
    String monkey2;
    LongBinaryOperator operator;

    public Operation(String monkey1, String monkey2, String opStr) {
        this.monkey1 = monkey1;
        this.monkey2 = monkey2;
        switch (opStr) {
            case "+":
                this.operator = plus;
                break;
            case "-":
                this.operator = minus;
                break;
            case "*":
                this.operator = mult;
                break;
            case "/":
                this.operator = div;
                break;
            default:
                throw new RuntimeException("yarr!");
        }
    }
}
