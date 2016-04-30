package lbz;

/** The exact solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ExactSolver {

    public static Iterable<Cycle> solve(CycleGraph cg) {
        //int[][] memo = new int[][]
        Cycle out;
        for(int i = 0; i < cg.getNumVertices(); i++) {
            out = cg.getCycle(i);
            for(int j = 0; j < cg.getNumVertices(); j++) {
                if(cg.getCycle(j).shareVertex(out) {
                    
                }
            }
        }
        return dps(cg);
    }

    public static Iterable<Cycle> dps(CycleGraph g) {
        if(g.getNumVertices() == 0) {
            return 0
        }
        return null;
    }
}
