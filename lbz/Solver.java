package lbz;

import java.util.Arrays;

/** The core of LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Solver {

    public static boolean isbest = false;
    public static Iterable<Cycle> solve(DonationGraph g) {
        isbest = false;
        Iterable<Cycle> solution = OurSolver.solve(g.clone());
        if (Evaluate.score(g, solution) == 0) {
            System.out.print("[OurNBSolver] ");
            isbest = true;
            return solution;
        }
        try {
            CycleGraph cg = new CycleGraph(g.clone());
            if (cg.getNumVertices() > Constants.maxExactCycle) {
                System.out.print("[ApprxSolver] ");
                solution = ApproxSolver.solve(cg);
            } else {
                System.out.print("[ExactSolver] ");
                isbest = true;
                solution = ExactSolver.solve(cg);
            }
            return solution;
        } catch (Exception e) {
            System.out.print("[OurSBSolver] ");
            return solution;
        }
    }

}
