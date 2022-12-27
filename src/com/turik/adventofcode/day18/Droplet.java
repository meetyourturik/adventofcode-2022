package com.turik.adventofcode.day18;

public class Droplet {
    final int x;
    final int y;
    final int z;
    int neighbours;

    public Droplet(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.neighbours = 0;
    }

    @Override
    public String toString() {
        return "Droplet{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
