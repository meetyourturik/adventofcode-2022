package com.turik.adventofcode.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15Part1 {

    static Pattern pattern = Pattern.compile("Sensor at x=(?<sensorx>-?\\d+), y=(?<sensory>-?\\d+): closest beacon is at x=(?<beaconx>-?\\d+), y=(?<beacony>-?\\d+)");
    static List<Point> sensors = new ArrayList<>();

    static void getCoordsFromLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int sensorx = Integer.parseInt(matcher.group("sensorx"));
            int sensory = Integer.parseInt(matcher.group("sensory"));

            int beaconx = Integer.parseInt(matcher.group("beaconx"));
            int beacony = Integer.parseInt(matcher.group("beacony"));

            Point beacon = new Point(beaconx, beacony);
            Point sensor = new Point(sensorx, sensory, beacon);

            sensors.add(sensor);
        } else {
            throw new RuntimeException("move strings should be unified");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day15.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            getCoordsFromLine(line);
        }

        int rowNumber = 2_000_000;
        Set<Integer> row = new HashSet<>();

        for (Point sensor : sensors) {
            Point beacon = sensor.beacon();
            int sx = sensor.x();
            int sy = sensor.y();

            int bx = beacon.x();
            int by = beacon.y();

            int distance = Math.abs(sx - bx) + Math.abs(sy - by);
            int distToRow = Math.abs(rowNumber - sy);

            for (int i = 0; i <= distance - distToRow; i++) {
                row.add(sx + i);
                row.add(sx - i);
            }
        }

        for (Point sensor : sensors) {
            Point beacon = sensor.beacon();
            if (beacon.y() == rowNumber) {
                row.remove(beacon.x());
            }
        }

        System.out.println(row.size());
    }
}
