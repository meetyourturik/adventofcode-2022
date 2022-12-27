package com.turik.adventofcode.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Day17Part2 {

    static Set<Long>[] chamber = new HashSet[7];
    static long time = 0;
    static String jets;
    static long maxRest = 0;

    static BiPredicate<Integer, Long>[] moveLeftPredicates = new BiPredicate[5];
    static BiPredicate<Integer, Long>[] moveRightPredicates = new BiPredicate[5];
    static BiPredicate<Integer, Long>[] restPredicates = new BiPredicate[5];
    static BiConsumer<Integer, Long>[] restConsumers = new BiConsumer[5];

    static {
        // presetting chamber
        for (int i = 0; i < chamber.length; i++) {
            chamber[i] = new HashSet<>();
        }

        // 0 - square, 1 - minus, 2 - plus, 3 - reverse l, 4 - vertical
        // move left predicates
        moveLeftPredicates[0] = (leftPosX, leftPosY) -> !chamber[leftPosX - 1].contains(leftPosY) && !chamber[leftPosX - 1].contains(leftPosY + 1);
        moveLeftPredicates[1] = (leftPosX, leftPosY) -> !chamber[leftPosX - 1].contains(leftPosY);
        moveLeftPredicates[2] = (leftPosX, leftPosY) -> !chamber[leftPosX - 1].contains(leftPosY + 1) && !chamber[leftPosX].contains(leftPosY) && !chamber[leftPosX].contains(leftPosY + 2);
        moveLeftPredicates[3] = (leftPosX, leftPosY) -> !chamber[leftPosX - 1].contains(leftPosY) && !chamber[leftPosX + 1].contains(leftPosY + 1) && !chamber[leftPosX + 1].contains(leftPosY + 2);
        moveLeftPredicates[4] = (leftPosX, leftPosY) -> !chamber[leftPosX - 1].contains(leftPosY) && !chamber[leftPosX - 1].contains(leftPosY + 1) && !chamber[leftPosX - 1].contains(leftPosY + 2) && !chamber[leftPosX - 1].contains(leftPosY + 3);
        // move right predicates
        moveRightPredicates[0] = (rightPosX, leftPosY) -> !chamber[rightPosX + 1].contains(leftPosY) && !chamber[rightPosX + 1].contains(leftPosY + 1);
        moveRightPredicates[1] = (rightPosX, leftPosY) -> !chamber[rightPosX + 1].contains(leftPosY);
        moveRightPredicates[2] = (rightPosX, leftPosY) -> !chamber[rightPosX + 1].contains(leftPosY + 1) && !chamber[rightPosX].contains(leftPosY) && !chamber[rightPosX].contains(leftPosY + 2);
        moveRightPredicates[3] = (rightPosX, leftPosY) -> !chamber[rightPosX + 1].contains(leftPosY) && !chamber[rightPosX + 1].contains(leftPosY + 1) && !chamber[rightPosX + 1].contains(leftPosY + 2);
        moveRightPredicates[4] = (rightPosX, leftPosY) -> !chamber[rightPosX + 1].contains(leftPosY) && !chamber[rightPosX + 1].contains(leftPosY + 1) && !chamber[rightPosX + 1].contains(leftPosY + 2) && !chamber[rightPosX + 1].contains(leftPosY + 3);
        // rest predicates
        restPredicates[0] = (leftPosX, leftPosY) -> chamber[leftPosX].contains(leftPosY - 1) || chamber[leftPosX + 1].contains(leftPosY - 1);;
        restPredicates[1] = (leftPosX, leftPosY) -> {
            for (int i = 0; i < 4; i++) {
                if (chamber[leftPosX + i].contains(leftPosY - 1)) {
                    return true;
                }
            }
            return false;
        };
        restPredicates[2] = (leftPosX, leftPosY) -> chamber[leftPosX].contains(leftPosY) || chamber[leftPosX + 1].contains(leftPosY - 1) || chamber[leftPosX + 2].contains(leftPosY);
        restPredicates[3] = (leftPosX, leftPosY) -> {
            for (int i = 0; i < 3; i++) {
                if (chamber[leftPosX + i].contains(leftPosY - 1)) {
                    return true;
                }
            }
            return false;
        };
        restPredicates[4] = (leftPosX, leftPosY) -> chamber[leftPosX].contains(leftPosY - 1);
        // rest consumers
        restConsumers[0] = (leftPosX, leftPosY) -> {
            chamber[leftPosX].add(leftPosY);
            chamber[leftPosX].add(leftPosY + 1);

            chamber[leftPosX + 1].add(leftPosY);
            chamber[leftPosX + 1].add(leftPosY + 1);
        };
        restConsumers[1] = (leftPosX, leftPosY) -> {
            for (int i = 0; i < 4; i++) {
                chamber[leftPosX + i].add(leftPosY);
            }
        };
        restConsumers[2] = (leftPosX, leftPosY) -> {
            chamber[leftPosX].add(leftPosY + 1);

            chamber[leftPosX + 1].add(leftPosY);
            chamber[leftPosX + 1].add(leftPosY + 1);
            chamber[leftPosX + 1].add(leftPosY + 2);

            chamber[leftPosX + 2].add(leftPosY + 1);
        };
        restConsumers[3] = (leftPosX, leftPosY) -> {
            chamber[leftPosX].add(leftPosY);

            chamber[leftPosX + 1].add(leftPosY);

            chamber[leftPosX + 2].add(leftPosY);
            chamber[leftPosX + 2].add(leftPosY + 1);
            chamber[leftPosX + 2].add(leftPosY + 2);
        };
        restConsumers[4] = (leftPosX, leftPosY) -> {
            chamber[leftPosX].add(leftPosY + 0);
            chamber[leftPosX].add(leftPosY + 1);
            chamber[leftPosX].add(leftPosY + 2);
            chamber[leftPosX].add(leftPosY + 3);
        };
    }

    static long getMaxHeight() {
        long max = 0;
        for (Set<Long> column : chamber) {
            max = Math.max(max, column.stream().max(Comparator.naturalOrder()).orElse(0L));
        }
        return max;
    }

    static void processRock(int rockNumber, int width) {
        int leftPosX = 2;
        long leftPosY = getMaxHeight() + 4;

        BiPredicate<Integer, Long> moveLeft = moveLeftPredicates[rockNumber];
        BiPredicate<Integer, Long> moveRight = moveRightPredicates[rockNumber];
        BiPredicate<Integer, Long> shouldRest = restPredicates[rockNumber];
        BiConsumer<Integer, Long> restCons = restConsumers[rockNumber];

        while (true) {
            char jet = jets.charAt((int) (time % jets.length()));
            time++;
            if (jet == '<') { // moving left
                if (leftPosX > 0 && moveLeft.test(leftPosX, leftPosY)) {
                    leftPosX--;
                }
            } else { // moving right
                if ((leftPosX < chamber.length - width) && moveRight.test(leftPosX + width - 1, leftPosY)) {
                    leftPosX++;
                }
            }

            if (leftPosY == 1 || shouldRest.test(leftPosX, leftPosY)) {
                restCons.accept(leftPosX, leftPosY);
                maxRest = Math.max(maxRest, leftPosY);
                break;
            }
            leftPosY--;
        }

    }

    static void processRock(long rockNumber) {
        int rem = (int) (rockNumber % 5);
        switch (rem) {
            case 1: // -
                processRock(1, 4);
                break;
            case 2: // +
                processRock(2, 3);
                break;
            case 3: // ⅃
                processRock(3, 3);
                break;
            case 4: // |
                processRock(4, 1);
                break;
            case 0: // □
                processRock(0, 2);
                break;
            default:
                throw new RuntimeException("we've checked all the remainders lol");
        }
    }

    static void clearChamber() {
        for (Set<Long> col : chamber) {
            col.removeIf(val -> val < maxRest - 100);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day17.txt"));
        jets = reader.readLine();

        /**
         * what happens here is that I noted that some cyclicality exists, so I spent some time looking for number
         * that will show that cyclicality on the puzzle input
         * when I found it, I used Day17Part2Help to calculate that cycle sum and remainder sum
         * it magically worked lol
         */
        long prevH = 0L;
        for (long i = 1; i <= 1_000_000_000_000L; i++) {
            processRock(i);
            if (i % 10 == 0) {
                clearChamber();
            }
            if (i % 50_000 == 0) {
                long curH = getMaxHeight();
                System.out.println(i + " " + (curH - prevH));
                prevH = curH;
            }
        }
    }
}
