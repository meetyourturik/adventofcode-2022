package com.turik.adventofcode.day13;

public class Pair<T> {
    private final T left;
    private final T right;

    public Pair(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T left() {
        return left;
    }

    public T right() {
        return right;
    }
}
