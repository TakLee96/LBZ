package lbz;

import java.util.LinkedHashSet;
import java.util.Collection;

/** A generic graph.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class DirectedGraph {

    @SuppressWarnings("unchecked")
    public DirectedGraph(boolean[][] adjacency) {
        numVertices = adjacency.length;
        neighbors = new LinkedHashSet[numVertices];
        parents = new LinkedHashSet[numVertices];
        vertices = new LinkedHashSet<Integer>();
        for (int i = 0; i < numVertices; i++) {
            neighbors[i] = new LinkedHashSet<Integer>();
            parents[i] = new LinkedHashSet<Integer>();
            vertices.add(i);
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjacency[i][j]) {
                    numEdges += 1;
                    neighbors[i].add(j);
                    parents[j].add(i);
                }
            }
        }
    }

    protected int numVertices = 0;
    public int getNumVertices() {
        return numVertices;
    }

    protected int numEdges = 0;
    public int getNumEdges() {
        return numEdges;
    }

    protected LinkedHashSet<Integer>[] neighbors;
    protected LinkedHashSet<Integer>[] parents;
    public boolean isConnected(int u, int v) {
        return neighbors[u].contains(v);
    }

    public int getNumNeighbors(int v) {
        return neighbors[v].size();
    }

    public int getNumParents(int v) {
        return parents[v].size();
    }

    public Collection<Integer> neighbors(int v) {
        return neighbors[v];
    }

    public Collection<Integer> parents(int v) {
        return parents[v];
    }

    protected LinkedHashSet<Integer> vertices;
    public Collection<Integer> getVertices() {
        return vertices;
    }

    public void remove(int v) {
        for (int n : neighbors[v]) {
            parents[n].remove(v);
        }
        for (int p : parents[v]) {
            neighbors[p].remove(v);
        }
        neighbors[v].clear();
        parents[v].clear();
        vertices.remove(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : vertices) {
            if (neighbors[i].size() != 0) {
                sb.append(i + " -> ");
                for (int n : neighbors[i]) {
                    sb.append(n + " ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
