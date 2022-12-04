package com.turik.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.ToIntFunction;

public class Utils {

    public static int processFile(String filePath, ToIntFunction<String> function) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        int result = 0;

        while ((line = reader.readLine()) != null) {
            result += function.applyAsInt(line);
        }

        return result;
    }

}
