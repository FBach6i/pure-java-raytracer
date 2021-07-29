package carlvbn.raytracing.rendering.sampler;

import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.PixelSampler;
import carlvbn.raytracing.rendering.Renderer;
import carlvbn.raytracing.rendering.Scene;

public class SingleRaySampler implements PixelSampler {

    @Override
    public void setPixelBlockSize(float blockSize) {
        ; // no significance for the single-ray sampler 
        
    }

    @Override
    public PixelData samplePixel(Scene scene, float u, float v) {
        return Renderer.computePixelInfo(scene, u, v);
    }
    
}
