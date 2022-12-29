package com.turik.adventofcode.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day20Part1 {


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day20.txt"));
        String line;

        List<WrapperInt> origin = new LinkedList<>();
        List<WrapperInt> copy = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            WrapperInt wint = new WrapperInt(Integer.parseInt(line));
            origin.add(wint);
            copy.add(wint);
        }

        int size = origin.size() - 1;

        for (WrapperInt wnum : origin) {
            int num = wnum.value;
            int idx = copy.indexOf(wnum);
            int newIdx = 0;

            if (idx + num >= 0) {
                newIdx = idx + num;
            } else {
                newIdx = idx + num;
                while (newIdx <= 0) {
                    newIdx += size;
                }
            }

            newIdx %= size;

            copy.remove(wnum);
            copy.add(newIdx, wnum);
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
