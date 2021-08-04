package fbach6i.raytracing.rendering.sampler;

public class RecursionCount {

    private int _recursionCount = 0;
    private int _maxRecursion = 0;

    public int getCount() {
        return _recursionCount;
    }

    public RecursionCount increase() {
        _recursionCount++;
        _maxRecursion = Math.max(_maxRecursion, _recursionCount);
        return this;
    }

    public RecursionCount decrease() {
        _recursionCount--;
        return this;
    }

    public int getMaxRecursion() {
        return _maxRecursion;
    }

    public void reset() {
        _recursionCount = 0;
        _maxRecursion = 0;
    }

}
