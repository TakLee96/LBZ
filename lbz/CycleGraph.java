package lbz;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/** A graph of cycle in DonationGraph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class CycleGraph extends UndirectedGraph {

    private ArrayList<Cycle> extractCycles() {
        LinkedHashSet<Cycle> cycles = new LinkedHashSet<Cycle>();
        int[] path = new int[5];
        for (int i : g.getVertices()) {
            findCycle(i, i, 0, 0, path, cycles, new Memo(g.getNumVertices()));
        }
        return new ArrayList<Cycle>(cycles);
    }

    private void findCycle(
        int vertex, int root, int weight, int depth,
        int[] path, LinkedHashSet<Cycle> cycles, Memo m) {
        if (cycles.size() > Constants.maxSearchCycle)
            throw new RuntimeException("cycle extraction failed");
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
                        findCycle(u, root, weight, depth + 1, path, cycles, m);
                    }
                }
            }
        }
    }

    private ArrayList<Cycle> cycles;
    public Cycle getCycle(int i) {
        return cycles.get(i);
    }
    public int weight(int i) {
        return cycles.get(i).weight(g);
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
        cycles = extractCycles();
        numVertices = cycles.size();
        vertices = new LinkedHashSet<Integer>();
        neighbors = new LinkedHashSet[numVertices];
        for (int i = 0; i < numVertices; i++) {
            neighbors[i] = new LinkedHashSet<Integer>();
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

    @SuppressWarnings("unchecked")
    public CycleGraph(CycleGraph cg) {
        super();
        this.g = cg.g.clone();
        cycles = new ArrayList<Cycle>(cg.cycles);
        numVertices = cycles.size();
        vertices = new LinkedHashSet<Integer>(cg.vertices);
        neighbors = new LinkedHashSet[numVertices];
        for (int i = 0; i < numVertices; i++) {
            neighbors[i] = new LinkedHashSet<Integer>(cg.neighbors[i]);
        }
        numEdges = cg.numEdges;
    }

    public CycleGraph clone() {
        return new CycleGraph(this);
    }

}
