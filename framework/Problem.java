package framework;

import java.util.List;

import protein.ProteinState;

public interface Problem {
    public State getInitialState();
    public boolean isGoalState(State s);
    public List<Action> getActions(State s);
    public int getHeuristic(State s);
    public boolean isActionValid(Action a);
    public State performActionOnState(State s, Action a);
}
