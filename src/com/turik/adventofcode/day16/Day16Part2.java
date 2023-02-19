package com.turik.adventofcode.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Part2 {

    static Pattern pattern = Pattern.compile("Valve (?<valveName>\\w+) has flow rate=(?<flowRate>\\d+); tunnels? leads? to valves? (?<paths>.+)");

    static Map<String, Valve> valves = new HashMap<>();
    static List<String> namesList = new ArrayList<>();
    static List<String> positiveNamesList = new ArrayList<>();
    static Map<String, Map<String, Integer>> dist = new HashMap<>();
    static Map<DoublePosition, Integer> cache = new HashMap<>();

    static void calcDist() {
        for (Map.Entry<String, Valve> entry: valves.entrySet()) {
            String valveName = entry.getKey();
            Valve valve = entry.getValue();

            if (!valveName.equals("AA") && valve.flow() == 0) {
                continue;
            }

            Map<String, Integer> curDists = new HashMap<>();
            curDists.put(valveName, 0);
            curDists.put("AA", 0); // ??
            dist.put(valveName, curDists);

            List<String> visited = new ArrayList<>();
            visited.add(valveName);

            Deque<Object[]> q = new ArrayDeque<>();
            q.add(new Object[]{0, valveName});

            while (!q.isEmpty()) {
                Object[] o = q.poll();
                int distance = (int) o[0];
                String vname = (String) o[1];

                for (String neigh : valves.get(vname).neighbours()) {
                    if (visited.contains(neigh)) {
                        continue;
                    }
                    visited.add(neigh);
                    if (valves.get(neigh).flow() > 0) {
                        dist.get(valveName).put(neigh, distance + 1);
                    }
                    q.add(new Object[]{distance + 1, neigh});
                }
            }

            dist.get(valveName).remove(valveName);

            if (!valveName.equals("AA")) {
                dist.get(valveName).remove("AA");
            }

        }
    }

    static int dfs(DoublePosition position) {
        if (cache.containsKey(position)) {
            return cache.get(position);
        }
        int maxval = 0;

        for (String neigh1 : dist.get(position.getValve1()).keySet()) {
            int bit1 = 1 << namesList.indexOf(neigh1);

            if ((position.getBitmask() & bit1) != 0) {
                continue;
            }
            int remtime1 = position.getRem1() - dist.get(position.getValve1()).get(neigh1) - 1;
            if (remtime1 <= 0) {
                continue;
            }
            for (String neigh2 : dist.get(position.getValve2()).keySet()) {
                int bit2 = 1 << namesList.indexOf(neigh2);

                if ((position.getBitmask() & bit2) != 0 || bit1 == bit2) {
                    continue;
                }

                int remtime2 = position.getRem2() - dist.get(position.getValve2()).get(neigh2) - 1;
                if (remtime2 <= 0) {
                    continue;
                }
                maxval = Math.max(maxval, dfs(new DoublePosition(neigh1, neigh2, remtime1, remtime2, position.getBitmask() | bit1 | bit2)) + valves.get(neigh1).flow() * remtime1 + valves.get(neigh2).flow() * remtime2);
            }
        }

        cache.put(position, maxval);
        return maxval;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day16.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group("valveName");
                int rate = Integer.parseInt(matcher.group("flowRate"));
                String[] neighbours = matcher.group("paths").split(", ");

                valves.put(name, new Valve(rate, neighbours));
                namesList.add(name);
                if (rate > 0) {
                    positiveNamesList.add(name);
                }
            } else {
                throw new RuntimeException("all lines should compile");
            }
        }

        calcDist();
        System.out.println(dfs(new DoublePosition( "AA","AA",26, 26, 0)));
    }
}
