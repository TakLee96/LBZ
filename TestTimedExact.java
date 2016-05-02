import lbz.*;

import java.util.ArrayList;
import java.io.File;

/** Jim and Tak's competition of ExactSolver.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class TestTimedExact {

    public static class Solver implements Runnable {
        private DonationGraph g;
        private ArrayList<Cycle> solution;
        private Thread curr;
        public Solver(DonationGraph g, ArrayList<Cycle> result) {
            solution = result;
            this.g = g;
            curr = Thread.currentThread();
        }
        @Override
        public void run() {
            CycleGraph cg = new CycleGraph(g);
            if (cg.dead) return;
            Iterable<Cycle> s = ExactSolverTak.solve(cg);
            if (s == null) return;
            for (Cycle c : s) {
                solution.add(c);
            }
        }
    }

    public static void main(String[] args) {
        File folder = new File("record");
        File[] records = folder.listFiles();
        for (int i = 0; i < records.length; i++) {
            String ii = records[i].getPath().split("/")[1].split(".out")[0];
            System.out.print(ii);
            ArrayList<Cycle> solution = new ArrayList<Cycle>();
            DonationGraph g = new DonationGraph("in/"+ii+".in");
            Solver s = new Solver(g, solution);
            Thread t = new Thread(s);
            t.start();
            long begin = System.currentTimeMillis();
            boolean timedOut = false;
            long elapsed = 0;
            while (elapsed < 5000 && t.isAlive()) {
                elapsed = System.currentTimeMillis() - begin;
            }
            if (elapsed >= 5000) {
                System.out.println(" timed out");
                t.interrupt();
            } else {
                int score = Evaluate.score(g, solution);
                System.out.println(" succeed with score " + score);
            }
        }
    }

}
