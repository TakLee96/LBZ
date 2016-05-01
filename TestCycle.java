import lbz.*;

public class TestCycle {

    public static void main(String[] args) {
        DonationGraph g = new DonationGraph("in/0.in");
        CycleGraph cg = new CycleGraph(g);
        System.out.println(g);
        for (int i = 0; i < cg.getNumVertices(); i++) {
            System.out.println(cg.getCycle(i));
        }
    }

}
