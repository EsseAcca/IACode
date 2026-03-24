package protein;

import framework.Action;
import framework.Problem;
import framework.State;
import framework.Solver;
import frontiers.AStarFrontier;
import frontiers.MinCostFrontier;
import general.Point;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Default fallback values
        String sequence = "PHPHPHPPH";
        String algorithm = "astar";

        // 2. Parse arguments if provided
        if (args.length >= 1) {
            sequence = args[0].toUpperCase();
        }
        if (args.length >= 2) {
            algorithm = args[1].toLowerCase();
        }

        System.out.println("==================================================");
        System.out.println("Starting Protein Folding Search");
        System.out.println("Sequence : " + sequence);
        System.out.println("Algorithm: " + algorithm.toUpperCase());
        System.out.println("==================================================");

        // 3. Initialize Problem and Solver
        Problem problem = new ProteinProblem(sequence);
        Solver solver = null;

        switch (algorithm) {
            case "astar":
                solver = new Solver(new AStarFrontier());
                break;
            case "mincost":
                solver = new Solver(new MinCostFrontier());
                break;
            default:
                System.out.println("Error: Unknown algorithm '" + algorithm + "'.");
                printUsage();
                return;
        }

        // 4. Run the Search and Time it
        long startTime = System.currentTimeMillis();
        List<Action> solution = solver.solve(problem);
        long endTime = System.currentTimeMillis();
        float executionTimeSec = (endTime - startTime) / 1000.0f;

        // 5. Output Results
        if (solution != null) {
            System.out.println("\n--- RESULTS ---");
            System.out.println("Solution found in " + solution.size() + " steps!");
            System.out.println("Execution Time: " + executionTimeSec + " seconds");
            
            State currentState = problem.getInitialState();
            int finalEnergy = 0;
            
            System.out.println("\nExecution Trace:");
            for (Action a : solution) {
                ProteinAction pa = (ProteinAction) a;
                System.out.println(" -> Move " + pa.getDirection() + " (Action Cost: " + pa.getCost() + ")");
                finalEnergy += a.getCost();
                currentState = problem.performActionOnState(currentState, pa);
            }
            
            printProteinGrid((ProteinState) currentState, sequence);
            
            System.out.println("\nFinal Total Energy: " + finalEnergy);
            System.out.println("==================================================");
            
        } else {
            System.out.println("\nNo valid non-crossing conformation found.");
            System.out.println("Execution Time: " + executionTimeSec + " seconds");
        }
    }

    private static void printUsage() {
        System.out.println("\nUsage: java protein.Main [sequence] [algorithm]");
        System.out.println("Algorithms:");
        System.out.println("  astar   - Runs A* Search (Requires Admissible Heuristic)");
        System.out.println("  mincost - Runs Min-Cost Search (Uniform Cost Search)");
        System.out.println("\nExample:");
        System.out.println("  java protein.Main HHPHPHPHPH astar");
    }

    public static void printProteinGrid(ProteinState state, String sequence) {
        java.util.List<Point> points = state.getPlacedAcids();
        if (points.isEmpty()) return;

        // 1. Find the boundaries of our grid
        int minX = 0, maxX = 0, minY = 0, maxY = 0;
        for (Point p : points) {
            minX = Math.min(minX, p.x);
            maxX = Math.max(maxX, p.x);
            minY = Math.min(minY, p.y);
            maxY = Math.max(maxY, p.y);
        }

        // 2. Create an empty grid
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        char[][] grid = new char[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '.'; 
            }
        }

        // 3. Place the amino acids onto the grid
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            int gridY = maxY - p.y; // Flip Y so positive goes UP
            int gridX = p.x - minX;
            grid[gridY][gridX] = sequence.charAt(i);
        }

        // 4. Print it out!
        System.out.println("\nFinal Protein Conformation:");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}