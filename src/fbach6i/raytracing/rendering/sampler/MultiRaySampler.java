package fbach6i.raytracing.rendering.sampler;

import carlvbn.raytracing.pixeldata.PixelData;
import carlvbn.raytracing.rendering.PixelSampler;
import carlvbn.raytracing.rendering.Scene;

public abstract class MultiRaySampler implements PixelSampler {
    
    protected float _pixelBlockSize = 1; 

    @Override
    public void setPixelBlockSize(float blockSize) {
        _pixelBlockSize = blockSize;
    }

    @Override
    public abstract PixelData samplePixel(Scene scene, float u, float v);

}
