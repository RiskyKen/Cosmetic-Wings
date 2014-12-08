package riskyken.cosmeticWings.client.render;

import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;
/**
 * Helps turn Minecraft's lighting on and off.
 * Yip that's all!
 * @author RiskyKen
 * 
 */
public final class LightingHelper {
    
    private static float lightX;
    private static float lightY;
    
    public static void disableLighting() {
        lightX = OpenGlHelper.lastBrightnessX;
        lightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        GL11.glDisable(GL11.GL_LIGHTING);
    }
    
    public static void enableLighting() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightX, lightY);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
