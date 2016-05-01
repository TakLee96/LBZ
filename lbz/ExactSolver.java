package lbz;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
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
        //System.out.println(cg);
        ArrayList<Cycle> result = new ArrayList<>();
        TreeSet<Integer> remove = new TreeSet<>();
        Set<Integer> set1 = new TreeSet<>();
        Set<Integer> set2 = new TreeSet<>();
        for(int i = 0; i < cg.getNumVertices(); i++) {
            if (cg.getNumNeighbors(i) == 0) {
                result.add(cg.getCycle(i));
                remove.add(i);
            } else {
                if(remove.contains(i)) {
                    continue;
                }
                set1 = new TreeSet<>();
                set1.addAll(cg.neighbors(i));
                set1.add(i);
                for(int j = i + 1; j < cg.getNumVertices(); j++) {
                    if(remove.contains(j)) {
                        continue;
                    }
                    set2 = new TreeSet<>();
                    set2.addAll(cg.neighbors(j));
                    set2.add(j);
                    if(set2.containsAll(set1) && cg.getCycle(i).getWeight() == cg.getCycle(j).getWeight()) {
                        //cg.remove(j);
                        remove.add(j);
                        //System.out.println(cg.getCycle(j));
                    }
                }
            }
        }
        System.out.println();
        visited = new boolean[cg.getNumVertices()];
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        ArrayList<Integer> temp;
        ArrayList<Integer> prev;
        visitInitialize(remove);
        for(int v = 0; v < cg.getNumVertices(); v++) {
            if(visited[v] == false && !remove.contains(v)) {
                temp = findComponent(v, cg, -1);
                //for(int i = 0; i < temp.size(); i++) {
                    //System.out.println(temp.get(i));
                //}
                components.add(temp);
            }
        }
        System.out.println();
        System.out.println("finish DFS");
        for(ArrayList<Integer> comp: components) {
            visitInitialize(remove);
            System.out.println("start dps");
            //System.out.println(comp.size());
            //System.out.println(comp.get(0));
            dps(cg, comp, remove);
            temp = comp;
            //System.out.println("finish dps");
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

    public static void visitInitialize(TreeSet<Integer> remove) {
        for(int i = 0; i < visited.length; i++) {
            if(remove.contains(i)) {
                visited[i] = true;
            } else {
                visited[i] = false;
            }
        }
    }


    public static ArrayList<Integer> findComponent(int index, CycleGraph cg, int maxDepth) {
        ArrayList<Integer>row = new ArrayList<>();
        ArrayList<Integer>deep = new ArrayList<>();
        int dist[] = null;
        if (maxDepth != -1) {
                dist = new int[cg.getNumVertices()];
                dist[index] = 0;
        }
        Queue<Integer> queue = new LinkedList<>();
        visited[index] =  true;
        int depth = 0;
        queue.offer(index);
        while (!queue.isEmpty()){
            if(maxDepth != -1) {
                depth++;
                if(depth > maxDepth) {
                    break;
                }
            }
            int u = queue.poll();
            row.add(u);
            //System.out.println(cg.neighbors(u).length);
            for (int v : cg.neighbors(u)){
                if (visited[v] == false){
                    visited[v] = true;
                    queue.offer(v);
                    if(maxDepth != -1) {
                        dist[v] = depth;
                        if(depth == maxDepth) {
                            deep.add(v);
                        }
                    }
                }
            }
            
        }
        if(maxDepth != -1) {
            return deep;
        }
        return row;
    }

    public static ArrayList<Integer> mirror(CycleGraph g, ArrayList<Integer> comp, TreeSet<Integer> remove, int v) {
        ArrayList<Integer> distTwo = findComponent(v, g, 2);
        TreeSet<Integer> Nv = new TreeSet<>(g.neighbors(v));
        TreeSet<Integer> Nu;
        TreeSet<Integer> remain;
        ArrayList<Integer> result = new ArrayList<>();
        for(Integer u: distTwo) {
            Nu = new TreeSet<>(g.neighbors(u));
            Nv.removeAll(Nu);
            remain = new TreeSet<>(Nv);
            Nv.addAll(Nv);
            if(remain.size() == 0) {
                result.add(u);
            } else if(isClique(g, remain)) {
                result.add(u);
            }
        }
        return result;
    }
  
    public static boolean isClique(CycleGraph g, TreeSet<Integer> remain) {
        boolean result = true;
        while(!remain.isEmpty()) {
            int u = remain.pollFirst();
            for(Integer v: remain) {
                result = g.isConnected(u, v) & result;
            }
        }
        return result;
    }

    public static Integer dps(CycleGraph g, ArrayList<Integer> comp, TreeSet<Integer> remove) {
        if(comp == null || comp.size() == 0 ||comp.get(0) == null) {
            //System.out.println("base case");
            return 0;
        }
        int v = comp.get(0);
        //System.out.println(v);
        ArrayList<Integer> neig = new ArrayList<>();

        //System.out.println("queue start");
        neig.add(v);
        for (int j : g.neighbors(v)) {
            if(comp.contains(j)) {
                neig.add(j);
            }
        }
        //System.out.println("vertex v is " + v);
        //System.out.println("memo check");
        if(!memo.containsKey(comp)) {
            //System.out.println("no memo before");
            int takeV, notTakeV;
            comp.remove(new Integer(v));
            ArrayList<Integer> mirrors = mirror(g, comp, remove, v);
            if(mirrors != null && mirrors.size() != 0) {
                //System.out.println(mirrors.size());
            }
            comp.removeAll(mirrors);
            ArrayList<Integer> removeV = new ArrayList<>(comp);
            comp.add(v);
            comp.addAll(mirrors);
            comp.removeAll(neig);
            ArrayList<Integer> removeVNeg = new ArrayList<>(comp);
            comp.addAll(neig);
            if(memo.containsKey(removeV)) {
                notTakeV = memo.get(removeV);
            } else {
                notTakeV = dps(g, removeV, remove);
            }
            if(memo.containsKey(removeVNeg)) {
                takeV = memo.get(removeVNeg);
            } else {
                takeV = dps(g, removeVNeg, remove) + g.getCycle(v).getWeight();
            }
            //System.out.println("takev is " + takeV);
            //System.out.println("nottakev is " + notTakeV);
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
        //System.out.println("memod before");
        return memo.get(comp);
    }
}
