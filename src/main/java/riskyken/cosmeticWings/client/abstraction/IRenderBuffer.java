package riskyken.cosmeticWings.client.abstraction;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRenderBuffer {

    public void draw();
    
    public void startDrawingQuads();
    
    public void startDrawing(int state);
    
    public void setBrightness(int brightness);
    
    public void setColourRGBA_F(float r, float g, float b, float a);
    
    public void setNormal(float x, float y, float z);
    
    public void setTextureUV(double u, double v);
    
    public void addVertex(double x, double y, double z);
    
    public void addVertexWithUV(double x, double y, double z, double u, double v);
}
