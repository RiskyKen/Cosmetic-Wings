package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMetalWings extends ModelWingBase {

    ModelRenderer wing;
    private final ResourceLocation wingsImage;

    public ModelMetalWings() {
        textureWidth = 64;
        textureHeight = 32;
        
        wing = new ModelRenderer(this, 0, 0);
        wing.addBox(0F, 0F, 0.5F, 20, 29, 1);
        wing.setTextureSize(32, 32);
        wing.mirror = true;
        
        wingsImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/metal-wings.png");
    }

    public void render(EntityPlayer player, RenderPlayer renderer) {
        GL11.glColor3f(1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage);
        RenderWing(player, player.capabilities.isFlying & player.isAirBorne);
    }
    
    private void RenderWing(EntityPlayer player, boolean isFlying) {
        GL11.glPushMatrix();
        
        GL11.glTranslatef(0, -7F * SCALE, 1F * SCALE * 2);
        
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);
        
        float scale = 1.00F;
        GL11.glScalef(scale, scale, scale);
        
        float angle = getWingAngle(isFlying, 40, 4000, 250, player.getEntityId());
        
        GL11.glTranslatef(0F, -0.5F * SCALE, 0F);
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0F, 2F * SCALE);
        GL11.glRotatef(50F - angle, 1F, 0F, 0F);
        GL11.glTranslatef(0F, 0F, -1F * SCALE);
        wing.render(SCALE);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0F, -2F * SCALE);
        GL11.glRotatef(-50F + angle, 1F, 0F, 0F);
        GL11.glTranslatef(0F, 0F, -1F * SCALE);
        wing.render(SCALE);
        GL11.glTranslatef(0F, 0F, 1F * SCALE);
        GL11.glPopMatrix();
        
        GL11.glPopMatrix();
    }
}
