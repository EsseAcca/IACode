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
        String sequence = "PHPHPHPPH";
        Problem problem = new ProteinProblem(sequence);

        Solver solver = new Solver(new AStarFrontier());
        
        List<Action> solution = solver.solve(problem);

        if (solution != null) {
            System.out.println("Solution found in " + solution.size() + " steps:");
            
            State currentState = problem.getInitialState();
            for (Action a : solution) {
                ProteinAction pa = (ProteinAction) a;
                System.out.println(" -> Move " + pa.getDirection() + " (Action Cost: " + pa.getCost() + ")");
                currentState = problem.performActionOnState(currentState, pa);
            }
            
            printProteinGrid((ProteinState) currentState, sequence);

            int finalEnergy = 0;
            for (Action a : solution) {
                finalEnergy += a.getCost(); 
            }
            
            System.out.println("\nFinal Total Energy: " + finalEnergy);
            
        } else {
            System.out.println("No valid non-crossing conformation found.");
        }
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