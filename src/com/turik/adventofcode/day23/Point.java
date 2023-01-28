package com.turik.adventofcode.day23;

public class Point {
    public int x;
    public int y;
    /**
     *        3(N)
     *        ^
     * (W)2 <-|-> 0(E)
     *        v
     *        1(S)
     */
    public int pMove;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.pMove = -1;
    }

    public Point getPmove() {
        if (pMove == 0) {
            return new Point(x+1, y);
        } else if (pMove == 1) {
            return new Point(x, y-1);
        } else if (pMove == 2) {
            return new Point(x-1, y);
        } else if (pMove == 3) {
            return new Point(x, y+1);
        } else {
            throw new RuntimeException("no pmove point for " + this);
        }
    }

    public void makeMove() {
        if (pMove == 0) {
            x++;
        } else if (pMove == 1) {
            y--;
        } else if (pMove == 2) {
            x--;
        } else if (pMove == 3) {
            y++;
        } else {
            throw new RuntimeException("cant make move for " + this);
        }
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

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
