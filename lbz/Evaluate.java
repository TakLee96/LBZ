package lbz;

import java.util.HashMap;

/** Evaluate the solution
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Evaluate {

    public static int score(DonationGraph g, Iterable<Cycle> cycles) {
        int maxscore = g.getNumVertices() + g.getNumChildren();
        int total = 0;
        boolean wrong = false;
        HashMap<Integer, Cycle> sanity = new HashMap<Integer, Cycle>();
        for (Cycle c : cycles) {
            for (int v : c.getVertices()) {
                if (!sanity.containsKey(v)) {
                    sanity.put(v, c);
                } else {
                    System.out.println(sanity.get(v));
                    System.out.println(c);
                    wrong = true;
                    System.out.println("----REPETITION----");
                }
            }
            if (!c.debugCheckExistence(g)) {
                wrong = true;
            }
            total += c.weight(g);
        }
        if (wrong) {
            return 213;
        }
        return total - maxscore;
    }

}
