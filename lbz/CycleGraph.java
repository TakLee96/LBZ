package lbz;

import java.util.ArrayList;
import java.util.HashSet;

/** A graph of cycle in DonationGraph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class CycleGraph extends UndirectedGraph {

    private static ArrayList<Cycle> extractCycles(DonationGraph g) {
        HashSet<Cycle> cycles = new HashSet<Cycle>();
        int[] path = new int[5];
        for (int i : g.getVertices()) {
            findCycle(g, i, i, 0, 0, path, cycles, new Memo(g.getNumVertices()));
            if (cycles.size() > Constants.maxSearchCycle)
                throw new RuntimeException("cycle extraction failed");
        }
        return new ArrayList<Cycle>(cycles);
    }

    private static void findCycle(
        DonationGraph g, int vertex, int root, int weight, int depth,
        int[] path, HashSet<Cycle> cycles, Memo m) {
        weight += (g.isChild(vertex)) ? 2 : 1;
        path[depth] = vertex;
        if (m.contains(vertex, depth)) {
            m.build(vertex, depth, path, weight, cycles);
        } else {
            m.init(depth, vertex);
            if (g.isConnected(vertex, root)) {
                int[] newpath = ArrayUtils.build(path, depth);
                cycles.add(new Cycle(newpath));
                m.add(newpath, depth, weight, g);
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

    private ArrayList<Cycle> cycles;
    public Cycle getCycle(int i) {
        return cycles.get(i);
    }
    public Iterable<Cycle> getCycles() {
        return cycles;
    }

    private DonationGraph g;
    public boolean isChild(int v) {
        return g.isChild(v);
    }

    @SuppressWarnings("unchecked")
    public CycleGraph(DonationGraph g) {
        super();
        this.g = g;
        cycles = extractCycles(g);
        numVertices = cycles.size();
        vertices = new HashSet<Integer>();
        neighbors = new HashSet[numVertices];
        for (int i = 0; i < numVertices; i++) {
            neighbors[i] = new HashSet<Integer>();
            vertices.add(i);
        }
        Cycle cyclei = null, cyclej = null;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                cyclei = cycles.get(i);
                cyclej = cycles.get(j);
                if (cyclei.shareVertex(cyclej)) {
                    numEdges += 1;
                    neighbors[i].add(j);
                    neighbors[j].add(i);
                }
            }
        }
    }

}
