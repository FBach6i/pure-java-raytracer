package fbach6i.raytracing.rendering.sampler.math;

import java.util.ArrayList;

public class Quadrant {
    private Vector2 _center;
    private float _size;

    public Quadrant(Vector2 center, float size){
        _center = center; 
        _size = size;
    }

    public Vector2 getTopRightCorner(){
        Vector2 cornerOffset = new Vector2(_size/2,_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getTopLeftCorner(){
        Vector2 cornerOffset = new Vector2(-_size/2,_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getBottomLeftCorner(){
        Vector2 cornerOffset = new Vector2(-_size/2,-_size/2);
        return _center.add(cornerOffset);
    }

    public Vector2 getBottomRightCorner(){
        Vector2 cornerOffset = new Vector2(_size/2,-_size/2);
        return _center.add(cornerOffset);
    }
    
    public ArrayList<Vector2> getSamplePoints(int gridDimension){
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

    public Vector2 getCenter(){
        return _center;
    }

    public Quadrant getTopRightSubquadrant(){
        Vector2 centerOffset = new Vector2(_size/4F, _size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant topRightSubquadrant = new Quadrant(newCenter, newSize);
        return topRightSubquadrant; 
    }

    public Quadrant getTopLeftSubquadrant(){
        Vector2 centerOffset = new Vector2(-_size/4F, _size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant topLeftSubquadrant = new Quadrant(newCenter, newSize);
        return topLeftSubquadrant; 
    }

    public Quadrant getBottomRightSubquadrant(){
        Vector2 centerOffset = new Vector2(_size/4F,-_size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant bottomRightSubquadrant = new Quadrant(newCenter, newSize);
        return bottomRightSubquadrant; 
    }

    public Quadrant getBottomLeftSubquadrant(){
        Vector2 centerOffset = new Vector2(-_size/4F,-_size/4F);
        Vector2 newCenter = _center.add(centerOffset);
        float newSize = _size/2;
        Quadrant bottomLeftSubquadrant = new Quadrant(newCenter, newSize);
        return bottomLeftSubquadrant; 
    }

    @Override
    public String toString() {
        return "" + _center + " and size:" + _size;
    }
    
}
