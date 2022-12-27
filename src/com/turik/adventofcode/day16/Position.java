package com.turik.adventofcode.day16;

public class Position {
    private final int time;
    private final String valve;
    private final int bitmask;

    public Position(int time, String valve, int bitmask) {
        this.time = time;
        this.valve = valve;
        this.bitmask = bitmask;
    }

    public int getTime() {
        return time;
    }

    public String getValve() {
        return valve;
    }

    public int getBitmask() {
        return bitmask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (time != position.time) return false;
        if (bitmask != position.bitmask) return false;
        return valve.equals(position.valve);
    }

    @Override
    public int hashCode() {
        int result = time;
        result = 31 * result + valve.hashCode();
        result = 31 * result + bitmask;
        return result;
    }
}
