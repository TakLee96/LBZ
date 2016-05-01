import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import lbz.*;

/** Driver class for LBZ searching algorithm.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class FinalOutput {

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("solutions.out"));
                for (int i = 1; i <= 492; i++) {
                    BufferedReader br = new BufferedReader(new FileReader("out/" + i + ".out"));
                    String line = br.readLine().trim();
                    br.close();
                    line += "\n";
                    bw.write(line, 0, line.length());
                }
                bw.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return;
        }
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
