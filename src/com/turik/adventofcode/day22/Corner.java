package com.turik.adventofcode.day22;

public class Corner {
    final int col;
    final int row;

    public Corner(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int col() {
        return col;
    }

    public int row() {
        return row;
    }
}
