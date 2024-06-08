import java.util.*;

public class UndirectedGraph {
    //written by Ghadeer Abboud 6549
    private HashSet<Integer> nodes = new HashSet<>();//Storing nodes of the graph
    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>(); //every node has adjacency list (out degree)
    private final Map<Integer, Integer> parent = new HashMap<>(); //use this map to avoid revisit v --> u if we visit u --> v
    int dfsCounter = 1;
    HashSet <Integer> marked = new HashSet<>(); //check if node have been visited
    HashMap <Integer, Integer> dfsNum = new HashMap<>(); //store discovery time
    HashMap<Integer,ArrayList<Integer>>TE = new HashMap<>(); //store Tree Edges
    HashMap<Integer,ArrayList<Integer>>BE = new HashMap<>(); //store Back Edges
    int numberOfCycles = 0;
    //written by:Ali Badr 6278
    public void addEdge(int from, int to) {
        if(!nodes.contains(from)){
            nodes.add(from);
            adjacencyList.putIfAbsent(from, new ArrayList<>());
        }
        if(!nodes.contains(to)){
            nodes.add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>());
        }
        // Edge (from --> to and to --> from)
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }
    //written by: Azzam Slman 6029 and Emad Mohammed 6670
    //run dfs and classify edges(only have Back and tree edges)
    private void dfs(Integer u){
        marked.add(u);
        dfsNum.put(dfsCounter++,u);
        for(var v : adjacencyList.get(u)){
            if(!marked.contains(v) && !v.equals(parent.get(u))){
                if(!TE.containsKey(v)){
                    TE.put(v, new ArrayList<>());
                }
                parent.put(v,u);
                TE.get(v).add(u);
                dfs(v);
            }
            else{
                if(!BE.containsKey(u)){
                    BE.put(u, new ArrayList<>());
                }
                BE.get(u).add(v);
                return;
            }
        }
    }
    //written by: Mohammed Abo Rz 6241 and Hadi Hasan 6618
    //initialize marked set and change the undirected graph to directed graph
    //by directing the tree edges towards the root and back edges away from root
    private void reDirect(){
        adjacencyList = new HashMap<>();
        nodes = new HashSet<>();
        marked = new HashSet<>();
        for(var edge:TE.keySet()){
            for(var v:TE.get(edge)) addEdge(edge,v);
        }
        for(var edge:BE.keySet()){
            for(var v:BE.get(edge)) addEdge(edge,v);
        }
    }
    //written by: Hadi Hasan 6618 and Ibrahim  Aljeblawy 6156
    public Boolean CheckTwoEdgeOrTwoVertex(){
        for(var u : nodes){
            if(!marked.contains(u))
                dfs(u);
        }
        reDirect();
        //marked every edge we visit
        HashSet <String> markedEdge = new HashSet<>();
        for(int i=1;i<=nodes.size();i++){
            var u = dfsNum.get(i);
            if(BE.containsKey(u)) {
                for (var v : BE.get(u)) {
                    //store in every iterate nodes to check if we have path or cycle
                    HashSet<Integer> cycle = new HashSet<>();
                    cycle.add(u);
                    reDfs(v, cycle, markedEdge);
                }
            }
        }
        //to have a 2-vertex connected graph you need to have only one cycle
        if(numberOfCycles!=1) return false;
        //check if we have a 2-edge connected graph by checking all edges if we have visited them
        for(var x : nodes){
            for (var y:adjacencyList.get(x)){
                if(!markedEdge.contains(x + "" + y)) return false;
            }
        }
        return true;
    }
    private void reDfs(Integer u,HashSet<Integer> cycle,HashSet<String>markedEdge){
        marked.add(u);
        for(var v:adjacencyList.get(u)){
            markedEdge.add(u + "" + v);
            if(!marked.contains(v)) {
                cycle.add(u);
                reDfs(v, cycle, markedEdge);
            }
            else{
                if(cycle.contains(v))
                    numberOfCycles++;
                return;
            }
        }
    }
}
