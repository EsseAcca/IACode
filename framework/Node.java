package framework;

public class Node {
    private Node parent;

    private int g;
    private int h;
    private int depth;

    private Action a;
    private State state;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public Action getA() {
        return a;
    }

    public void setA(Action a) {
        this.a = a;
    }

    public String toString(){
        return state.toString();
    }
}
