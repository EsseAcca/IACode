package framework_iterative;

import java.util.List;

import framework.Node;
import framework.Problem;
import framework.State;
import miglioramento_iterativo.QueenState;

public class IterativeImprovementSolver {

    public State solve(IterativeImprovementProblem p){
        Node node = new Node();
        node.setState(p.getState());

        return hillClimbing(p);
    }

    public State hillClimbing(IterativeImprovementProblem p){
        Node currentNode = new Node();
        currentNode.setState(p.getState());
        currentNode.setH(p.getHeuristic(currentNode.getState()));

        boolean actionFound = false;
        while(true){
            actionFound = false;

            List<State> neighborhood = p.getNeighborhood(currentNode.getState());
            while(!actionFound && !neighborhood.isEmpty()){
                State neighbor = neighborhood.removeLast();
                int neighborH = p.getHeuristic(neighbor);

                if(neighborH < currentNode.getH()){
                    currentNode.setState(neighbor);
                    currentNode.setH(neighborH);
                
                    actionFound = true;
                }
            }

            if(actionFound == false){
                return currentNode.getState();
            }
        }
    }

    public State steepestAscent(IterativeImprovementProblem p){
        Node currentNode = new Node();
        currentNode.setState(p.getState());
        currentNode.setH(p.getHeuristic(currentNode.getState()));

        while(true){
            Node neighborNode = new Node();
            State neighborhood = p.getBestNeighbor(currentNode.getState());

            neighborNode.setState(neighborhood);
            
            if(currentNode.getH() <= p.getHeuristic(neighborNode.getState())){
                return currentNode.getState();
            } else{
                currentNode = neighborNode;
            }
        }
    }

}
