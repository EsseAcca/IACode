package framework;

public interface Frontier {
    public void enqueue(Node n);
    public Node dequeue();
    public void removeNode(Node n);
    public boolean isEmpty();
    public boolean contains(State s);
    public Node getNodeByState(State s);
}
