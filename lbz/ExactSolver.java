package lbz;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/** The exact solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ExactSolver {
    protected static boolean visited[];
    protected static int parts[];

    public static Iterable<Cycle> solve(CycleGraph cg) {
        //int[][] memo = new int[][]
        Cycle inner, outer;
        ArrayList<Cycle> result = new ArrayList<>();
        TreeSet<Integer> remove = new TreeSet<>();
        Set<Integer> set1, set2;
        for(int i = 0; i < cg.getNumVertices(); i++) {
            if (cg.getNumNeighbors(i) == 0) {
                result.add(cg.getCycle(i));
            } else {
                set1 = allAdd(cg.neighbors(i), i);
                for(int j = 0; j < cg.getNumVertices(); j++) {
                    if(i == j) {
                        continue;
                    }
                    set2 = allAdd(cg.neighbors(j), j);
                    if(set2.containsAll(set1)) {
                        cg.remove(j);
                        remove.add(j);
                    }
                }
            }
        }
        visited = new boolean[cg.getNumVertices()];
        ArrayList<ArrayList> components = new ArrayList<>();
        ArrayList<Integer> temp;
        for(int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        for(int v: cg.getVertices()) {
            if(visited[v] == false && !remove.contains(v)) {
                temp = findComponent(v);
                components.add(temp);
            }
        }
        for(ArrayList<Integr> comp: components) {
            dps(cg, remove);
        }
        return null;
    }

    public static TreeSet<Integer> allAdd(int[] neighbor, int v) {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < neighbor.length; i++) {
            set.add(neighbor[i]);
        }
        set.add(v);
        return set;
    }

    public static ArrayList<Intger> findComponent(int index, Cyclegraph cg) {
        Arraylist<Integer>row = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        visited[index] =  true;
        queue.offer(index);
        while (!queue.isEmpty()){
            int u = queue.poll();
            row.add(u);    
            for (int v : cg.neighbors(j)){
                if (visited[v] == false){
                    visited[v] = true;
                    queue.offer(v);
                }
            }
        }
    }

    public static Iterable<Cycle> dps(CycleGraph g, TreeSet remove) {
        if (g.getNumVertices() == 0) {
            return null;
        }
        return null;
    }
}
