package lbz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;

/** The approximate algorithm that directly solves g
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class OurSolver {

    private static Cycle largestCycle(DonationGraph g, int v) {
        if (g.getNumNeighbors(v) == 0) {
            return null;
        }

        HashSet<Cycle> cycles = new HashSet<Cycle>();
        int[] path = new int[5];
        findCycle(g, v, v, 0, 0, path, cycles, new Memo(g.getNumVertices()));

        flag = false;
        int maxweight = 0; Cycle maxcycle = null;
        for (Cycle c : cycles) {
            if (c.weight(g) > maxweight) {
                maxweight = c.weight(g);
                maxcycle = c;
            }
        }
        return maxcycle;
    }


    private static ArrayList<Cycle> extractCycles(DonationGraph g) {
        HashSet<Cycle> cycles = new HashSet<Cycle>();
        int[] path = new int[5];
        for (int i : g.getVertices()) {
            findCycle(g, i, i, 0, 0, path, cycles, new Memo(g.getNumVertices()));
        }
        return new ArrayList<Cycle>(cycles);
    }

    private static int[] build(int[] path, int depth) {
        int[] result = new int[depth + 1];
        System.arraycopy(path, 0, result, 0, depth + 1);
        return result;
    }

    private static boolean contains(int[] path, int elem, int depth) {
        for (int i = 0; i < depth; i++) {
            if (path[i] == elem) {
                return true;
            }
        }
        return false;
    }

    private static boolean flag = false;
    private static void findCycle(
        DonationGraph g, int vertex, int root, int weight, int depth,
        int[] path, HashSet<Cycle> cycles, Memo m) {
        if (flag) return;
        weight += (g.isChild(vertex)) ? 2 : 1;
        path[depth] = vertex;
        if (m.contains(vertex, depth)) {
            m.build(vertex, depth, path, weight, cycles);
        } else {
            m.init(depth, vertex);
            if (g.isConnected(vertex, root)) {
                int[] newpath = build(path, depth);
                Cycle c = new Cycle(newpath.clone());
                if (c.weight(g) > 5 || c.getVertices().length == 5) {
                    flag = true;
                    cycles.clear();
                } else {
                    m.add(newpath, depth, weight, g);
                }
                cycles.add(c);
            }
            if (depth < 4) {
                for (int u : g.neighbors(vertex)) {
                    if (!contains(path, u, depth)) {
                        findCycle(g, u, root, weight, depth + 1, path, cycles, m);
                    }
                }
            }
        }
    }

    private static class Tuple implements Comparable<Tuple> {
        public int index; public int rank;
        public Tuple(int index, int rank) {
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
            return rank - other.rank;
        }
    }

    public static Iterable<Cycle> solve(DonationGraph g) {
        ArrayList<Cycle> solution = new ArrayList<Cycle>();
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();

        Tuple[] tuples = new Tuple[g.getNumVertices()];
        for (int v : g.getVertices()) {
            tuples[v] = new Tuple(v,
                Math.min(g.getNumNeighbors(v), g.getNumParents(v)));
            pq.offer(tuples[v]);
        }

        Tuple elem; HashSet<Integer> changed; Cycle c;
        while (!pq.isEmpty()) {
            changed = new HashSet<Integer>();
            elem = pq.poll();
            c = largestCycle(g, elem.index);
            if (c != null) {
                solution.add(c);
                for (int v : c.getVertices()) {
                    for (int n : g.neighbors(v)) {
                        changed.add(n);
                    }
                }
                for (int v : c.getVertices()) {
                    g.remove(v);
                    changed.remove(v);
                    pq.remove(tuples[v]);
                }
            } else {
                for (int n : g.neighbors(elem.index)) {
                    changed.add(n);
                }
                g.remove(elem.index);
            }
            for (int ch : changed) {
                pq.remove(tuples[ch]);
                tuples[ch] = new Tuple(ch,
                    Math.min(g.getNumNeighbors(ch), g.getNumParents(ch)));
                pq.offer(tuples[ch]);
            }
        }

        return solution;
    }

}
