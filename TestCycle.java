import lbz.*;

public class TestCycle {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter instance number [0-492]");
            return;
        }
        DonationGraph g = new DonationGraph("in/" + args[0] + ".in");
        CycleGraph cg = new CycleGraph(g);
        for (int i = 0; i < cg.getNumVertices(); i++) {
            System.out.println(cg.getCycle(i));
        }
    }

}
