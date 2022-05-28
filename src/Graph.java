import java.util.HashMap;
import java.util.List;

public class Graph {
  public HashMap<String, Vertex> vertexMap;

  public Graph() {
    vertexMap = new HashMap<>();
  }

  public Vertex getVertex(String name) {
    Vertex v = vertexMap.get(name);
    if (v == null) {
      v = new Vertex(name);
      vertexMap.put(name, v);
    }
    return v;
  }

  public void addEdge(String source, String dest, int capacity) {
    // System.out.printf("Adding edge from %s to %s\n", source, dest);
    Vertex v = getVertex(source);
    Vertex w = getVertex(dest);
    List<Edge> L = v.adjacents;
    L.add(new Edge(w, capacity));
  }

  public Edge getBackwardEdge(Vertex a, Vertex b) {
    for (Edge edge : b.adjacents) {
      if (edge.destination == a && edge.isBackward) {
        b.adjacents.remove(edge);
        edge.isBackward = true;
        return edge;
      }
    }

    Edge newEdge = new Edge(a, 0);
    newEdge.isBackward = true;
    return newEdge;
  }
}
