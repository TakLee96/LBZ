package lbz;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;

/** The exact solver for MWIS problem.
 * @author Jim Bai, Tak Li, Zirui Zhou */
public class ExactSolver {
    protected static boolean visited[];
    protected static int parts[];
    protected static HashMap<ArrayList, Integer> memo = new HashMap<>();
    protected static HashMap<ArrayList, ArrayList<Integer>> track = new HashMap<>();
    protected static HashMap<ArrayList, Integer> vertices = new HashMap<>();

    public static Iterable<Cycle> solve(CycleGraph cg) {
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
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        ArrayList<Integer> temp;
        ArrayList<Integer> prev;
        for(int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        for(int v = 0; v < cg.getNumVertices(); v++) {
            if(visited[v] == false && !remove.contains(v)) {
                temp = findComponent(v, cg);
                components.add(temp);
            }
        }
        for(ArrayList<Integer> comp: components) {
            dps(cg, comp);
            temp = comp;
            while(temp != null && temp.size() != 0) {
                prev = track.get(temp);
                if(vertices.get(temp) != null) {
                    result.add(cg.getCycle(vertices.get(temp)));
                }
                temp = prev;
            }
            memo.clear();
            track.clear();
            vertices.clear();
        }
        return result;
    }

    public static TreeSet<Integer> allAdd(int[] neighbor, int v) {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < neighbor.length; i++) {
            set.add(neighbor[i]);
        }
        set.add(v);
        return set;
    }

    public static ArrayList<Integer> findComponent(int index, CycleGraph cg) {
        ArrayList<Integer>row = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        visited[index] =  true;
        queue.offer(index);
        while (!queue.isEmpty()){
            int u = queue.poll();
            row.add(u);    
            for (int v : cg.neighbors(u)){
                if (visited[v] == false){
                    visited[v] = true;
                    queue.offer(v);
                }
            }
        }
        return row;
    }

    public static Integer dps(CycleGraph g, ArrayList<Integer> comp) {
        if(comp == null || comp.size() == 0 ||comp.get(0) == null) {
            return 0;
        }
        int v = comp.get(0);
        ArrayList<Integer> neig = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(v);
        while (!queue.isEmpty()){
            int u = queue.poll();
            neig.add(u);
            for (int j : g.neighbors(u)) {
                queue.offer(j);
            }
        }
        if(!memo.containsKey(comp)) {
            int takeV, notTakeV;
            comp.remove(new Integer(v));
            ArrayList<Integer> removeV = new ArrayList<>(comp);
            comp.add(v);
            comp.removeAll(neig);
            ArrayList<Integer> removeVNeg = new ArrayList<>(comp);
            comp.addAll(neig);
            if(memo.containsKey(removeV)) {
                takeV = memo.get(removeV);
            } else {
                takeV = dps(g, removeV);
            }
            if(memo.containsKey(removeVNeg)) {
                notTakeV = memo.get(removeVNeg);
            } else {
                notTakeV = dps(g, removeVNeg) + g.getCycle(v).getWeight();
            }
            if(takeV < notTakeV) {
                memo.put(comp, notTakeV);
                track.put(comp,removeV);
                return notTakeV;
            } else {
                memo.put(comp, takeV);
                track.put(comp, removeVNeg);
                vertices.put(comp, v);
                return takeV;
            }
        }
        return memo.get(comp);
    }
}
