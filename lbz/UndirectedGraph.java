package lbz;

import java.util.LinkedHashSet;
import java.util.Collection;

/** A generic graph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class UndirectedGraph {

    protected int numVertices = 0;
    public int getNumVertices() {
        return numVertices;
    }

    protected int numEdges = 0;
    public int getNumEdges() {
        return numEdges;
    }

    protected LinkedHashSet<Integer>[] neighbors;
    public boolean isConnected(int u, int v) {
        return neighbors[u].contains(v);
    }

    public int getNumNeighbors(int v) {
        return neighbors[v].size();
    }

    public Collection<Integer> neighbors(int v) {
        return neighbors[v];
    }

    protected LinkedHashSet<Integer> vertices;
    public Collection<Integer> getVertices() {
        return vertices;
    }

    public void remove(int v) {
        for (int n : neighbors[v]) {
            neighbors[n].remove(v);
        }
        neighbors[v].clear();
        vertices.remove(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (isConnected(i, j)) {
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
