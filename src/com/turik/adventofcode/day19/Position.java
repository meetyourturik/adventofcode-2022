package com.turik.adventofcode.day19;

import java.util.Arrays;

public class Position {
    int[] resources;
    int[] robots;
    int time;
    int build;
    int skipped;

    public Position(int[] resources, int[] robots, int time, int build, int skipped) {
        this.resources = resources;
        this.robots = robots;
        this.time = time;
        this.build = build;
        this.skipped = skipped;
    }

    public Position(int[] resources, int[] robots, int time, int build) {
        this(resources, robots, time, build, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (time != position.time) return false;
        if (build != position.build) return false;
        if (!Arrays.equals(resources, position.resources)) return false;
        return Arrays.equals(robots, position.robots);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(resources);
        result = 31 * result + Arrays.hashCode(robots);
        result = 31 * result + time;
        result = 31 * result + build;
        return result;
    }
}
