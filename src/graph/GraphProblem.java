package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

import framework.Action;
import framework.Problem;
import framework.State;

public class GraphProblem implements Problem {
    Map<String, List<Action>> graph;
    Map<String, Integer> heuristics;
    Set<String> goalStates;

    public GraphProblem(Map<String, List<Action>> graph, Map<String, Integer> heuristics, Set<String> goalStates) {
        this.graph = graph;
        this.heuristics = heuristics;
        this.goalStates = goalStates;
    }

    @Override
    public State getInitialState() {
        return new GraphState("S");
    }

    @Override
    public boolean isGoalState(State s) {
        GraphState gs = (GraphState) s;
        return goalStates.contains(gs.getName());   
    }

    @Override
    public List<Action> getActions(State s) {
        GraphState gs = (GraphState) s;
        if (graph.containsKey(gs.getName())) {
            return graph.get(gs.getName());
        }
        return new java.util.ArrayList<Action>();
    }

    @Override
    public boolean isActionValid(Action a) {
        return true;
    }

    @Override
    public State performActionOnState(State s, Action a) {
        GraphAction ga = (GraphAction) a;

        return new GraphState(ga.target);
}

    @Override
    public int getHeuristic(State s) {
        GraphState gs = (GraphState) s;
        if (heuristics.containsKey(gs.getName())) {
            return heuristics.get(gs.getName());
        }
        return 0;
    }

}
