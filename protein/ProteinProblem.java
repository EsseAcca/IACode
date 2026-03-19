package protein;

import java.util.ArrayList;
import java.util.List;

import framework.Action;
import framework.Problem;
import framework.State;
import general.Point;

public class ProteinProblem implements Problem {
    private ProteinState initialState;
    private String proteinSequence;

    public ProteinProblem(String proteinSequence) {
        this.proteinSequence = proteinSequence;
        this.initialState = new ProteinState(java.util.Arrays.asList(new Point(0, 0)));
    }

    @Override
    public State getInitialState() {
        return initialState;
    }

    @Override
    public List<Action> getActions(State s) {
        ProteinState ps = (ProteinState) s;
        List<Action> validActions = new ArrayList<>();

        // If the protein is fully folded, no more actions
        if (isGoalState(s)) {
            return validActions;
        }

        List<Point> placed = ps.getPlacedAcids();
        Point head = placed.get(placed.size() - 1);
        
        int nextIndex = placed.size(); 
        boolean isNextH = proteinSequence.charAt(nextIndex) == 'H';

        for (Direction d : Direction.values()) {
            Point nextPoint = calculateNextPoint(head, d);

            if (!placed.contains(nextPoint)) {
                
                int newContacts = 0;

                if (isNextH) {
                    for (Direction radarDir : Direction.values()) {
                        Point adjacentPoint = calculateNextPoint(nextPoint, radarDir);
                        
                        // Skip the 'head' we just came from (must be non-sequential!)
                        if (adjacentPoint.equals(head)) {
                            continue;
                        }

                        int hitIndex = placed.indexOf(adjacentPoint);
                        if (hitIndex != -1) {
                            if (proteinSequence.charAt(hitIndex) == 'H') {
                                newContacts++;
                            }
                        }
                    }
                }

                // We add the action. The cost is negative because A* minimizes cost, 
                // and we want to maximize contacts (lower energy).
                // 1 contact = cost of -1.
                validActions.add(new ProteinAction(d, -newContacts));
            }
        }

        return validActions;
    }

    @Override
    public int getHeuristic(State s) {
        ProteinState ps = (ProteinState) s;

        // At the goal, potential for new contacts is exactly 0.
        if (isGoalState(ps)) {
            return 0;
        }

        int unplacedHs = 0;
        for (int i = ps.getPlacedAcids().size(); i < proteinSequence.length(); i++) {
            if (proteinSequence.charAt(i) == 'H') {
                unplacedHs++;
            }
        }

        // Assume the absolute best: every unplaced H finds 2 contacts.
        int optimisticContacts = unplacedHs * 2;
        
        int lastIndex = proteinSequence.length() - 1;
        if (lastIndex >= ps.getPlacedAcids().size() && proteinSequence.charAt(lastIndex) == 'H') {
            optimisticContacts++; 
        }

        // Return the negative to represent reduced energy/cost.
        return -optimisticContacts;
    }

    @Override
    public boolean isActionValid(Action a) {
        return true;
    }

    @Override
    public State performActionOnState(State s, Action a) {
        ProteinState ps = (ProteinState) s;
        ProteinAction pa = (ProteinAction) a;
        
        return ps.performAction(pa.getDirection());
    }

    private Point calculateNextPoint(Point current, Direction d) {
        switch (d) {
            case UP: return new Point(current.x, current.y + 1);
            case DOWN: return new Point(current.x, current.y - 1);
            case LEFT: return new Point(current.x - 1, current.y);
            case RIGHT: return new Point(current.x + 1, current.y);
            default: throw new IllegalArgumentException("Unknown direction");
        }
    }


    @Override
    public boolean isGoalState(State s) {
        return ((ProteinState) s).getPlacedAcids().size() == proteinSequence.length();
    }


}
