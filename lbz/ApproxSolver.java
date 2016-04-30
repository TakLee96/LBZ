package lbz;

import java.util.HashSet;
import java.util.ArrayList;

/** The approximate solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ApproxSolver {

    public static Iterable<Cycle> solve(CycleGraph cg) {
        HashSet<Integer> effective = new HashSet<Integer>();
        for (int i = 0; i < cg.getNumVertices(); i++) {
            effective.add(i);
        }

        ArrayList<Cycle> solution = new ArrayList<Cycle>();

        int maxindex = -1; double maxrank, rank;
        while (!effective.isEmpty()) {
            maxrank = 0;
            for (int i : effective) {
                rank = 1.0 * cg.getCycle(i).getWeight() /
                    (cg.getNumNeighbors(i) + 1);
                if (rank > maxrank) {
                    maxrank = rank;
                    maxindex = i;
                }
            }
            solution.add(cg.getCycle(maxindex));
            for (int n : cg.neighbors(maxindex)) {
                cg.remove(n);
                effective.remove(n);
            }
            cg.remove(maxindex);
            effective.remove(maxindex);
        }

        return solution;
    }

}
