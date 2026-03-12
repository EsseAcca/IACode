package framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Solver {
    private Frontier f;
    private HashSet<State> exploredSet;

    public Solver(Frontier f){
        this.f = f;
        this.exploredSet = new HashSet<>();
    }

    public List<Action> solve(Problem p){
        return this.solve(p, Integer.MAX_VALUE);
    }

    public List<Action> solveIDS(Problem p, int maxDepth){
        int currentLimit = 0;
        List<Action> solution = null;

        while(currentLimit <= maxDepth){
            this.exploredSet.clear();
            solution = this.solve(p, currentLimit);
            if(solution != null){
                break;
            }
            currentLimit++;
        }

        return solution;
    }

    List<Action> solve(Problem p, int depthLimit){
        boolean PRINT_TRACE = true;

        Node node = new Node();
        node.setG(0);
        node.setState(p.getInitialState());
        node.setDepth(0);
        
        f.enqueue(node);

        int iterations = 0;

        Node currentNode;
        while(!f.isEmpty()){
            currentNode = f.dequeue();

            iterations++;

            if(PRINT_TRACE){
            System.out.println("Iterazione " + iterations + ":");
            System.out.println("  Nodo Espanso: " + currentNode);
            }

            if(currentNode.getDepth() > depthLimit && !p.isGoalState(currentNode.getState())){
                continue;
            }

            exploredSet.add(currentNode.getState());

            if(p.isGoalState(currentNode.getState())){
                if(PRINT_TRACE){
                    System.out.println("\n*** SOLUZIONE TROVATA IN " + iterations + " ITERAZIONI ***");
                }
                return extractSolution(currentNode);
            }

            for (Action action : p.getActions(currentNode.getState())) {
                if(!p.isActionValid(action))
                    continue;
                
                Node childNode = getChild(p, currentNode, action);

                if(!exploredSet.contains(childNode.getState()) && !f.contains(childNode.getState())){
                    f.enqueue(childNode);
                }else if(!exploredSet.contains(childNode.getState()) && f.contains(childNode.getState())){
                    if(childNode.getG() < f.getNodeByState(childNode.getState()).getG()){
                        Node alreadyPresentStateNode = f.getNodeByState(childNode.getState());
    
                        // If the new path is cheaper...
                        if (childNode.getG() < alreadyPresentStateNode.getG()) {
                            
                            // 1. Remove the old, expensive node from the frontier
                            f.removeNode(alreadyPresentStateNode);
                            
                            // 2. Enqueue cheaper child node
                            f.enqueue(childNode);
                        }
                    }
                }
            }

            if(PRINT_TRACE){
                System.out.println("  Nodi nell'Explored Set: " + getExploredNosedAsString());
                // Note: PriorityQueue doesn't guarantee print order, but it shows contents
                System.out.println("  Frontiera corrente: " + f); 
                System.out.println("-------------------------------------------------");
            }            
        }

        System.out.println("\nNessuna soluzione trovata dopo " + iterations + " iterazioni.");
        return null;
    }

    private String getExploredNosedAsString(){
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{ ");
        for(State s : exploredSet){
            sBuilder.append(s.toString());
            sBuilder.append(" ");
        }
        sBuilder.append("}");

        return sBuilder.toString();
    }

    private Node getChild(Problem p, Node n,Action a){
        Node childNode = new Node();

        State childState = p.performActionOnState(n.getState(), a);
        childNode.setState(childState);

        childNode.setParent(n);
        childNode.setA(a);
        childNode.setG(n.getG() + a.getCost());

        childNode.setH(p.getHeuristic(childState));
        childNode.setDepth(n.getDepth() + 1);

        return childNode;
    }

    private List<Action> extractSolution(Node n){
        ArrayList<Action> actionsList = new ArrayList<>();
        Node ptr = n;
        while(ptr.getParent() != null){
            actionsList.add(ptr.getA());
            ptr = ptr.getParent();
        }

        Collections.reverse(actionsList);
        return actionsList;
    }
}
