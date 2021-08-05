package fbach6i.raytracing.rendering.sampler;

import java.util.ArrayList;
import java.util.Random;

import carlvbn.raytracing.pixeldata.Color;
import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.Renderer;
import carlvbn.raytracing.rendering.Scene;
import fbach6i.raytracing.rendering.sampler.math.Quadrant;
import fbach6i.raytracing.rendering.sampler.math.Vector2;

public class AdaptiveStochasticSuperSampler extends MultiRaySampler {

    private static float EPSILON = .01F;
    private Scene _scene;
    private int _recursionLimit;
    private Random _random;
    private RecursionCount _recursionCount = new RecursionCount();

    public AdaptiveStochasticSuperSampler(Scene scene, int recursionLimit, Random random) {
        _scene = scene;
        _recursionLimit = recursionLimit;
        _random = random;
    }

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        Quadrant pixel = new Quadrant(new Vector2(u,v), _pixelBlockSize);
        PixelData pixelInfo = sample(pixel, _recursionLimit, _recursionCount);
        _recursionCount.reset();
        return pixelInfo;
    }

    private PixelData sample(Quadrant q, int recursionLimit, RecursionCount recursionCount) {

        PixelData pixelInfoCenter = Renderer.computePixelInfo(_scene,q.getCenter().getXCoordinate(), q.getCenter().getYCoordinate());

        Vector2 topLeftCornerStratified = q.getTopLeftCornerStratified(_random);
        Vector2 topRightCornerStratified = q.getTopRightCornerStratified(_random);
        Vector2 bottomLeftCornerStratified = q.getBottomLeftCornerStratified(_random);
        Vector2 bottomRightCornerStratified = q.getBottomRightCornerStratified(_random);

        PixelData pixelInfoTopLeftCorner = Renderer.computePixelInfo(_scene, topLeftCornerStratified.getXCoordinate(), topLeftCornerStratified.getYCoordinate());
        PixelData pixelInfoTopRightCorner = Renderer.computePixelInfo(_scene, topRightCornerStratified.getXCoordinate(), topRightCornerStratified.getYCoordinate());
        PixelData pixelInfoBottomLeftCorner = Renderer.computePixelInfo(_scene, bottomLeftCornerStratified.getXCoordinate(), bottomLeftCornerStratified.getYCoordinate());
        PixelData pixelInfoBottomRightCorner = Renderer.computePixelInfo(_scene, bottomRightCornerStratified.getXCoordinate(), bottomRightCornerStratified.getYCoordinate());
        
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
