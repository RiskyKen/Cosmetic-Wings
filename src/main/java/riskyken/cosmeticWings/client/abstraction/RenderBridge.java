package riskyken.cosmeticWings.client.abstraction;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBridge implements IRenderBuffer {

    public static IRenderBuffer INSTANCE;
    
    Tessellator tessellator;
    
    public static void init() {
        INSTANCE = new RenderBridge();
    }
    
    public RenderBridge() {
        tessellator = Tessellator.getInstance();
    }
    
    @Override
    public void draw() {
        tessellator.draw();
    }

    @Override
    public void startDrawingQuads(VertexFormat vertexFormat) {
        tessellator.getBuffer().begin(GL11.GL_QUADS, vertexFormat);
    }

    @Override
    public void startDrawing(int glMode, VertexFormat vertexFormat) {
    	tessellator.getBuffer().begin(glMode, vertexFormat);
    }

    @Override
    public void setBrightness(int brightness) {
        //tessellator.getBuffer().putBrightness4(p_178962_1_, p_178962_2_, p_178962_3_, p_178962_4_);
    }
    
    @Override
    public void setColourRGBA_F(float r, float g, float b, float a) {
        tessellator.getBuffer().color(r, g, b, a);
    }

    @Override
    public void setNormal(float x, float y, float z) {
        tessellator.getBuffer().normal(x, y, z);
    }

    @Override
    public void setTextureUV(double u, double v) {
        tessellator.getBuffer().tex(u, v);
    }

    @Override
    public void addVertex(double x, double y, double z) {
        tessellator.getBuffer().pos(x, y, z);
    }

    @Override
    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        tessellator.getBuffer().pos(x, y, z);
        tessellator.getBuffer().tex(u, v);
    }
    
    @Override
    public void endVertex() {
        tessellator.getBuffer().endVertex();
    }
}
