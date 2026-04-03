package csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import framework.Action;
import framework.State;
import framework_iterative.IterativeImprovementProblem;

public class CspProblem implements IterativeImprovementProblem{

    private CspState state;
    private List<ToIntFunction<CspState>> constraints;

    public CspProblem(List<Integer> initialValues, List<ToIntFunction<CspState>> constraints) {
        // Initialize the state with default values (e.g., all zeros)
        this.state = new CspState(initialValues);
        this.constraints = constraints;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public boolean isGoalState(State s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGoalState'");
    }

    @Override
    public List<Action> getActions(State s) {
        List<Action> actionsList = new ArrayList<>();
        CspState cs = (CspState)s;
        List<Integer> currentValues = cs.getX();

        for(int i = 0; i < currentValues.size(); i++){
            for(int j = 1; j <= 5; j++){ // Assuming the values can range from 1 to 5
                if(currentValues.get(i) != j){
                    actionsList.add(new CspAction(i, j));
                }
            }
        }

        return actionsList;
    }

    @Override
    public State getBestNeighbor(State s) {
        int bestH = this.getHeuristic(s);
        List<CspState> currentBest = new ArrayList<>();

        // Randomly choose a column
        // Collections.shuffle(state.getQueenPositions());
        List<Action> legalActions = this.getActions(s);

        // Move to a random row starting from the littlest one
        for(Action a : legalActions){
            State possibleNewState = performActionOnState(s, a);
            int possibleNewH = getHeuristic(possibleNewState);

            if(possibleNewH > bestH)
                continue;

            if(possibleNewH < bestH){
                bestH = possibleNewH;
                currentBest.clear();
            }
            
            currentBest.add((CspState)possibleNewState);
        }

        Collections.shuffle(currentBest);
        return !currentBest.isEmpty() ? currentBest.getLast() : null;

    }

    @Override
    public List<State> getNeighborhood(State s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeighborhood'");
    }

    @Override
    public int getHeuristic(State s) {
        // Calculate the total violation of constraints for the given state
        int totalViolation = 0;

        for(ToIntFunction<CspState> constraint : constraints) {
            totalViolation += constraint.applyAsInt((CspState) s);
        }

        return totalViolation;
    }

    @Override
    public boolean isActionValid(Action a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActionValid'");
    }

    @Override
    public State performActionOnState(State s, Action a) {
        CspState cs = (CspState)s;
        CspAction ca = (CspAction)a;

        CspState newState = new CspState(new ArrayList<>(cs.getX()));
        newState.getX().set(ca.getTargetIndex(), ca.getTargetValue());

        return newState;
    }
}
