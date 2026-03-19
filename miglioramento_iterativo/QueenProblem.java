package miglioramento_iterativo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import framework.Action;
import framework.State;
import framework_iterative.IterativeImprovementProblem;
import general.Point;

public class QueenProblem implements IterativeImprovementProblem{

    private QueenState state;
    private int stateValue;
    private Random random;

    private int dim;

    public QueenProblem(int dim){
        this.state = new QueenState();
        this.dim = dim;
        random = new Random();
        for(int j = 0; j < dim; j++){
            int randomRow = random.nextInt(dim);
            state.addQueen(new Point(j,randomRow));
        }

        //Should i call heuristic "cost"?
        this.stateValue = this.getHeuristic(state);
    }

    public int getDim(){
        return this.dim;
    }

    public int getStateValue(){
        return this.stateValue;
    }

    @Override
    public boolean isGoalState(State s) {
        return false;
    }

    @Override
    public List<Action> getActions(State s) {
        List<Action> actionsList = new ArrayList<>();
        //for each point
        QueenState qs = (QueenState)s;
        for(Point q : qs.getQueenPositions()){
            for(int i = q.y+1; i < dim; i++){
                actionsList.add(new QueenAction(q, new Point(q.x, i)));
            }

            for(int i = q.y-1; i >= 0; i--){
                actionsList.add(new QueenAction(q, new Point(q.x, i)));
            }
        }
        return actionsList;

        //0

        //1

        //2  

        //3

        //4

        //QueenAction qa = new QueenAction(new Point(), null)
    }

    @Override
    public int getHeuristic(State s) {
        QueenState queenState = (QueenState)s;
        int countAttacks = 0;

        for(Point p : queenState.getQueenPositions()){
                if(queenState.getQueenPositions().stream().filter(_p -> _p.x > p.x && _p.y == p.y).findAny().isPresent()){
                    countAttacks++;
                }
                if(queenState.getQueenPositions().stream().filter(_p -> _p.y > p.y && _p.x == p.x).findAny().isPresent()){
                    countAttacks++;
                }
                if (queenState.getQueenPositions().stream()
                    .anyMatch(_p -> (_p.x != p.x || _p.y != p.y) && Math.abs(_p.x - p.x) == Math.abs(_p.y - p.y))) {
                    countAttacks++;
                }
        }

        return countAttacks;
    }

    @Override
    public boolean isActionValid(Action a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActionValid'");
    }

    @Override
    public State performActionOnState(State s, Action a) {
        QueenState qs = (QueenState)s;
        QueenAction qa = (QueenAction)a;

        QueenState newState = new QueenState();
        
        for(Point p : qs.getQueenPositions()){
            newState.addQueen(new Point(p.x, p.y));
        }

        newState.getQueenPositions().remove(qa.getTargetQueen());
        newState.getQueenPositions().add(qa.getTargetPosition());

        return newState;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public State getBestNeighbor(State s) {
        // Find the s state heuristic
        int currentH = this.getHeuristic(s);
        

        // Randomly choose a column
        Collections.shuffle(state.getQueenPositions());
        List<Action> legalActions = this.getActions(s);

        // Move to a random row starting from the littlest one
        for(Action a : legalActions){
            State possibleNewState = performActionOnState(s, a);
            if(getHeuristic(possibleNewState) < currentH){
                System.out.println("Find a better one: " + getHeuristic(possibleNewState) + " previous: " + currentH);
                //NOTE: Save here a equal one(?)
                return possibleNewState;
            }
        }

        return s;
    }

    @Override
    public List<State> getNeighborhood(State s) {
        List<Action> legalActions = this.getActions(s);
        List<State> neighborhood = new ArrayList<>();
        Collections.shuffle(((QueenState)s).getQueenPositions());

        // Move to a random row starting from the littlest one
        for(Action a : legalActions){
            neighborhood.add(performActionOnState(s, a));
        }

        return neighborhood;
    }
}
