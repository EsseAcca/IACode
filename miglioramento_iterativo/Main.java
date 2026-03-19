package miglioramento_iterativo;

import framework_iterative.IterativeImprovementSolver;
import general.Point;
import protein.ProteinState;

public class Main {
    public static void main(String[] args) {
        int dim = 8;
        int total0 = 0;
        int total = 5000000;
        for(int i = 0; i < total; i++){
            
            QueenProblem qp = new QueenProblem(dim);

            IterativeImprovementSolver solver = new IterativeImprovementSolver();

            QueenState localOptimumState = (QueenState)solver.solve(qp);

            if(qp.getHeuristic(localOptimumState) == 0)
                total0 += 1;

        }

        float y  = 100.0f / total * (float)total0;

        System.out.println("Estimated %: " + y + " Total: " + total0);

    }
}