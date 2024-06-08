import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
In order to prove that the Graph is 2-Vertex Bicconnected Graph we need to prove that the graph is:
   --> A Strongly Connected Graph
   --> It does not contain any Articulation Points
*/
public class Main {
    public static void main(String[] args) throws IOException {
        Graph G = new Graph();
        UndirectedGraph G1 = new UndirectedGraph();
        BufferedReader reader = new BufferedReader(new FileReader("p2p-Gnutella04.txt"));
        //BufferedReader reader = new BufferedReader(new FileReader("p2p-Gnutella05.txt"));
        //BufferedReader reader = new BufferedReader(new FileReader("p2p-Gnutella06.txt"));
        //BufferedReader reader = new BufferedReader(new FileReader("p2p-Gnutella08.txt"));
        //BufferedReader reader = new BufferedReader(new FileReader("p2p-Gnutella09.txt"));
        //BufferedReader reader = new BufferedReader(new FileReader("Wiki-Vote.txt"));
        while (true) {
            String line = reader.readLine(); // read next line
            if (line == null) break;
            String[] nodes = line.split("[ \t]");
            try{
                Double.parseDouble(nodes[0]);
            } catch (NumberFormatException nfe) {
                continue;
            }
            int x = Integer.parseInt(nodes[0]);
            int y = Integer.parseInt(nodes[1]);
            G.addEdge(x,y);
            G1.addEdge(x,y);
        }
        reader.close();
        if(Main.CheckIfTwoVertexStronglyBIConnected(G, G1)) System.out.println("The graph is Two Vertex Biconnected Graph");
    }
    public static Boolean CheckIfTwoVertexStronglyBIConnected(Graph G,UndirectedGraph G1){
        if(G.CherianneMehlhorn() != 1) return false;
        return G1.CheckTwoEdgeOrTwoVertex();
    }
}
