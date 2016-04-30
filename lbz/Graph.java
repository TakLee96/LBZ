package lbz;

import java.io.FileReader;
import java.io.BufferedReader;

/** The graph representation of the donation network.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Graph {

    private int numVertices;
    public int getNumVertices() {
        return numVertices;
    }

    private int numEdges;
    public int getNumEdges() {
        return numEdges;
    }

    private int[] childVertices;
    public int[] getChildVertices() {
        return childVertices.clone();
    }

    private boolean[][] connected;
    public boolean isConnected(int u, int v) {
        return connected[u][v];
    }
    public int[] neighbors(int v) {
        int[] temp = new int[numVertices];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            if (isConnected(v, i)) {
                temp[index] = i;
                index += 1;
            }
        }
        int[] result = new int[index];
        System.arraycopy(temp, 0, result, 0, index);
        return result;
    }
    public int[] parents(int v) {
        int[] temp = new int[numVertices];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            if (isConnected(i, v)) {
                temp[index] = i;
                index += 1;
            }
        }
        int[] result = new int[index];
        System.arraycopy(temp, 0, result, 0, index);
        return result;
    }

    public Graph(String filename) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (connected[i][j]) {
                    sb.append("1 ");
                } else {
                    sb.append("0 ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
