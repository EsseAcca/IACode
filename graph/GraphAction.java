package graph;

import framework.Action;

public class GraphAction implements Action {
    String action;
    String target;
    int cost;

    public GraphAction(String a, String t, int c){
        super();
        this.action = a;
        this.target = t;
        this.cost = c;
    }

    public String toString() {
        return String.format("%s -> %s (cost: %d)", action, target, cost);
    }

    @Override
    public int getCost() {
        return this.cost;
    }
}
