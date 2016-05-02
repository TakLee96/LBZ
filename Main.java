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
            System.out.println("[           ] #" + i + " done. Best already exists.");
            return;
        }
        String infilename = "in/" + i + ".in";
        String outfilename = "out/" + i + ".out";
        DonationGraph g = new DonationGraph(infilename);
        long time = System.currentTimeMillis();
        Cycle.output(Solver.solve(g), g, outfilename);
        time = System.currentTimeMillis() - time;
        System.out.println("#" + i + " done. Didn't break record.");
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter instance number [0-492]");
            return;
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
