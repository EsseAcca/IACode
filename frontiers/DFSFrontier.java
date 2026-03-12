package frontiers;

import java.util.ArrayDeque;
import java.util.Iterator;

import framework.Frontier;
import framework.Node;
import framework.State;

public class DFSFrontier implements Frontier {
    private ArrayDeque<Node> stack;

    public DFSFrontier() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public void enqueue(Node n) {
        stack.push(n);
    }

    @Override
    public Node dequeue() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public boolean contains(State s) {
        for (Node node : stack) {
            if(node.getState().equals(s))
                return true;
        }

        return false;
    }

    public Node getNodeByState(State s) {
        Iterator<Node> nodeIterator = stack.iterator();

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
        
        Iterator<Node> nodeIterator = stack.iterator();

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

    @Override
    public void removeNode(Node n) {
        if (n == null) return;
        Iterator<Node> it = stack.iterator();
        while (it.hasNext()) {
            Node curr = it.next();
            if (curr == n || curr.getState().equals(n.getState())) {
                it.remove();
                return;
            }
        }
    }

    
}
