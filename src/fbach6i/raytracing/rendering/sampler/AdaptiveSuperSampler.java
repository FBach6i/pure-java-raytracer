package fbach6i.raytracing.rendering.sampler;

import java.util.ArrayList;

import carlvbn.raytracing.pixeldata.Color;
import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.Renderer;
import carlvbn.raytracing.rendering.Scene;
import fbach6i.raytracing.rendering.sampler.math.Quadrant;
import fbach6i.raytracing.rendering.sampler.math.Vector2;

public class AdaptiveSuperSampler extends MultiRaySampler { 
    
    private static float EPSILON = .01F; //3F;//0.9F;
    private Scene _scene;
    private int _recursionLimit;

    // analytic info member variables
    private int _pixelCount = 0;
    private RecursionCount _recursionCount = new RecursionCount();
    private int _maxRecursionDepth = 0;
    private int _numberOfPixelsCalculatedRecursively = 0;
    private int _overallSamplesTraced = 0;

    public AdaptiveSuperSampler(Scene scene, int recursionLimit) {
        _scene = scene;
        _recursionLimit = recursionLimit;
    }
    
    public void logAnalytics(float u, float v) {
        if (_recursionCount.getCount() != 0) {
            throw new Error("recursion count inconsistency!");
        }
        if (_recursionCount.getMaxRecursion() > 0) {
            _numberOfPixelsCalculatedRecursively++;
        }
        
        _maxRecursionDepth = Math.max(_maxRecursionDepth, _recursionCount.getMaxRecursion());
        if ((_pixelCount % 100_000 == 0)) {
            System.out.println("" + _pixelCount + " another 100k pixels rendered at (" + u + "/" + v + ") now, max recursion depth was: " + _maxRecursionDepth);
            if (_maxRecursionDepth > 0) {
                System.out.println("... number of pixels calculated recursively: " + _numberOfPixelsCalculatedRecursively); 
            }
            System.out.println("... number of samples traced so far: " + _overallSamplesTraced); 
            _maxRecursionDepth = 0;
            _numberOfPixelsCalculatedRecursively = 0;
        }
        _pixelCount++;
    }

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        Quadrant pixel = new Quadrant(new Vector2(u,v), _pixelBlockSize);
        PixelData pixelInfo = sample(pixel, _recursionLimit, _recursionCount);
        logAnalytics(u, v);
        _recursionCount.reset();
        return pixelInfo;
    }

    private PixelData sample(Quadrant q, int recursionLimit, RecursionCount recursionCount) {

        PixelData pixelInfoCenter = Renderer.computePixelInfo(_scene,q.getCenter().getXCoordinate(), q.getCenter().getYCoordinate());

        Vector2 topLeftCorner = q.getTopLeftCorner();
        Vector2 topRightCorner = q.getTopRightCorner();
        Vector2 bottomLeftCorner = q.getBottomLeftCorner();
        Vector2 bottomRightCorner = q.getBottomRightCorner();

        PixelData pixelInfoTopLeftCorner = Renderer.computePixelInfo(_scene, topLeftCorner.getXCoordinate(), topLeftCorner.getYCoordinate());
        PixelData pixelInfoTopRightCorner = Renderer.computePixelInfo(_scene, topRightCorner.getXCoordinate(), topRightCorner.getYCoordinate());
        PixelData pixelInfoBottomLeftCorner = Renderer.computePixelInfo(_scene, bottomLeftCorner.getXCoordinate(), bottomLeftCorner.getYCoordinate());
        PixelData pixelInfoBottomRightCorner = Renderer.computePixelInfo(_scene, bottomRightCorner.getXCoordinate(), bottomRightCorner.getYCoordinate());
    
        _overallSamplesTraced += 5;

        ArrayList<Color> rayColors = new ArrayList<Color>();

        if((recursionCount.getCount() < recursionLimit) && !(pixelInfoTopLeftCorner.getColor().isSimilarTo(pixelInfoCenter.getColor(), EPSILON))) {
            PixelData recursivePixelInfo = this.sample(q.getTopLeftSubquadrant(), recursionLimit, recursionCount.increase());
            recursionCount.decrease();
            rayColors.add(recursivePixelInfo.getColor());
        } else {
            rayColors.add(Color.average(pixelInfoTopLeftCorner.getColor(), pixelInfoCenter.getColor()));
        }
    
        if((recursionCount.getCount() < recursionLimit) && !(pixelInfoTopRightCorner.getColor().isSimilarTo(pixelInfoCenter.getColor(), EPSILON))) {
            PixelData recursivePixelInfo = this.sample(q.getTopRightSubquadrant(), recursionLimit, recursionCount.increase());
            recursionCount.decrease();
            rayColors.add(recursivePixelInfo.getColor());
        } else {
            rayColors.add(Color.average(pixelInfoTopRightCorner.getColor(), pixelInfoCenter.getColor()));
        }
    
        if((recursionCount.getCount() < recursionLimit) && !(pixelInfoBottomRightCorner.getColor().isSimilarTo(pixelInfoCenter.getColor(), EPSILON))) {
            PixelData recursivePixelInfo = this.sample(q.getBottomRightSubquadrant(), recursionLimit, recursionCount.increase());
            recursionCount.decrease();
            rayColors.add(recursivePixelInfo.getColor());
        } else {
            rayColors.add(Color.average(pixelInfoBottomRightCorner.getColor(), pixelInfoCenter.getColor()));
        }
    
        if((recursionCount.getCount() < recursionLimit) && !(pixelInfoBottomLeftCorner.getColor().isSimilarTo(pixelInfoCenter.getColor(), EPSILON))) {
            PixelData recursivePixelInfo = this.sample(q.getBottomLeftSubquadrant(), recursionLimit, recursionCount.increase());
            recursionCount.decrease();
            rayColors.add(recursivePixelInfo.getColor());
        } else {
            rayColors.add(Color.average(pixelInfoBottomLeftCorner.getColor(), pixelInfoCenter.getColor()));
        }

        return new PixelData(Color.average(rayColors),0,0);
    }
    
}
