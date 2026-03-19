package protein;

public class ProteinAction implements framework.Action {
    private Direction direction;
    private int cost;
    
    public ProteinAction(Direction d, int cost) {
        this.direction = d;
        this.cost = cost;
    }
    
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getCost() {
        return cost; 
    }
}
