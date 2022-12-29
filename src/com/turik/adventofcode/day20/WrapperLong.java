package com.turik.adventofcode.day20;

public class WrapperLong {
    final long value;

    public WrapperLong(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
