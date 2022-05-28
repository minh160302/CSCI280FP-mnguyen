public class Main {
  public static void main(String[] args) {
    Graph graph = createGraph();
    Graph graph_org = createGraph();
    String sourceName = "s";
    String sinkName = "t";

    EdmondsKarp EK = new EdmondsKarp();

    int maxflow = EK.EK(graph, graph_org, sourceName, sinkName);
    System.out.println("Max flow: " + maxflow);

    EK.findReachableVerticesFromS(sourceName);
    EK.findReachableVerticesFromT();
    System.out.println("Reachable from source node: S = " + EK.S);
    System.out.println("Reachable from sink node: T = " + EK.T);
    System.out.println("Capacity of the minimum cut: " + EK.findCutCapacity());
  }


  /**
   * Create the graph based on input
   * Normal problem on maximum flow
   *
   * @return A graph
   */
  public static Graph createGraph() {
    Graph G = new Graph();

////    Example 1: Network flow worksheet (in class)
//    G.addEdge("s", "a", 9);
//    G.addEdge("s", "b", 8);
//    G.addEdge("s", "d", 1);
//    G.addEdge("b", "t", 6);
//    G.addEdge("a", "b", 7);
//    G.addEdge("a", "c", 4);
//    G.addEdge("d", "a", 2);
//    G.addEdge("d", "c", 3);
//    G.addEdge("c", "t", 6);


////      Example 2: Assignment 6
//    G.addEdge("s", "a", 3);
//    G.addEdge("s", "b", 8);
//    G.addEdge("s", "c", 2);
//    G.addEdge("b", "t", 3);
//    G.addEdge("a", "b", 4);
//    G.addEdge("a", "c", 8);
//    G.addEdge("c", "t", 10);

//      Example 3: Question 1.a - Exam 2
    G.addEdge("s", "a", 4);
    G.addEdge("s", "b", 3);
    G.addEdge("s", "c", 5);
    G.addEdge("a", "d", 3);
    G.addEdge("a", "e", 1);
    G.addEdge("b", "d", 1);
    G.addEdge("b", "e", 3);
    G.addEdge("b", "f", 1);
    G.addEdge("c", "e", 1);
    G.addEdge("c", "f", 3);
    G.addEdge("d", "t", 5);
    G.addEdge("e", "t", 3);
    G.addEdge("f", "t", 4);
    return G;
  }
}
