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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Droplet droplet = (Droplet) o;

        if (x != droplet.x) return false;
        if (y != droplet.y) return false;
        return z == droplet.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
