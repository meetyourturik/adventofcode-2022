package com.turik.adventofcode.day16;

public class DoublePosition {
    private final String valve1;
    private final String valve2;
    private final int rem1;
    private final int rem2;
    private final int bitmask;

    public DoublePosition(String valve1, String valve2, int rem1, int rem2, int bitmask) {
        this.valve1 = valve1;
        this.valve2 = valve2;
        this.rem1 = rem1;
        this.rem2 = rem2;
        this.bitmask = bitmask;
    }

    public String getValve1() {
        return valve1;
    }

    public String getValve2() {
        return valve2;
    }

    public int getRem1() {
        return rem1;
    }

    public int getRem2() {
        return rem2;
    }

    public int getBitmask() {
        return bitmask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoublePosition position = (DoublePosition) o;

        if (rem1 != position.rem1) return false;
        if (rem2 != position.rem2) return false;
        if (bitmask != position.bitmask) return false;
        return valve1.equals(position.valve1) && valve2.equals(position.valve2);
    }

    @Override
    public int hashCode() {
        int result = rem1;
        result = 31 * result + rem2;
        result = 31 * result + valve1.hashCode();
        result = 31 * result + valve2.hashCode();
        result = 31 * result + bitmask;
        return result;
    }
}
