package com.ecwid.ip;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static boolean validationDisabled = true;
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
                Scanner sc = new Scanner(new File(fileName));
                System.out.println("Parsing of '" + fileName + "' started. Time elapsed: " + getTimeElapsed());
                BinaryTree bt = new BinaryTree();
                while (sc.hasNext()) {
                    String str = sc.nextLine();
                    if (validationDisabled || IPv4ValidatorRegex.isValid(str)) {
                        bt.insert(str);
                        if (bt.nodesCount % 1000000 == 0) {
                            System.out.println("IP Addresses parsed: " + bt.nodesCount + " Time elapsed: " + getTimeElapsed());
                        }
                    } else {
                        System.out.println("Invalid IP ignored: " + str);
                    }
                }
                sc.close();
                System.out.println("Unique IP addresses count: " + bt.nodesCount + " Time elapsed: " + getTimeElapsed());
            } else {
                System.out.println("Please, provide file name as an argument");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTimeElapsed() {
        long msElapsed = System.currentTimeMillis() - msInitial;
        return String.format("%02d min, %02d sec, %03d ms",
                TimeUnit.MILLISECONDS.toMinutes(msElapsed),
                TimeUnit.MILLISECONDS.toSeconds(msElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(msElapsed)),
                TimeUnit.MILLISECONDS.toMillis(msElapsed) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(msElapsed))
        );
    }
}
