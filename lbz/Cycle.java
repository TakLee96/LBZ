package lbz;

import java.io.FileWriter;
import java.io.BufferedWriter;

/** A class for recording and generating output.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Cycle {

    private int[] vertices;
    public Cycle(int[] vs) {
        this.vertices = vs;
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
