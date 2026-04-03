package csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import framework.State;
import framework_iterative.IterativeImprovementProblem;
import framework_iterative.IterativeImprovementSolver;
import miglioramento_iterativo.QueenProblem;

// Section 5 first exercise about CSP Steepest Ascent.
public class Main {
    public static void main(String args[]) {
        ArrayList<Integer> initialValues = new ArrayList<>(Arrays.asList(3, 1, 4, 5, 2));
        
        List<ToIntFunction<CspState>> constraints = new ArrayList<>();

        constraints.add(state -> constraint1(state.getX().get(0), state.getX().get(2)));
        constraints.add(state -> constraint2(state.getX().get(1), state.getX().get(2)));
        constraints.add(state -> constraint3(state.getX().get(2), state.getX().get(3)));
        constraints.add(state -> constraint4(state.getX().get(4)));
        constraints.add(state -> constraint5(state.getX().get(0), state.getX().get(4)));

        CspProblem cspp = new CspProblem(initialValues, constraints);

        IterativeImprovementSolver solver = new IterativeImprovementSolver();

        solver.steepestAscent(cspp);
        

    }

    private static int constraint1(int x1, int x3) {
        return Math.max(0, 0 + 1 - (x1 - x3));
    }

    private static int constraint2(int x2, int x3) {
        return Math.max(0, (x2 - x3) - 0);
    }

    private static int constraint3(int x3, int x4) {
        return Math.max(0, ((x3 * x3) + (x4 * x4)) - 15);
    }

    private static int constraint4(int x5) {
        return Math.max(0, 3 - x5);
    }

    private static int constraint5(int x1, int x5) {
        return Math.max(0, 3 - (x1 + x5));
    }
}
