package com.turik.adventofcode.day15;

public class Point {

    private final int x;
    private final int y;
    private final Point beacon;

    public Point(int x, int y) {
        this(x, y, null);
    }

    public Point(int x, int y, Point beacon) {
        this.x = x;
        this.y = y;
        this.beacon = beacon;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Point beacon() {
        return beacon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
