package com.turik.adventofcode.day21;

import java.math.BigInteger;
import java.util.function.BinaryOperator;

public class BigIntOperation {

    static BinaryOperator<BigInteger> plus = BigInteger::add;
    static BinaryOperator<BigInteger> minus = BigInteger::subtract;
    static BinaryOperator<BigInteger> mult = BigInteger::multiply;
    static BinaryOperator<BigInteger> div = BigInteger::divide;

    String monkey1;
    String monkey2;
    BinaryOperator<BigInteger> operator;

    public BigIntOperation(String monkey1, String monkey2, String opStr) {
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
