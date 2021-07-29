package carlvbn.raytracing.rendering;

import carlvbn.raytracing.pixeldata.PixelData;

public interface PixelSampler {

    public void setPixelBlockSize(float blockSize);
    public PixelData samplePixel(Scene scene, float u, float v);
    
}
