public class MetaConsultant {
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
    System.out.println("Reachable from source node: " + EK.S);
    System.out.println("Reachable from sink node: " + EK.T);
    System.out.println("Value of the minimum cut: " + EK.findCutCapacity());


    // Total amount of project needs to be done
    int totalProjects = 8 + 3 + 7 + 6;
    System.out.println(totalProjects <= maxflow ? "Possible to schedule" : "Impossible to schedule");
  }


  /**
   * Create the graph based on input
   *
   * @return A graph
   */
  public static Graph createGraph() {
    Graph G = new Graph();

    /*
      - Source node = s
      - m_i: month i
          m1 = 9, m2 = 9, m3 = 6
     */
    G.addEdge("s", "m1", 9);
    G.addEdge("s", "m2", 9);
    G.addEdge("s", "m3", 6);
    /*
      project A
     */
    G.addEdge("m1", "A", 8);
    G.addEdge("m2", "A", 8);
    /*
      project B
     */
    G.addEdge("m1", "B", 3);
    /*
      project C
     */
    G.addEdge("m2", "C", 2);
    G.addEdge("m3", "C", 7);
    /*
      project D
     */
    G.addEdge("m1", "D", 3);
    G.addEdge("m2", "D", 3);
    G.addEdge("m3", "D", 3);
    /*
      Sink node = t
     */
    G.addEdge("A", "t", 8);
    G.addEdge("B", "t", 3);
    G.addEdge("C", "t", 7);
    G.addEdge("D", "t", 6);

    return G;
  }

}
