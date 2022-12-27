package com.turik.adventofcode.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Day17Part1 {

    static Set<Integer>[] chamber = new HashSet[7];
    static int time = 0;
    static String jets;

    static BiPredicate<Integer, Integer>[] moveLeftPredicates = new BiPredicate[5];
    static BiPredicate<Integer, Integer>[] moveRightPredicates = new BiPredicate[5];
    static BiPredicate<Integer, Integer>[] restPredicates = new BiPredicate[5];
    static BiConsumer<Integer, Integer>[] restConsumers = new BiConsumer[5];

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

    static int getMaxHeight() {
        int max = 0;
        for (Set<Integer> column : chamber) {
            max = Math.max(max, column.stream().max(Comparator.naturalOrder()).orElse(0));
        }
        return max;
    }

    static void processRock(int rockNumber, int width) {
        int leftPosX = 2;
        int leftPosY = getMaxHeight() + 4;

        BiPredicate<Integer, Integer> moveLeft = moveLeftPredicates[rockNumber];
        BiPredicate<Integer, Integer> moveRight = moveRightPredicates[rockNumber];
        BiPredicate<Integer, Integer> shouldRest = restPredicates[rockNumber];
        BiConsumer<Integer, Integer> restCons = restConsumers[rockNumber];

        while (true) {
            char jet = jets.charAt(time % jets.length());
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
                break;
            }
            leftPosY--;
        }

    }

    static void processRock(int rockNumber) {
        switch (rockNumber % 5) {
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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day17.txt"));
        jets = reader.readLine();

        for (int i = 1; i <= 2022; i++) {
            processRock(i);
        }

        System.out.println(getMaxHeight());
    }
}
