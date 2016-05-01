package lbz;

import java.util.HashSet;

/** Evaluate the solution
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Evaluate {

    public static int score(DonationGraph g, Iterable<Cycle> cycles) {
        int maxscore = g.getNumVertices() + g.getNumChildren();
        int total = 0;
        HashSet<Integer> sanity = new HashSet<Integer>();
        for (Cycle c : cycles) {
            for (int v : c.getVertices()) {
                if (!sanity.contains(v)) {
                    sanity.add(v);
                } else {
                    System.out.println(c);
                    throw new RuntimeException("repetition error");
                }
            }
            total += c.weight(g);
        }
        return total - maxscore;
    }

}
