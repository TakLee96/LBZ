package lbz;

import java.io.FileReader;
import java.io.BufferedReader;

/** The graph representation of the donation network.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class DonationGraph extends Graph {

    private int[] childVertices;
    public int[] getChildVertices() {
        return childVertices.clone();
    }

    public DonationGraph(String filename) {
        super();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            numVertices = Integer.parseInt(br.readLine().trim());

            String[] cvs = br.readLine().trim().split(" ");
            childVertices = new int[cvs.length];
            for (int i = 0; i < cvs.length; i++)
                childVertices[i] = Integer.parseInt(cvs[i]);

            connected = new boolean[numVertices][numVertices];
            String line = br.readLine();
            String[] neighbors;
            int v = 0;
            numEdges = 0;
            while (line != null) {
                line = line.trim();
                neighbors = line.split(" ");
                for (int u = 0; u < numVertices; u++) {
                    if (neighbors[u].equals("0")) {
                        connected[v][u] = false;
                    } else if (neighbors[u].equals("1")) {
                        connected[v][u] = true;
                        numEdges += 1;
                    } else {
                        throw new RuntimeException(neighbors[u]);
                    }
                }
                v += 1;
                line = br.readLine();
            }
            if (v != numVertices)
                throw new RuntimeException("input file corrupted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
