package lbz;

import java.util.Arrays;

/** The core of LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Solver {

    public static Iterable<Cycle> solve(DonationGraph g) {
        Iterable<Cycle> solution = OurSolver.solve(g.clone());
        if (Evaluate.score(g, solution) == 0) {
            System.out.print("[OurSolver]");
            return solution;
        }
        try {
            CycleGraph cg = new CycleGraph(g);
            if (cg.getNumVertices() > Constants.maxExactCycle) {
                System.out.print("[ApproxSolver]");
                solution = ApproxSolver.solve(cg);
            } else {
                System.out.print("[ExactSolver]");
                solution = ExactSolver.solve(cg);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        } finally {
            return solution;
        }
    }

}
