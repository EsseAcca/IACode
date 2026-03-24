package miglioramento_iterativo;

import framework.Action;
import general.Point;

public class QueenAction implements Action{
    private Point targetQueen;
    private Point targetPosition;
    private int cost;
    
    public QueenAction(Point targetQueen, Point targetPosition) {
        this.targetQueen = targetQueen;
        this.targetPosition = targetPosition;
        this.cost = 1;
    }

    public Point getTargetQueen(){
        return targetQueen;
    }
    
    public Point getTargetPosition() {
        return targetPosition;
    }

    @Override
    public int getCost() {
        return cost;
    }

}
