package com.turik.adventofcode.day20;

public class WrapperInt {
    final int value;

    public WrapperInt(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
