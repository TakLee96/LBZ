package lbz;

import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.BufferedWriter;

/** A class for cycle, as well as result recording and output generating.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Cycle {

    private static final Random r = new Random();
    private static final int[] rand = new int[]{
        r.nextInt(500) + 1, r.nextInt(500) + 1,
        r.nextInt(500) + 1, r.nextInt(500) + 1,
        r.nextInt(500) + 1
    };

    private int weight;
    public int getWeight() {
        return weight;
    }

    private int[] vertices;
    public int[] getVertices() {
        return vertices.clone();
    }

    public Cycle(int[] vs, int w) {
        if (vs != null && vs.length > 5)
            throw new RuntimeException("cycle too long");
        if (vs != null)
            Arrays.sort(vs);
        this.vertices = vs;
        this.weight = w;
    }

    public Cycle(List<Integer> vs, int w) {
        if (vs.size() > 5)
            throw new RuntimeException("cycle too long");
        vertices = new int[vs.size()];
        int index = 0;
        for (int v : vs) {
            vertices[index] = v;
            index += 1;
        }
        Arrays.sort(vertices);
        this.weight = w;
    }

    public boolean shareVertex(Cycle other) {
        for (int v : vertices) {
            if (Arrays.binarySearch(other.vertices, v) >= 0) {
                return true;
            }
        }
        return false;
    }

    public Cycle merge(int[] other, int w) {
        if (vertices == null) return new Cycle(other, w);
        int totallength = other.length + vertices.length;
        if (totallength > 5)
            throw new RuntimeException("merged cycle too long");
        int[] merged = new int[totallength];
        System.arraycopy(vertices, 0, merged, 0, vertices.length);
        System.arraycopy(other, 0, merged, vertices.length, other.length);
        return new Cycle(merged, w + weight);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        Cycle other = (Cycle) o;
        if (vertices == other.vertices) return true;
        if (vertices.length != other.vertices.length) {
            return false;
        }
        return Arrays.equals(vertices, other.vertices);
    }

    @Override
    public int hashCode() {
        if (vertices == null) return 0;
        int product = 1;
        for (int i = 0; i < vertices.length; i++) {
            product += (vertices[i] + 1) * rand[i];
        }
        return product;
    }

    @Override
    public String toString() {
        if (vertices == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.length; i++) {
            sb.append(vertices[i]);
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static void output(Iterable<Cycle> cycles, DonationGraph g,
                              String filename) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Cycle c : cycles) {
            sb.append(c.toString());
            sb.append("; ");
            count += 1;
        }
        sb.delete(sb.length() - 2, sb.length());
        /* v DEBUG v */
        sb.append("\nAnalysis:");
        sb.append("\n# Vertices: " + g.getNumVertices());
        sb.append("\n# Edges: " + g.getNumEdges());
        sb.append("\n# Cycles Found: " + count);
        int score = Evaluate.score(g, cycles);
        sb.append("\nScore: " + score);
        if (score < 0)
            System.out.println("This one is hard!");
        /* ^ DEBUG ^ */
        String result = sb.toString();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(result, 0, result.length());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
