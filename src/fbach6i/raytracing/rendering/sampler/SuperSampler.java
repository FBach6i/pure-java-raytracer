package fbach6i.raytracing.rendering.sampler;

import java.util.ArrayList;

import carlvbn.raytracing.pixeldata.Color;
import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.Renderer;
import carlvbn.raytracing.rendering.Scene;
import fbach6i.raytracing.rendering.sampler.math.Quadrant;
import fbach6i.raytracing.rendering.sampler.math.Vector2;

public class SuperSampler extends MultiRaySampler {

    private int _gridDimension;

    public SuperSampler(int gridDimension) {
        _gridDimension = gridDimension;
    }

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        Quadrant pixel = new Quadrant(new Vector2(u,v), _pixelBlockSize);
        ArrayList<Vector2> samplePoints = pixel.getUniformSamplePoints(_gridDimension);
        ArrayList<Color> rayColors = new ArrayList<Color>();


        for (Vector2 samplePoint: samplePoints) { 
            PixelData pixelData = Renderer.computePixelInfo(scene,samplePoint.getXCoordinate(), samplePoint.getYCoordinate());
            rayColors.add(pixelData.getColor());
        }

        return new PixelData(Color.average(rayColors),0,0);
    }
    
}
