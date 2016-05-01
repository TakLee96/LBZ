package lbz;

import java.util.HashSet;
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
        System.out.println("BEGIN");
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        Tuple[] tuples = new Tuple[cg.getNumVertices()];
        for (int v : cg.getVertices()) {
            tuples[v] = new Tuple(v, 1.0 * cg.getCycle(v).weight(cg) /
                (cg.getNumNeighbors(v) + 1));
            pq.offer(tuples[v]);
        }
        System.out.println("PQ READY");
        HashSet<Integer> changed; Tuple elem;
        while (!pq.isEmpty()) {
            System.out.print(pq.size());
            changed = new HashSet<Integer>();
            elem = pq.poll();
            solution.add(cg.getCycle(elem.index));

            for (int n1 : cg.neighbors(elem.index)) {
                for (int n2 : cg.neighbors(n1)) {
                    changed.add(n2);
                }
            }
            changed.remove(elem.index);

            for (Object o : cg.neighbors(elem.index).toArray()) {
                cg.remove(((Integer) o).intValue());
            }
            cg.remove(elem.index);

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
