package lbz;

import java.io.File;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou*/
public class Main {
    /** Usage: java lbz.Main ARGS, where ARGS contains
     * <FILENAME>*/
    public static void main(String... args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter an input.");
            return;
        }
        String filename = args[0];
        int length = args.length;
        File instance = new File(filename);
    }
}
