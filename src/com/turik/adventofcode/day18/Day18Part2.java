package com.turik.adventofcode.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day18.txt"));
        String line;

        int xmax = 0;
        int xmin = Integer.MAX_VALUE;

        int ymax = 0;
        int ymin = Integer.MAX_VALUE;

        int zmax = 0;
        int zmin = Integer.MAX_VALUE;

        List<Droplet2> droplets = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] cs = line.split(",");
            int x = Integer.parseInt(cs[0]);
            int y = Integer.parseInt(cs[1]);
            int z = Integer.parseInt(cs[2]);

            xmax = Math.max(xmax, x);
            ymax = Math.max(ymax, y);
            zmax = Math.max(zmax, z);

            xmin = Math.min(xmin, x);
            ymin = Math.min(ymin, y);
            zmin = Math.min(zmin, z);

            droplets.add(new Droplet2(x,y,z));
        }

        int xc = (xmax + xmin) / 2;
        int yc = (ymax + ymin) / 2;
        int zc = (zmax + zmin) / 2;

        for (Droplet2 droplet1 : droplets) {
            for (Droplet2 droplet2 : droplets) {
                int dist = Math.abs(droplet1.x - droplet2.x) + Math.abs(droplet1.y - droplet2.y) + Math.abs(droplet1.z - droplet2.z);
                if (dist == 1) {
                    if (droplet1.x - droplet2.x == 1) {
                        droplet1.neighbours[0] = 1;
                        droplet2.neighbours[1] = 1;
                    } else if (droplet1.x - droplet2.x == -1) {
                        droplet1.neighbours[1] = 1;
                        droplet2.neighbours[0] = 1;
                    } else if (droplet1.y - droplet2.y == 1) {
                        droplet1.neighbours[2] = 1;
                        droplet2.neighbours[3] = 1;
                    } else if (droplet1.y - droplet2.y == -1) {
                        droplet1.neighbours[3] = 1;
                        droplet2.neighbours[2] = 1;
                    } else if (droplet1.z - droplet2.z == 1) {
                        droplet1.neighbours[4] = 1;
                        droplet2.neighbours[5] = 1;
                    } else if (droplet1.z - droplet2.z == -1) {
                        droplet1.neighbours[5] = 1;
                        droplet2.neighbours[4] = 1;
                    }
                }
            }
        }

        List<Droplet2> inners = new ArrayList<>();

        for (int x = xmin; x <= xmax; x++) {
            for (int y = ymin; y <= ymax; y++) {
                for (int z = zmin; z <= zmax; z++) {
                    boolean isempty = true;
                    for (Droplet2 droplet: droplets) {
                        if (droplet.x == x && droplet.y == y && droplet.z == z) {
                            isempty = false;
                            break;
                        }
                    }
                    if (isempty) {
                        byte xcoord = 0;
                        byte ycoord = 0;
                        byte zcoord = 0;

                        for (Droplet2 droplet : droplets) {
                            if (x - droplet.x == 1) {
                                xcoord |= 0B10;
                            } else if (x - droplet.x == -1) {
                                xcoord |= 0B01;
                            }
                            if (y - droplet.y == 1) {
                                ycoord |= 0B10;
                            } else if (y - droplet.y == -1) {
                                ycoord |= 0B01;
                            }
                            if (z - droplet.z == 1) {
                                zcoord |= 0B10;
                            } else if (z - droplet.z == -1) {
                                zcoord |= 0B01;
                            }
                        }

                        if (xcoord == 0B11 && ycoord == 0B11 && zcoord == 0B11) {
                            inners.add(new Droplet2(x, y, z));
                        }
                    }
                }
            }
        }

        int res = 0;

        List<Droplet2> toRemove = new ArrayList<>();
        for (Droplet2 droplet :droplets) {
            long ne = Arrays.stream(droplet.neighbours).filter(v -> v == 0).count(); // number of empty vertices
            if (ne == 6) {
                res += 6;
                toRemove.add(droplet);
            }
        }
        droplets.removeAll(toRemove);

        for (Droplet2 droplet : droplets) {
            res += Arrays.stream(droplet.neighbours).filter(v -> v == 0).count();
        }

        System.out.println(res);
    }
}
