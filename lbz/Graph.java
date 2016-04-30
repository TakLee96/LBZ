package lbz;

/** A generic graph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public abstract class Graph {

    protected int numVertices = 0;
    public int getNumVertices() {
        return numVertices;
    }

    protected int numEdges = 0;
    public int getNumEdges() {
        return numEdges;
    }

    protected boolean[][] connected = null;
    public boolean isConnected(int u, int v) {
        return connected[u][v];
    }

    protected int[] numOutEdges = null;
    public int getNumNeighbors(int v) {
        return numOutEdges[v];
    }

    protected int[] numInEdges = null;
    public int getNumParents(int v) {
        return numInEdges[v];
    }

    public int[] neighbors(int v) {
        int[] result = new int[getNumNeighbors(v)];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            if (isConnected(v, i)) {
                result[index] = i;
                index += 1;
            }
        }
        return result;
    }

    public int[] parents(int v) {
        int[] result = new int[getNumParents(v)];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            if (isConnected(i, v)) {
                result[index] = i;
                index += 1;
            }
        }
        return result;
    }

    public void remove(int v) {
        int[] vn = neighbors(v);
        int[] pn = parents(v);
        for (int n : vn) {
            numInEdges[n] -= 1;
        }
        for (int p : pn) {
            numOutEdges[n] -= 1;
        }
        for (int i = 0; i < numVertices; i++) {
            connected[v][i] = false;
            connected[i][v] = false;
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
