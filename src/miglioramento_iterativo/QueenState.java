package miglioramento_iterativo;

import java.util.ArrayList;

import framework.State;
import general.Point;

public class QueenState extends State{
    private java.util.List<Point> queenPositions;

    public QueenState(){
        this.queenPositions = new ArrayList<>();
    }

    public java.util.List<Point> getQueenPositions() {
        return queenPositions;
    }

    public void addQueen(Point q){
        this.queenPositions.add(q);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueenState)) return false;
        QueenState other = (QueenState) o;
        return this.queenPositions.equals(other.getQueenPositions());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(queenPositions);
    }

}
