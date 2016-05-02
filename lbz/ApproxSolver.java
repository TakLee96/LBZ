package lbz;

import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;

/** The approximate solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ApproxSolver {

    private static class Tuple implements Comparable<Tuple> {
        public int index; public double rank;
        public Tuple(int index, double rank) {
            this.index = index; this.rank = rank;
        }
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            Tuple other = (Tuple) o;
            return index == other.index;
        }
        @Override
        public int compareTo(Tuple other) {
            return (int) (other.rank - rank);
        }
    }

    public static Iterable<Cycle> solve(CycleGraph cg) {
        ArrayList<Cycle> solution = new ArrayList<Cycle>();
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();

        Tuple[] tuples = new Tuple[cg.getNumVertices()];
        for (int v : cg.getVertices()) {
            tuples[v] = new Tuple(v, 1.0 * cg.getCycle(v).weight(cg) /
                (cg.getNumNeighbors(v) + 1));
            pq.offer(tuples[v]);
        }

        LinkedHashSet<Integer> changed; Tuple elem; int v;
        while (!pq.isEmpty()) {
            changed = new LinkedHashSet<Integer>();
            elem = pq.poll();
            solution.add(cg.getCycle(elem.index));

            Object[] copy = cg.neighbors(elem.index).toArray();
            for (int n : cg.neighbors(elem.index)) {
                for (int n2 : cg.neighbors(n)) {
                    changed.add(n2);
                }
            }

            for (Object o : cg.neighbors(elem.index).toArray()) {
                v = ((Integer) o).intValue();
                cg.remove(v);
                changed.remove(v);
                pq.remove(tuples[v]);
            }
            cg.remove(elem.index);
            changed.remove(elem.index);

            for (int c : changed) {
                pq.remove(tuples[c]);
                tuples[c] = new Tuple(c, 1.0 * cg.getCycle(c).weight(cg) /
                    (cg.getNumNeighbors(c) + 1));
                pq.offer(tuples[c]);
            }
        }

        return solution;
    }

}
