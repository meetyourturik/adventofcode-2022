package com.turik.adventofcode.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Day23Part1 {

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

    // minx = [0] maxx = [1] miny = [2] maxy = [3]
    static int[] getBoundaries() {
        int minx = elfs.get(0).x;
        int maxx = elfs.get(0).x;
        int miny = elfs.get(0).y;
        int maxy = elfs.get(0).y;

        for (Point elf : elfs) {
            minx = Math.min(minx, elf.x);
            maxx = Math.max(maxx, elf.x);
            miny = Math.min(miny, elf.y);
            maxy = Math.max(maxy, elf.y);
        }
        return new int[] {minx, maxx, miny, maxy};
    }

    // for debug
    static void draw() {
        int[] boundaries = getBoundaries();
        for (int y = boundaries[3]; y >= boundaries[2]; y--) {
            StringBuilder sb = new StringBuilder();
            for (int x = boundaries[0]; x <= boundaries[1]; x++) {
                boolean isElf = false;
                for (Point elf : elfs) {
                    if (elf.x == x && elf.y == y) {
                        isElf = true;
                        break;
                    }
                }
                sb.append(isElf ? '#' : '.');
            }
            System.out.println(sb);
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
        while (round < 10) {
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

        int[] boundaries = getBoundaries();
        System.out.println((boundaries[1] - boundaries[0] + 1)*(boundaries[3] - boundaries[2] + 1) - elfs.size());
    }
}
