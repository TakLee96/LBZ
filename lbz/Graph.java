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
