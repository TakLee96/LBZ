import lbz.*;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Main {
    /** Usage: java Main ARGS, where ARGS contains
     * <FILENAME> */
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter instance number [0-492]");
            return;
        }
        if (args[0].equals("all")) {
            for (int i = 1; i <= 492; i++) {
                String infilename = "in/" + i + ".in";
                String outfilename = "out/" + i + ".out";
                DonationGraph g = new DonationGraph(infilename);
                long time = System.currentTimeMillis();
                Cycle.output(Solver.solve(g), g, outfilename);
                time = System.currentTimeMillis() - time;
                System.out.println("#" + i + " done. [" + time + "ms]");
            }
        } else if (args.length == 2) {
            for (int i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++) {
                String infilename = "in/" + i + ".in";
                String outfilename = "out/" + i + ".out";
                DonationGraph g = new DonationGraph(infilename);
                long time = System.currentTimeMillis();
                Cycle.output(Solver.solve(g), g, outfilename);
                time = System.currentTimeMillis() - time;
                System.out.println("#" + i + " done. [" + time + "ms]");
            }
        } else {
            String infilename = "in/" + args[0] + ".in";
            String outfilename = "out/" + args[0] + ".out";
            DonationGraph g = new DonationGraph(infilename);
            long time = System.currentTimeMillis();
            Cycle.output(Solver.solve(g), g, outfilename);
            time = System.currentTimeMillis() - time;
            System.out.println("Done. Check: (" + outfilename + ") [" + time + "ms]");
        }
    }
}
