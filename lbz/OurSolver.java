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

    private static boolean contains(int[] arr, int e) {
        for (int elem : arr) {
            if (elem == e) {
                return true;
            }
        }
        return false;
    }

    private static boolean flag = false;
    private static void findCycle(DonationGraph g, int u, int depth, int v,
                                  ArrayList<Cycle> found, int[] path) {
        if (flag) return;
        int[] newpath = new int[path.length + 1];
        System.arraycopy(path, 0, newpath, 0, path.length);
        newpath[path.length] = u;
        if (g.isConnected(u, v)) {
            Cycle c = new Cycle(newpath, weight(g, newpath));
            if (c.getWeight() > 5 || c.getVertices().length == 5) {
                flag = true;
                found.clear();
            }
            found.add(c);
        }
        if (depth < 4) {
            for (int n : g.neighbors(u)) {
                if (!contains(newpath, n)) {
                    findCycle(g, n, depth+1, v, found, newpath);
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
            findCycle(g, n, 1, v, found, new int[]{v});
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
        HashSet<Integer> effective = new HashSet<Integer>();
        for (int i = 0; i < g.getNumVertices(); i++) {
            if (g.getNumNeighbors(i) > 0 && g.getNumParents(i) > 0) {
                effective.add(i);
            }
        }

        ArrayList<Cycle> solution = new ArrayList<Cycle>();

        int min, curr, minIndex = -1; Cycle c;
        while (!effective.isEmpty()) {
            System.out.println(effective.size());
            min = g.getNumVertices();
            for (int v : effective) {
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
                    effective.remove(v);
                }
            } else {
                g.remove(minIndex);
                effective.remove(minIndex);
            }
        }

        return solution;
    }

}
