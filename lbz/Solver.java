package lbz;

import java.util.Arrays;

/** The core of LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Solver {

    public static Iterable<Cycle> solve(DonationGraph g) {
        if (g.getNumVertices() < Constants.numVerticesThreshold ||
            g.getNumEdges() < Constants.v2eThreshold * g.getNumVertices()) {
            CycleGraph cg = new CycleGraph(g);
            if (cg.getNumVertices() < Constants.cycleThreshold) {
                // TODO: MWIS Exact Algorithm
                return ExactSolver.solve(cg);
            } else {
                // TODO: MWIS Approx Algorithm
                return ApproxSolver.solve(cg);
            }
        } else {
            // TODO: Our Approx Algo
        }

        // TODO: placeholder output for now
        return Arrays.asList(new Cycle[]{
            new Cycle(new int[]{1, 2, 3}, 4),
            new Cycle(new int[]{4, 5}, 4)
        });
    }

}
