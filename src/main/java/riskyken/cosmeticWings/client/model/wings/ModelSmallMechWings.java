package riskyken.cosmeticWings.client.model.wings;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSmallMechWings extends ModelWingBase {
    
    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImages;
    
    public ModelSmallMechWings() {
        textureWidth = 64;
        textureHeight = 32;
        
        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-8.5F, 0F, -0.5F, 17, 12, 1);
        leftWing.setTextureSize(64, 32);
        
        rightWing = new ModelRenderer(this, 0, 0);
        rightWing.addBox(-8.5F, 0F, -0.5F, 17, 12, 1);
        rightWing.setTextureSize(64, 32);
        rightWing.mirror = true;
        
        wingsImages = new ResourceLocation[2];
        wingsImages[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/small-mech-wings.png");
        wingsImages[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/small-mech-wings-colour.png");
    }
    
    public void render(EntityPlayer player, int layer, WingsData wingData) {
        if (wingData.wingType.isNomalRender(layer)) {
            preRender(player, wingData);
        } else {
            postRender(player, wingData);
        }
    }
    
    private void preRender(EntityPlayer player, WingsData wingData) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImages[0]);
        RenderWing(player, player.capabilities.isFlying & player.isAirBorne, wingData);
        GL11.glPopMatrix();
    }
    
    private void postRender(EntityPlayer player, WingsData wingData) {
        GL11.glPushMatrix();
        Color c = new Color(wingData.colour);
        float red = (float) c.getRed() / 255;
        float green = (float) c.getGreen() / 255;
        float blue = (float) c.getBlue() / 255;
        GL11.glColor4f(red, green, blue, 0.5F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImages[1]);
        RenderWing(player, player.capabilities.isFlying & player.isAirBorne, wingData);
        GL11.glPopMatrix();
    }
    
    private void RenderWing(EntityPlayer player, boolean isFlying, WingsData wingData) {
        float angle = getWingAngle(isFlying, 40, 4000, 250, player.getEntityId());
        
        setRotation(leftWing, (float) Math.toRadians(angle + 20), (float) Math.toRadians(-4), (float) Math.toRadians(6));
        setRotation(rightWing, (float) Math.toRadians(-angle - 20), (float) Math.toRadians(4), (float) Math.toRadians(6));
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 4 * SCALE, 1.5F * SCALE);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, wingData.centreOffset * 3 * SCALE);
        GL11.glScalef(wingData.wingScale, wingData.wingScale, wingData.wingScale);
        leftWing.render(SCALE);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, -wingData.centreOffset * 3 * SCALE);
        GL11.glScalef(wingData.wingScale, wingData.wingScale, wingData.wingScale);
        rightWing.render(SCALE);
        GL11.glPopMatrix();
        
        GL11.glPopMatrix();
    }
}
