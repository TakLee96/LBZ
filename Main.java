import lbz.*;

import java.io.File;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Main {
    /** Usage: java Main ARGS, where ARGS contains
     * <FILENAME> */
    private static void test(int i) {
        File f = new File("best/" + i + ".out");
        if (f.exists() && !f.isDirectory()) {
            if (!silent) {
                System.out.println("[           ] #" + i + " done. Best already exists.");
            }
            return;
        }
        String infilename = "in/" + i + ".in";
        String outfilename = "out/" + i + ".out";
        DonationGraph g = new DonationGraph(infilename);
        long time = System.currentTimeMillis();
        Cycle.output(Solver.solve(g, silent), g, outfilename);
        time = System.currentTimeMillis() - time;
        if (!silent) {
            System.out.println("#" + i + " done. Didn't break record.");
        }
    }

    private static boolean silent = false;
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter instance number [0-492]");
            return;
        }
        silent = args.length >= 2 && args[1].equals("-q");
        int repeat = (args.length == 3) ? Integer.parseInt(args[2]) : 1;
        for (int j = 0; j < repeat; j++) {
            if (repeat > 1) {
                System.out.println("Iteration #" + j);
            }
            if (args[0].equals("all")) {
                for (int i = 1; i <= 492; i++) {
                    test(i);
                }
            } else if (args.length == 2) {
                for (int i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++) {
                    test(i);
                }
            } else {
                test(Integer.parseInt(args[0]));
            }
        }
    }
}
