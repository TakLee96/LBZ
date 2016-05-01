package lbz;

import java.util.ArrayList;
import java.util.HashSet;

/** A graph of cycle in DonationGraph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class CycleGraph extends Graph {

    private static ArrayList<Cycle> extractCycles(DonationGraph g) {
        /* TODO: Extract all the cycles in g */
        int v = g.getNumVertices();
        HashSet<Cycle> result = new HashSet<Cycle>();
        for (int i = 1; i < v+1; i++) {
            int root = i;
            ArrayList<Integer> p = new ArrayList<Integer>();
            p.add(i);
            HashSet<Cycle> cur = CycleGraph.helper(0, 5, p, root, g, result);
            %%Make sure there's no duplication
            for (Cycle c: cur) {
                result.add(c);
            }
        }
        ArrayList<Cycle> res = new ArrayList<Cycle>(result);
        return res;
    }

    private static HashSet<Cycle> helper(int weight, int deg, ArrayList<Integer> path, int root, DonationGraph g, HashSet<Cycle> partialRes) {
        if (deg < 1) {
            return null;
        }
        for(int i = deg; i>0; i--) {
            for (int vertex: g.neighbors(root)) {
                int w = 0;
                if (g.isChild(vertex)) {
                    w=2;
                }
                else w=1;
                ArrayList<Integer> p = new ArrayList<Integer>(path);
                if(vertex==path.get(0)) {
                    partialRes.add(new Cycle(p, weight+w));
                }
                p.add(vertex);
                CycleGraph.helper(weight+w, deg-1, p, vertex, g, partialRes);
            }
        }
        return partialRes;
    }

    private ArrayList<Cycle> cycles;
    public Cycle getCycle(int i) {
        return cycles.get(i);
    }

    public CycleGraph(DonationGraph g) {
        super();
        cycles = extractCycles(g);
        numVertices = cycles.size();
        numInEdges = new int[numVertices];
        numOutEdges = new int[numVertices];
        connected = new boolean[numVertices][numVertices];
        Cycle cyclei = null, cyclej = null;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                cyclei = cycles.get(i);
                cyclej = cycles.get(j);
                if (cyclei.shareVertex(cyclej)) {
                    connected[i][j] = true;
                    connected[j][i] = true;
                    numEdges += 2;
                    numInEdges[i] += 1;
                    numInEdges[j] += 1;
                    numOutEdges[i] += 1;
                    numOutEdges[j] += 1;
                } else {
                    connected[i][j] = false;
                    connected[j][i] = false;
                }
            }
        }
    }

}
