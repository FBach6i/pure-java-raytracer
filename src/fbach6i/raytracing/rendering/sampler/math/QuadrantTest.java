package fbach6i.raytracing.rendering.sampler.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuadrantTest {
    
    public void testUniformSamplePoints() {
        Quadrant quadrant1 = new Quadrant(new Vector2(0,0), 4);
        ArrayList<Vector2> uniformSamplePoints = quadrant1.getUniformSamplePoints(4);

        Vector2[] expectedResult = {
            new Vector2(-1.5F,-1.5F), 
            new Vector2(-0.5F,-1.5F),
            new Vector2(0.5F,-1.5F),
            new Vector2(1.5F,-1.5F),
            new Vector2(-1.5F,-0.5F), 
            new Vector2(-0.5F,-0.5F), 
            new Vector2(0.5F,-0.5F), 
            new Vector2(1.5F,-0.5F), 
            new Vector2(-1.5F,0.5F), 
            new Vector2(-0.5F,0.5F), 
            new Vector2(0.5F,0.5F), 
            new Vector2(1.5F,0.5F), 
            new Vector2(-1.5F,1.5F), 
            new Vector2(-0.5F,1.5F), 
            new Vector2(0.5F,1.5F), 
            new Vector2(1.5F,1.5F)        
        };

        ArrayList<Vector2> expectedUniformSamplePoints = new ArrayList<>(Arrays.asList(expectedResult));

        if (!uniformSamplePoints.equals(expectedUniformSamplePoints)) { 
            throw new Error("uniform sample points test failed, actual elements " + expectedUniformSamplePoints + " vs. expected " + uniformSamplePoints);
        } else {
            System.out.println(uniformSamplePoints);
        }
    }

    public static void main(String[] args) {
        QuadrantTest test = new QuadrantTest();
        test.testUniformSamplePoints();

        Quadrant quadrant1 = new Quadrant(new Vector2(0,0), 4);
        System.out.println(quadrant1.getStratifiedSamplePoints(4, new Random()));
    }

}
