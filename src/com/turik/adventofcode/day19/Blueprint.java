package com.turik.adventofcode.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blueprint {

    static Pattern pattern = Pattern.compile("Blueprint (?<number>-?\\d+): Each ore robot costs (?<oreore>-?\\d+) ore. Each clay robot costs (?<clayore>-?\\d+) ore. Each obsidian robot costs (?<obsore>-?\\d+) ore and (?<obsclay>-?\\d+) clay. Each geode robot costs (?<geodeore>-?\\d+) ore and (?<geodeobs>-?\\d+) obsidian\\.");

    final int oreore;

    final int clayore;

    final int obsore;
    final int obsclay;

    final int geodeore;
    final int geodeobs;

    public Blueprint(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            this.oreore = Integer.parseInt(matcher.group("oreore"));

            this.clayore = Integer.parseInt(matcher.group("clayore"));

            this.obsore = Integer.parseInt(matcher.group("obsore"));
            this.obsclay = Integer.parseInt(matcher.group("obsclay"));

            this.geodeore = Integer.parseInt(matcher.group("geodeore"));
            this.geodeobs = Integer.parseInt(matcher.group("geodeobs"));

        } else {
            throw new RuntimeException("strings should be unified");
        }
    }
}
