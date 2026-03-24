package miglioramento_iterativo;

import framework.State;
import framework_iterative.IterativeImprovementSolver;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        // Parse arguments with fallbacks for dimension and iterations
        String algorithm = args[0].toLowerCase();
        int dim = (args.length > 1) ? Integer.parseInt(args[1]) : 8;
        int totalIterations = (args.length > 2) ? Integer.parseInt(args[2]) : 100000;

        int successfulRuns = 0;
        IterativeImprovementSolver solver = new IterativeImprovementSolver();

        System.out.println("==================================================");
        System.out.println("Starting N-Queens Iterative Improvement Test");
        System.out.println("Algorithm : " + algorithm.toUpperCase());
        System.out.println("Board Dim : " + dim + "x" + dim);
        System.out.println("Iterations: " + totalIterations);
        System.out.println("==================================================");

        long startTime = System.currentTimeMillis();

        // 3. Run the massive test loop
        for (int i = 0; i < totalIterations; i++) {
            
            // Generate a random initial board state
            QueenProblem qp = new QueenProblem(dim);
            State localOptimumState = null;

            // Execute the chosen algorithm
            switch (algorithm) {
                case "steepest":
                    localOptimumState = solver.steepestAscent(qp);
                    break;
                case "firstchoice":
                    localOptimumState = solver.hillClimbing(qp);
                    break;
                case "annealing":
                    localOptimumState = solver.simulatedAnnealing(qp);
                    break;
                case "restart-steepest":
                    localOptimumState = randomRestartHillClimbing(dim, solver, "steepest");
                    break;
                case "restart-firstchoice":
                    localOptimumState = randomRestartHillClimbing(dim, solver, "firstchoice");
                    break;
                default:
                    System.out.println("Error: Unknown algorithm '" + algorithm + "'.");
                    printUsage();
                    return;
            }

            // Check if the final state is a global optimum (0 attacks)
            if (qp.getHeuristic(localOptimumState) == 0) {
                successfulRuns++;
            }
        }

        long endTime = System.currentTimeMillis();
        float executionTimeSec = (endTime - startTime) / 1000.0f;
        float successRate = ((float) successfulRuns / totalIterations) * 100.0f;

        // 4. Print the final analytics
        System.out.println("\n--- RESULTS ---");
        System.out.println("Global Optima Found : " + successfulRuns + " / " + totalIterations);
        System.out.println("Estimated Success % : " + String.format("%.2f", successRate) + "%");
        System.out.println("Total Execution Time: " + executionTimeSec + " seconds");
        System.out.println("==================================================");
    }

    private static void printUsage() {
        System.out.println("Usage: java Main <algorithm> [dimension] [iterations]");
        System.out.println("Algorithms:");
        System.out.println("  steepest            - Runs Steepest Ascent Hill Climbing");
        System.out.println("  firstchoice         - Runs First-Choice Hill Climbing");
        System.out.println("  annealing           - Runs Simulated Annealing");
        System.out.println("  restart-steepest    - Runs Steepest Ascent Hill Climbing with Restarts");
        System.out.println("  restart-firstchoice - Runs First-Choice Hill Climbing with Restarts");
        System.out.println("\nExample:");
        System.out.println("  java Main steepest 8 1000000");
    }

    public static State randomRestartHillClimbing(int dim, IterativeImprovementSolver solver, String baseAlgorithm) {
        int restarts = 0;
        
        while (true) {
            QueenProblem qp = new QueenProblem(dim);
            State localOptimum = null;
            
            switch (baseAlgorithm) {
                case "steepest":
                    localOptimum = solver.steepestAscent(qp);
                    break;
                case "firstchoice":
                    localOptimum = solver.hillClimbing(qp);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported base algorithm for restart.");
            }
            
            if (qp.getHeuristic(localOptimum) == 0) {
                System.out.println("Global optimum found! Total restarts required: " + restarts);
                return localOptimum;
            }
            
            // If it's a local maximum (attacks > 0), the loop continues, 
            // throwing away the old state and generating a new random one.
            restarts++;
        }
    }
}