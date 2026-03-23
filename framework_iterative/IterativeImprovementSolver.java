package framework_iterative;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import framework.Action;
import framework.Node;
import framework.State;

public class IterativeImprovementSolver {

    public State solve(IterativeImprovementProblem p){
        Node node = new Node();
        node.setState(p.getState());

        return hillClimbing(p);
    }

    public State hillClimbing(IterativeImprovementProblem p){
        State currentState = p.getState();
        int currentH = p.getHeuristic(currentState);

        boolean actionFound = false;
        while(true){
            actionFound = false;

            State nextNeighbor = null;
            int nextNeighborH = 0;

            List<Action> availableActions = p.getActions(currentState);
            Collections.shuffle(availableActions);

            while(!actionFound){
                if(availableActions.isEmpty())
                    break;

                nextNeighbor = p.performActionOnState(currentState, availableActions.removeLast());
                nextNeighborH = p.getHeuristic(nextNeighbor);

                if(nextNeighborH < currentH){
                    currentState = nextNeighbor;
                    currentH = nextNeighborH;
                
                    actionFound = true;
                }
            }

            if(actionFound == false){
                return currentState;
            }
        }
    }

    public State steepestAscent(IterativeImprovementProblem p){
        State currentState = p.getState();
        int currentH = p.getHeuristic(currentState);

        while(true){
            State neighborState = p.getBestNeighbor(currentState);
            if(neighborState == null)
                return currentState;

            int neighborH = p.getHeuristic(neighborState);
            if(currentH <= neighborH){
                return currentState;
            }

            currentState = neighborState;
            currentH = neighborH;
        }
    }

    public State simulatedAnnealing(IterativeImprovementProblem p){
        Random random = new Random();

        Node currentNode = new Node();
        currentNode.setState(p.getState());
        currentNode.setH(p.getHeuristic(currentNode.getState()));
        float T = 0;

        State neighborState;
        List<State> neighborhood;
        int neighborH;

        int deltaH = 0;

        for(int t = 1;;t++){
            T = coolingFunction(t);

            if(T == 0)
                return currentNode.getState();
            
            neighborhood = p.getNeighborhood(currentNode.getState());
            neighborState = neighborhood.get(random.nextInt(neighborhood.size()));
            neighborH = p.getHeuristic(neighborState);

            if(neighborH < currentNode.getH()){
                currentNode.setState(neighborState);
                currentNode.setH(neighborH);
            }else{
                deltaH = Math.abs(neighborH - currentNode.getH());
                //probability of e^deltaE/T
                if(takeShot(Math.exp((double)-deltaH / (double)T), random)){
                    currentNode.setState(neighborState);
                    currentNode.setH(neighborH);
                }
            }
        }
    }

    private float coolingFunction(int t) {
        float initialTemp = 30.0f; 
        float alpha = 0.95f; 
        
        float T = (float) (initialTemp * Math.pow(alpha, t));

        return (T < 0.001f) ? 0 : T;
    }

    private boolean takeShot(double probability, Random random) {
        return random.nextDouble() < probability;
    }

}
