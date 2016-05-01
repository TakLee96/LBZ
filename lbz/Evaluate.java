package lbz;

/** Evaluate the solution
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class Evaluate {

    public static int score(DonationGraph g, Iterable<Cycle> cycles) {
        int maxscore = g.getNumVertices() + g.getNumChildren();
        int total = 0;
        for (Cycle c : cycles) {
            total += c.getWeight();
        }
        return total - maxscore;
    }

}
