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
    protected static HashMap<TreeSet<Integer>, Integer> memo = new HashMap<>();
    protected static HashMap<TreeSet<Integer>, TreeSet<Integer>> track = new HashMap<>();
    protected static HashMap<TreeSet<Integer>, Integer> vertices = new HashMap<>();

    public static Iterable<Cycle> solve(CycleGraph cg) {
        Cycle inner, outer;
        //System.out.println(cg);
        ArrayList<Cycle> result = new ArrayList<>();
        TreeSet<Integer> remove = new TreeSet<>();
        Set<Integer> set1 = new TreeSet<>();
        Set<Integer> set2 = new TreeSet<>();
        //System.out.println("remove dominate");
        for(int i = 0; i < cg.getNumVertices(); i++) {
            if(remove.contains(i)) {
                continue;
            }
            set1 = new TreeSet<>();
            set1.addAll(cg.neighbors(i));
            set1.add(i);
            for(Integer j: cg.neighbors(i)) {
                if(remove.contains(j)) {
                    continue;
                }
                set2 = new TreeSet<>();
                set2.addAll(cg.neighbors(j));
                set2.add(j);
                if(set2.containsAll(set1) && cg.getCycle(i).weight(cg) == cg.getCycle(j).weight(cg)) {
                    remove.add(j);
                }
            }
        }
//                for(int j = i + 1; j < cg.getNumVertices(); j++) {
//                    if(remove.contains(j)) {
//                        continue;
//                    }
//                    set2 = new TreeSet<>();
//                    set2.addAll(cg.neighbors(j));
//                    set2.add(j);
//                    if(set2.containsAll(set1) && cg.getCycle(i).weight(cg) == cg.getCycle(j).weight(cg)) {
//                        remove.add(j);
//                        //cg.remove(j);
//                        //System.out.println(cg.getCycle(i));
//                    }
//                }
            
        //ArrayList<Integer> debug = new ArrayList<>();
        //System.out.println("remove cycle");
        for(int i = 0; i < cg.getNumVertices(); i++) {
            if (cg.getNumNeighbors(i) == 0) {
                result.add(cg.getCycle(i));
                //System.out.println(cg.getCycle(i) + "\n");
                //System.out.println(i);
                remove.add(i);
                cg.remove(i);
                //debug.add(i);
            }
        }
        //System.out.println();
        visited = new boolean[cg.getNumVertices()];
        ArrayList<TreeSet<Integer>> components = new ArrayList<>();
        TreeSet<Integer> temp;
        TreeSet<Integer> prev;
        visitInitialize(remove);
        //System.out.println("split parts");
        //System.out.println(cg.getCycle(7));
        for(int v = 0; v < cg.getNumVertices(); v++) {
            if(visited[v] == false && !remove.contains(v)) {
                temp = findComponent(v, cg, -1, null);
                //for(int i = 0; i < temp.size(); i++) {
                    //System.out.println(temp.get(i));
                //}
                components.add(temp);
                //System.out.println("part size " + temp.size());
            }
        }
        //System.out.println();
        //System.out.println("there are " + components.size() +" parts");
        //System.out.println("add vertices");
        for(TreeSet<Integer> comp: components) {
            //System.out.println("start dps");
            //System.out.println(comp.size());
            //System.out.println(comp.get(0));
//            for(Integer ac: comp) {
//                System.out.println(cg.getCycle(ac));
//            }
            //visitInitialize(remove);
            temp = comp;
            dps(cg, comp, remove);
            //System.out.println("finish dps");
            while(temp != null && temp.size() != 0) {
                prev = track.get(temp);
//                System.out.println("inside");
//                for(Integer abc: temp) {
//                    System.out.println(abc);
//                }
                //System.out.println(vertices.get(temp) == null);
                if(vertices.get(temp) != null) {
                    //System.out.println(vertices.get(temp));
                    //debug.add(vertices.get(temp));
                    result.add(cg.getCycle(vertices.get(temp)));
                }
                temp = prev;
            }
            //System.out.println("memo size is " + memo.size());
            
            memo.clear();
            track.clear();
            vertices.clear();
            //break;
        }
//        System.out.println();
//        System.out.println("debug");
//        ArrayList<Integer> d = new ArrayList<>(debug);
//        for(int a = 0; a < d.size(); a++) {
//            System.out.println(cg.getCycle(debug.get(a)));
//            for(int b = a + 1; b < d.size(); b++) {
//                if(cg.isConnected(debug.get(a), d.get(b))) {
//                    System.out.println(debug.get(a) + " and " + d.get(b) + " are connected");
//                }
//            }
//        }
//        System.out.println("debug\n");
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


    public static TreeSet<Integer> findComponent(int index, CycleGraph cg, int maxDepth, TreeSet<Integer> comp) {
        TreeSet<Integer>row = new TreeSet<>();
        TreeSet<Integer>deep = new TreeSet<>();
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
                        if(comp.contains(v)) {
                            if(depth == maxDepth && cg.getCycle(v).weight(cg) == cg.getCycle(u).weight(cg)) {
                                deep.add(v);
                            }
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

    public static TreeSet<Integer> mirror(CycleGraph g, TreeSet<Integer> comp, TreeSet<Integer> remove, int v) {
        TreeSet<Integer> distTwo = findComponent(v, g, 2, comp);
        TreeSet<Integer> Nv = new TreeSet<>(g.neighbors(v));
        TreeSet<Integer> Nu;
        TreeSet<Integer> remain;
        TreeSet<Integer> result = new TreeSet<>();
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

    public static Integer dps(CycleGraph g, TreeSet<Integer> comp, TreeSet<Integer> remove) {
        if(comp == null || comp.size() == 0) {
            //System.out.println("base case");
            return 0;
        }
        int v = comp.first();
        //System.out.println(v);
        TreeSet<Integer> neig = new TreeSet<>();

        //System.out.println("queue start");
//        neig.add(v);
//        for (int j : g.neighbors(v)) {
//            if(comp.contains(j)) {
//                neig.add(j);
//            }
//        }
        //System.out.println("vertex v is " + v);
        //System.out.println("memo check");
        if(!memo.containsKey(comp)) {
            //System.out.println("no memo before");
            int takeV, notTakeV;
            comp.remove(v);
            TreeSet<Integer> mirrors = mirror(g, comp, remove, v);
            comp.removeAll(mirrors);
            TreeSet<Integer> removeV = new TreeSet<>(comp);
            comp.add(v);
            comp.addAll(mirrors);
            neig.add(v);
            for (int j : g.neighbors(v)) {
                if(comp.contains(j)) {
                    neig.add(j);
                    comp.remove(j);
                }
            }
            //comp.removeAll(neig);
            TreeSet<Integer> removeVNeg = new TreeSet<>(comp);
//            System.out.println("removeV");
//            for(Integer abc: removeV) {
//                System.out.println(abc);
//            }
//            System.out.println("removeNeg");
//            for(Integer ab: removeVNeg) {
//                System.out.println(ab);
//            }
            comp.addAll(neig);
            if(memo.containsKey(removeV)) {
                notTakeV = memo.get(removeV);
            } else {
                notTakeV = dps(g, removeV, remove);
            }
            if(memo.containsKey(removeVNeg)) {
                takeV = memo.get(removeVNeg) + g.getCycle(v).weight(g);
            } else {
                takeV = dps(g, removeVNeg, remove) + g.getCycle(v).weight(g);
            }
            //System.out.println("take " + v  + " is " + takeV);
            //System.out.println("nottakev is " + notTakeV);
            if(takeV < notTakeV) {
                memo.put(comp, notTakeV);
                track.put(comp,removeV);
                return notTakeV;
            } else {
                memo.put(comp, takeV);
                track.put(comp, removeVNeg);
                vertices.put(comp, v);
                //System.out.println("takev is " + v);
                return takeV;
            }
        }
        //System.out.println("memod before");
        return memo.get(comp);
    }
}
