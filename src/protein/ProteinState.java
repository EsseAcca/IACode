package protein;

import general.Point;

public class ProteinState extends framework.State {
    private java.util.List<Point> placedAcids;
    
    public ProteinState(java.util.List<Point> acids) {
        this.placedAcids = acids;
    }

    public java.util.List<Point> getPlacedAcids() {
        return placedAcids;
    }

    public ProteinState performAction(Direction d) {
        // Create a new list of placed acids with the new point added
        java.util.List<Point> newPlacedAcids = new java.util.ArrayList<>(placedAcids);
        Point lastPoint = placedAcids.get(placedAcids.size() - 1);
        Point newPoint = getNewPoint(lastPoint, d);
        newPlacedAcids.add(newPoint);
        return new ProteinState(newPlacedAcids);
    }

    private Point getNewPoint(Point current, Direction d) {
        switch (d) {
            case UP:
                return new Point(current.x, current.y + 1);
            case DOWN:
                return new Point(current.x, current.y - 1);
            case LEFT:
                return new Point(current.x - 1, current.y);
            case RIGHT:
                return new Point(current.x + 1, current.y);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProteinState)) return false;
        ProteinState other = (ProteinState) o;
        return this.placedAcids.equals(other.getPlacedAcids());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(placedAcids);
    }
}