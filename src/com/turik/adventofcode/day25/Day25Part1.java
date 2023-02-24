package com.turik.adventofcode.day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day25Part1 {

    static int getNumFromChar(char c) {
        switch (c) {
            case '2':
                return 2;
            case '1':
                return 1;
            case '0':
                return 0;
            case '-':
                return -1;
            case '=':
                return -2;
            default:
                throw new IllegalArgumentException("unknown character: " + c);
        }
    }

    static long snafuToDec(String snafu) {
        long res = 0;
        long power = 1;

        for (int i = snafu.length() - 1; i >=0; i--) {
            char c = snafu.charAt(i);
            int num = getNumFromChar(c);
            res += num * power;
            power *= 5;
        }

        return res;
    }

    static char getCharFromNum(Long n) {
        int num = n.intValue();
        switch (num) {
            case 2:
                return '2';
            case 1:
                return '1';
            case 0:
                return '0';
            case -1:
                return '-';
            case -2:
                return '=';
            default:
                throw new IllegalArgumentException("wrong number: " + num);
        }
    }

    static String decToSnafu(long num) {
        StringBuilder sb = new StringBuilder();
        long pow = 5;
        while (num > 0) {
            long r;
            if (num % 5 > 2) {
                r = (num % 5) - 5;
                num +=5;
            } else {
                r = num % 5;
            }
            num /= 5;
            sb.append(getCharFromNum(r));
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./inputs/day25.txt"));
        String line;
        long res = 0;
        while ((line = reader.readLine()) != null) {
            res += snafuToDec(line);
        }
        System.out.println(decToSnafu(res));
    }
}
