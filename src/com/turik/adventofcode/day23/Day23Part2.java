package com.turik.adventofcode.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Day23Part2 {

    static List<Point> elfs = new ArrayList<>();

    static Predicate<Point> freeN = point -> {
        for (Point elf: elfs) {
            if (elf.y == point.y+1 && Math.abs(elf.x - point.x) <= 1) {
                return false;
            }
        }
        return true;
    };

    static Predicate<Point> freeS = point -> {
        for (Point elf: elfs) {
            if (elf.y == point.y-1 && Math.abs(elf.x - point.x) <= 1) {
                return false;
            }
        }
        return true;
    };

    static Predicate<Point> freeW = point -> {
        for (Point elf: elfs) {
            if (elf.x == point.x-1 && Math.abs(elf.y - point.y) <= 1) {
                return false;
            }
        }
        return true;
    };

    static Predicate<Point> freeE = point -> {
        for (Point elf: elfs) {
            if (elf.x == point.x+1 && Math.abs(elf.y - point.y) <= 1) {
                return false;
            }
        }
        return true;
    };

    static Predicate<Point> freeAll = point -> freeN.test(point) && freeS.test(point) && freeW.test(point) && freeE.test(point);

    static Deque<Consumer<Point>> plans = new ArrayDeque<>();

    static Consumer<Point> planN = (point -> {
        if (freeN.test(point)) {
            point.pMove = 3;
        }
    });

    static Consumer<Point> planS = (point -> {
        if (freeS.test(point)) {
            point.pMove = 1;
        }
    });

    static Consumer<Point> planW = (point -> {
        if (freeW.test(point)) {
            point.pMove = 2;
        }
    });

    static Consumer<Point> planE = (point -> {
        if (freeE.test(point)) {
            point.pMove = 0;
        }
    });

    static {
        plans.add(planN);
        plans.add(planS);
        plans.add(planW);
        plans.add(planE);
    }

    static boolean allStopped() {
        for (Point elf : elfs) {
            if (!freeAll.test(elf)) {
                return false;
            }
        }
        return true;
    }

    static void resetPmoves() {
        for (Point elf: elfs) {
            elf.pMove = -1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day23.txt"));
        String line;

        int y = 0;

        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '#') {
                    elfs.add(new Point(i, y));
                }
            }
            y--;
        }

        Map<Point, List<Point>> potentialMoves = new HashMap<>();
        int round = 0;
        while (!allStopped()) {
            // phase 1
            for (Point elf : elfs) {
                if (freeAll.test(elf)) {
                    continue;
                }
                for (Consumer<Point> plan : plans) {
                    if (elf.pMove == -1) {
                        plan.accept(elf);
                    }
                }
            }
            // phase 1.5
            for (Point elf : elfs) {
                if (elf.pMove == -1) {
                    continue;
                }
                Point potentialMove = elf.getPmove();
                List<Point> elfsWithPmove = potentialMoves.computeIfAbsent(potentialMove, point -> new ArrayList<>());
                elfsWithPmove.add(elf);
            }
            // phase 2
            for (List<Point> points : potentialMoves.values()) {
                if (points.size() == 1) {
                    points.get(0).makeMove();
                }
            }
            // cleaning up
            plans.addLast(plans.removeFirst());
            resetPmoves();
            potentialMoves.clear();
            round++;
        }

        System.out.println(round + 1);
    }
}
