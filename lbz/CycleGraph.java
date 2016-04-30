package lbz;

import java.util.ArrayList;

/** A graph of cycle in DonationGraph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class CycleGraph extends Graph {

    private static ArrayList<Cycle> extractCycles(DonationGraph g) {
        /* TODO: Extract all the cycles in g */
        int v = g.getNumVertices();
        ArrayList<Cycle> result = new ArrayList<Cycle>();
        for (int i = 1; i < v+1; i++) {
            int root = i;
            ArrayList<Cycle> cur = CycleGraph.helper(5, new ArrayList<int>, root, g, result);
            for ()
        }
        return null;
    }
    
    private static ArrayList<Cycle> helper(int weight, int deg, ArrayList<int> path, int root, DonationGraph g, HashSet<Cycle> partialRes) {
        for(int i = deg; i>0; i--) {
            for (int vertex: g.neighbors(i)) {
                int w = 0;
                if (g.isChild(v)) { 
                    w=2;
                }
                else w=1;
                if(vertex==target) {
                    ArrayList<int> p = new ArrayList<int>();
                    p.add(vertex);
                    partialRes.add(new Cycle(p, weight+w));
                } 
                helper(weight+w, )
            }
        }
        return null;
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
