import lbz.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/** Test class for different solvers.
 * @author Jim Bai, Tak Li, Zirui Zhou */
 public class TestOutput {

     public static void main(String[] args) {
         try {
             BufferedReader br = new BufferedReader(new FileReader("solutions.out"));
             boolean error = false;
             int total = 0;
             for (int i = 1; i <= 492; i++) {
                 String line = br.readLine().trim();
                 if (line.equals("None")) {
                     continue;
                 }
                 String[] cycles = line.split(";");
                 ArrayList<Cycle> list = new ArrayList<Cycle>();
                 for (String c : cycles) {
                     String[] cycle = c.trim().split(" ");
                     int[] cint = new int[cycle.length];
                     for (int j = 0; j < cint.length; j++) {
                         cint[j] = Integer.parseInt(cycle[j]);
                     }
                     list.add(new Cycle(cint));
                 }
                 int score = Evaluate.score(new DonationGraph("in/" + i + ".in"), list);
                 if (score > 0) {
                     error = true;
                 }
                 total += score;
             }
             if (!error) {
                 System.out.println("OK [score=" + total + "]");
             }
             br.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

 }
