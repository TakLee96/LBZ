package lbz;

import java.util.HashSet;
import java.io.FileReader;
import java.io.BufferedReader;

/** The graph representation of the donation network.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class DonationGraph extends DirectedGraph {

    private HashSet<Integer> children;
    public boolean isChild(int v) {
        return children.contains(v);
    }
    public int getNumChildren() {
        return children.size();
    }

    private static HashSet<Integer> kids;
    private static boolean[][] getAdjacencyFromFile(String filename) {
        boolean[][] connected = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            int numv = Integer.parseInt(br.readLine().trim());

            String line = br.readLine().trim();
            kids = new HashSet<Integer>();
            if (line.length() != 0) {
                String[] cvs = line.split(" ");
                for (int i = 0; i < cvs.length; i++) {
                    kids.add(Integer.parseInt(cvs[i]));
                }
            }

            connected = new boolean[numv][numv];
            line = br.readLine();
            String[] neighbors;
            int v = 0;
            while (line != null) {
                line = line.trim();
                neighbors = line.split(" ");
                for (int u = 0; u < numv; u++) {
                    if (neighbors[u].equals("0")) {
                        connected[v][u] = false;
                    } else if (neighbors[u].equals("1")) {
                        connected[v][u] = true;
                    } else {
                        System.out.println("Illegal character: \"" + neighbors[u] + "\"");
                        throw new RuntimeException();
                    }
                }
                v += 1;
                line = br.readLine();
            }
            if (v != numv)
                throw new RuntimeException("input file corrupted.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return connected;
        }
    }

    public DonationGraph(String filename) {
        super(getAdjacencyFromFile(filename));
        children = kids;
        this.filename = filename;
    }

    String filename;
    public DonationGraph clone() {
        return new DonationGraph(filename);
    }

}
