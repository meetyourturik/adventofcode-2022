package com.turik.adventofcode.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day19Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day19t.txt"));
        String line;

        List<Blueprint> blueprints = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            blueprints.add(new Blueprint(line));
        }

        int[] resources = new int[4];
        int[] robots = new int[4];
        int robotprogress = -1;

        robots[0] = 1;

        Blueprint bp = blueprints.get(0);
        for (int i = 1; i <= 24; i++) {
            // checking if enough resources
            if (resources[2] >= bp.geodeobs && resources[0] >= bp.geodeore) { // for building a geode robot
                resources[2] -= bp.geodeobs;
                resources[0] -= bp.geodeore;
                robotprogress = 3;
            } else if (resources[1] >= bp.obsclay && resources[0] >= bp.obsore) { // for building an obsid robot
                resources[1] -= bp.obsclay;
                resources[0] -= bp.obsore;
                robotprogress = 2;
            } else if (resources[0] >= bp.clayore) { // for building a clay robot
                resources[0] -= bp.clayore;
                robotprogress = 1;
            } else { // can't build a robot, we assume there's no need to build an ore one
                robotprogress = -1;
            }
            // collecting resources
            for (int r = 0; r < 4; r++) {
                resources[r] += robots[r];
            }
            // building robot
            if (robotprogress != -1) {
                robots[robotprogress]++;
            }
        }

        System.out.println(resources[3]);
    }
}
