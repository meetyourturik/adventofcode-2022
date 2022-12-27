package com.turik.adventofcode.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day18Part1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day18.txt"));
        String line;

        List<Droplet> droplets = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] cs = line.split(",");
            droplets.add(new Droplet(Integer.parseInt(cs[0]), Integer.parseInt(cs[1]), Integer.parseInt(cs[2])));
        }

        for (Droplet droplet1 : droplets) {
            for (Droplet droplet2 : droplets) {
                int dist = Math.abs(droplet1.x - droplet2.x) + Math.abs(droplet1.y - droplet2.y) + Math.abs(droplet1.z - droplet2.z);
                if (dist == 1) {
                    droplet1.neighbours++;
                    droplet2.neighbours++;
                }
            }
        }

        int res = 0;
        for (Droplet droplet : droplets) {
            res += droplet.neighbours;
        }
        // dividing by 2 because above we calculate each neigbour twice
        System.out.println(droplets.size() * 6 - res / 2);
    }
}
