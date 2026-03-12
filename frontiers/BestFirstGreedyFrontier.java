package frontiers;

import framework.Frontier;
import framework.Node;
import framework.State;

public class BestFirstGreedyFrontier implements Frontier{
    private java.util.PriorityQueue<framework.Node> nodes;

    public BestFirstGreedyFrontier() {
        this.nodes = new java.util.PriorityQueue<>((a, b) -> Double.compare(a.getH(), b.getH()));
    }

    @Override
    public void enqueue(framework.Node n) {
        this.nodes.add(n);
    }

    @Override
    public framework.Node dequeue() {
        return this.nodes.poll();
    }

    @Override
    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    @Override
    public boolean contains(framework.State s) {
        for (framework.Node node : nodes) {
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

}
