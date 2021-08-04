package fbach6i.raytracing.rendering.sampler;

import java.util.ArrayList;
import java.util.Random;

import carlvbn.raytracing.pixeldata.Color;
import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.Renderer;
import carlvbn.raytracing.rendering.Scene;
import fbach6i.raytracing.rendering.sampler.math.Quadrant;
import fbach6i.raytracing.rendering.sampler.math.Vector2;

public class StochasticSuperSampler extends MultiRaySampler {

    private int _gridDimension;
    private Random _random;

    public StochasticSuperSampler(int gridDimension, Random random) {
        _gridDimension = gridDimension;
        _random = random;  
    }

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        Quadrant pixel = new Quadrant(new Vector2(u,v), _pixelBlockSize);
        ArrayList<Vector2> samplePoints = pixel.getStratifiedSamplePoints(_gridDimension, _random);
        ArrayList<Color> rayColors = new ArrayList<Color>();


        for (Vector2 samplePoint: samplePoints) { 
            PixelData pixelData = Renderer.computePixelInfo(scene,samplePoint.getXCoordinate(), samplePoint.getYCoordinate());
            rayColors.add(pixelData.getColor());
        }

        return new PixelData(Color.average(rayColors),0,0);
    }
    
}