package fbach6i.raytracing.rendering.sampler.math;

public class Vector2Test {

    private Vector2 _firstVector = new Vector2(2,2);
    private Vector2 _secondVector = new Vector2(7,4);

    public void testAddition() {
        Vector2 result = _firstVector.add(_secondVector);
        Vector2 expectedResult = new Vector2(9,6);
        if (result.equals(expectedResult)) {
            System.out.println(_firstVector + " + " + _secondVector + " = " + result);
        } else {
            throw new Error("result was " + result + " and was expected to be " + expectedResult);
        }
    }
    
    public void testSubtraction() {
        Vector2 result = _firstVector.subtract(_secondVector);
        Vector2 expectedResult = new Vector2(-5,-2);
        if (result.equals(expectedResult)) {
            System.out.println(_firstVector + " + " + _secondVector + " = " + result);
        } else {
            throw new Error("result was " + result + " and was expected to be " + expectedResult);
        }
    }

    public static void main(String[] args) {
        Vector2Test test = new Vector2Test();
        test.testAddition();
        test.testSubtraction();
    }

}
