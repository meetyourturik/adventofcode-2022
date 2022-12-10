package com.turik.adventofcode.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;

public class Day7Part1And2 {

    private static int sum = 0;

    private static final String DIR_PREFIX = "dir";
    private static final String COMMAND_PREFIX = "$";
    private static final String LS_COMMAND = COMMAND_PREFIX + " ls";
    private static final String CD_COMMAND = COMMAND_PREFIX + " cd";

    // part 2 stuff
    private static final int TOTAL_SPACE = 70_000_000;
    private static final int REQUIRED_SPACE = 30_000_000;
    private static int NEED_TO_FREE_SPACE;

    private static int minSize = REQUIRED_SPACE;

    /**
     * recursively calculates folder size, applying a consumer to the size of a folder if it satisfies a predicate
     */
    private static int calculateFolderSize(Folder folder, BiPredicate<Folder, Integer> predicate, IntConsumer consumer) {
        int size = folder.getFileSize();

        for (Folder subfolder : folder.getSubfolders()) {
            size += calculateFolderSize(subfolder, predicate, consumer);
        }

        if (predicate.test(folder, size)) {
            consumer.accept(size);
        }

        return size;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day07.txt"));
        String line = reader.readLine(); // $ cd /

        int fileSize = 0;
        Folder currentFolder = new Folder(null, "/");
        Folder root = currentFolder;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(CD_COMMAND)) {
                if (currentFolder.getFileSize() == 0) {
                    currentFolder.setFileSize(fileSize);
                }
                fileSize = 0;
                String target = line.split(" ")[2];
                currentFolder = target.equals("..") ? currentFolder.getParent() : currentFolder.getSubfolderByName(target);
            } else if (line.equals(LS_COMMAND)) {
                // looks like doing nothing
            } else if (line.startsWith(DIR_PREFIX)) {
                currentFolder.addSubFolder(line.split(" ")[1]);
            } else { // file
                fileSize += Integer.parseInt(line.split(" ")[0]);
            }
        }
        currentFolder.setFileSize(fileSize); // setting up last folder filesize

        // --------------------

        BiPredicate<Folder, Integer> part1predicate = (folder, size) -> folder.getParent() != null && size < 100000;
        BiPredicate<Folder, Integer> part2predicate = (folder, size) -> size >= NEED_TO_FREE_SPACE && size < minSize;
        IntConsumer part1consumer = size -> sum += size;
        IntConsumer part2consumer = size -> minSize = size;

        int takenSpace = calculateFolderSize(root, part1predicate, part1consumer);
        NEED_TO_FREE_SPACE = REQUIRED_SPACE - (TOTAL_SPACE - takenSpace);
        calculateFolderSize(root, part2predicate, part2consumer);

        System.out.printf("part 1 answer: %d\n", sum);
        System.out.printf("part 2 answer: %d", minSize);
    }
}
