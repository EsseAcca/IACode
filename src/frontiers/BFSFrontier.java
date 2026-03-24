package frontiers;
import java.util.Queue;

import framework.Frontier;
import framework.Node;
import framework.State;

public class BFSFrontier implements Frontier{
    Queue<Node> nodes;

    public BFSFrontier() {
        this.nodes = new java.util.LinkedList<>();
    }

    @Override
    public Node dequeue() {
        return nodes.poll();
    }

    @Override
    public void enqueue(Node n) {
        this.nodes.add(n);
    }

    @Override
    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    @Override
    public boolean contains(State s) {
        for (Node node : nodes) {
            if(node.getState().equals(s))
                return true;
        }

        return false;
    }

    public Node getNodeByState(State s) {
        for (Node node : nodes) {
            if(node.getState().equals(s))
                return node;
        }

        return null;
    }

    public String toString(){
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("\n");
        

        for(Node n : nodes){
            sBuilder.append("    { ");
            sBuilder.append(n.getState().toString());
            sBuilder.append(":\tG= ");
            sBuilder.append(n.getG());
            sBuilder.append("\tH= " );
            sBuilder.append(n.getH());
            sBuilder.append("\tDepth= ");
            sBuilder.append(n.getDepth());
            sBuilder.append(" } ");
            sBuilder.append("\n");
        }

        return sBuilder.toString();
    }

    public void removeNode(Node n) {
        if (n == null) return;
        java.util.Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            Node curr = it.next();
            if (curr.getState().equals(n.getState())) {
                it.remove();
                return;
            }
        }
    }
    
}
