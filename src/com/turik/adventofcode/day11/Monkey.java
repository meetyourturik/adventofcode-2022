package com.turik.adventofcode.day11;

import java.util.List;
import java.util.function.LongFunction;

public class Monkey {

    private int inspectedCount;
    private final List<Long> items;
    private final LongFunction<Long> operation;
    private final int test;
    private final int monkeyIfTrue;
    private final int monkeyIfFalse;

    public Monkey(List<Long> items, LongFunction<Long> operation, int test, int monkeyIfTrue, int monkeyIfFalse) {
        inspectedCount = 0;
        this.items = items;
        this.operation = operation;
        this.test = test;
        this.monkeyIfTrue = monkeyIfTrue;
        this.monkeyIfFalse = monkeyIfFalse;
    }

    public long inspect(int itemPos) {
        long item = items.get(itemPos);
        item = operation.apply(item);
        return item;
    }

    public void throwTo(Monkey monkey, long item) {
        monkey.items.add(item);
    }

    public int monkeyBusiness(long item) {
        return (item % test == 0) ? monkeyIfTrue : monkeyIfFalse;
    }

    public void removeAll() {
        inspectedCount += items.size();
        items.removeAll(items);
    }

    public int getInspectedCount() {
        return inspectedCount;
    }

    public List<Long> getItems() {
        return items;
    }
}
