package csp;

import framework.Action;

public class CspAction implements Action{
    private int targetIndex;
    private int targetValue;

    public CspAction(int targetIndex, int targetValue) {
        this.targetIndex = targetIndex;
        this.targetValue = targetValue;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public int getTargetValue() {
        return targetValue;
    }

    @Override
    public int getCost() {
        return 1;
    }
    
}
