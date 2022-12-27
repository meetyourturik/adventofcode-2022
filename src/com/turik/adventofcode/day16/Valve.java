package com.turik.adventofcode.day16;

public class Valve {

    private final int flow;
    private final String[] neighbours;

    public Valve(int flow, String[] neighbours) {
        this.flow = flow;
        this.neighbours = neighbours;
    }

    public int flow() {
        return flow;
    }

    public String[] neighbours() {
        return neighbours;
    }
}
