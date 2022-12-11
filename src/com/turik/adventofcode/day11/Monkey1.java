package com.turik.adventofcode.day11;

import java.util.List;
import java.util.function.IntFunction;

public class Monkey1 {

    private int inspectedCount;
    private final List<Integer> items;
    private final IntFunction<Integer> operation;
    private final int test;
    private final int monkeyIfTrue;
    private final int monkeyIfFalse;

    public Monkey1(List<Integer> items, IntFunction<Integer> operation, int test, int monkeyIfTrue, int monkeyIfFalse) {
        inspectedCount = 0;
        this.items = items;
        this.operation = operation;
        this.test = test;
        this.monkeyIfTrue = monkeyIfTrue;
        this.monkeyIfFalse = monkeyIfFalse;
    }

    public int inspect(int itemPos) {
        int item = items.get(itemPos);
        item = operation.apply(item);
        item /= 3;
        return item;
    }

    public void throwTo(Monkey1 monkey, int item) {
        monkey.items.add(item);
    }

    public int monkeyBusiness(int item) {
        return (item % test == 0) ? monkeyIfTrue : monkeyIfFalse;
    }

    public void removeAll() {
        inspectedCount += items.size();
        items.removeAll(items);
    }

    public int getInspectedCount() {
        return inspectedCount;
    }

    public List<Integer> getItems() {
        return items;
    }
}
