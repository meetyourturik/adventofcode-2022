package com.turik.adventofcode.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15Part2 {

    static Pattern pattern = Pattern.compile("Sensor at x=(?<sensorx>-?\\d+), y=(?<sensory>-?\\d+): closest beacon is at x=(?<beaconx>-?\\d+), y=(?<beacony>-?\\d+)");
    static Map<Integer, Set<Interval>> network = new HashMap<>();
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

    private static void addInterval(Set<Interval> intervals, int start, int end) {
        if (intervals.size() == 0) {
            intervals.add(new Interval(start, end));
            return;
        }
        boolean intersect = false;

        for (Interval interval: intervals) {
            int is = interval.getStart();
            int ie = interval.getEnd();

            if (end < is || start > ie) { // new interval is fully outside current one
                continue;
            }

            intersect = true;

            if (start >= is && end <= ie) { // new interval is fully inside the current one
                return;
            }
        }

        if (!intersect) {
            intervals.add(new Interval(start, end));
        } else {
            Set<Interval> toRemove = new HashSet<>();

            int newStart = start;
            int newEnd = end;

            for (Interval interval: intervals) {
                int is = interval.getStart();
                int ie = interval.getEnd();

                if (start <= is && end >= is || start <= ie && end >= ie) {
                    toRemove.add(interval);
                    newStart = Math.min(newStart, is);
                    newEnd = Math.max(newEnd, ie);
                }
            }

            intervals.removeAll(toRemove);
            intervals.add(new Interval(newStart, newEnd));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day15.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            getCoordsFromLine(line);
        }

        int maxPos = 4_000_000;

        for (int rowNumber = 0; rowNumber <= maxPos; rowNumber++) {
            Set<Interval> intervals = network.computeIfAbsent(rowNumber, key -> new TreeSet<>());

            for (Point sensor : sensors) {
                Point beacon = sensor.beacon();
                int sx = sensor.x();
                int sy = sensor.y();

                int bx = beacon.x();
                int by = beacon.y();

                int distance = Math.abs(sx - bx) + Math.abs(sy - by);
                int distToRow = Math.abs(rowNumber - sy);

                if (distance - distToRow >= 0) {
                    int start = sx - (distance - distToRow);
                    int end = sx + (distance - distToRow);
                    addInterval(intervals, start, end);
                }
            }
        }

        long res = 0;

        for (int rowNumber = 0; rowNumber <= maxPos; rowNumber++) {
            Set<Interval> intervals = network.get(rowNumber);
            if (intervals != null && intervals.size() == 2) {
                long x = 0;
                boolean isFirst = true;
                for (Interval interval : intervals) {
                    if (isFirst) {
                        x += interval.getEnd();
                        isFirst = false;
                    } else {
                        x += interval.getStart();
                    }
                }
                res = maxPos * x / 2 + rowNumber;
                break;
            }
        }
        System.out.println(res);
    }
}
