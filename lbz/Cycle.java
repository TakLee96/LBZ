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

    private int[] vertices;
    public int[] getVertices() {
        return vertices.clone();
    }

    public Cycle(int[] vs) {
        if (vs != null && vs.length > 5)
            throw new RuntimeException("cycle too long");
        if (vs != null) {
            Arrays.sort(vs);
            // TODO: DEBUG
            for (int i = 0; i < vs.length; i++) {
                for (int j = i + 1; j < vs.length; j++) {
                    if (vs[i] == vs[j]) {
                        for (int v : vs) {
                            System.out.print(v + " ");
                        }
                        throw new RuntimeException("repetition error");
                    }
                }
            }
        }
        this.vertices = vs;
    }

    public int weight(DonationGraph g) {
        int total = 0;
        for (int v : vertices) {
            total += (g.isChild(v)) ? 2 : 1;
        }
        return total;
    }

    public int weight(CycleGraph cg) {
        int total = 0;
        for (int v : vertices) {
            total += (cg.isChild(v)) ? 2 : 1;
        }
        return total;
    }

    public boolean shareVertex(Cycle other) {
        for (int v : vertices) {
            if (Arrays.binarySearch(other.vertices, v) >= 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(int[] arr, int elem) {
        return Arrays.binarySearch(arr, elem) >= 0;
    }

    public Cycle merge(int[] other) {
        if (vertices == null) return new Cycle(other);
        int totallength = other.length + vertices.length;
        if (totallength > 5)
            throw new RuntimeException("merged cycle too long");
        Arrays.sort(other);
        for (int v : vertices) {
            if (contains(other, v)) {
                return null;
            }
        }
        int[] merged = new int[totallength];
        System.arraycopy(vertices, 0, merged, 0, vertices.length);
        System.arraycopy(other, 0, merged, vertices.length, other.length);
        return new Cycle(merged);
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
        if (count != 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        /* v DEBUG v */
        sb.append("\nAnalysis:");
        sb.append("\n# Vertices: " + g.getNumVertices());
        sb.append("\n# Edges: " + g.getNumEdges());
        sb.append("\n# Cycles Found: " + count);
        int score = Evaluate.score(g, cycles);
        sb.append("\nScore: " + score);
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

    public static BufferedWriter beginWriting() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("solutions.out"));
            return bw;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void writeOne(BufferedWriter bw, DonationGraph g, Iterable<Cycle> solution) {
        StringBuilder sb = new StringBuilder();
        if (Evaluate.score(g, solution) > 0) {
            throw new RuntimeException("hey, wtf");
        }
        int count = 0;
        for (Cycle c : solution) {
            sb.append(c.toString());
            sb.append("; ");
            count += 1;
        }
        String result = "None\n";
        if (count != 0) {
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
            result = sb.toString();
        }
        try {
            bw.write(result, 0, result.length());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void doneWriting(BufferedWriter bw) {
        try {
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
