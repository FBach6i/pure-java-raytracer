package fbach6i.raytracing.rendering.sampler.math;

import java.util.ArrayList;
import java.util.Random;

public class Quadrant {
    private Vector2 _center;
    private float _size;

    public Quadrant(Vector2 center, float size) {
        _center = center; 
        _size = size;
    }

    public Vector2 getTopLeftCorner() {
        Vector2 cornerOffset = new Vector2(-_size/2,_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getTopLeftCornerStratified(Random random) {
        Vector2 corner = getTopLeftCorner();
        Vector2 cornerOffset = new Vector2((random.nextFloat() * _size/4), -(random.nextFloat() * _size/4));
        Vector2 cornerStratified = corner.add(cornerOffset);
        return cornerStratified;
    }

    public Vector2 getTopRightCorner() {
        Vector2 cornerOffset = new Vector2(_size/2,_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getTopRightCornerStratified(Random random) {
        Vector2 corner = getTopRightCorner();
        Vector2 cornerOffset = new Vector2(-(random.nextFloat() * _size/4), -(random.nextFloat() * _size/4));
        Vector2 cornerStratified = corner.add(cornerOffset);
        return cornerStratified;
    }

    public Vector2 getBottomLeftCorner() {
        Vector2 cornerOffset = new Vector2(-_size/2,-_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getBottomLeftCornerStratified(Random random) {
        Vector2 corner = getBottomLeftCorner();
        Vector2 cornerOffset = new Vector2((random.nextFloat() * _size/4), (random.nextFloat() * _size/4));
        Vector2 cornerStratified = corner.add(cornerOffset);
        return cornerStratified;
    }

    public Vector2 getBottomRightCorner() {
        Vector2 cornerOffset = new Vector2(_size/2,-_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getBottomRightCornerStratified(Random random) {
        Vector2 corner = getBottomRightCorner();
        Vector2 cornerOffset = new Vector2(-(random.nextFloat() * _size/4), (random.nextFloat() * _size/4));
        Vector2 cornerStratified = corner.add(cornerOffset);
        return cornerStratified;
    }
    
    public ArrayList<Vector2> getUniformSamplePoints(int gridDimension) {
        ArrayList<Vector2> result = new ArrayList<Vector2>();
        Vector2 startingpoint = this.getBottomLeftCorner();
        Vector2 horizontalOffset = new Vector2(_size/gridDimension,0);
        Vector2 verticalOffset = new Vector2(0,_size/gridDimension);
        Vector2 diagonalOffset = new Vector2(_size/(2*gridDimension), _size/(2*gridDimension));
    
        for (int i=0; i < gridDimension; i++) {
            for (int j=0; j < gridDimension; j++){
                Vector2 gridPoint = startingpoint.add(horizontalOffset.scale(j)).add(diagonalOffset);
                result.add(gridPoint);
            }
            startingpoint = startingpoint.add(verticalOffset);
        }
        
        return result;
    }

    public ArrayList<Vector2> getStratifiedSamplePoints(int gridDimension, Random random) {
        ArrayList<Vector2> uniformSamplePoints = getUniformSamplePoints(gridDimension);
        ArrayList<Vector2> stratifiedSamplePoints = new ArrayList<>();

        int divider = 2 * gridDimension;

        for (Vector2 samplePoint : uniformSamplePoints) {
            float xOffset = random.nextFloat();
            if (xOffset < 0.5) { xOffset = -xOffset; }
            float yOffset = random.nextFloat();
            if (yOffset < 0.5) { yOffset = -yOffset; }
            Vector2 offset = new Vector2((xOffset *_size / divider), (yOffset * _size / divider));
            stratifiedSamplePoints.add(samplePoint.add(offset));
        }

        return stratifiedSamplePoints;
    }

    public Vector2 getCenter() {
        return _center;
    }

    public Quadrant getTopLeftSubquadrant() {
        Vector2 centerOffset = new Vector2(-_size/4F, _size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant topLeftSubquadrant = new Quadrant(newCenter, newSize);
        return topLeftSubquadrant; 
    }

    public Quadrant getTopRightSubquadrant() {
        Vector2 centerOffset = new Vector2(_size/4F, _size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant topRightSubquadrant = new Quadrant(newCenter, newSize);
        return topRightSubquadrant; 
    }

    public Quadrant getBottomRightSubquadrant() {
        Vector2 centerOffset = new Vector2(_size/4F,-_size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant bottomRightSubquadrant = new Quadrant(newCenter, newSize);
        return bottomRightSubquadrant; 
    }

    public Quadrant getBottomLeftSubquadrant() {
        Vector2 centerOffset = new Vector2(-_size/4F,-_size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant bottomLeftSubquadrant = new Quadrant(newCenter, newSize);
        return bottomLeftSubquadrant; 
    }

    @Override
    public String toString() {
        return "Quadrant with center: " + _center + " and size: " + _size;
    }
    
}
