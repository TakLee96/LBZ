import lbz.*;

public class TestCycle {

    public static void main(String[] args) {
        DonationGraph g = new DonationGraph("in/305.in");
        CycleGraph cg = new CycleGraph(g);
        Iterable<Cycle> result = ExactSolver.solve(cg);
        System.out.println();
        for(Cycle c: result) {
            System.out.println(c);
        }
        System.out.println("Score is " + Evaluate.score(g, result));
    }

}
