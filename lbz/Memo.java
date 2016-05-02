package lbz;

import java.util.HashSet;

/** Supporting data structure for cycle finding.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Memo {

    private HashSet<Cycle>[][] memo;

    @SuppressWarnings("unchecked")
    public Memo(int v) {
        memo = new HashSet[5][v];
    }

    public void init(int depth, int vertex) {
        for (int i = depth; i < 5; i++) {
            if (memo[i][vertex] == null) {
                memo[i][vertex] = new HashSet<Cycle>();
            }
        }
    }

    public boolean contains(int vertex, int depth) {
        return memo[depth][vertex] != null;
    }

    public void build(int vertex, int depth, int[] path, int weight, HashSet<Cycle> cycles) {
        for (int i = depth; i < 5; i++) {
            for (Cycle c : memo[i][vertex]) {
                int[] np = ArrayUtils.build(path, depth);
                Cycle merged = c.merge(np);
                if (merged != null) {
                    cycles.add(merged);
                }
            }
        }
    }

    public void add(int[] path, int depth, int weight, DonationGraph g) {
        int[] partial;
        for (int i = depth; i >= 0; i--) {
            partial = ArrayUtils.buildReverse(path, i);
            memo[i][path[i]].add(new Cycle(partial));
            weight -= (g.isChild(path[i])) ? 2 : 1;
        }
    }

}
