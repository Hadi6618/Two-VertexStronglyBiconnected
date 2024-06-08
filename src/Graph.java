import java.util.*;


public class Graph {
    //written by: Ghadeer Abboud 6549
    private final HashSet<Integer> nodes = new HashSet<>();//Storing nodes of the graph
    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>(); //every node has adjacency list (out degree)
    int dfsCounter = 1;
    HashSet <Integer> marked = new HashSet<>(); //check if node have been visited
    HashMap <Integer, Integer> dfsNum = new HashMap<>(); //store discovery time

    //written by:Ali Badr 6278
    public void addEdge(int from, int to) {
        //line 15 --> 23 check and create node
        if(!nodes.contains(from)){
            nodes.add(from);
            adjacencyList.putIfAbsent(from, new ArrayList<>());
        }
        if(!nodes.contains(to)){
            nodes.add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>());
        }
        // Edge (from --> to)
        adjacencyList.get(from).add(to);
    }

    public void print(){
        for(var v : adjacencyList.keySet()){
            var targets = adjacencyList.get(v);
            if(!targets.isEmpty()) System.out.println(v + " is connected to " + targets);
        }
    }
    //check the number of strongly connected components using the Cherianne-Mehlhorn-Gabow Algorithm
    //written by:Mohammed Abo Rz 6241, Moussa Moussa 6665 and Ghadeer Abboud 6549
    public int CherianneMehlhorn(){
        HashMap <Integer, Integer> comp = new HashMap<>(); //every node will have a represented node
        Stack <Integer> OpStack = new Stack<>(); //store each component
        Stack <Integer> RStack = new Stack<>(); //represented nodes
        int scc = 0;
        for (var v: nodes) {
            dfsNum.put(v, -1);
            comp.put(v, -1);
        }
        for(var u : nodes){
            if(!marked.contains(u))
                dfs(u,comp,OpStack,RStack);
        }
        for(var v : comp.values()){
            if(v == -2) scc++;
        }
        return scc;

    }
    private void dfs(Integer u,HashMap <Integer,Integer> comp,Stack<Integer> OpStack,Stack<Integer> RStack) {
        marked.add(u);
        dfsNum.put(u, dfsCounter++);
        OpStack.add(u);
        RStack.add(u);
        for(var v : adjacencyList.get(u)){
            if(!marked.contains(v))
                dfs(v,comp,OpStack,RStack);
            else if (OpStack.contains(v)) {
                while (!RStack.isEmpty() && dfsNum.get(v) < dfsNum.get(RStack.peek()))
                    RStack.pop();
            }
        }
        if(!RStack.isEmpty() && u.equals(RStack.peek())) {
            comp.put(u,-2);
            RStack.pop();
            while (!OpStack.isEmpty() && !u.equals(OpStack.peek())) {
                int w = OpStack.pop();
                comp.put(w, u);
            }
            OpStack.pop();
        }
    }
}