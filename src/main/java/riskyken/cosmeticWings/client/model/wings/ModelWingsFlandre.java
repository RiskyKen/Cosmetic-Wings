package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.IWings;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWingsFlandre extends ModelWingBase {
    
    ModelRenderer leftWing;
    ModelRenderer rightWing;
    
    private final ResourceLocation[] wingsImage;

    public ModelWingsFlandre() {
        textureWidth = 64;
        textureHeight = 64;
        
        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-10F, 0F, -0.5F, 20, 31, 1);
        leftWing.setRotationPoint(0F, 0F, 0F);
        leftWing.setTextureSize(64, 64);
        
        rightWing = new ModelRenderer(this, 0, 32);
        rightWing.addBox(-10F, 0F, -0.5F, 20, 31, 1);
        rightWing.setRotationPoint(0F, 0F, 0F);
        rightWing.setTextureSize(64, 64);
        rightWing.mirror = true;

        wingsImage = new ResourceLocation[2];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/flandre-wings.png");
        wingsImage[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/flandre-wings-glow.png");
    }

    public void render(EntityPlayer player, int layer, IWings wings, WingsData wingData) {
        renderWingLayer(player, layer, wingData);
    }

    private void renderWingLayer(EntityPlayer player, int layerId, WingsData wingData) {
        if (layerId >= 0 & layerId < wingsImage.length) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[layerId]);
        }
        
        float angle = getWingAngle(player.capabilities.isFlying & player.isAirBorne, 30, 8000, 400, player.getEntityId());
        
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
