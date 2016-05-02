import lbz.*;

/** Jim and Tak's competition of ExactSolver.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class TestExact {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Usage: java TestExact [1-492] [hasJim]");
            return;
        }
        boolean hasJim = args.length == 2;
        int i = Integer.parseInt(args[0]);
        DonationGraph g = new DonationGraph("in/" + i + ".in");
        CycleGraph cgt = new CycleGraph(g.clone());
        CycleGraph cgj = null;
        if (hasJim) {
            cgj = cgt.clone();
        }

        long ttime = System.currentTimeMillis();
        Iterable<Cycle> tr = ExactSolverTak.solve(cgt);
        ttime = System.currentTimeMillis() - ttime;
        int tscore = Evaluate.score(g, tr);
        System.out.println("Tak solved " + i + " in " + ttime + "ms with score " + tscore);

        if (hasJim) {
            long jtime = System.currentTimeMillis();
            Iterable<Cycle> jr = ExactSolver.solve(cgj);
            jtime = System.currentTimeMillis() - jtime;
            int jscore = Evaluate.score(g, jr);
            System.out.println("Jim solved " + i + " in " + jtime + "ms with score " + jscore);
        }
    }

}
