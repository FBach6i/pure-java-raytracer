package fbach6i.raytracing.rendering.sampler.math;

public class QuadrantTest {
    
    public static void main(String[] args) {
        Quadrant quadrant1 = new Quadrant(new Vector2(-1.86F,0.61F),0.002939F);
        //System.out.println(quadrant1.getTopLeftCorner());
        //System.out.println(quadrant1.getTopRightCorner());
        //System.out.println(quadrant1.getBottomLeftCorner());
        //System.out.println(quadrant1.getBottomRightCorner());
        //System.out.println(quadrant1.getTopRightSubquadrant());
        //System.out.println(quadrant1.getTopLeftSubquadrant());
        //System.out.println(quadrant1.getBottomRightSubquadrant());
        //System.out.println(quadrant1.getBottomLeftSubquadrant());

        System.out.println(quadrant1.getSamplePoints(9));

    }

}
