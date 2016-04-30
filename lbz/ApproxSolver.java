package lbz;

import java.util.HashSet;

/** The approximate solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ApproxSolver {

    public static Iterable<Cycle> solve(CycleGraph cg) {

        HashSet<Integer> effective = new HashSet<Integer>();
        for (int i = 0; i < cg.getNumVertices(); i++) {
            effective.add(i);
        }

        int maxrank, maxindex, rank;
        while (not effective.isEmpty()) {
            maxrank = 0;
            rank = -1;
            for (int i : effective) {
                rank = cg.getCycle(i).getWeight();
                if (weight > maxweight) {
                    maxweight = weight;
                    maxindex = i;
                }
            }

        }

    }

}
