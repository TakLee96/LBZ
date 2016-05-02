import lbz.*;

import java.io.File;

/** Scan records for potential chance to move it to best.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class TestScanner {

    private static void check(int i) {
        if (i == 148) {
            System.out.println("FUCK 148!!!");
            return;
        }
        DonationGraph g = new DonationGraph("in/" + i + ".in");
        try {
            CycleGraph cg = new CycleGraph(g);
            System.out.println("i="+i+", v="+g.getNumVertices()+", e="+
            g.getNumEdges()+", c="+cg.getNumVertices()+", ce="+cg.getNumEdges());
        } catch (Exception e) {
            return;
        }
    }

    public static void main(String[] args) {
        File folder = new File("record");
        File[] records = folder.listFiles();

        System.out.println("Records:");
        for (File record : records) {
            check(Integer.parseInt(record.getPath().split("/")[1].split(".out")[0]));
        }
    }

}
