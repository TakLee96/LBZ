import lbz.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/** Test class for different solvers.
 * @author Jim Bai, Tak Li, Zirui Zhou */
 public class TestOutput {

     public static void main(String[] args) {
         if (args == null || args.length < 1) {
             System.out.println("Usage: java TestSolver [0-492]");
         }
         String infilename = "in/" + args[0] + ".in";
         String outfilename = "out/" + args[0] + ".out";
         try {
             BufferedReader br = new BufferedReader(new FileReader(outfilename));
             String line = br.readLine();
             String[] cycles = line.trim().split(";");
             ArrayList<Cycle> list = new ArrayList<Cycle>();
             for (String c : cycles) {
                 String[] cycle = c.trim().split(" ");
                 int[] cint = new int[cycle.length];
                 for (int i = 0; i < cint.length; i++) {
                     cint[i] = Integer.parseInt(cycle[i]);
                 }
                 list.add(new Cycle(cint));
             }
             int score = Evaluate.score(new DonationGraph(infilename), list);
             if (score <= 0) {
                 System.out.println("OK");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

 }
