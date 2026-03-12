package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import framework.Action;
import framework.Frontier;
import framework.Problem;
import framework.Solver;
import frontiers.AStarFrontier;
import frontiers.BFSFrontier;
import frontiers.BestFirstGreedyFrontier;
import frontiers.DFSFrontier;
import frontiers.MinCostFrontier;

public class Main {
    public static void main(String args[]) {
        String edgeData1 = """
        S -> A : 2
        S -> B : 7
        S -> D : 5
        A -> B : 4
        B -> G1 : 9
        B -> C : 3
        C -> S : 1
        C -> J : 5
        C -> F : 2
        D -> S : 8
        D -> C : 3
        D -> E : 3
        E -> G2 : 7
        F -> D : 1
        F -> G2 : 4
        J -> G1 : 3
        """;

    String heuristicData1 = """
        S : 7
        A : 9
        B : 3
        C : 2
        D : 4
        E : 5
        F : 3
        J : 1
        G1 : 0
        G2 : 0
    """;

    String edgeData2 = """
        S -> A : 3
        S -> B : 3
        S -> D : 3
        A -> E : 1
        A -> H : 8
        B -> C : 2
        B -> I : 3
        B -> J : 5
        C -> S : 1
        C -> G2 : 18
        D -> C : 2
        E -> H : 7
        E -> D : 2
        H -> G1 : 9
        I -> A : 1
        I -> H : 4
        J -> G2 : 12
        G2 -> B : 15
        """;

    String heuristicData2 = """
        S : 20
        A : 16
        B : 16
        C : 14
        D : 17
        E : 15
        H : 8
        I : 12
        J : 10
        G1 : 0
        G2 : 0
    """;

        Set<String> goalStates = Set.of("G1", "G2");

        Map<String, List<Action>> graph = new java.util.HashMap<>();
        Map<String, Integer> heuristics = new java.util.HashMap<>();

        GraphBuilder.loadGraph(edgeData2, graph);
        GraphBuilder.loadHeuristics(heuristicData2, heuristics);

        Problem problem = new GraphProblem(graph, heuristics, goalStates);

        //Solver solver = new Solver(new MinCostFrontier());

        List<Frontier> frontierList = new ArrayList<>();
        frontierList.add(new DFSFrontier());
        frontierList.add(new BFSFrontier());
        frontierList.add(new MinCostFrontier());
        frontierList.add(new BestFirstGreedyFrontier());
        frontierList.add(new AStarFrontier());
        for(Frontier f : frontierList){
            System.out.println("====================================");
            Solver solver = new Solver(f);
            List<Action> solution = solver.solve(problem);
            System.out.println("Solution: " + solution.toString());
            System.out.println("====================================");
        }

        //int depthLimit = 10;
        
        
        //List<Action> solutionIDS = solver.solveIDS(problem, depthLimit);
        
        //System.out.println("Solution IDS: " + solutionIDS.toString());
    }
}
