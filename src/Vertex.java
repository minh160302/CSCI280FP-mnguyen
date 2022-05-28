import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Vertex {
  public String name;
  public List<Edge> adjacents;
  public boolean isVisited;
  public Vertex predecessor;

  public Vertex(String name) {
    this.name = name;
    this.adjacents = new ArrayList<>();
    this.predecessor = null;
    this.isVisited = false;
  }

  public String toString() {
    return name;
  }

  public List<Vertex> getNeighbors() {
    return adjacents.stream().map((v) -> v.destination).toList();
  }

  public String getPath() {
    StringBuilder path;
    Stack<Vertex> paths = new Stack<>();

    Vertex pred = this;
    while (pred != null) {
      paths.push(pred);
      pred = pred.predecessor;
    }

    path = new StringBuilder(paths.pop().toString());
    while(!paths.isEmpty()) {
      String node = paths.pop().toString();
      path.append(" => ").append(node);
    }

    return path.toString();
  }

  public Edge getEdgeToVertex(Vertex v) {
    for (Edge edge: adjacents) {
      if (edge.destination == v) {
        return edge;
      }
    }

    return new Edge(v, 0);
  }
}
