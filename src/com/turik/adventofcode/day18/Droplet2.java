package com.turik.adventofcode.day18;

public class Droplet2 {
    final int x;
    final int y;
    final int z;
    int[] neighbours;

    public Droplet2(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.neighbours = new int[6];
    }
}
