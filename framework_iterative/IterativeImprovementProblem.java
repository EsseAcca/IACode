package framework_iterative;

import java.util.List;

import framework.Action;
import framework.State;

public interface IterativeImprovementProblem {
    public State getState();
    public boolean isGoalState(State s);
    public List<Action> getActions(State s);
    public State getBestNeighbor(State s);
    public List<State> getNeighborhood(State s);
    public int getHeuristic(State s);
    public boolean isActionValid(Action a);
    public State performActionOnState(State s, Action a);
}
