package lbz;

import java.util.ArrayList;

/** A graph of cycle in DonationGraph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class CycleGraph extends Graph {

    private static ArrayList<Cycle> extractCycles(DonationGraph g) {
        /* TODO: Extract all the cycles in g */
        return null;
    }

    public CycleGraph(DonationGraph g) {
        super();
        ArrayList<Cycle> cycles = extractCycles(g);
        numVertices = cycles.size();
        numInEdges = new int[numVertices];
        numOutEdges = new int[numVertices]
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
