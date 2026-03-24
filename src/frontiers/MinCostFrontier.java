package frontiers;

import java.util.Iterator;

import framework.Frontier;
import framework.Node;
import framework.State;

public class MinCostFrontier implements Frontier {
    private java.util.PriorityQueue<framework.Node> queue;

    public MinCostFrontier() {
        this.queue = new java.util.PriorityQueue<>((a, b) -> Integer.compare(a.getG(), b.getG()));
    }

    @Override
    public void enqueue(Node n) {
        this.queue.add(n);
    }

    @Override
    public Node dequeue() {
        return this.queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public boolean contains(State s) {
        for (Node node : queue) {
            if(node.getState().equals(s))
                return true;
        }

        return false;
    }

    public Node getNodeByState(State s) {
        Iterator<Node> nodeIterator = queue.iterator();

        Node node = null;
        while(nodeIterator.hasNext()){
            node = nodeIterator.next();
            if(node.getState().equals(s)){
                return node;
            }
        }

        return null;
    }

    public String toString(){
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("\n");
        
        Iterator<Node> nodeIterator = queue.iterator();

        Node n;
         while(nodeIterator.hasNext()){
            n = nodeIterator.next();
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
        Iterator<Node> it = queue.iterator();
        while (it.hasNext()) {
            Node cur = it.next();
            if (cur == n || (cur.getState().equals(n.getState()) && cur.getG() == n.getG())) {
                it.remove();
                return;
            }
        }
    }


}
