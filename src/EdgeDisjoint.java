import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// In class Example
public class EdgeDisjoint {
  public static void main(String[] args) {
    Graph graph = createGraph();
    Graph graph_org = createGraph();
    String sourceName = "s";
    String sinkName = "t";

    EdmondsKarp EK = new EdmondsKarp();

    // Run Edmonds - Karp algorithm
    int maxflow = EK.EK(graph, graph_org, sourceName, sinkName);
    System.out.println("Max flow: " + maxflow);

    // Set of the max cardinality edge-disjoint paths
    List<List<Vertex>> D = new ArrayList<>();
    if (maxflow == 0) {
      System.out.println("The set of edge-disjoint paths is: " + D);
    } else {
      while (maxflow > 0) {
        List<Vertex> path = findPath(graph_org, sourceName, sinkName);
        D.add(path);
        // Decrease capacity by 1
        decreaseCapacity(path);
        // Reset visited status
        resetVisitStatus(graph_org);
        // Decrease max flow
        maxflow--;
      }
    }
    System.out.println("The set of edge-disjoint paths is: " + D);
  }


  /**
   * Create the graph based on input
   *
   * @return A graph
   */
  public static Graph createGraph() {
    Graph G = new Graph();
    // Assign 1 to all the edges' capacity
    // from s
    G.addEdge("s", "a", 1);
    G.addEdge("s", "b", 1);
    G.addEdge("s", "c", 1);
    // from a
    G.addEdge("a", "b", 1);
    G.addEdge("a", "d", 1); // !!! a little bit different from class' example
    // from b
    G.addEdge("b", "c", 1);
    G.addEdge("b", "f", 1);
    // from c
    G.addEdge("c", "f", 1);
    // from d
    G.addEdge("d", "b", 1);
    G.addEdge("d", "t", 1);
    // from e
    G.addEdge("e", "a", 1);
    G.addEdge("e", "d", 1);
    G.addEdge("e", "t", 1);
    // from f
    G.addEdge("f", "e", 1);
    G.addEdge("f", "t", 1);

    return G;
  }


  /**
   * Reset all vertices in the graph as unvisited
   *
   * @param graph_org The original graph
   */
  private static void resetVisitStatus(Graph graph_org) {
    for (Vertex vertex : graph_org.vertexMap.values()) {
      vertex.isVisited = false;
    }
  }


  /**
   * Decrease the capacity of each edge in the path by 1.
   *
   * @param D list of vertices on the graph
   */
  private static void decreaseCapacity(List<Vertex> D) {
    for (int i = 0; i < D.size() - 1; i++) {
      Vertex from = D.get(i);
      Vertex to = D.get(i + 1);
      from.getEdgeToVertex(to).capacity--;
    }
  }


  /**
   * @param graph      The original graph
   * @param sourceName Source vertex
   * @param sinkName   Sink vertex
   * @return An s-t path in the residual graph
   */
  private static List<Vertex> findPath(Graph graph, String sourceName, String sinkName) {
    List<Vertex> path = new ArrayList<>();
    Stack<Vertex> stack = new Stack<>();
    Vertex source = graph.getVertex(sourceName);
    stack.push(source);

    while (!stack.isEmpty()) {
      Vertex p = stack.pop();
      if (!p.isVisited) {
        if (p.name.equals(sinkName)) {
          path.add(p);
          return path;
        }
        p.isVisited = true;
        path.add(p);
      }

      List<Vertex> neighbors = p.getNeighbors();
      for (Vertex neighbor : neighbors) {
        if (!neighbor.isVisited && p.getEdgeToVertex(neighbor).capacity > 0)
          stack.push(neighbor);
      }
    }

    return path;
  }

}
