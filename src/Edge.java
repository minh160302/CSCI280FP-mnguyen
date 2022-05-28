public class Edge {
  public int capacity;
  public Vertex destination;
  public boolean isBackward;

  public Edge(Vertex destination, int capacity) {
    this.capacity = capacity;
    this.destination = destination;
    this.isBackward = false;
  }
}
