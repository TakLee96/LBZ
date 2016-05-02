package lbz;

import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeSet;

/** The exact solver for MWIS problem by Tak.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ExactSolverTak {
    
    CycleGraph cg;
    LinkedHashSet<Cycle> result;
    private ExactSolverTak(CycleGraph cg) {
        this.cg = cg;

        LinkedList<TreeSet<Integer>> ccs = new LinkedList<>();
        TreeSet<Integer> visited;
        TreeSet<Integer> fringe = new TreeSet<Integer>();
        removeDominance();
        TreeSet<Integer> effective = new TreeSet<Integer>(cg.getVertices());

        while (!effective.isEmpty()) {
            int one = effective.first();
            fringe.add(one);
            visited = new TreeSet<>();
            while (!fringe.isEmpty()) {
                int elem = fringe.pollFirst();
                if (!visited.contains(elem)) {
                    visited.add(elem);
                    for (int n : cg.neighbors(elem)) {
                        if (!visited.contains(n)) {
                            fringe.add(n);
                        }
                    }
                }
            }
            ccs.add(visited);
            effective.removeAll(visited);
        }

        System.out.println("# of Connected Component: " + ccs.size());

        LinkedHashSet<Cycle> solution = new LinkedHashSet<Cycle>();
        for (TreeSet<Integer> cc : ccs) {
            solution.addAll(solve(cc));
        }
        result = solution;
    }

    private void removeDominance() {
        LinkedHashSet<Integer> mySet;
        LinkedHashSet<Integer> otherSet;

        int count = 0;
        boolean remove = false;

        LinkedList<Integer> queue = new LinkedList<Integer>(cg.getVertices());
        while (!queue.isEmpty()) {
            int v = queue.pollLast();
            mySet = cg.neighbors(v);
            remove = false;
            for (int n : mySet) {
                otherSet = cg.neighbors(n);
                otherSet.remove(v);
                if (mySet.containsAll(otherSet) && cg.weight(v) == cg.weight(n)) {
                    remove = true;
                    count++;
                    break;
                }
                otherSet.add(v);
            }
            if (remove) {
                cg.remove(v);
            }
        }

        System.out.println("removed " + count + " dominance");
    }

    private LinkedHashSet<Cycle> solve(TreeSet<Integer> cc) {
        if (cc.size() == 0) throw new RuntimeException("empty?!");
        if (cc.size() == 1) {
            LinkedHashSet<Cycle> result = new LinkedHashSet<Cycle>();
            result.add(cg.getCycle(cc.last()));
            return result;
        }
        int first = cc.last();
        return choose(first, cc, new LinkedHashMap<Tuple, LinkedHashSet<Cycle>>());
    }

    private int weight(LinkedHashSet<Cycle> set) {
        int total = 0;
        for (Cycle c : set) {
            total += c.weight(cg);
        }
        return total;
    }

    private static class Tuple {
        public int v;
        public TreeSet<Integer> rest;
        public Tuple(int v, TreeSet<Integer> rest) {
            this.v = v; this.rest = rest;
        }
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            Tuple other = (Tuple) o;
            return v == other.v && rest.equals(other.rest);
        }
        @Override
        public int hashCode() {
            return v + rest.hashCode();
        }
    }

    private LinkedHashSet<Cycle> choose(int v, TreeSet<Integer> rest,
        LinkedHashMap<Tuple, LinkedHashSet<Cycle>> memo) {
        if (rest.size() == 1) {
            LinkedHashSet<Cycle> found = new LinkedHashSet<Cycle>();
            found.add(cg.getCycle(v));
            return found;
        }

        // memo get
        Tuple t = new Tuple(v, rest);
        if (memo.containsKey(t)) {
            return new LinkedHashSet<Cycle>(memo.get(t));
        }

        // both
        TreeSet<Integer> m = new TreeSet<Integer>(rest);
        rest.remove(v);

        // notake
        int next = rest.last();
        LinkedHashSet<Cycle> notake = choose(next, rest, memo);


        // take
        TreeSet<Integer> removed = new TreeSet<Integer>();
        for (int n : cg.neighbors(v)) {
            if (rest.remove(n)) {
                removed.add(n);
            }
        }
        LinkedHashSet<Cycle> take;
        if (rest.isEmpty()) {
            take = new LinkedHashSet<Cycle>();
        } else {
            next = rest.last();
            take = choose(next, rest, memo);
        }
        take.add(cg.getCycle(v));

        // clean up
        for (int n : removed) {
            rest.add(n);
        }
        rest.add(v);

        // choose
        LinkedHashSet<Cycle> selected;
        if (weight(notake) > weight(take)) {
            selected = notake;
        } else {
            selected = take;
        }

        // memo set
        memo.put(new Tuple(v, m), new LinkedHashSet<Cycle>(selected));

        return selected;
    }

    private Iterable<Cycle> solve() {
        return result;
    }

    public static Iterable<Cycle> solve(CycleGraph cg) {
        ExactSolverTak solver = new ExactSolverTak(cg);
        return solver.solve();
    }

}
