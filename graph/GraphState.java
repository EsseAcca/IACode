package graph;
import framework.State;

public class GraphState extends State{
    private String name;

    public GraphState(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return this.name.equalsIgnoreCase(((GraphState) o).getName());
 }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString(){
        return this.name;
    }
}