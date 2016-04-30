import lbz.*;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Main {
    /** Usage: java Main ARGS, where ARGS contains
     * <FILENAME> */
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter instance number [1-492]");
            return;
        }
        String infilename = "in/" + args[0] + ".in";
        String outfilename = "out/" + args[0] + ".out";
        DonationGraph g = new DonationGraph(infilename);
        Cycle.output(Solver.solve(g), outfilename);
        System.out.println("Done. Check: [" + outfilename + "]");
    }
}
