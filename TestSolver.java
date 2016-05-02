import lbz.*;

/** Test class for different solvers.
 * @author Jim Bai, Tak Li, Zirui Zhou */
 public class TestSolver {

     public static void main(String[] args) {
         if (args == null || args.length < 2) {
             System.out.println("Usage: java TestSolver [o/e/a] [0-492]");
         }
         Iterable<Cycle> solution = null;
         DonationGraph g = new DonationGraph("in/" + args[1] + ".in");
         long time = System.currentTimeMillis();
         switch (args[0]) {
             case "o": solution = OurSolver.solve(g); break;
             case "e": solution = ExactSolver.solve(new CycleGraph(g)); break;
             case "a": solution = ApproxSolver.solve(new CycleGraph(g)); break;
             default: System.out.println("Usage: java TestSolver [o/e/a] [0-492]"); return;
         }
         String outfilename = "out/" + args[1] + "." + args[0] + ".out";
         Cycle.output(solution, g, outfilename);
         time = System.currentTimeMillis() - time;
         System.out.println("Done. Check: (" + outfilename + ") [" + time + "ms]");
     }

 }
