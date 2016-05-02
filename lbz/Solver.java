package lbz;

import java.util.Arrays;

/** The core of LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Solver {

    public static boolean isbest = false;

    public static Iterable<Cycle> solve(DonationGraph g) {
        return solve(g, false);
    }

    public static Iterable<Cycle> solve(DonationGraph g, boolean silent) {
        isbest = false;
        Iterable<Cycle> solution = OurSolver.solve(g.clone());
        if (Evaluate.score(g, solution) == 0) {
            if (!silent) System.out.print("[OurNBSolver] ");
            isbest = true;
            return solution;
        }
        try {
            CycleGraph cg = new CycleGraph(g.clone());
            if (cg.dead) {
                throw new RuntimeException();
            }
            if (cg.getNumVertices() > Constants.maxExactCycle) {
                if (!silent) System.out.print("[ApprxSolver] ");
                solution = ApproxSolver.solve(cg);
            } else {
               if (!silent) System.out.print("[ExactSolver] ");
               solution = ExactSolver.solve(cg);
               isbest = true;
            }
            return solution;
        } catch (Exception e) {
            if (!silent) System.out.print("[OurSBSolver] ");
            return solution;
        }
    }

}
