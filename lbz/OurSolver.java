package lbz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

/** The approximate algorithm that directly solves g
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class OurSolver {

    private static int weight(DonationGraph g, int[] path) {
        int total = 0;
        for (int v : path) {
            if (g.isChild(v)) {
                total += 2;
            } else {
                total += 1;
            }
        }
        return total;
    }

    private static boolean contains(int[] arr, int e, int depth) {
        for (int i = 0; i < depth; i++) {
            if (arr[i] == e) {
                return true;
            }
        }
        return false;
    }

    private static int[] build(int[] arr, int depth) {
        int[] result = new int[depth+1];
        System.arraycopy(arr, 0, result, 0, depth+1);
        return result;
    }

    private static boolean flag = false;
    private static void findCycle(DonationGraph g, int u, int depth, int v,
                                  ArrayList<Cycle> found, int[] path, int w) {
        if (flag) return;
        path[depth] = u;
        w += (g.isChild(u)) ? 2 : 1;
        if (g.isConnected(u, v)) {
            Cycle c = new Cycle(build(path, depth), w);
            if (c.getWeight() > 5 || c.getVertices().length == 5) {
                flag = true;
                found.clear();
            }
            found.add(c);
        }
        if (depth < 4) {
            for (int n : g.neighbors(u)) {
                if (!contains(path, n, depth)) {
                    findCycle(g, n, depth+1, v, found, path, w);
                }
            }
        }
    }

    private static Cycle largestCycle(DonationGraph g, int v) {
        if (g.getNumNeighbors(v) == 0) {
            return null;
        }
        ArrayList<Cycle> found = new ArrayList<Cycle>();
        for (int n : g.neighbors(v)) {
            findCycle(g, n, 1, v, found,
                new int[]{v, -1, -1, -1, -1},
                (g.isChild(v)) ? 2 : 1
            );
        }
        flag = false;
        int maxweight = 0; Cycle maxcycle = null;
        for (Cycle c : found) {
            if (c.getWeight() > maxweight) {
                maxweight = c.getWeight();
                maxcycle = c;
            }
        }
        return maxcycle;
    }

    public static Iterable<Cycle> solve(DonationGraph g) {
        ArrayList<Cycle> solution = new ArrayList<Cycle>();

        int min, curr, minIndex = -1; Cycle c;
        while (!g.getVertices().isEmpty()) {
            System.out.println(g.getVertices().size());
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
