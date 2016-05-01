package lbz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

/** The approximate algorithm that directly solves g
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class OurSolver {

    // private static int weight(DonationGraph g, int[] path) {
    //     int total = 0;
    //     for (int v : path) {
    //         if (g.isChild(v)) {
    //             total += 2;
    //         } else {
    //             total += 1;
    //         }
    //     }
    //     return total;
    // }
    //
    // private static boolean contains(int[] arr, int e, int depth) {
    //     for (int i = 0; i < depth; i++) {
    //         if (arr[i] == e) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    //
    // private static int[] build(int[] arr, int depth) {
    //     int[] result = new int[depth+1];
    //     System.arraycopy(arr, 0, result, 0, depth+1);
    //     return result;
    // }
    //
    // private static boolean flag = false;
    // private static void findCycle(DonationGraph g, int u, int depth, int v,
    //                               ArrayList<Cycle> found, int[] path, int w) {
    //     if (flag) return;
    //     path[depth] = u;
    //     w += (g.isChild(u)) ? 2 : 1;
    //
    //     if (g.isConnected(u, v)) {
    //         Cycle c = new Cycle(build(path, depth), w);
    //         if (c.getWeight() > 5 || c.getVertices().length == 5) {
    //             flag = true;
    //             found.clear();
    //         }
    //         found.add(c);
    //     }
    //     if (depth < 4) {
    //         for (int n : g.neighbors(u)) {
    //             if (!contains(path, n, depth)) {
    //                 findCycle(g, n, depth+1, v, found, path, w);
    //             }
    //         }
    //     }
    // }

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
            if (c.getWeight() > maxweight) {
                maxweight = c.getWeight();
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
                Cycle c = new Cycle(newpath.clone(), weight);
                if (c.getWeight() > 5 || c.getVertices().length == 5) {
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

    public static Iterable<Cycle> solve(DonationGraph g) {
        ArrayList<Cycle> solution = new ArrayList<Cycle>();

        int min, curr, minIndex = -1; Cycle c;
        while (!g.getVertices().isEmpty()) {
            min = g.getNumVertices();
            for (int v : g.getVertices()) {
                curr = Math.min(g.getNumNeighbors(v), g.getNumParents(v));
                if (curr < min) {
                    min = curr;
                    minIndex = v;
                }
            }
            c = largestCycle(g, minIndex);
            if (c != null) {
                solution.add(c);
                for (int v : c.getVertices()) {
                    g.remove(v);
                }
            } else {
                g.remove(minIndex);
            }
        }

        return solution;
    }

}
