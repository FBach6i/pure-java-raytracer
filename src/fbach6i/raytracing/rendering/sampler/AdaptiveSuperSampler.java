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

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        Quadrant pixel = new Quadrant(new Vector2(u,v), _pixelBlockSize);
        PixelData pixelInfo = sample(pixel, _recursionLimit, _recursionCount);

        // **** analytic info ***
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
        _recursionCount.reset();
        // **** end analytic info ***

        return pixelInfo;
    }

    private PixelData sample(Quadrant q, int recursionLimit, RecursionCount recursionCount) {

        PixelData pixelInfoCenter = Renderer.computePixelInfo(_scene,q.getCenter().getXCoordinate(), q.getCenter().getYCoordinate());
        PixelData pixelInfoTopLeftCorner = Renderer.computePixelInfo(_scene, q.getTopLeftCorner().getXCoordinate(), q.getTopLeftCorner().getYCoordinate());
        PixelData pixelInfoTopRightCorner = Renderer.computePixelInfo(_scene, q.getTopRightCorner().getXCoordinate(), q.getTopRightCorner().getYCoordinate());
        PixelData pixelInfoBottomRightCorner = Renderer.computePixelInfo(_scene, q.getBottomRightCorner().getXCoordinate(), q.getBottomRightCorner().getYCoordinate());
        PixelData pixelInfoBottomLeftCorner = Renderer.computePixelInfo(_scene, q.getBottomLeftCorner().getXCoordinate(), q.getBottomLeftCorner().getYCoordinate());
    
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

    private class RecursionCount {

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
    
}
