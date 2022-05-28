import java.util.*;

public class EdmondsKarp {
  public static Graph G;
  public static Graph G_org;
  public Set<Vertex> S = new HashSet<>();
  public Set<Vertex> T = new HashSet<>();

  /**
   * Trace back from sink to source and find the flow in this path,
   * which is the minimum capacity among the edges
   *
   * @param sinkName Sink vertex
   * @return Value of the flow
   */
  public static int getFlowFromPath(String sinkName) {
    int flow = Integer.MAX_VALUE;
    Vertex p = G.getVertex(sinkName);
    while (p.predecessor != null) {
      Edge edge = p.predecessor.getEdgeToVertex(p);
      if (!edge.isBackward) {
        flow = Math.min(flow, edge.capacity);
      }
      p = p.predecessor;
    }

    return flow;
  }


  /**
   * Reset isVisited to false between bfs calls.
   */
  public static void resetVisitStatus() {
    for (Vertex vertex : G.vertexMap.values()) {
      vertex.isVisited = false;
    }
  }


  /**
   * BFS algorithm to check if there exists an s-t path
   * Keep tracks on the predecessor if there is one
   *
   * @param s Source vertex
   * @param t Sink vertex
   * @return true/false
   */
  public static boolean bfs(String s, String t) {
    Vertex v = G.getVertex(s);
    v.isVisited = true;
    Queue<Vertex> queue = new LinkedList<>();
    queue.offer(v);

    while (!queue.isEmpty()) {
      Vertex p = queue.poll();
      if (p.name.equals(t)) {
        return true;
      }
      List<Edge> edges = p.adjacents;
      for (Edge edge : edges) {
        Vertex destination = edge.destination;
        if (edge.capacity > 0 && !destination.isVisited) {
          destination.predecessor = p;
          destination.isVisited = true;
          queue.offer(destination);
        }
      }
    }

    return false;
  }


  /**
   * Find all vertices that are reachable by source node in the residual graph
   * Add to S
   *
   * @param sourceName Source vertex
   */
  public void findReachableVerticesFromS(String sourceName) {
    Queue<Vertex> q = new LinkedList<>();
    Vertex s = G.getVertex(sourceName);
    q.offer(s);
    S.add(s);
    while (!q.isEmpty()) {
      Vertex p = q.poll();
      for (Vertex neighbor : p.getNeighbors()) {
        Edge fw = p.getEdgeToVertex(neighbor);
        Edge bw = neighbor.getEdgeToVertex(p);
        if (bw.isBackward && bw.capacity > 0 && !fw.isBackward && fw.capacity > 0) {
          neighbor.isVisited = true;
          S.add(neighbor);
          q.offer(neighbor);
        }
      }
    }
  }


  /**
   * Get the remaining node
   * Add to T
   */
  public void findReachableVerticesFromT() {
    for (Vertex vertex : G.vertexMap.values()) {
      if (!S.contains(vertex)) {
        T.add(vertex);
      }
    }
  }


  /**
   * Find the value of the s-t cut based on S and T
   *
   * @return String to print. Can change to integer if needed.
   */
  //
  public String findCutCapacity() {
    List<String> vertexNames = T.stream().map((v) -> v.name).toList();
    StringBuilder output = new StringBuilder();
    int cutCap = 0;
    for (Vertex vertex : S) {
      Vertex inOrigin = G_org.vertexMap.get(vertex.name);
      for (Vertex neighbor : inOrigin.getNeighbors()) {
        if (vertexNames.contains(neighbor.name)) {
          Edge edge = inOrigin.getEdgeToVertex(neighbor);
          System.out.println("Edge coming out of " + vertex
                  + " and into " + neighbor
                  + " has capacity of " + edge.capacity);
          output.append(edge.capacity).append(" + ");
          cutCap += edge.capacity;
        }
      }
    }
    output.replace(output.length() - 3, output.length() - 1, " =");
    output.append(cutCap);

    return output.toString();
  }


  /**
   * Edmonds - Karp algorithm
   *
   * @param graph      The graph
   * @param graph_org  A copy of graph; we don't modify this
   * @param sourceName Source node
   * @param sinkName   Sink node
   * @return Value of the maximum flow
   */
  public int EK(Graph graph, Graph graph_org, String sourceName, String sinkName) {
    G = graph;
    G_org = graph_org;

    int maxflow = 0;

    while (bfs(sourceName, sinkName)) {
      int flow = getFlowFromPath(sinkName);

      // decrease the flow from each edge
      Vertex sink = G.getVertex(sinkName);
      System.out.println(sink.getPath());

      while (sink.predecessor != null) {
        Edge edge = sink.predecessor.getEdgeToVertex(sink);
        if (!edge.isBackward) {
          edge.capacity -= flow;
          Edge rEdge = G.getBackwardEdge(sink.predecessor, sink);
          rEdge.capacity += flow;
          sink.adjacents.add(rEdge);
        }
        sink = sink.predecessor;
      }

      maxflow += flow;
      resetVisitStatus();
    }

    return maxflow;
  }
}
