package lbz;

import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

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
        if (vertices == null) return null;
        return vertices.clone();
    }

    public Cycle(int[] vs) {
        if (vs != null && vs.length > 5)
            throw new RuntimeException("cycle too long");
        this.vertices = vs;
    }

    public int weight(DonationGraph g) {
        if (vertices == null) {
            return 0;
        }
        int total = 0;
        for (int v : vertices) {
            total += g.weight(v);
        }
        return total;
    }

    public int weight(CycleGraph cg) {
        if (vertices == null) {
            return 0;
        }
        int total = 0;
        for (int v : vertices) {
            total += (cg.isChild(v)) ? 2 : 1;
        }
        return total;
    }

    public boolean shareVertex(Cycle other) {
        return ArrayUtils.intersect(other.vertices, vertices);
    }

    public Cycle merge(int[] other) {
        if (vertices == null) return new Cycle(other);
        int length = other.length + vertices.length;
        if (other.length + vertices.length > 5) {
            throw new RuntimeException("merged cycle too long");
        }
        if (ArrayUtils.intersect(vertices, other)) {
            return null;
        }
        return new Cycle(ArrayUtils.extend(other, vertices));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return ArrayUtils.shiftEquals(((Cycle) o).vertices, vertices);
    }

    @Override
    public int hashCode() {
        if (vertices == null) return 0;
        int[] shifted = ArrayUtils.shiftedCopy(vertices);
        int product = 1;
        for (int i = 0; i < shifted.length; i++) {
            product += (shifted[i] + 1) * rand[i];
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

    public boolean debugCheckExistence(DonationGraph g) {
        int j;
        for (int i = 0; i < vertices.length - 1; i++) {
            j = i + 1 % vertices.length;
            if (!g.isConnected(vertices[i], vertices[j])) {
                System.out.println("In cycle [" + toString() + "], edge (" +
                vertices[i] + "->" + vertices[j] + ") does not exist in original graph");
                return false;
            }
        }
        return true;
    }

    private static int readscore(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            for (int i = 0; i < 6; i++) {
                line = br.readLine();
            }
            return Integer.parseInt(line.trim().split(":")[1].trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 213;
        }
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
        } else {
            sb.append("None");
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
            String[] two = filename.split("/");
            String record = "record/" + two[1];
            File rf = new File(record);
            if (Solver.isbest) {
                System.out.println("SHIT!!! NEW BEST FOUND FOR " + two[1]);
                String newname = "best/" + two[1];
                BufferedWriter bw = new BufferedWriter(new FileWriter(newname));
                bw.write(result, 0, result.length());
                bw.close();
                if (rf.exists() && !rf.isDirectory())
                    rf.delete();
            } else {
                if (!(rf.exists() && !rf.isDirectory()) || score > readscore(record)) {
                    System.out.println("SHIT!!! NEW RECORD FOUND FOR " + two[1]);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(record));
                    bw.write(result, 0, result.length());
                    bw.close();
                }
            }
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

    public static void main(String[] args) {
        Cycle a = new Cycle(new int[]{ 1, 2, 3 });
        Cycle b = new Cycle(new int[]{ 1, 2, 3 });
        Cycle c = new Cycle(new int[]{ 2, 3, 1 });
        Cycle d = new Cycle(new int[]{ 2, 3 }).merge(new int[]{ 1 });
        Cycle e = new Cycle(null).merge(a.vertices);
        System.out.println(a.equals(b));
        System.out.println(b.equals(c));
        System.out.println(c.equals(d));
        System.out.println(d.equals(e));
    }

}
