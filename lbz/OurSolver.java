package lbz;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/** The approximate algorithm that directly solves g
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class OurSolver {

    private static Random random = new Random(System.currentTimeMillis());

    private static Cycle largestCycle(DonationGraph g, int v) {
        if (g.getNumNeighbors(v) == 0) {
            return null;
        }

        LinkedHashSet<Cycle> cycles = new LinkedHashSet<Cycle>();
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
        LinkedHashSet<Cycle> cycles = new LinkedHashSet<Cycle>();
        int[] path = new int[5];
        for (int i : g.getVertices()) {
            findCycle(g, i, i, 0, 0, path, cycles, new Memo(g.getNumVertices()));
        }
        return new ArrayList<Cycle>(cycles);
    }

    private static boolean flag = false;
    private static void findCycle(
        DonationGraph g, int vertex, int root, int weight, int depth,
        int[] path, LinkedHashSet<Cycle> cycles, Memo m) {
        if (flag) return;
        weight += (g.isChild(vertex)) ? 2 : 1;
        path[depth] = vertex;
        if (m.contains(vertex, depth)) {
            m.build(vertex, depth, path, weight, cycles);
        } else {
            m.init(depth, vertex);
            if (g.isConnected(vertex, root)) {
                int[] newpath = ArrayUtils.build(path, depth);
                Cycle c = new Cycle(newpath);
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
                    if (!ArrayUtils.contains(path, u, depth)) {
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

    private static int rank(DonationGraph g, int v) {
        //return 100 * (g.isChild(v) ? 2 : 1) * g.getNumParents(v) / (g.getNumNeighbors(v) + 1);
        //return random.nextInt(10000000);
        int ntotal = 0;
        for (int n : g.neighbors(v)) {
            ntotal += g.weight(n);
        }
        int ptotal = 0;
        for (int p : g.parents(v)) {
            ptotal += g.weight(p);
        }
        return ntotal * ptotal - random.nextInt(ntotal * ptotal + 1);
    }

    public static Iterable<Cycle> solve(DonationGraph g) {
        ArrayList<Cycle> solution = new ArrayList<Cycle>();
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();

        ArrayList<Integer> toremove = new ArrayList<Integer>();
        for (int v : g.getVertices()) {
            if (g.getNumNeighbors(v) == 0 || g.getNumParents(v) == 0) {
                toremove.add(v);
            }
        }
        for (int v : toremove) {
            g.remove(v);
        }

        Tuple[] tuples = new Tuple[g.getNumVertices()];
        for (int v : g.getVertices()) {
            tuples[v] = new Tuple(v, rank(g, v));
            pq.offer(tuples[v]);
        }

        Tuple elem; LinkedHashSet<Integer> changed; Cycle c;
        while (!pq.isEmpty()) {
            changed = new LinkedHashSet<Integer>();
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
                tuples[ch] = new Tuple(ch, rank(g, ch));
                pq.offer(tuples[ch]);
            }
        }

        return solution;
    }

}
