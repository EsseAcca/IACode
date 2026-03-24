package general;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point other = (Point) o;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    public String toString(){
        return "{y:" + this.y + " x:" + this.x + "}";
    }
}
