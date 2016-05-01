import java.io.BufferedWriter;
import lbz.*;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class FinalOutput {

    public static void main(String[] args) {
        BufferedWriter bw = Cycle.beginWriting();
        for (int i = 1; i <= 492; i++) {
            String filename = "in/" + i + ".in";
            DonationGraph g = new DonationGraph(filename);
            long time = System.currentTimeMillis();
            Cycle.writeOne(bw, g, Solver.solve(g));
            time = System.currentTimeMillis() - time;
            System.out.println("#" + i + " done. [" + time + "ms]");
        }
    }

}
