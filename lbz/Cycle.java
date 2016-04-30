package lbz;

import java.util.Arrays;
import java.io.FileWriter;
import java.io.BufferedWriter;

/** A class for cycle, as well as result recording and output generating.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Cycle {

    private int weight;
    public int getWeight() {
        return weight;
    }

    private int[] vertices;
    public Cycle(int[] vs, int w) {
        if (vs.length > 5)
            throw new RuntimeException("cycle too long");
        Arrays.sort(vs);
        this.vertices = vs;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        Cycle other = (Cycle) o;
        if (vertices.length != other.vertices.length) {
            return false;
        }
        for (int v : vertices) {
            if (Arrays.binarySearch(other.vertices, v) < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int product = 1;
        for (int v : vertices) {
            product *= (v + 1);
        }
        return product;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.length; i++) {
            sb.append(vertices[i]);
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static void output(Iterable<Cycle> cycles, String filename) {
        StringBuilder sb = new StringBuilder();
        for (Cycle c : cycles) {
            sb.append(c.toString());
            sb.append("; ");
        }
        sb.delete(sb.length() - 2, sb.length());
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
