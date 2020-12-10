package com.ecwid.ip;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UniqueIPCounter {
    private static boolean validationDisabled = true;
    private static boolean showPercent = false;
    private static long msInitial = System.currentTimeMillis();

    /**
     * main method accepts path to file as a first argument
     * scans text file line by line, adds every IP into binary tree
     * for better performance IP validation should be removed
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                String fileName = args[0];
                long total = showPercent? getLinesTotal(fileName): 0;
                long check = (total == 0)? 100000000 : total / 100;
                long rowNum = 0;
                Scanner sc = new Scanner(new File(fileName));
                System.out.println("Parsing of '" + fileName + "' started. Time elapsed: " + getTimeElapsed());
                AllIPv4Flags fl = new AllIPv4Flags();
                while (sc.hasNext()) {
                    String str = sc.nextLine();
                    if (validationDisabled || IPv4ValidatorRegex.isValid(str)) {
                        fl.markIP(str);
                        if (rowNum > 0 && (rowNum % check == 0)) {
                            System.out.println("IP Addresses parsed: " + getLinesProgress(rowNum, total) + " Time elapsed: " + getTimeElapsed());
                        }
                    } else {
                        System.out.println("Invalid IP ignored: " + str);
                    }
                    rowNum++;
                }
                sc.close();
                System.out.println("Unique IP addresses count: " + Long.toString(fl.getUniqueCount()) + " Time elapsed: " + getTimeElapsed());
            } else {
                System.out.println("Please, provide file name as an argument");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getLinesTotal(String filename) throws IOException {
        long linesTotal = 0;
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                System.out.println("Zero lines");
            }

            // make it easy for the optimizer to tune this loop
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++linesTotal;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                System.out.println(readChars);
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++linesTotal;
                    }
                }
                readChars = is.read(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return linesTotal;
    }

    private static String getTimeElapsed() {
        long msElapsed = System.currentTimeMillis() - msInitial;
        return String.format("%02d min, %02d sec, %03d ms",
                TimeUnit.MILLISECONDS.toMinutes(msElapsed),
                TimeUnit.MILLISECONDS.toSeconds(msElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(msElapsed)),
                TimeUnit.MILLISECONDS.toMillis(msElapsed) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(msElapsed))
        );
    }

    private static String getLinesProgress(long current, long total) {
        if (total > 0) {
            return String.format("%02", current/total) + " %";
        } else {
            return Long.toString(current);
        }
    }
}
