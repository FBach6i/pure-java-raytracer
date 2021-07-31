package fbach6i.raytracing.rendering.sampler.math;

import java.util.Objects;

public class Vector2 {

    private float _xCoordinate;
    private float _yCoordinate;

    public Vector2(float xCoordinate,float yCoordinate){
        _xCoordinate = xCoordinate;
        _yCoordinate = yCoordinate;
    }
     
    public float getXCoordinate(){
        return _xCoordinate;
    }

    public float getYCoordinate(){
        return _yCoordinate;
    }
    public Vector2 add(Vector2 vectorToBeAdded){
        float newX = _xCoordinate + vectorToBeAdded._xCoordinate;
        float newY = _yCoordinate + vectorToBeAdded._yCoordinate;
        Vector2 result = new Vector2(newX,newY);
        return result; 
    }

    public Vector2 subtract(Vector2 vectorToBeSubtracted){
        float newX = _xCoordinate - vectorToBeSubtracted._xCoordinate;
        float newY = _yCoordinate - vectorToBeSubtracted._yCoordinate;
        Vector2 result = new Vector2(newX,newY);
        return result; 
    }

    public Vector2 scale(float factor){
        float newX = _xCoordinate * factor;
        float newY = _yCoordinate * factor;
        Vector2 result = new Vector2(newX,newY);
        return result; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xCoordinate, _yCoordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Vector2)) { return false; }
        Vector2 other = (Vector2)o;
        return other._xCoordinate == _xCoordinate & other._yCoordinate == _yCoordinate;
    }

    @Override
    public String toString() {
        return "" + _xCoordinate + "/" + _yCoordinate;
    }
    
}
