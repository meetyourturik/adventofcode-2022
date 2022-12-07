package com.turik.adventofcode.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day7Part1 {

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

    private static int calculateFolderSizePart1(Folder folder) {
        int size = folder.getFileSize();

        for (Folder subfolder : folder.getSubfolders()) {
            size += calculateFolderSizePart1(subfolder);
        }

        if (folder.getParent() != null && size < 100000) {
            sum += size;
        }

        return size;
    }

    private static int calculateFolderSizePart2(Folder folder) {
        int size = folder.getFileSize();

        for (Folder subfolder : folder.getSubfolders()) {
            size += calculateFolderSizePart2(subfolder);
        }

        if (size >= NEED_TO_FREE_SPACE && size < minSize) {
            minSize = size;
        }

        return size;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day7.txt"));
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

        int takenSpace = calculateFolderSizePart1(root);
        NEED_TO_FREE_SPACE = REQUIRED_SPACE - (TOTAL_SPACE - takenSpace);
        calculateFolderSizePart2(root);

        System.out.printf("part 1 answer: %d\n", sum);
        System.out.printf("part 2 answer: %d", minSize);
    }
}
