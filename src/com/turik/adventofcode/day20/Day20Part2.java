package com.turik.adventofcode.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day20Part2 {
    

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day20.txt"));
        String line;

        List<WrapperLong> origin = new LinkedList<>();
        List<WrapperLong> copy = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            WrapperLong wint = new WrapperLong(Integer.parseInt(line) * 811589153L);
            origin.add(wint);
            copy.add(wint);
        }

        int size = origin.size() - 1;

        for (int i = 1; i <= 10; i++) {
            for (WrapperLong wnum : origin) {
                long num = wnum.value;
                int idx = copy.indexOf(wnum);
                long newIdx = 0;

                if (idx + num >= 0) {
                    newIdx = idx + num;
                } else {
                    newIdx = idx + num;
                    long mult = Math.abs(newIdx) / size - 1;
                    newIdx += size * mult;
                    while (newIdx <= 0) {
                        newIdx += size;
                    }
                }

                newIdx %= size;

                copy.remove(wnum);
                copy.add((int) newIdx, wnum);
            }
        }

        int idx0 = -1;
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i).value == 0) {
                idx0 = i;
            }
        }

        size++;
        int idx1000 = (idx0 + 1000) % size;
        int idx2000 = (idx0 + 2000) % size;
        int idx3000 = (idx0 + 3000) % size;

        System.out.println(copy.get(idx1000).value + copy.get(idx2000).value + copy.get(idx3000).value);
    }
}
