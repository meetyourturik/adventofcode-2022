package com.turik.adventofcode.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day19Part2 {

    static Map<Position, Integer> cache = new HashMap<>();

    static int search(Position position, Blueprint bp) {
        if (cache.containsKey(position)) {
            return cache.get(position);
        }

        if (position.time == 0) {
            return position.resources[3];
        }

        int time = position.time - 1;
        int[] resources = position.resources.clone();
        int[] robots = position.robots.clone();
        // gathering
        for (int r = 0; r < 4; r++) {
            resources[r] += robots[r];
        }
        int build = position.build;
        // building robot
        if (build == 0) {
            resources[0] -= bp.oreore;
        } else if (build == 1) {
            resources[0] -= bp.clayore;
        } else if (build == 2) {
            resources[1] -= bp.obsclay;
            resources[0] -= bp.obsore;
        } else if (build == 3) {
            resources[2] -= bp.geodeobs;
            resources[0] -= bp.geodeore;
        }
        if (build != -1) {
            robots[build]++;
        }
        // next turn
        Position nextPos;
        int maxval = 0;
        // checking if enough resources, always building geode and obsid if possible, otherwise - exploring all options
        if (resources[2] >= bp.geodeobs && resources[0] >= bp.geodeore) { // building a geode robot
            nextPos = new Position(resources, robots, time, 3);
            maxval = Math.max(maxval, search(nextPos, bp));
        } else if (resources[1] >= bp.obsclay && resources[0] >= bp.obsore) { // building an obsid robot
            nextPos = new Position(resources, robots, time, 2);
            maxval = Math.max(maxval, search(nextPos, bp));
        } else {
            boolean built = false;
            if (robots[0] <= 20 && resources[0] >= bp.oreore) { // building an ore robot
                nextPos = new Position(resources, robots, time, 0);
                maxval = Math.max(maxval, search(nextPos, bp));
                built = true;
            }
            if (robots[1] <= 20 && resources[0] >= bp.clayore) { // building a clay robot
                nextPos = new Position(resources, robots, time, 1);
                maxval = Math.max(maxval, search(nextPos, bp));
                built = true;
            }
            int skipped = position.skipped;
            if (!built || skipped < 3) { // not skipping more than 3 turns, also skipping only if couldn't build anything
                nextPos = new Position(resources, robots, time, -1, skipped + 1);
                maxval = Math.max(maxval, search(nextPos, bp));
            }
        }

        cache.put(position, maxval);

        return maxval;
    }

    public static void main(String[] args) throws IOException {
        int c = 0;
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day19.txt"));
        String line;

        List<Blueprint> blueprints = new ArrayList<>();

        while ((line = reader.readLine()) != null && c++ < 3) {
            blueprints.add(new Blueprint(line));
        }

        int res = 1;

        for (Blueprint bp : blueprints) {
            // 0 - ore 1 - clay 2 - obsidian 3 - geode
            int[] resources = new int[4];
            int[] robots = new int[4];
            cache.clear();

            robots[0] = 1;
            int geodes = search(new Position(resources, robots, 32, -1), bp);
            res *= geodes;
        }

        System.out.println(res);
    }
}
