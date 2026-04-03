package csp;

import java.util.List;

import framework.State;

public class CspState extends State{
    private List<Integer> x;

    public CspState(List<Integer> x) {
        this.x = x;
    }

    public List<Integer> getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof CspState && this.x.equals(((CspState) o).x));
    }

    @Override
    public int hashCode() {
        return x.hashCode();
    }
    
}
