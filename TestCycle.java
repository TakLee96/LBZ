import lbz.*;

public class TestCycle {

    public static void main(String[] args) {
        DonationGraph g = new DonationGraph("in/368.in");
        CycleGraph cg = new CycleGraph(g);
        System.out.println("start solving");
        Iterable<Cycle> result = ExactSolver.solve(cg);
        System.out.println();
        for(Cycle c: result) {
            System.out.println(c);
        }
        System.out.println("Score is " + Evaluate.score(g, result));
    }

}
