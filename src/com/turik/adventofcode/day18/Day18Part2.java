package com.turik.adventofcode.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18Part2 {

    static List<Droplet> droplets = new ArrayList<>();
    static Set<Droplet> free = new HashSet<>();
    static Set<Droplet> trapped = new HashSet<>();

    static boolean isFree(int x, int y, int z) {
        Droplet checked = new Droplet(x, y, z);
        if (free.contains(checked)) {
            return true;
        }
        if (trapped.contains(checked)) {
            return false;
        }

        Set<Droplet> seen = new HashSet<>();
        Deque<Droplet> q = new ArrayDeque<>();
        q.push(checked);

        while (!q.isEmpty()) {
            Droplet d = q.poll();
            if (seen.contains(d) || droplets.contains(d)) {
                continue;
            }
            seen.add(d);
            if (seen.size() > 5000) {
                free.addAll(seen);
                return true;
            }
            q.add(new Droplet(d.x+1,d.y,d.z));
            q.add(new Droplet(d.x-1,d.y,d.z));
            q.add(new Droplet(d.x,d.y+1,d.z));
            q.add(new Droplet(d.x,d.y-1,d.z));
            q.add(new Droplet(d.x,d.y,d.z+1));
            q.add(new Droplet(d.x,d.y,d.z-1));
        }

        trapped.addAll(seen);

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day18.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] cs = line.split(",");
            int x = Integer.parseInt(cs[0]);
            int y = Integer.parseInt(cs[1]);
            int z = Integer.parseInt(cs[2]);

            droplets.add(new Droplet(x,y,z));
        }

        int res = 0;
        for (Droplet droplet: droplets) {
            if (isFree(droplet.x + 1, droplet.y, droplet.z)) {
                res++;
            }
            if (isFree(droplet.x - 1, droplet.y, droplet.z)) {
                res++;
            }
            if (isFree(droplet.x, droplet.y + 1, droplet.z)) {
                res++;
            }
            if (isFree(droplet.x, droplet.y - 1, droplet.z)) {
                res++;
            }
            if (isFree(droplet.x, droplet.y, droplet.z + 1)) {
                res++;
            }
            if (isFree(droplet.x, droplet.y, droplet.z - 1)) {
                res++;
            }
        }
        System.out.println(res);
    }
}
